/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import beans.Account;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tobias
 */
public class Client {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public static void main(String[] args) {
        new Client();
    }
    public Client() {
        connect();
        logIn(new Account("Hans", "abcd", null, 0, null));
        startGame();
    }
    public void startGame()
    {
        try {
            oos.writeObject("startgame");
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void registrate(Account account) {
        try {
            oos.writeObject("registration");
            oos.flush();
            oos.writeObject(account);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void logIn(Account account)
    {
        try {
            oos.writeObject("login");
            oos.flush();
            oos.writeObject(account);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        try {
            
                Socket s = new Socket("10.151.77.50", 9999);
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());
                ServerMessages sm = new ServerMessages();
                sm.start();
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ServerMessages extends Thread {

        @Override
        public void run() {
            super.run(); //To change body of generated methods, choose Tools | Templates.
            try {   
                while (true) {
                    String message = (String) ois.readObject();
                    System.out.println("message");
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
