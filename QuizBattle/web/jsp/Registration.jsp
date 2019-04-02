<%-- 
    Document   : Registration
    Created on : 01.04.2019, 08:04:47
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
        <title>Registration</title>
        <style>
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
    </head>
    <body>
        <form action="QuizServlet" method="GET">
            <div>
                <table align="center">
                        <tr>
                            <td>Userame: </td>
                            <td><input type="text" name="username" value="" /></td>
                        </tr>
                        <tr>
                            <td>Mail: </td>
                            <td><input type="text" name="mail" value="" /></td>
                        </tr>
                        <tr>
                            <td>Passwort: </td>
                            <td><input type="text" name="pass" value="" /></td>
                        </tr>
                        <tr>
                            <td>Passwort bestätigen: </td>
                            <td><input type="text" name="confirmPass" value="" /></td>
                        </tr>
                        <tr>
                            <td>Geburtsdatum: </td>
                            <td><input type="date" name="dateOfBirth" value="" /></td>
                        </tr>
                        <tr>
                            <td><input type="submit" name="dateOfBirth" value="Fertig" /></td>
                        </tr>
                </table>
            </div>
        </form>
    </body>
</html>
