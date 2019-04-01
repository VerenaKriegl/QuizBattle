/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import beans.Account;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tobias
 */
public class Server {

    public ServerSocket serverSocket = null;

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
                System.out.println("connected");

                while (true) {
                    Socket socket = ss.accept();
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
                while (true) {

                    String type = (String) ois.readObject();
                    if (type.equals("registration")) {
                        Account account = (Account)ois.readObject();
                        oos.writeObject("Account erstellt");
                        oos.flush();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
