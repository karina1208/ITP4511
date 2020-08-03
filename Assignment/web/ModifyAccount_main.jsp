<%-- 
    Document   : ModifyAccount_main
    Created on : 2019?12?26?, ??05:22:36
    Author     : yusin
--%>

<%@page import="ict.bean.UserInfo"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
         <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>ModifyAccount</title>
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
                       
                            
                                           
                   
<!--                        <p><div ><h3 class="mb-3">Search Account</h3></div></p>
                        <form id="search_form" class="form-inline my-2 my-lg-0">                         
                            <input class="form-control mr-sm-3" type="search" placeholder="Search" aria-label="Search">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Select</button>                      
                        </form>-->
                         <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">user ID</th>
                                    <th scope="col">user Password</th>
                                    <th scope="col">user Name</th>
                                    <th scope="col">user Role</th>
                                    <th scope="col">user Class</th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            </thead>
                            <tbody>
                                <%
                    
                        ArrayList<UserInfo> Uinfo  = (ArrayList<UserInfo> )request.getAttribute("Uinfo");                      

                        
                        for (int i = 0; i < Uinfo.size(); i++) {
                            UserInfo u = Uinfo.get(i);                        
                            out.println("<tr>");
                            out.println("<td>" + u.getUserID() + "</td>");
                            out.println("<td>" + u.getPassword() + "</td>");
                            out.println("<td>" + u.getUsername() + "</td>");  
                            out.println("<td>" + u.getUserRole() + "</td>");
                            out.println("<td>" + u.getUserClass() + "</td>");
                            out.println("<td><a href=\"manUser?action=edit&id="+u.getUserID()+"\">Edit</a></td>");
                            out.println("<td><a href=\"manUser?action=delete&id="+u.getUserID()+"\">Delete</a></td>");
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
