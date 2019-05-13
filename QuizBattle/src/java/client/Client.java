package client;

import beans.Account;
import configFiles.Config;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import servlet.QuizServlet;

/**
 *
 * @author tobias
 */
public class Client {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private int highestId;
    private String errorType;
    private boolean loggedIn;
    private boolean opponentFound;

    public Client() {
        connect();
    }

    public int getHighestId() {
        return highestId;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getErrorType() {
        return errorType;
    }

    public boolean isOpponentFound() 
    {
        if(opponentFound)
        {
            System.out.println("hier");
        }
        return opponentFound;
    }

    private void log(String message) {
        System.out.println("Client Log: " + message);
    }

    public void logout() {
        try {
            oos.writeObject("logout");
            oos.flush();
        } catch (IOException ex) {
            log("Exception: unable to logout");
        }

    }

    public void startGame() {
        try {
            oos.writeObject("startgame");
            oos.flush();
        } catch (IOException ex) {
            log("Exception: unable to start game");
        }
    }

    public void signup(Account account) {
        try {
            oos.writeObject("signup");
            oos.flush();
            oos.writeObject(account);
            oos.flush();
        } catch (IOException ex) {
            log("Exception: unable to sign up");
        }
    }

    public void login(Account account) {
        try {
            oos.writeObject("login");
            oos.flush();
            oos.writeObject(account);
            oos.flush();
        } catch (IOException ex) {
            log("Exception: unable to login");
        }
    }
    private Socket socket;

    public void connect() {
        try {
            socket = new Socket(Config.IP_ADDRESS, 9999);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            ServerMessages sm = new ServerMessages();
            sm.start();

        } catch (IOException ex) {
            log("Exception: unable to connect to server");
        }
    }

    class ServerMessages extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    String message = (String) ois.readObject();
                    if (message.equals("failed")) {
                        loggedIn = false;
                        log("Login or registration failed!");
                    } else if (message.equals("loggedin")) {
                        loggedIn = true;
                        log("You are logged in!");
                    } else if (message.equals("highestID")) {
                        highestId = (int) ois.readObject();
                        System.out.println(highestId);
                    } else if (message.equals("opponent found")) {
                        System.out.println("opponent found");
                        opponentFound = true;
                    } else {
                        log(message);
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                log("Exception: unable to read received object");
            }
        }
    }
}
