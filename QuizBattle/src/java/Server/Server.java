package Server;

import beans.Account;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tobias
 */
public class Server {

    public ServerSocket serverSocket;
    private DBAccess dba;
    private Map<Integer, ArrayList<ObjectOutputStream>> mapGames;
    private ArrayList<ObjectOutputStream> players;
    private Map<String, ObjectOutputStream> mapClients;
    private ArrayList<Account> accountList = new ArrayList<>();
    private boolean running;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        dba = new DBAccess();
        mapGames = new HashMap<>();
        mapClients = new HashMap<>();
        startServer();
    }

    private void log(String message) {
        System.out.println("Server Log: " + message);
    }

    private void startServer() {
        try {
            running = true;
            accountList = dba.getAllAccounts();
            System.out.println(accountList.size());
            InetAddress inetAddress = InetAddress.getByName("172.20.10.2"); //172.20.10.2
            serverSocket = new ServerSocket(9999, 70, inetAddress);

            AcceptClient acceptClient = new AcceptClient(serverSocket);
            acceptClient.start();
        } catch (IOException ex) {
            log("Exception: unable to start server");
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
                oos.writeObject("!signedup");
                oos.flush();
                oos.writeObject(errorType);
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            try {
                int highestID = dba.getHighestUserId();
                oos.writeObject("highestID");
                oos.flush();
                oos.writeObject(highestID);
                oos.flush();
                boolean ok = false;
                String username = "";
                String type = "";
                boolean registrated = true;
                do {
                    type = (String) ois.readObject();
                    if (type.equals("signup")) {
                        Account newAccount = (Account) ois.readObject();
                        System.out.println(newAccount.getPassword());
                        if (newAccount.getPassword().length() <= 7) {
                            registrated = false;
                            errorMessage("pass");
                        } else {
                            for (Account account : accountList) {
                                if (account.getUsername().equals(newAccount.getUsername())) {
                                    errorMessage("username");
                                    registrated = false;
                                    break;
                                } else if (account.getMailAddress().equals(newAccount.getMailAddress())) {
                                    errorMessage("mail");
                                    registrated = false;
                                    break;
                                }
                            }
                        }
                        if (registrated) {

                            username = newAccount.getUsername();
                            dba.addAccount(newAccount);
                            mapClients.put(username, oos);
                            oos.writeObject("loggedin");
                            oos.flush();

                            ok = true;
                            log(username + " signed up");
                        }
                    } else if (type.equals("login")) {
                        Account recievedAccount = (Account) ois.readObject();
                        String recPas = recievedAccount.getPassword();
                        Account loginAccount = dba.getAccountByUsername(recievedAccount.getUsername());
                        String logPas = loginAccount.getPassword();
                        if (loginAccount.getPassword().equals(recievedAccount.getPassword())) {
                            username = loginAccount.getUsername();
                            mapClients.put(username, oos);
                            oos.writeObject("loggedin");
                            oos.flush();
                            ok = true;
                            log(username + " logged in");
                        }
                    }
                    if (!ok) {
                        oos.writeObject("failed");
                        oos.flush();
                    }
                    
                } while (!ok);
                System.out.println("hier");
                while (running) {
                    type = (String) ois.readObject();
                    if (type.equals("logout")) {
                        mapClients.remove(username);
                        oos.writeObject("loggedout");
                        oos.flush();
                        break;
                    } else if (type.equals("startgame")) {
                        startGame(username);
                    }
                }
                log("after while()");
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                log("Exception: unable to communicate with client");
            }
        }

        private void startGame(String username) {
            boolean gameStarted = false;

            if (mapGames.isEmpty()) {
                players = new ArrayList<>();
                players.add(oos);
                log("Username: " + username);
                mapGames.put(1, players);
                WaitForPlayer waitForPlayer = new WaitForPlayer(1);
                waitForPlayer.start();
            } else {
                for (ArrayList list : mapGames.values()) {
                    if (list.size() == 1) {
                        for (int mapKey : mapGames.keySet()) {
                            if (list.equals(mapGames.get(mapKey))) {
                                mapGames.remove(mapKey, list);
                                list.add(oos);
                                
                                mapGames.put(mapKey, list);
                                gameStarted = true;
                            }
                        }
                        break;
                    } else {
                        log("Opponent already found");
                    }
                }
                if (!gameStarted) {
                    int gameCount = mapGames.size() + 1;
                    players = new ArrayList<>();
                    players.add(oos);
                    mapGames.put(gameCount, players);
                    WaitForPlayer waitForPlayer = new WaitForPlayer(1);
                    waitForPlayer.start();
                }
            }
        }
    }

    class PlayGame extends Thread {

        private ArrayList<ObjectOutputStream> players = new ArrayList<>();

        public PlayGame(ArrayList<ObjectOutputStream> players) {
            this.players = players;
        }

        @Override
        public void run() {

            if(players.get(0) instanceof ObjectOutputStream)
            {
                System.out.println("objectstream");
            }
            System.out.println(players.toString());
            
            for (ObjectOutputStream oos : players) {
                try {
                    oos.writeObject("opponent found");
                    oos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           // System.out.println("Größe: "+players.size());
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
            System.out.println(players.size());
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
