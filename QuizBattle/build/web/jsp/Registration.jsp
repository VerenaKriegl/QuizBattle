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
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <title>Registration</title>
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
        input[type=text], input[type=password], input[type=date] {
            background-color: white;
            color: black;
            width: 100%;
        }
        td {
            border-bottom: 1px solid #ddd;
        }
        table {
            top: 50%;
            bottom: 50%;
            height: 500px;
            width: 600px;
        }
        .divCenter {
            width: 100%;
            height: 800px;

            position:absolute;
            left:0; right:0;
            top:0; bottom:0;
            margin:auto;

            max-width:100%;
            max-height:100%;
            overflow:auto;
        }
        .w3-animate-left{
           animation:animateleft 2s;
        }
        .w3-animate-right{
           animation:animateright 2s;
        }
        .w3-animate-top{
           animation:animatetop 2s;
        }
   
        </style>
        <script>
            function showErrorMessage() {
                alert("Username oder EMail schon vorhanden!");
            }
        </script>
    </head>
    <body> 
        <form action="QuizServlet" method="POST">
            <div class="divCenter">
                <h1 class="w3-animate-bottom" style="text-align: center;">Registrieren</h1>
                <table style = "margin-top: 40px" align="center" style="vertical-align: middle;">
                    <tr>
                        <td class="w3-animate-left">Userame: </td>
                        <td class="w3-animate-left"><input type="text" name="username" value="" required/></td>
                    </tr>
                    <tr>
                        <td class="w3-animate-right">Mail: </td>
                        <td class="w3-animate-right"><input type="text" name="mail" value="" required/></td>
                    </tr>
                    <tr>
                        <td class="w3-animate-left">Passwort: </td>
                        <td class="w3-animate-left"><input type="password" name="pass" value="" minlength="8" required/></td>
                    </tr>
                    <tr>
                        <td class="w3-animate-right">Geburtsdatum: </td>
                        <td class="w3-animate-right"><input type="date" name="dateOfBirth" value="" required/></td>
                    </tr>
                </table>
                <div align="center">
                    <input class="w3-animate-top" type="submit" name="signup" value="Fertig" />
                </div>
            </div>
        </form>
     
    </body>
</html>
