package client;

import beans.Account;
import beans.Category;
import beans.Question;
import configFiles.Config;
import gui.BattleViewGUI;
import gui.ChooseCategory;
import gui.GUIBuilder;
import gui.LoadingView;
import gui.QuestionView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tobias
 */
public class Client {
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private int highestUserID;
    private String errorType;
    private boolean loggedIn = false;
    private GUIBuilder gui;
    private ChooseCategory choosecategory;
    
    public Client() {
        connect();
    }
    
    public int getHighestId() {
        return highestUserID;
    }
    
    public boolean isLoggedIn() {
        System.out.println("Getter: " + loggedIn);
        return loggedIn;
    }
    
    public String getErrorType() {
        return errorType;
    }
    
    private void log(String message) {
        System.out.println(message);
    }
    
    public void setAnswer(String answer) throws IOException {
        oos.writeObject("answer");
        oos.flush();
        oos.writeObject(answer);
        oos.flush();
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
            startGame();
        } catch (IOException ex) {
            log("Exception: unable to sign up");
        }
    }
    
    public void setGUIBuilder(GUIBuilder gui) {
        this.gui = gui;
    }
    
    public void choosedCategoryName(String categoryName) {
        try {
            oos.writeObject("categoryName");
            oos.flush();
            oos.writeObject(categoryName);
            System.out.println("Client: " + categoryName);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void login(Account account) {
        try {
            oos.writeObject("login");
            oos.flush();
            oos.writeObject(account);
            oos.flush();
            startGame();
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
    
    class GameCommunication extends Thread {
        
        @Override
        public void run() {
            try {
                while (true) {
                    Scanner sc = new Scanner(System.in);
                    String answerQuestion = sc.nextLine();
                    System.out.println(answerQuestion);
                    oos.writeObject(answerQuestion);
                    oos.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    private QuestionView questionview;
    class ServerMessages extends Thread {

        /* Thread zur Kommunikation mit dem Server */
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
                        highestUserID = (int) ois.readObject();
                        System.out.println(highestUserID);
                    } else if (message.equals("choose category")) {
                        ArrayList<Category> categories = (ArrayList) ois.readObject();
                        gui.closeLoadingView();
                        choosecategory = gui.openChooseCategoryGUI(categories);
                    } else if (message.equals("question")) {
                        Question question = (Question) ois.readObject();
                        gui.closeCategoryView(choosecategory);
                         questionview = gui.openQuestionView(question);
                    } else if (message.equals("rightAnswer")) {
                        
                    } else {
                        log(message);
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                log("Exception: unable to read received object");
            }
        }
    }
    /*
    public static void main(String[] args) {
        Client c = new Client();
        c.login(new Account("adsf", "sadf", null, 0, LocalDate.MIN));
        c.startGame();
    } */
}