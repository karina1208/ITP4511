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
@WebServlet(name = "classAttendanceController", urlPatterns = {"/getStudents"})
public class classAttendanceController extends HttpServlet {

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

        if ("list".equalsIgnoreCase(action)) {
            LocalDate date = java.time.LocalDate.now();
            String dateString = date.toString();

            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");
            //bean.getUserClass()

            //check if the day have already recorded attendacne, if yes, redirect to error page.
            ArrayList<Student> studentsTest = studentDb.getStudentRecordByClassAndDate(bean.getUserClass(), dateString);
            if (studentsTest.isEmpty()) {
                ArrayList<Student> students = studentDb.getStudentByClass(bean.getUserClass());
                //String test = studentDb.getStudentByClassColumnNo("IT114105");

                //request.setAttribute("count", test);
                request.setAttribute("students", students);
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/2_teacherRecordAttendance.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("error_attendanceAlreadyInsert.jsp");
            }

        } else if ("dayAttendance".equals(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");

            ArrayList<Student> students = studentDb.getStudentByClass(bean.getUserClass());

            String status[] = request.getParameterValues("status");

            System.out.println(status.length);
            boolean haha = false;

            LocalDate date = java.time.LocalDate.now();
            String dateString = date.toString();

            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                //寫個method check每一項入左野未, 如果入過就轉errorpage;

                // first, insert all record into 0 (absent)
                studentDb.addDayAttendRecord(s.getStudentId(), s.getClassId(), 0, dateString); //0==absent
                for (int j = 0; j < status.length; j++) {
                    if (status[j].equals(s.getStudentId())) {
                        //then, scan for the check box value, and change the record into 1.
                        haha = studentDb.editAttendanceByDay(s.getStudentId(), s.getClassId(), 1, dateString);
                    }
                }
            }

            if (haha) {
                response.sendRedirect("Success.jsp");
            } else {
                response.sendRedirect("Error.jsp");
            }

        } else if ("editAttendancePage".equalsIgnoreCase(action)) {

            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");

            ArrayList<Student> students = studentDb.getStudentByClass(bean.getUserClass());
            //String test = studentDb.getStudentByClassColumnNo("IT114105");

            //request.setAttribute("count", test);
            request.setAttribute("students", students);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/2_teacherEditRecordAttendance.jsp");
            rd.forward(request, response);

        } else if ("selectDay".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");
            String date = request.getParameter("date");
            ArrayList<Student> students = studentDb.getStudentRecordByClassAndDate(bean.getUserClass(), date);
            //String test = studentDb.getStudentByClassColumnNo("IT114105");
            //request.setAttribute("count", test);

            //if students==empty, no data was inserted, call add day attendance class...
            if (students.isEmpty()) {
                ArrayList<Student> tempStudents = studentDb.getStudentByClass(bean.getUserClass());
                for (int i = 0; i < tempStudents.size(); i++) {
                    Student s = tempStudents.get(i);
                    // first, insert all record into 0 (absent)
                    studentDb.addDayAttendRecord(s.getStudentId(), s.getClassId(), 0, date); //0==absent
                }
                students = studentDb.getStudentRecordByClassAndDate(bean.getUserClass(), date);
            }
            request.setAttribute("students", students);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/2_teacherEditRecordAttendanceShowDate.jsp");
            rd.forward(request, response);

        } else if ("editDayAttendance".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = (UserInfo) session.getAttribute("userInfo");
            ArrayList<Student> students = studentDb.getStudentByClass(bean.getUserClass());

            String status[] = request.getParameterValues("status");
            String date = request.getParameter("date");
            System.out.println(status.length);
            boolean haha = false;

            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);

                haha = studentDb.editAttendanceByDay(s.getStudentId(), s.getClassId(), 0, date);
                for (int j = 0; j < status.length; j++) {
                    if (status[j].equals(s.getStudentId())) {
                        //then, scan for the check box value, and change the record into 1.
                        haha = studentDb.editAttendanceByDay(s.getStudentId(), s.getClassId(), 1, date);
                    }
                }
            }

            if (haha) {
                response.sendRedirect("Success.jsp");
            } else {
                response.sendRedirect("Error.jsp");
            }

        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }
}
