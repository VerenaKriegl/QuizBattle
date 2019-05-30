package Server;

import beans.Account;
import beans.Category;
import beans.Question;
import configFiles.Config;
import database.DBAccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tobias
 */
public class Server {

    public ServerSocket serverSocket;
    private DBAccess dba;
    private Map<Integer, ArrayList<ObjectOutputStream>> mapGames;
    private ArrayList<ObjectOutputStream> players;
    private Map<ObjectOutputStream, String> mapClients;
    private Map<ObjectOutputStream, ObjectInputStream> mapInputClients;
    private ArrayList<Account> accountList = new ArrayList<>();
    private boolean running;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        dba = new DBAccess();
        mapGames = new HashMap<>();
        mapClients = new HashMap<>();
        mapInputClients = new HashMap<>();
        startServer();
    }

    private void log(String message) {
        System.out.println("Server Log: " + message);
    }

    private void startServer() {
        try {
            running = true;

            InetAddress inetAddress = InetAddress.getByName(Config.IP_ADDRESS); //172.20.10.2
            serverSocket = new ServerSocket(9999, 70, inetAddress);

            AcceptClient acceptClient = new AcceptClient(serverSocket);
            acceptClient.start();
        } catch (IOException ex) {
            log("Exception: unable to start server" + ex);
        }
    }

    class AcceptClient extends Thread {

        private ServerSocket ss;

        public AcceptClient(ServerSocket ss) {
            this.ss = ss;
        }

        @Override
        public void run() {
            try {
                log("is running");
                while (running) {
                    Socket socket = ss.accept();
                    log("client connected");

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                    ClientCommunicatoin cc = new ClientCommunicatoin(oos, ois, socket);
                    cc.start();
                }
            } catch (IOException ex) {
                log("Exception: unable to accept client");
            }
        }
    }

    class ClientCommunicatoin extends Thread {

        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        private Socket socket;

        private ClientCommunicatoin(ObjectOutputStream oos, ObjectInputStream ois, Socket socket) {
            this.oos = oos;
            this.ois = ois;
            this.socket = socket;
        }

        private void errorMessage(String errorType) {
            try {
                oos.writeObject("failed");
                oos.flush();
                oos.writeObject(errorType);
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private boolean login(Account account) throws SQLException {
            System.out.println(account.getUsername());
            Account loginAccount = dba.getAccountByUsername(account.getUsername());
            if (loginAccount == null) {
                return false;
            } else if (loginAccount.getPassword().equals(account.getPassword())) {
                return true;
            } else {
                return false;
            }

        }

        private void sendMessage(String message) {
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private boolean registrate(Account account) {
            for (Account existAccount : accountList) {
                if (existAccount.getUsername().equals(account.getUsername()) || existAccount.getMailAddress().equals(account.getMailAddress())) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public void run() {
            try {
                int highestID = dba.getHighestUserId();
                accountList = dba.getAllAccounts();

                String username = "";
                String type = "";

                boolean registrated = false;
                boolean loggedIn = false;

                do {
                    type = (String) ois.readObject();
                    System.out.println(type.toString());
                    if (type.equals("signup")) {
                        Account newAccount = (Account) ois.readObject();
                        registrated = registrate(newAccount);
                        newAccount.setUserid(highestID + 1);

                        if (registrated) {
                            username = newAccount.getUsername();
                            dba.addAccount(newAccount);
                            mapClients.put(oos, username);
                            mapInputClients.put(oos, ois);
                            sendMessage("loggedin");
                            log(username + " signed up");
                        } else {
                            sendMessage("Registrierung fehlgeschlagen!");
                        }
                    } else if (type.equals("login")) {
                        Account recievedAccount = (Account) ois.readObject();
                        System.out.println(recievedAccount.getUsername());
                        loggedIn = login(recievedAccount);
                        if (loggedIn) {
                            username = recievedAccount.getUsername();
                            accountList.add(recievedAccount);
                            mapClients.put(oos, username);
                            mapInputClients.put(oos, ois);
                            sendMessage("loggedin");
                            log(username + " logged in");
                        } else {
                            sendMessage("failed");
                        }
                    }
                } while (!registrated && !loggedIn);

                boolean isAvailable = true;
                while (isAvailable) {
                    type = (String) ois.readObject();
                    if (type.equals("logout")) {
                        mapClients.remove(username);
                        sendMessage("loggedout");
                        running = false;
                        isAvailable = false;
                        break;
                    } else if (type.equals("startgame")) {
                        System.out.println("hier");
                        startGame();
                        isAvailable = false;
                    }
                }
                log("after while()");
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                log("Exception: unable to communicate with client: " + ex);
            }
        }

        private void createNewGame() {
            players = new ArrayList<>();
            players.add(oos);
            mapGames.put(mapGames.size() + 1, players);
            WaitForPlayer waitForPlayer = new WaitForPlayer(mapGames.size());
            waitForPlayer.start();
            System.out.println("Spiel erzeugt!");
        }

        private boolean joinGame() {
            System.out.println("hier");
            for (ArrayList gamePlayer : mapGames.values()) {
                if (gamePlayer.size() == 1) {
                    for (int gameNumber : mapGames.keySet()) {
                        gamePlayer.add(oos);
                        mapGames.replace(gameNumber, gamePlayer);
                        System.out.println("Spiel gefunden!");
                        return true;
                    }
                }
            }
            return false;
        }

        private void startGame() {
            if (mapGames.isEmpty()) {
                createNewGame();
            } else {
                boolean gameJoined = joinGame();
                if (!gameJoined) {
                    createNewGame();
                }
            }
        }

    }

    class PlayGame extends Thread {
        private int score = 0;
        private Map<ObjectOutputStream, Integer> mapScore = new HashMap<>();
        private ArrayList<ObjectOutputStream> players = new ArrayList<>();
        private ObjectOutputStream currentPlayer;
        private ObjectOutputStream secondPlayer;
        private Question question;
        private ArrayList<Category> listCategory = new ArrayList<Category>();

        public PlayGame(ArrayList<ObjectOutputStream> players) {
            this.players = players;
        }

        private void getQuestionFromDB(String catname, ObjectOutputStream oos) {
            try {
                int count = dba.getMaxCountFromQuestionsPerCategory(catname);
                Random rand = new Random();
                int randomQuestionNumber = rand.nextInt((count - 0) + 0) + 0;
                question = dba.getQuestionByCategory(catname, randomQuestionNumber);
                currentPlayer.writeObject("question");
                currentPlayer.flush();
                currentPlayer.writeObject(question);
                currentPlayer.flush();
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            
            try {
                int countPlayer = 0;
                firstPlayer();
                mapScore.put(currentPlayer, score);
                mapScore.put(secondPlayer, score);
                for (int i = 0; i <= 5; i++) {
                    if (countPlayer > 0) {
                        nextPlayer();
                    }
                    sendCategory();
                    ObjectInputStream inputCurrentPlayer = mapInputClients.get(currentPlayer);
                    ArrayList<Category> listCategory = dba.getCategory();
                    String categoryName = (String) inputCurrentPlayer.readObject();
                    for (Category cat : listCategory) {
                        if (cat.getCategoryname().equals(categoryName)) {
                            getQuestionFromDB(categoryName, currentPlayer);
                            System.out.println("CategoryName: " + categoryName);
                            break;
                        }
                    }

                    String userAnswer = (String) inputCurrentPlayer.readObject();
                    if (userAnswer.equals(question.getRightAnswer())) {
                        score = mapScore.get(currentPlayer);
                        score += 1;
                        mapScore.replace(currentPlayer, score);
                    } 
                    countPlayer++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void firstPlayer() {
            currentPlayer = players.get(0);
            secondPlayer = players.get(1);
        }

        private void nextPlayer() {
            ObjectOutputStream help = currentPlayer;
            currentPlayer = secondPlayer;
            secondPlayer = help;
        }

        private void sendCategory() throws IOException {
            secondPlayer.writeObject("wait");
            secondPlayer.flush();
            secondPlayer.writeObject(mapScore.get(secondPlayer));
            secondPlayer.flush();
            secondPlayer.writeObject(mapScore.get(currentPlayer));
            secondPlayer.flush();
            currentPlayer.writeObject("choose category");
            currentPlayer.flush();
            listCategory = dba.getCategory();
            currentPlayer.writeObject(listCategory);
            currentPlayer.flush();
        }
    }

    class WaitForPlayer extends Thread {

        private int gameCount;
        private boolean isOnePlayer = true;

        public WaitForPlayer(int gameCount) {
            this.gameCount = gameCount;
        }

        @Override
        public void run() {
            while (isOnePlayer) {
                try {
                    if (mapGames.get(gameCount).size() == 2) {
                        log("Opponent found");

                        PlayGame playGame = new PlayGame(mapGames.get(gameCount));
                        playGame.start();

                        isOnePlayer = false;
                    }
                } catch (Exception ex) {
                    log("Exception: unable to find opponent");
                }
            }
        }
    }
}
