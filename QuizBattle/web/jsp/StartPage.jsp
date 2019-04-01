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
        .alignButton {
            width: 900px;
            height: 300px;
            left: 50%;
            margin-top: 100px;
            margin-left: -300px;
            position: relative;
        }
    </style>
    <body>
        <img style="left: 50%; position: relative; margin-left: -150px; margin-top: 50px;" src="../images/Logo.PNG">
        <div class="alignButton">
            <button style="border: 2px solid black; font-size: 100px; width:70%;margin-bottom: 50px;" type="button" name="registration" class="btn btn-outline-dark">Registrieren</button>
            <button style="border: 2px solid black; font-size: 100px; width:70%;" type="button" name="login" class="btn btn-outline-dark">Login</button>
        </div>
    </body>
</html>
