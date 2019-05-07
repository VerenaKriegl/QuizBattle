<%-- 
    Document   : BattleView
    Created on : 02.04.2019, 09:36:21
    Author     : Alex Mauko2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Battle</title>
        <style>
            body {
                background-color: #f4511e; /* Orange */
                color: #ffffff;
            }
            #loader {
                position: absolute;
                left: 50%;
                top: 50%;
                z-index: 1;
                width: 150px;
                height: 150px;
                margin: -75px 0 0 -75px;
                border: 16px solid lightsalmon;
                border-radius: 50%;
                border-top: 16px solid white;
                width: 120px;
                height: 120px;
                -webkit-animation: spin 2s linear infinite;
                animation: spin 2s linear infinite;
            }
            @-webkit-keyframes spin {
                0% { -webkit-transform: rotate(0deg); }
                100% { -webkit-transform: rotate(360deg); }
            }
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
            .animate-bottom {
                position: relative;
                -webkit-animation-name: animatebottom;
                -webkit-animation-duration: 1s;
                animation-name: animatebottom;
                animation-duration: 1s
            }
            @-webkit-keyframes animatebottom {
                from { bottom:-100px; opacity:0 } 
                to { bottom:0px; opacity:1 }
            }
            @keyframes animatebottom { 
                from{ bottom:-100px; opacity:0 } 
                to{ bottom:0; opacity:1 }
            }
            #myDiv {
                display: none;
                text-align: center;
            }
        </style>
    </head>
    <body onload="myFunction()" style="margin:0;">
        <div id="loader"></div>
        <h2>Warten auf Gegner...</h2>
        <div style="display:none;" id="myDiv" class="animate-bottom">
          <h2>spielstand...</h2>
        </div>
        <script>
            var myVar;

            function myFunction() {
              myVar = setTimeout(showPage, 3000);
            }

            function showPage() {
              document.getElementById("loader").style.display = "none";
              document.getElementById("myDiv").style.display = "block";
            }
        </script>
    </body>
</html>
