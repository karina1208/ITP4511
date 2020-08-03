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
import ict.db.StudentSideDB;
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
@WebServlet(name = "StuSideController", urlPatterns = {"/studentSide"})
public class StuSideController extends HttpServlet {

    private StudentSideDB studentSideDb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        studentSideDb = new StudentSideDB(dbUrl, dbUser, dbPassword);

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

        if ("showSchedule".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");

            ArrayList<Schedule> schedule = studentSideDb.getSchedule(bean.getUserClass());
            ArrayList<Subject> subjects = studentSideDb.getSubjectList();

            //request.setAttribute("count", test);
            request.setAttribute("schedule", schedule);
            request.setAttribute("subjects", subjects);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/5_stuShowSchedule.jsp");
            rd.forward(request, response);
        } else if ("showAtt".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");
            ArrayList<Student> student = studentSideDb.getOneStudentAttendanceRate(bean.getUserClass(), bean.getUserID());

            request.setAttribute("student", student);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/5_stuAttendanceRate.jsp");
            rd.forward(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }

}
