package client;

import beans.Account;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author tobias
 */
public class Client {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private int highestId;
    private boolean signedUp;

    public boolean isSignedUp() {
        return signedUp;
    }

    public int getHighestId() {
        return highestId;
    }

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        connect();
        //logIn(new Account("Hans", "abcd", null, 0, null));
        // registrate(new Account("adsf", "sadf", "asdfökj", 10, LocalDate.now()));
    }

    private void log(String message) {
        System.out.println("Client Log: " + message);
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
    private Socket socket;

    public void connect() {
        try {
            socket = new Socket("192.168.43.131", 9999); //172.20.10.2
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            ServerMessages sm = new ServerMessages();
            sm.start();

        } catch (IOException ex) {
            log("Exception: unable to connect to server");
        }
    }

    class ServerMessages extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    String message = (String) ois.readObject();
                    if (message.equals("!signedup")) {
                        signedUp = false;
                        log("Sign up failed!");
                    } else if(message.equals("signedup")) {
                        signedUp = true;
                        log("You are signed up!");
                    } else if (message.equals("highestID")) {
                        highestId = (int) ois.readObject();
                        System.out.println(highestId);
                    } else {
                        log(message);
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                log("Exception: unable to read recived object");
            }
        }
    }
}
