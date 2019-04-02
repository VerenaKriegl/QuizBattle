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

    private Map<Integer, ArrayList<ObjectOutputStream>> mapGames = new HashMap<>();
    private ArrayList<ObjectOutputStream> listGameStreams;
    private Map<String, ObjectOutputStream> mapClients = new HashMap<>();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        startServer();

    }

    private void startServer() {
        try {
            InetAddress inetAddress = InetAddress.getByName("10.151.77.50");
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
                    ClientCommunicatoin cc = new ClientCommunicatoin(oos, ois);
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

        private ClientCommunicatoin(ObjectOutputStream oos, ObjectInputStream ois) {
            this.oos = oos;
            this.ois = ois;
        }

        @Override
        public void run() {
            try {
                DBAccess dba = new DBAccess();
                String username = null;

                String type = (String) ois.readObject();
                if (type.equals("registration")) {
                    Account account = (Account) ois.readObject();
                    username = account.getUsername();
                    mapClients.put(account.getUsername(), oos);
                    oos.writeObject("Account erstellt");
                    oos.flush();

                } else if (type.equals("login")) {
                    Account account = (Account) ois.readObject();
                    Account loginAccount = dba.getAccountByUsername(account.getUsername());
                    if (loginAccount.getPassword().equals(account.getPassword())) {
                        mapClients.put(username, oos);
                        oos.writeObject("Herzlich willkommen");
                        oos.flush();
                    }
                    while (true) {
                        type = (String) ois.readObject();
                        if (type.equals("logout")) {
                            mapClients.remove(username);
                            oos.writeObject("Auf wiedersehen!");
                            oos.flush();
                        } else if (type.equals("startgame")) {
                            startGame(oos);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void startGame(ObjectOutputStream oos) {
            boolean availableGame = false;
            if (mapGames.isEmpty()) {
                listGameStreams = new ArrayList<>();
                listGameStreams.add(oos);
                mapGames.put(1, listGameStreams);
                WaitForPlayer wfp = new WaitForPlayer(1);
                wfp.start();
            } else {
                for (ArrayList list : mapGames.values()) {
                    if (list.size() == 1) {
                        for (int mapKey : mapGames.keySet()) {
                            if (list.equals(mapGames.get(mapKey))) {
                                mapGames.remove(mapKey, list);
                                list.add(oos);
                                listGameStreams = list;
                                mapGames.put(mapKey, listGameStreams);
                                availableGame = true;
                            }
                        }
                        System.out.println("Beitreten");
                        break;
                    } else {
                        System.out.println("voll");
                    }
                }
                if (!availableGame) {
                    int gameCount = mapGames.size() + 1;
                    listGameStreams = new ArrayList<>();
                    listGameStreams.add(oos);
                    mapGames.put(mapGames.size() + 1, listGameStreams);
                    WaitForPlayer wfp = new WaitForPlayer(gameCount);
                    wfp.start();
                }
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
                if (mapGames.get(gameCount).size() == 2) {
                    System.out.println("Spieler gefunden");
                    isOnePlayer = false;
                    System.out.println(mapGames.size());
                } else {
                    System.out.println("hier");
                }
            }
        }

    }

}
