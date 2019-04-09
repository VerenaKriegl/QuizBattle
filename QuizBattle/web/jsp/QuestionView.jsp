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
        <script src="../js/FormatQuestions.js"></script>
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
                width: 400px;
                height: 50px;
            }
            input[type=submit]:hover {
                background-color: white;
                color: black;
            }

            .styleDiv{
                width: 900px;
                height: 800px;

                position:absolute;
                left:0; right:0;
                top:0; bottom:0;
                margin:auto;

                max-width:100%;
                max-height:100%;
                overflow:auto;
            }

            .timePanel{
                width: 100px;
                height: 20px;
                background: black;
                position: relative;
                transition-duration: 1s;
            }
        </style>
    </head>
    <body>
        <div class="styleDiv">
            <h1 align="center">Aller guten Dinge sind ... ?</h1>

            <table align="center">
                <c:forEach var="question" items="">
                </c:forEach>
                <tr>
                    <td><input type="submit" name="answer" value="zwei" /></td>
                    <td><input type="submit" name="answer" value="drei" /></td>
                </tr>
                <tr>
                    <td><input type="submit" name="answer" value="vier" /></td>
                    <td><input type="submit" name="answer" value="dreißig" /></td>
                </tr>
                <div id="timePanel" class="timePanel" onClick="setTimePanel()">

                </div>
            </table>
        </div>
    </body>
</html>
