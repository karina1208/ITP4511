<%-- 
    Document   : 1_adminMain
    Created on : 2019/12/18, 下午 05:38:21
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <title>Teacher</title>
        <%@page import="ict.bean.*, java.util.ArrayList" %>
    </head>
    <body>
         <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Attendance System</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Class Management
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="classManage?action=setSchedule">Set Class schedule</a>
                            <a class="dropdown-item" href="SetUpClass.jsp">Setup class data</a>
                        </div>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Attendance
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="getStudents?action=list">Record Attendance</a>
                            <a class="dropdown-item" href="getStudents?action=editAttendancePage">View/edit Attendance History</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Report
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="teacherReport?action=allRate">Student Attendance Rate</a>
                            <a class="dropdown-item" href="teacherReport?action=lowRate">Low Attendance Rate</a>
                            <a class="dropdown-item" href="classManage?action=showSchedule">Class Schedule Report</a>
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

        <div class="align-items-center d-flex justify-content-center">
            <div class="card " style="margin:20px; width:600px;">
                <div class="card-body ">
                    <%
                        ArrayList<Student> students
                                = (ArrayList<Student>) request.getAttribute("students");
                        String className = students.get(0).getClassId();
                        String date = students.get(0).getDate();
                    %>
                    <h4>Class:
                        <%= className%>
                    </h4>

                    <form method="post" action="getStudents">
                        <input type="hidden" name="action" value="editDayAttendance">
                        <input type="hidden" name="date" value="<%= date%>">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Status</th>
                                    <th scope="col">Student ID</th>
                                    <th scope="col">Student Name</th>

                                </tr>
                            </thead>
                            <tbody>

                                <%

                                    for (int i = 0; i < students.size(); i++) {
                                        Student s = students.get(i);
                                        String isCheck = "";
                                        if (s.getStatus() == 0) {
                                            isCheck = "";
                                        } else if (s.getStatus() == 1) {
                                            isCheck = "checked";
                                        }
                                        out.println("<tr><td><input type=\"checkbox\" name=\"status\" value=\"" + s.getStudentId() + "\"" + isCheck + "/></td>");
                                        out.println("<td>" + s.getStudentId() + "</td>");
                                        out.println("<td>" + s.getStudentName() + "</td></tr>");
                                    }
                                %>

                            </tbody>
                        </table> 
                        <input type="submit" value="Submit" class="btn btn-primary my-2 my-sm-0" name="dayAttendButton"> <a class="btn btn-secondary my-2 my-sm-0" href="getStudents?action=editAttendancePage" role="button">Back</a>
                    </form>


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
