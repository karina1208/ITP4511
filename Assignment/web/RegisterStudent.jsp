<%-- 
    Document   : newjsp
    Created on : 2019年12月23日, 下午03:24:14
    Author     : yusin
--%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.Classss"%>
<%@page import ="ict.db.ClassDB"%>

<!--import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;-->




<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
         <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>CreateAccount</title>
    </head>
   <body>
         <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Attendance System</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                   
                     <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Report
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="adminReport?action=allRate">Student Attendance Rate</a>
                            <a class="dropdown-item" href="adminReport?action=lowRate">Low Attendance Rate</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Account Management
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="CreateAccount.jsp">Create Account</a>
                            <a class="dropdown-item" href="manUser?action=list">Modify Account</a>
                            <a class="dropdown-item" href="getClass?action=list">Register student</a>
                        </div>
                    </li>
                </ul>
                Welcome back, <jsp:getProperty name="userInfo" property="username"/>&nbsp;
                
                <form method="post" action="main">
                    <input type="hidden" name="action"
                           value="logout">
                    <input type="submit" value="Logout" class="btn btn-outline-primary my-2 my-sm-0" name="logoutButton">
                </form>


            </div>
        </nav>
                <br />
            <div class="container" >  
                <div class="row" > 
                    <div class="col-md-4 order-md-2 mb-4">
                        <form method="post" action="manUser?action=add_s"  >
                            <p><h2 class="mb-3">Add Student Account</h2></p>
                            <p><label>User ID :&nbsp;</label><input type="text" class="form-control" name="userID"/><br /></p>
                            <p><label>password :&nbsp; </label><input type="password" class="form-control" name="userPW"/><br /></p>
                            <p><label>User Name :&nbsp;</label><input type="text" class="form-control mr-sm-2"  name="userName"/><br /></p>
                            <input type="hidden" class="form-control mr-sm-2"  name="userRole" value="S"/>
                            <p><label>Class ID :&nbsp;</label><input type="text" class="form-control mr-sm-2"  name="userClass"/><br /></p>
                         
                            <input type="submit" value="Add " class="btn btn-outline-primary my-2 my-sm-0">
                        </form>
                    </div>
                    <div class="col-md-1 order-md-2 mb-4"></div>
                    
                    <div class="col-md-4 order-md-2 mb-4"><br />
                          <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Class ID</th>
                                    <th scope="col">Class name</th>
                                </tr>
                            </thead>
                            </thead>
                            <tbody>
                      
                    <%
                    
                        ArrayList<Classss> Class  = (ArrayList<Classss> )request.getAttribute("Class");                      

                        
                        for (int i = 0; i < Class.size(); i++) {
                            Classss c = Class.get(i);                        
                            out.println("<tr>");
                            out.println("<td>" + c.getClassID() + "</td>");
                            out.println("<td>" + c.getClassName() + "</td>");                     
                            out.println("</tr>");
                        }  

                      %>
                    </div>
                </div>
            </div>
                
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    </body>
</html>
