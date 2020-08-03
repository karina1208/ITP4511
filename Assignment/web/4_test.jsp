<%-- 
    Document   : 4_test
    Created on : 2019/12/27, 下午 10:39:12
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            String[] items = (String[]) request.getAttribute("items");
            for (int i = 0; i < items.length; i++) {
                out.println(items[i]);
            }
        %>
    </body>
</html>
