<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : QuestionView
    Created on : 02.04.2019, 09:36:32
    Author     : Alex Mauko2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question</title>
        <style>
            body {
                background-color: #f4511e; /* Orange */
                color: #ffffff;
            }
            input[type=submit] {
                background-color: darkgray;
                border: none;
                color: white;
                padding: 16px 32px;
                text-decoration: none;
                margin: 4px 2px;
                cursor: pointer;
                -webkit-transition-duration: 0.4s;
                width: 600px;
                height: 50px;
            }
            input[type=submit]:hover {
                background-color: white;
                color: black;
            }
        </style>
    </head>
    <body>
        <div align="center">
            <p></p>
        </div>
        <table align="center">
            <c:forEach var="question" items="">
            </c:forEach>
            <tr>
                <td><input type="submit" name="answer" value="" /></td>
                <td><input type="submit" name="answer" value="" /></td>
            </tr>
            <tr>
                <td><input type="submit" name="answer" value="" /></td>
                <td><input type="submit" name="answer" value="" /></td>
            </tr>
        </table>
    </body>
</html>
