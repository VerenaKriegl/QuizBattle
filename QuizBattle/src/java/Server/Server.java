/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public ServerSocket serverSocket = null;
    private DBAccess dba = new DBAccess();
    private Map<Integer, ArrayList<String>> mapGames = new HashMap<>();
    private ArrayList<String> listGameUser;
    private Map<String, ObjectOutputStream> mapClients = new HashMap<>();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        startServer();

    }

    private void startServer() {
        try {
            InetAddress inetAddress = InetAddress.getByName("172.20.10.2");
            serverSocket = new ServerSocket(9999, 70, inetAddress);
            AcceptClient ac = new AcceptClient(serverSocket);
            ac.start();
        } catch (IOException ex) {
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
                
                while (true) {
                    Socket socket = ss.accept();
                    System.out.println("connected");
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    
                    ClientCommunicatoin cc = new ClientCommunicatoin(oos, ois, socket);
                    cc.start();
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    class ClientCommunicatoin extends Thread {

        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        private Socket s;

        private ClientCommunicatoin(ObjectOutputStream oos, ObjectInputStream ois, Socket s) {
            this.oos = oos;
            this.ois = ois;
            this.s = s;
        }

        @Override
        public void run() {
            try {
                int highestID = dba.getHighestUserId();
                oos.writeObject("highestID");
                oos.flush();
                oos.writeObject(highestID);
                oos.flush();
                boolean isOk = false;
                String username = null;
                String type;
                do {
                    type = (String) ois.readObject();
                    if (type.equals("registration")) {
                        Account account = (Account) ois.readObject();
                        username = account.getUsername();
                        dba.addAccount(account);
                        mapClients.put(account.getUsername(), oos);
                        oos.writeObject("Account erstellt");
                        oos.flush();
                        isOk=true;
                    } else if (type.equals("login")) {
                        Account account = (Account) ois.readObject();
                        Account loginAccount = dba.getAccountByUsername(account.getUsername());
                        if (loginAccount.getPassword().equals(account.getPassword())) {
                            username = loginAccount.getUsername();
                            mapClients.put(username, oos);
                            oos.writeObject("Herzlich willkommen");
                            oos.flush();
                            isOk=true;
                        }
                    }
                    if(!isOk)
                    {
                        oos.writeObject("fehlgeschlagen");
                        oos.flush();
                    }
                    else
                    {
                        System.out.println("funktioniert");
                    }
                } while (!isOk);

                while (true) {
                    type = (String) ois.readObject();
                    if (type.equals("logout")) {
                        mapClients.remove(username);
                        oos.writeObject("Auf wiedersehen!");
                        oos.flush();
                        break;
                    } else if (type.equals("startgame")) {
                        startGame(username);
                    }
                }
                System.out.println("Hier!");
                /*oos.close();
                ois.close();
                s.close(); */

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void startGame(String username) {
            boolean availableGame = false;

            if (mapGames.isEmpty()) {
                listGameUser = new ArrayList<>();
                listGameUser.add(username);
                System.out.println("Username: " + username);
                mapGames.put(1, listGameUser);
                WaitForPlayer wfp = new WaitForPlayer(1);
                wfp.start();
            } else {
                for (ArrayList list : mapGames.values()) {
                    if (list.size() == 1) {
                        for (int mapKey : mapGames.keySet()) {
                            if (list.equals(mapGames.get(mapKey))) {
                                mapGames.remove(mapKey, list);
                                list.add(username);
                                listGameUser = list;
                                mapGames.put(mapKey, listGameUser);
                                availableGame = true;
                            }
                        }
                        break;
                    } else {
                        System.out.println("voll");
                    }
                }
                if (!availableGame) {
                    int gameCount = mapGames.size() + 1;
                    listGameUser = new ArrayList<>();
                    listGameUser.add(username);
                    mapGames.put(gameCount, listGameUser);
                    WaitForPlayer wfp = new WaitForPlayer(1);
                    wfp.start();
                }
            }

        }
    }

    class PlayGame extends Thread {

        private ArrayList<String> playerGame;

        public PlayGame(ArrayList<String> playerGame) {
            this.playerGame = playerGame;

        }

        @Override
        public void run() {
            for (String player : playerGame) {
                System.out.println(player);
            }
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
                        System.out.println("Spieler gefunden");
                        isOnePlayer = false;
                        System.out.println(mapGames.size());
                        ArrayList<String> list = mapGames.get(gameCount);
                        PlayGame pg = new PlayGame(mapGames.get(gameCount));
                        pg.start();
                    }
                } catch (Exception ex) {

                }

            }

        }

    }

}
