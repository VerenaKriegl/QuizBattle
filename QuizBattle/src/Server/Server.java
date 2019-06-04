package Server;

import beans.Account;
import beans.Category;
import beans.Question;
import com.sun.xml.internal.ws.resources.SenderMessages;
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
/**
 *
 * @author Tobias Wechtitsch
 */
public class Server {

    public ServerSocket serverSocket;

    //verantwortlich für den Datenbankzugriff
    private DBAccess dba;

    //In dieser Map sind alle Games über eine SpielID identifizierbar
    private Map<Integer, ArrayList<ObjectOutputStream>> mapGames;

    //In players sind zwei ObjectOutputStreams
    private ArrayList<ObjectOutputStream> players;

    //Alle Clients werden in dieser Map gespeichert
    private Map<ObjectOutputStream, String> mapClients;

    //Inputstream wird über den Outputstream gefunden
    private Map<ObjectOutputStream, ObjectInputStream> mapInputClients;

    //Beinhaltet zu jedem Usernamen den derzeitigen HighScore
    private Map<String, Integer> mapHighScore;

    //Hier werden alle Accounts gespeichert, für login und registrierung erforderlich
    private ArrayList<Account> accountList = new ArrayList<>();

    //prüft, ob der Client noch immer verbunden ist
    private boolean running;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        dba = new DBAccess();
        mapGames = new HashMap<>();
        mapClients = new HashMap<>();
        mapInputClients = new HashMap<>();
        mapHighScore = new HashMap<>();
        startServer();
    }

    private void log(String message) {
        System.out.println("Server Log: " + message);
    }

    //erstellen des ServerSocket und starten vom AcceptClient Thread
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

    //Herstellen einer Socketverbindung mit einem client
    class AcceptClient extends Thread {

        private ServerSocket serverSocket;

        public AcceptClient(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            try {
                log("is running");
                while (running) {
                    Socket socket = serverSocket.accept();
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

        //überprüft, ob sich der Benutzer einloggen kann
        private boolean login(Account account) throws SQLException {
            Account loginAccount = dba.getAccountByUsername(account.getUsername());
            if (loginAccount == null) {
                return false;
            } else if (loginAccount.getPassword().equals(account.getPassword())) {
                return true;
            } else {
                return false;
            }
        }

        //übermittelt generelle Nachrichten an den Client
        private void sendMessage(String message) {
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException ex) {
                log("Excpetion: unable to communicate with client");
            }
        }

        //überprüft, ob sich der Benutzer registrieren kann
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
                //erforderlich für die UserId eines Accounts in der Datenbank
                int highestID = dba.getHighestUserId();
                //Abfrage aus der Datenbank von allen Account Datensätzen
                accountList = dba.getAllAccounts();

                String username = "";
                String type = "";

                boolean registrated = false;
                boolean loggedIn = false;

                //login und registrieren
                do {
                    type = (String) ois.readObject();
                    if (type.equals("signup")) {
                        Account newAccount = (Account) ois.readObject();
                        registrated = registrate(newAccount);
                        newAccount.setUserid(highestID + 1);
                        if (registrated) {
                            username = newAccount.getUsername();
                            sendMessage("loggedin");
                            sendMessage(username);
                            
                            dba.addAccount(newAccount);
                            mapClients.put(oos, username);
                            mapInputClients.put(oos, ois);
                            
                            mapHighScore = dba.getAllHighScoresFromDB();                           
                            oos.writeObject(mapHighScore);
                            oos.flush();
                            log(username + " signed up");
                        } else {
                            sendMessage("Registrierung fehlgeschlagen!");
                        }
                    } else if (type.equals("login")) {
                        Account recievedAccount = (Account) ois.readObject();
                        loggedIn = login(recievedAccount);
                        if (loggedIn) {
                            username = recievedAccount.getUsername();
                            accountList.add(recievedAccount);
                            mapClients.put(oos, username);
                            mapInputClients.put(oos, ois);
                            sendMessage("loggedin");
                            sendMessage(username);
                            
                            mapHighScore = dba.getAllHighScoresFromDB();                           
                            oos.writeObject(mapHighScore);
                            oos.flush();
                            log(username + " logged in");
                        } else {
                            sendMessage("failed");
                        }
                    }
                } while (!registrated && !loggedIn);

                boolean isAvailable = true;
                //warten auf Nachrichten vom Client
                while (isAvailable) {
                    type = (String) ois.readObject();
                    if (type.equals("logout")) {
                        mapClients.remove(username);
                        sendMessage("loggedout");
                        running = false;
                        isAvailable = false;
                        break;
                    } else if (type.equals("startgame")) {
                        startGame();
                        isAvailable = false;
                    } 
                    
                }
                log("after while()");
            } catch (Exception ex) {
                log("Exception: unable to communicate with client: " + ex);
            }
        }

        //ein neues Spiel wird in der Map erstellt und der WaitForPlayer Thread aufgerufen
        private void createNewGame() {
            players = new ArrayList<>();
            players.add(oos);
            mapGames.put(mapGames.size() + 1, players);
            WaitForPlayer waitForPlayer = new WaitForPlayer(mapGames.size());
            waitForPlayer.start();
            log("Spiel erzeugt!");
        }

        //User tritt einem Spiel bei
        private boolean joinGame() {
            for (ArrayList gamePlayer : mapGames.values()) {
                if (gamePlayer.size() == 1) {
                    for (int gameNumber : mapGames.keySet()) {
                        gamePlayer.add(oos);
                        mapGames.replace(gameNumber, gamePlayer);
                        log("Spiel gefunden!");
                        return true;
                    }
                }
            }
            return false;
        }

        //Connected zwei Spieler miteinander
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

    //Thread wartet darauf, bis ein Spiel gestartet werden kann
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

    //Regelt den Spielablauf
    class PlayGame extends Thread {

        private int score = 0;
        //ordnet jedem User einen Score zu
        private Map<ObjectOutputStream, Integer> mapRoundScore = new HashMap<>();
        //ArrayList der teilnehmenden Spieler
        private ArrayList<ObjectOutputStream> players = new ArrayList<>();
        //Spieler der eine Runde gestartet hat
        private ObjectOutputStream currentPlayer;
        //Spieler der die nächste Runde beginnt
        private ObjectOutputStream secondPlayer;
        //Spieler der in der aktuellen Runde wartet
        private ObjectOutputStream currentRoundWaiter;
        //Spieler der in der aktuellen Runde am Zug ist
        private ObjectOutputStream currentRoundPlayer;
        //Question Object
        private Question question;
        //Liste mit allen Categorien
        private ArrayList<Category> listCategory = new ArrayList<>();

        public PlayGame(ArrayList<ObjectOutputStream> players) {
            this.players = players;
        }

        //vom der Datenbank wird eine Question genommen und an den Client gesendet
        private Question getQuestionFromDB(String catname, ObjectOutputStream oos) {
            try {
                int count = dba.getMaxCountFromQuestionsPerCategory(catname);
                Random rand = new Random();
                int randomQuestionNumber = rand.nextInt((count - 0) + 0) + 0;
                return dba.getQuestionByCategory(catname, randomQuestionNumber);
            } catch (SQLException ex) {
                log("Exception: Unable to read Questions from DB");
            }
            return null;
        }

        @Override
        public void run() {

            try {
                firstPlayer();
                mapRoundScore.put(currentPlayer, score);
                mapRoundScore.put(secondPlayer, score);
                playRound(5);
            } catch (Exception ex) {
                log("Exception: unable to start the battle");
            }
        }

        //überprüfen der Antwort vom User
        private void checkUserAnswer(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            String userAnswer = (String) ois.readObject();
            if (userAnswer.equals(question.getRightAnswer())) {
                score = mapRoundScore.get(currentRoundPlayer);
                score += 1;
                mapRoundScore.replace(currentRoundPlayer, score);
            }
        }

        //Am Ende eines Battles werden die HighScores aktualisiert
        private void setHighScores(int points, ObjectOutputStream oosLoser, ObjectOutputStream oosWinner) {
            try {

                mapHighScore = dba.getAllHighScoresFromDB();
                String usernameFromWinner = mapClients.get(oosWinner);
                int newHighScoreFromWinner = mapHighScore.get(usernameFromWinner);
                newHighScoreFromWinner += points;
                String usernameFromLoser = mapClients.get(oosLoser);
                int newHighScoreFromLoser = mapHighScore.get(usernameFromLoser);
                if (points == 5) {
                    newHighScoreFromLoser += points;
                } else {
                    newHighScoreFromLoser -= points;
                }
                dba.setNewHighScoreFromUser(newHighScoreFromWinner, usernameFromWinner);
                dba.setNewHighScoreFromUser(newHighScoreFromLoser, usernameFromLoser);
            } catch (SQLException ex) {
                log("Exception: unable to set new highscore in DB");
            }
        }

        //Gewinner wird überprüft
        private void calculateWinner() {
            try {
                int scoreFromFirstPlayer = mapRoundScore.get(currentRoundPlayer);
                int scoreFromSecondPlayer = mapRoundScore.get(currentRoundWaiter);

                if (scoreFromFirstPlayer > scoreFromSecondPlayer) {
                    currentRoundPlayer.writeObject("winner");
                    currentRoundPlayer.flush();
                    currentRoundWaiter.writeObject("loser");
                    currentRoundWaiter.flush();
                    setHighScores(10, currentRoundWaiter, currentRoundPlayer);

                } else if (scoreFromFirstPlayer < scoreFromSecondPlayer) {
                    currentRoundPlayer.writeObject("loser");
                    currentRoundPlayer.flush();
                    currentRoundWaiter.writeObject("winner");
                    currentRoundWaiter.flush();
                    setHighScores(10, currentRoundPlayer, currentRoundWaiter);
                } else {
                    currentRoundPlayer.writeObject("equal");
                    currentRoundPlayer.flush();
                    currentRoundWaiter.writeObject("equal");
                    currentRoundWaiter.flush();
                    setHighScores(5, currentRoundPlayer, currentRoundWaiter);
                }
            } catch (IOException ex) {
                log("Exception: unable to calculate winner and loser");
            }
        }

        //Regelt den Ablauf einer Runde
        private void playRound(int roundAmount) throws IOException, ClassNotFoundException {
            int countPlayer = 0;
            for (int i = 0; i < roundAmount; i++) {
                if (countPlayer > 0) {
                    nextPlayer();
                } else {
                    playerWait(currentRoundWaiter);
                }
                ObjectInputStream inputCurrentPlayer = mapInputClients.get(currentRoundPlayer);
                openBattleView();
                String ready = (String) inputCurrentPlayer.readObject();
                sendCategory();

                ArrayList<Category> listCategory = dba.getCategory();
                String categoryName = (String) inputCurrentPlayer.readObject();
                for (Category cat : listCategory) {
                    if (cat.getCategoryname().equals(categoryName)) {
                        question = getQuestionFromDB(categoryName, currentPlayer);
                        sendQuestion(question);
                        log("CategoryName: " + categoryName);
                        break;
                    }
                }
                checkUserAnswer(inputCurrentPlayer);

                currentRoundPlayer = secondPlayer;
                currentRoundWaiter = currentPlayer;
                inputCurrentPlayer = mapInputClients.get(currentRoundPlayer);
                playerWait(currentRoundWaiter);
                openBattleView();
                ready = (String) inputCurrentPlayer.readObject();
                sendQuestion(question);
                checkUserAnswer(inputCurrentPlayer);
                countPlayer++;
            }
            calculateWinner();
        }

        //Spieler, der am Zug ist, wird dieses Fenster angezeigt
        private void openBattleView() throws IOException {
            currentRoundPlayer.writeObject("battleview");
            currentRoundPlayer.flush();
            currentRoundPlayer.writeObject(mapRoundScore.get(currentRoundPlayer));
            currentRoundPlayer.flush();
            currentRoundPlayer.writeObject(mapRoundScore.get(currentRoundWaiter));
            currentRoundPlayer.flush();
        }

        //Frage wird an den User gesendet
        private void sendQuestion(Question question) throws IOException {
            currentRoundPlayer.writeObject("question");
            currentRoundPlayer.flush();
            currentRoundPlayer.writeObject(question);
            currentRoundPlayer.flush();
        }

        //Usernamen werden an die Clients versendet
        private void sendUsername(ObjectOutputStream myOutputStream, ObjectOutputStream opponentOutputStream) {
            try {
                myOutputStream.writeObject("usernameOpponent");
                myOutputStream.flush();
                String usernameOpponent = mapClients.get(opponentOutputStream);
                myOutputStream.writeObject(usernameOpponent);
                myOutputStream.flush();
            } catch (IOException ex) {
                log("Exception: unable to get the username of the opponent");
            }
        }

        //definiert wer das Spiel beginnt
        private void firstPlayer() {
            currentPlayer = players.get(0);
            secondPlayer = players.get(1);

            sendUsername(currentPlayer, secondPlayer);
            sendUsername(secondPlayer, currentPlayer);

            currentRoundPlayer = currentPlayer;
            currentRoundWaiter = secondPlayer;
        }

        //wechseln des Spielers der die Runde beginnt
        private void nextPlayer() {
            ObjectOutputStream help = currentPlayer;
            currentPlayer = secondPlayer;
            secondPlayer = help;
            currentRoundPlayer = currentPlayer;
            currentRoundWaiter = secondPlayer;
        }

        //Wartebildschirm während ein anderer Spieler am Zug ist
        private void playerWait(ObjectOutputStream oos) throws IOException {
            oos.writeObject("wait");
            oos.flush();
            oos.writeObject(mapRoundScore.get(currentRoundWaiter));
            oos.flush();
            oos.writeObject(mapRoundScore.get(currentRoundPlayer));
            oos.flush();
        }

        //Categorien werden an den Client versendet
        private void sendCategory() throws IOException {
            currentRoundPlayer.writeObject("choose category");
            currentRoundPlayer.flush();
            listCategory = dba.getCategory();
            currentRoundPlayer.writeObject(listCategory);
            currentRoundPlayer.flush();
        }
    }
}
