/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.UserInfo;
import ict.db.UserDB;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "LoginController", urlPatterns = {"/main"})
public class LoginController extends HttpServlet {

    private UserDB db;
    
    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB(dbUrl, dbUser, dbPassword);
        System.out.println("Init Firing: ");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (!isAuthenticated(req) && !("authenticate".equals(action))) {
            doLogin(req, resp);
            return;
        }
        if ("authenticate".equals(action)) {
            doAuthenticate(req, resp);
        } else if ("logout".equals(action)) {
            doLogout(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    private void doAuthenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String targetURL = "loginError.jsp";
        if (db.isValidUser(username, password)) {
            HttpSession session = req.getSession(true);
            UserInfo bean = new UserInfo();
            bean.setUsername(username);
            bean.setUserRole(db.checkUserRole(username));
            bean.setUserClass(db.checkUserClass(username));
            bean.setUserID(username);
            session.setAttribute("userInfo", bean);
            if (bean.getUserRole().equals("S")) {
                targetURL = "1_stuMain.jsp";
            } else if (bean.getUserRole().equals("I")) {
                targetURL = "1_adminMain.jsp";
            } else if (bean.getUserRole().equals("T")) {
                targetURL = "1_teacherMain.jsp";
            } 
        } else {
            targetURL = "loginError.jsp";
        }
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(req, resp);
    }

    private boolean isAuthenticated(HttpServletRequest req) {
        boolean result = false;
        HttpSession session = req.getSession();
        //get the Userinfo from session
        if (session.getAttribute("userInfo") != null) {
            result = true;
        }
        return result;
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String targetURL = "login.jsp";
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(req, resp);
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            //remove the attr from session
            session.removeAttribute("userInfo");
            // ivalidate the session
            session.invalidate();
        }
        doLogin(req, resp);
    }

    
}
