<%-- 
    Document   : MainMenu.jsp
    Created on : 02.04.2019, 09:35:34
    Author     : Alex Mauko2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MainMenu</title>
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
        <form action="QuizServlet" method="POST">
            <div align="center">
                <input type="submit" name="startGame" value="Spiel starten" />
            </div>
        </form>
    </body>
</html>
