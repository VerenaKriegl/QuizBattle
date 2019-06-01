package client;

import beans.Account;
import beans.Category;
import beans.Question;
import configFiles.Config;
import gui.ChooseCategory;
import start.GUIBuilder;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private GUIBuilder gui;
    private ChooseCategory choosecategory;
    private int count = 0;
    private String myUsername, usernameFromOpponent;
    private int scorePlayerOne = 0;
    private int scorePlayerTwo = 0;
    private Socket socket;
    private Map<String, Integer> mapHighScores = new HashMap<>();

    public Client() {
        connect();
    }

    public int getScorePlayerOne() {
        return scorePlayerOne;
    }

    public int getScorePlayerTwo() {
        return scorePlayerTwo;
    }

    public int getHighestId() {
        return highestUserID;
    }
    
    public Map<String, Integer> getAllHighScores(){
        return mapHighScores;
    }

    private void log(String message) {
        System.out.println(message);
    }

    public void setGUIBuilder(GUIBuilder gui) {
        this.gui = gui;
    }

    public void setAnswer(String answer) throws IOException {
        //Schickt die angeklickte Antwort zum Server
        gui.closeQuestionView();
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
        //Schickt dem Server eine Nachricht, dass der Client ein Spiel starten möchte
        try {
            gui.openLoadingViewGUI();
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
    
    public void highScoreMap(){
        try {
            oos.writeObject("highScoreList");
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void choosedCategoryName(String categoryName) {
        try {
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
        } catch (IOException ex) {
            log("Exception: unable to login");
        }
    }

    public void connect() {
        // Verbindung zum Server herstellen
        try {
            socket = new Socket(Config.IP_ADDRESS, 9999); //Holt sich die IP-Adresse aus dem Config-File
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            ServerMessages sm = new ServerMessages();
            sm.start();
        } catch (IOException ex) {
            log("Exception: unable to connect to server");
        }
    }

    public void getQuestion() {
        try {
            oos.writeObject("ready");
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ServerMessages extends Thread {

        // Thread zur Kommunikation mit dem Server
        @Override
        public void run() {
            try {
                while (true) {
                    String message = (String) ois.readObject();
                    if (message.equals("failed")) {
                        gui.openStartPage(true);
                        log("Login or registration failed!");
                    } else if (message.equals("loggedin")) {
                        myUsername = (String) ois.readObject();
                        gui.openStartGame();
                        log("You are logged in!");
                    } else if (message.equals("wait")) {
                        //Setzte den Player in den Wartezustand
                        scorePlayerOne = (int) ois.readObject();
                        scorePlayerTwo = (int) ois.readObject();
                        gui.closeLoadingView();
                        gui.openBattleView(myUsername, usernameFromOpponent, scorePlayerOne, scorePlayerTwo);
                    } else if (message.equals("choose category")) {
                        //Aufruf der GUI um eine Kategorie auszuwählen
                        gui.closeBattleView();
                        ArrayList<Category> categories = (ArrayList) ois.readObject();
                        gui.closeLoadingView();
                        choosecategory = gui.openChooseCategoryGUI(categories);
                    } else if (message.equals("battleview")) {
                        //Weckt den Player vom Wartezustand auf und er ist andere zu spielen
                        scorePlayerOne = (int) ois.readObject();
                        scorePlayerTwo = (int) ois.readObject();
                        if (gui.isBattleViewOpen()) {
                            gui.closeBattleView();
                        }
                        gui.closeLoadingView();
                        gui.openBattleView(myUsername, usernameFromOpponent, scorePlayerOne, scorePlayerTwo);
                        gui.setButton();
                    } else if (message.equals("question")) {
                        //Zeigt dem User die Question an
                        Question question = (Question) ois.readObject();
                        if (count == 0) {
                            count++;
                        }
                        gui.closeBattleView();
                        gui.openQuestionView(question);
                        if(choosecategory != null) {
                            gui.closeCategoryView(choosecategory);
                        }
                    } else if (message.equals("usernameOpponent")) {
                        usernameFromOpponent = (String) ois.readObject();
                    } else if (message.equals("winner")) {
                        gui.closeBattleView();
                        gui.openWinnerView();
                    } else if (message.equals("loser")) {
                        gui.closeBattleView();
                        gui.openLoserView();
                    } else if (message.equals("equal")) {
                        //Unentschieden
                        gui.closeBattleView();
                        gui.openEqualView();
                    } else if(message.equals("highScores")){    
                        mapHighScores = (Map<String, Integer>) ois.readObject();
                    }else {
                        log(message);
                    } 
                }
            } catch (IOException | ClassNotFoundException ex) {
                log("Exception: unable to read received object");
            }
        }
    }
}
