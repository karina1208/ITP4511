/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.Classss;
import ict.db.ClassDB;
import java.io.IOException;
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
@WebServlet(name = "ClassController", urlPatterns = {"/getClass"})
public class ClassController extends HttpServlet {
    
    private ClassDB ClassDb;
    
     public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        ClassDb = new ClassDB(dbUrl, dbUser, dbPassword);
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
                ArrayList<Classss> Class  = ClassDb.showClass();
                request.setAttribute("Class", Class);
                
                RequestDispatcher rd ;
                rd = getServletContext().getRequestDispatcher("/RegisterStudent.jsp");
                rd.forward(request, response);
          }else if("add".equalsIgnoreCase(action)) {
            ClassDb.addRecord(request.getParameter("classID"),request.getParameter("className"), Integer.parseInt(request.getParameter("schoolday")));          
            response.sendRedirect("SetUpClass.jsp");
        }
     
     }
    
}
