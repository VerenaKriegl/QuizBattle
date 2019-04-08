/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import beans.Account;
import client.Client;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tobias
 */
@WebServlet(name = "QuizServlet", urlPatterns = {"/QuizServlet"})
public class QuizServlet extends HttpServlet {

    private Client client;
    private DateTimeFormatter dtf;
    private Account newAccount;
    private int userid;

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        System.out.println("hier");
        client = new Client();
        dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        userid = 1;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("hier");
        request.getRequestDispatcher("/jsp/StartPage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("registration") != null) {
            getServletConfig().getServletContext().
                    getRequestDispatcher("/jsp/Registration.jsp").
                    forward(request, response);
        }

        if (request.getParameter("signup") != null) {
            String username = request.getParameter("username");
            String mail = request.getParameter("mail");
            String pass = request.getParameter("pass");
            String date = request.getParameter("dateOfBirth");
            LocalDate dateOfBirth = (LocalDate) dtf.parse(date);
            newAccount = new Account(username, mail, pass, userid, dateOfBirth);
            userid++;
            client.registrate(newAccount);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
