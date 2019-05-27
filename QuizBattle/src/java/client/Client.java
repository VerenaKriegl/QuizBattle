package client;

import beans.Account;
import configFiles.Config;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author Tobias
 */
public class Client {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private int highestUserID;
    private String errorType;
    private boolean loggedIn;
    private boolean opponentFound = false;

    public Client() {
        connect();
    }

    public int getHighestId() {
        return highestUserID;
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
            System.out.println("Boolean in Client: "+opponentFound);
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
            GameCommunication gameCommunication = new GameCommunication();
            gameCommunication.start();
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
        /* Verbindung zum Server herstellen */
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
    class GameCommunication extends Thread
    {

        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            String answerQuestion = sc.nextLine();
        }
        
    }

    class ServerMessages extends Thread {
        /* Thread zur Kommunikation mit dem Server */
        @Override
        public void  run() {
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
                        highestUserID = (int) ois.readObject();
                        System.out.println(highestUserID);
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
    public static void main(String[] args) {
        Client c = new Client();
        c.login(new Account("adsf", "sadf", null, 0, LocalDate.MIN));
        c.startGame();
    }
}
