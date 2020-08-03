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
                    %>
                    <div class="row">
                        <div class="col">
                            <h4>Class:
                                <jsp:getProperty name="userInfo" property="userClass"/>
                            </h4>
                        </div>
                        <div class="col-auto" style="margin-bottom: 5px;">
                            <div class="dropdown mr-1">
                                <button type="button" class="btn btn-info dropdown-toggle" id="dropdownMenuOffset" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-offset="10,20">
                                    Export
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuOffset">
                                    <a class="dropdown-item" href="#" onclick="doExportExcel();">Excel</a>
                                    <a class="dropdown-item" href="#" onclick="doExportPDF();">PDF</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <table class="table" id="attendance">
                        <thead>
                            <tr>
                                <th scope="col">Student ID</th>
                                <th scope="col">Student Name</th>
                                <th scope="col">Attendance Rate</th>
                            </tr>
                        </thead>
                        <tbody>

                            <%
                                double[] rateArray = new double[students.size()];
                                String[] nameArray = new String[students.size()];
                                String[] colorArray = new String[students.size()];
                                for (int i = 0; i < students.size(); i++) {
                                    Student s = students.get(i);
                                    String tempRate = Double.toString(s.getAttendanceRate() * 100) + "%";
                                    if (s.getAttendanceRate() * 100 < 60) {
                                        tempRate = "<font color=\"red\"><b>" + Double.toString(s.getAttendanceRate() * 100) + "%</b></font>";
                                        colorArray[i] = "rgb(255, 60, 60)";
                                    } else {
                                        colorArray[i] = "rgb(201, 203, 207)";
                                    }

                                    rateArray[i] = s.getAttendanceRate() * 100;
                                    nameArray[i] = s.getStudentName();

                                    out.println("<tr><td>" + s.getStudentId() + "</td>");
                                    out.println("<td>" + s.getStudentName() + "</td>");
                                    out.println("<td>" + tempRate + "</td></tr>");
                                }
                            %>

                            <%
                                String result = "[";
                                for (int i = 0; i < rateArray.length; i++) {
                                    result += "\"" + rateArray[i] + "\"";
                                    if (i < rateArray.length - 1) {
                                        result += ", ";
                                    }
                                }
                                result += "]";
                                //////////////////////////////////////////////////
                                String namePrint = "[";
                                for (int i = 0; i < nameArray.length; i++) {
                                    namePrint += "\"" + nameArray[i] + "\"";
                                    if (i < nameArray.length - 1) {
                                        namePrint += ", ";
                                    }
                                }
                                namePrint += "]";
                                //////////////////////////////////////////////////
                                String colorPrint = "[";
                                for (int i = 0; i < colorArray.length; i++) {
                                    colorPrint += "\"" + colorArray[i] + "\"";
                                    if (i < colorArray.length - 1) {
                                        colorPrint += ", ";
                                    }
                                }
                                colorPrint += "]";
                            %>

                        </tbody>
                    </table> 


                </div>
            </div>
        </div>
        <div class="align-items-center d-flex justify-content-center">
            <div class="card" style="margin:0px 20px 20px 20px; width:600px;">
                <div class="card-body">
                    <canvas id="horizontalBar"></canvas>
                </div>
            </div>
        </div>



        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


        <!-- export to excel / pdf -->
        <script type="text/javascript" src="js/FileSaver/FileSaver.min.js"></script>
        <script type="text/javascript" src="js/js-xlsx/xlsx.core.min.js"></script>
        <script type="text/javascript" src="js/jsPDF/jspdf.min.js"></script>
        <script type="text/javascript" src="js/jsPDF-AutoTable/jspdf.plugin.autotable.js"></script>
        <script type="text/javascript" src="js/tableExport.min.js"></script>

        <script>
                                        // -- export -- //
                                        function doExportExcel() {
                                            $('#attendance').tableExport({
                                                type: 'excel'
                                            });
                                        }
                                        function doExportPDF() {
                                            $('#attendance').tableExport({type: 'pdf',
                                                jspdf: {orientation: 'l',
                                                    format: 'a4',
                                                    margins: {left: 10, right: 10, top: 20, bottom: 20},
                                                    autotable: {styles: {fillColor: 'inherit',
                                                            textColor: 'inherit'},
                                                        tableWidth: 'auto'}
                                                }
                                            });
                                        }
        </script>


        <!-- chart -->
        <script src="js/mdb.min.js"></script>

        <script>
                                        var ctx = document.getElementById("horizontalBar").getContext('2d');
                                        var myChart = new Chart(document.getElementById("horizontalBar"), {
                                            "type": "horizontalBar",
                                            "data": {
                                                "labels": <%=namePrint%>,
                                                "datasets": [{
                                                        "label": "Percentage of all students' attendance",
                                                        "data": <%=result%>,
                                                        "fill": false,
                                                        "backgroundColor": <%=colorPrint%>,
                                                        "borderColor": <%=namePrint%>,
                                                        "borderWidth": 1
                                                    }]
                                            },
                                            "options": {
                                                "scales": {
                                                    "xAxes": [{
                                                            "ticks": {
                                                                "beginAtZero": true
                                                            }
                                                        }]
                                                }
                                            }
                                        }
                                        );



        </script>
    </body>
</html>
