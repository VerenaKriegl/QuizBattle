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
    }
    
    public void logout()
    {
        try {
            oos.writeObject("logout");
            oos.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    private Socket socket;
    public void connect() {
        try {
            
                socket = new Socket("10.151.77.50", 9999);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                ServerMessages sm = new ServerMessages();
                sm.start();
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ServerMessages extends Thread {

        @Override
        public void run() {
            try {   
                while (true) {
                    String message = (String) ois.readObject();
                    if(message.equals("Auf wiedersehen!"))
                    {
                        oos.close();
                        ois.close();
                        socket.close();
                    }
                    System.out.println("Server message: "+message);
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
