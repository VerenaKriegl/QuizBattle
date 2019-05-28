/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import client.Client;
import configFiles.Config;
import dlg.LoginDlg;
import dlg.SignUpDlg;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author kriegl
 */
public class StartPage extends JFrame {
    private Client client;
    private GUIBuilder gui;

    public StartPage(String title, Client client, GUIBuilder gui) {
        super(title);
        this.client = client;
        this.gui = gui;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(3, 1));
        ImageIcon logo = new ImageIcon("src/images/Logo.PNG");
        JLabel lbImage = new JLabel(logo);
        c.add(lbImage);
        Button btLogin = new Button("Login");
        btLogin.addActionListener(e -> login());
        c.add(btLogin);
        Button btSignUp = new Button("Registrieren");
        btSignUp.addActionListener(e -> signUp());
        c.add(btSignUp);
    }

   
    private void login() {
        System.out.println("login");
        //Open login
        this.setVisible(false);
        LoginDlg dlg = new LoginDlg(this, true, client, gui);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            dlg.getLoginAccount();
        }
    }

    private void signUp() {
        System.out.println("signup");
        //Open Signup
        this.setVisible(false);
        System.out.println(client);
        SignUpDlg dlg = new SignUpDlg(this, true, client);
        dlg.setVisible(true);
        if (dlg.isOK()) {
        }
    }
}
