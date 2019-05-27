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
            width: 600px;
            height: 200px;
        }
        input[type=submit]:hover {
            background-color: white;
            color: black;
        }
        .divCenter {
            width: 800px;
            height: 800px;

            position:absolute;
            left:0; right:0;
            top:0; bottom:0;
            margin:auto;

            max-width:100%;
            max-height:100%;
            overflow:auto;
        }
    </style>
    <body>
        <form action="QuizServlet" method="GET">
            <div class="divCenter">
                <img style="display:block; margin:auto; margin-bottom: 20px;" src="images/Logo.PNG" alt="LOGO">
                <table align="center">
                    <tr>
                        <td><input style="margin-bottom: 20px; font-size: 16px;" type="submit" name="registration" value="Registrieren"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" name="login" value="LogIn"/></td>
                    </tr>
                </table>
            </div>
        </form>
    </body>
</html>
