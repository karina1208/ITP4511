/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.Schedule;
import ict.bean.Student;
import ict.bean.Subject;
import ict.bean.UserInfo;
import ict.db.ClassssDB;
import ict.db.StudentDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "teacherClassController", urlPatterns = {"/classManage"})
public class teacherClassController extends HttpServlet {

    private ClassssDB classssDb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        classssDb = new ClassssDB(dbUrl, dbUser, dbPassword);

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

        if ("setSchedule".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");

//            for (int i = 1; i <= 5; i++) {
//                int time = 830;
//                for (int j = 8; j <= 16; j++) {
//                    classssDb.insertEmptySchedule(bean.getUserClass(), i, time);
//                    time += 100;
//                }
//            }
            ArrayList<Schedule> schedule = classssDb.getSchedule(bean.getUserClass());
            ArrayList<Subject> subjects = classssDb.getSubjectList();

            //request.setAttribute("count", test);
            request.setAttribute("schedule", schedule);
            request.setAttribute("subjects", subjects);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/4_teacherSchedule.jsp");
            rd.forward(request, response);
        } else if ("showSchedule".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");

            ArrayList<Schedule> schedule = classssDb.getSchedule(bean.getUserClass());
            ArrayList<Subject> subjects = classssDb.getSubjectList();

            //request.setAttribute("count", test);
            request.setAttribute("schedule", schedule);
            request.setAttribute("subjects", subjects);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/4_teacherShowSchedule.jsp");
            rd.forward(request, response);
        } else if ("editSchedule".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");
            String itemString = "";
            int[] items = new int[45];
            for (int i = 0; i < items.length; i++) {
                itemString = "item" + i;
                items[i] = Integer.parseInt(request.getParameter(itemString));
            }

            int loop = 0;
            boolean isSuccess = false;
            for (int i = 1; i <= 5; i++) {
                int time = 830;
                for (int j = 8; j <= 16; j++) {
                    isSuccess = classssDb.editSchedule(bean.getUserClass(), items[loop], i, time);
                    time += 100;
                    loop++;
                }
            }

            if (isSuccess) {
                ArrayList<Schedule> schedule = classssDb.getSchedule(bean.getUserClass());
                ArrayList<Subject> subjects = classssDb.getSubjectList();

                //request.setAttribute("count", test);
                request.setAttribute("schedule", schedule);
                request.setAttribute("subjects", subjects);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/4_teacherShowSchedule.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("Error.jsp");
            }

        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }

}
