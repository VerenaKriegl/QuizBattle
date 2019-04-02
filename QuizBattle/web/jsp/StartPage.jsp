<%-- 
    Document   : StartPage
    Created on : 30.03.2019, 09:15:29
    Author     : kriegl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <title>Start Page</title>
    </head>
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
            width: 350px;
            height: 80px;
        }
        input[type=submit]:hover {
            background-color: white;
            color: black;
        }
    </style>
    <body>
        <form action="QuizServlet" method="POST">
            <div vertical-align="middle">
                <div align="center">
                    <img src="images/Logo.PNG" alt="LOGO">
                </div>
                <table align="center">
                    <tr>
                        <td><input type="submit" name="registration" value="Registrieren"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" name="login" value="LogIn"/></td>
                    </tr>
                </table>
            </div>
        </form>
    </body>
</html>
