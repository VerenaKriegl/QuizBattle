package servlet;

import beans.Account;
import client.Client;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private DateTimeFormatter dtf;
    private Account newAccount;
    private Account loginAccount;
    private int userid;
    private boolean connected = false;
    private boolean signedup;

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher("/jsp/StartPage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("registration") != null) {
            getServletConfig().getServletContext().
                    getRequestDispatcher("/jsp/Registration.jsp").
                    forward(request, response);
            if (!connected) {
                client = new Client();
            }
        }
        if (request.getParameter("login") != null) {
            getServletConfig().getServletContext().
                    getRequestDispatcher("/jsp/Login.jsp").
                    forward(request, response);
            if (!connected) {
                client = new Client();
            }
        }
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("signup") != null) {
            userid = client.getHighestId();
            System.out.println(userid);

            String username = request.getParameter("username");
            String mail = request.getParameter("mail");
            String pass = request.getParameter("pass");
            String date = request.getParameter("dateOfBirth");
            LocalDate dateOfBirth = LocalDate.parse(date);
            dateOfBirth.format(dtf);
            userid = client.getHighestId();

            userid++;
            newAccount = new Account(username, pass, mail, userid, dateOfBirth);
            System.out.println(pass);
            client.signup(newAccount);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(QuizServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!client.isLoggedIn()) {
                request.setAttribute("errorType", client.getErrorType());
                getServletConfig().getServletContext().
                        getRequestDispatcher("/jsp/Registration.jsp").
                        forward(request, response);
            } else {
                getServletConfig().getServletContext().
                        getRequestDispatcher("/jsp/MainMenu.jsp").
                        forward(request, response);
            }
        } else if (request.getParameter("login") != null) {
            String username = request.getParameter("username");
            String pass = request.getParameter("pass");
            loginAccount = new Account(username, pass, null, 0, null);
            client.login(loginAccount);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(QuizServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!client.isLoggedIn())
            {
                request.setAttribute("errorType", client.getErrorType());
                getServletConfig().getServletContext().getRequestDispatcher("/jsp/Login.jsp").forward(request, response);
            }
            else
            {
                getServletConfig().getServletContext().
                    getRequestDispatcher("/jsp/MainMenu.jsp").
                    forward(request, response);
            }
            
        } else if (request.getParameter("startGame") != null) {
            client.startGame();
            while(!opponentFound) {
                if(loadingCount==0)
                {
                    getServletConfig().getServletContext().
                        getRequestDispatcher("/jsp/LoadingView.jsp").
                        forward(request, response);
                    loadingCount++;
                }
                opponentFound = client.isOpponentFound();
            }
            getServletConfig().getServletContext().
                    getRequestDispatcher("/jsp/BattleView.jsp").
                    forward(request, response);
        }
    }
    private int loadingCount = 0;
    private boolean opponentFound = false;
    public void setSignedUp(boolean signedup) {
        this.signedup = signedup;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
