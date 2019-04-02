/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import client.Client;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private SimpleDateFormat sdf;

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        System.out.println("hier");
        client = new Client();
        sdf = new SimpleDateFormat("dd.MM.yyyy");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("hier");
        request.getRequestDispatcher("jsp/StartPage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String signup = request.getParameter("signup");
        String back = request.getParameter("back");
        
        if (signup != null) {
            String username = request.getParameter("username");
            String mail = request.getParameter("mail");
            String pass = request.getParameter("pass");
            String date = request.getParameter("dateOfBirth");
            Date dateOfBirth;
            try {
                java.util.Date utilDate = sdf.parse(date);
                dateOfBirth = new Date(utilDate.getTime());
            } catch (ParseException ex) {
                System.out.println("Date error in Servlet");
            }
        }
        
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String option = request.getParameter("registration");

        if (option != null) {
            getServletConfig().getServletContext().
                    getRequestDispatcher("/jsp/Registration.jsp").
                    forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
