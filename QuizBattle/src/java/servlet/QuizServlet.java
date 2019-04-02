/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import client.Client;
import java.io.IOException;
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

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        System.out.println("hier");
        client = new Client();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("hier");
        request.getRequestDispatcher("jsp/StartPage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String option = request.getParameter("registration");
        System.out.println("im post");
        if (option != null) {
            System.out.println("gleich");
            getServletConfig().getServletContext().
                    getRequestDispatcher("/jsp/Registration.jsp").
                    forward(request, response);
            System.out.println("da");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
