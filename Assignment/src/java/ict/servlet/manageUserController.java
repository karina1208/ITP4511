/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.UserInfo;
import ict.db.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yusin
 */
@WebServlet(name = "manageUserController", urlPatterns = {"/manUser"})

public class manageUserController extends HttpServlet {
    
    private UserDB db;    
    
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB(dbUrl, dbUser, dbPassword);
        System.out.println("Init Firing: ");

   }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equalsIgnoreCase(action)) {
            db.addRecord(request.getParameter("userID"),request.getParameter("userPW"), request.getParameter("userName"), request.getParameter("userRole"), request.getParameter("userClass"));          
            response.sendRedirect("CreateAccount.jsp");
        }else if ("add_s".equalsIgnoreCase(action)) {
            db.addRecord(request.getParameter("userID"),request.getParameter("userPW"), request.getParameter("userName"), request.getParameter("userRole"), request.getParameter("userClass"));          
            response.sendRedirect("getClass?action=list");
        }else if ("list".equalsIgnoreCase(action)) {            
            ArrayList<UserInfo> Uinfo  = db.showClass();
            request.setAttribute("Uinfo", Uinfo);
                
            RequestDispatcher rd ;
            rd = getServletContext().getRequestDispatcher("/ModifyAccount_main.jsp");
            rd.forward(request, response);           
        }else if ("edit".equalsIgnoreCase(action)) {
            
            String id = request.getParameter("id");
            if (id != null) {
               UserInfo userinfo = db.queryInfoByID(id);
               request.setAttribute("u", userinfo);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/ModifyAccount.jsp");
                rd.forward(request, response);
            }
        }else if ("edit_user".equalsIgnoreCase(action)) {

            UserInfo userinfo = new UserInfo(request.getParameter("userID"),request.getParameter("userPW"), request.getParameter("userName"), request.getParameter("userRole"), request.getParameter("userClass"));
            boolean good = db.editRecord(userinfo);
            
            if (good) {
                response.sendRedirect("manUser?action=list");
            } else {
                PrintWriter out = response.getWriter();
                out.println(request.getParameter("id"));
            }            
          
        }else if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                db.delRecord(id);
                response.sendRedirect("manUser?action=list");
            }
        } 
    }
    
}
