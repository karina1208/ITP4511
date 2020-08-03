/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.Student;
import ict.bean.UserInfo;
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
@WebServlet(name = "teacherReportController", urlPatterns = {"/teacherReport"})
public class teacherReportController extends HttpServlet {
    
     private StudentDB studentDb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        studentDb = new StudentDB(dbUrl, dbUser, dbPassword);

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

        if ("allRate".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");
            ArrayList<Student> students = studentDb.getAllStudentAttendanceRate(bean.getUserClass());
            //String test = studentDb.getStudentByClassColumnNo("IT114105");

            //request.setAttribute("count", test);
            request.setAttribute("students", students);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/3_teacherAllAttendanceRate.jsp");
            rd.forward(request, response);
        } else if ("lowRate".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");
            ArrayList<Student> students = studentDb.getAllStudentAttendanceRate(bean.getUserClass());
            //String test = studentDb.getStudentByClassColumnNo("IT114105");

            //request.setAttribute("count", test);
            request.setAttribute("students", students);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/3_teacherLowAttendanceRate.jsp");
            rd.forward(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }

}
