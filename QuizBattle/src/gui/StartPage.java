/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import start.GUIBuilder;
import client.Client;
import dlg.SignUpDlg;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
//import com.seaglasslookandfeel.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

/**
 *
 * @author kriegl
 */
public class StartPage extends JFrame {

    private Client client;
    private GUIBuilder gui;
    private JPanel plImage;

    public StartPage(String title, Client client, GUIBuilder gui) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {       
        super(title);
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        this.client = client;
        this.gui = gui;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        initComponents();
    }

    public static void main(String[] args) {
        try {
            new StartPage("StartPage", null, null).setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StartPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(3, 1));

        ImageIcon logo = new ImageIcon("src/images/Logo.PNG");
        JLabel lbImage = new JLabel(logo);
        plImage = new JPanel();
        plImage.setLayout(new BorderLayout());
        plImage.add(lbImage);
        plImage.setBackground(Color.white);
        plImage.setBorder(new LineBorder(Color.BLACK));
        c.add(plImage);

       
        
        JButton btLogin = new JButton("Login");
        Font font = btLogin.getFont();
        btLogin.addActionListener(e -> login());
        btLogin.setBorder(new LineBorder(Color.BLACK));
//        btLogin.setBorder(null);
//        btLogin.setBackground(new Color(2, 189, 252));
//        btLogin.setBorder(new LineBorder(new Color(36, 37, 38)));
//        btLogin.setFont(new Font(font.getName(), Font.PLAIN, 35));
//        btLogin.setForeground(new Color(237, 148, 4));
//        btLogin.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                btLogin.setBackground(new Color(237, 148, 4));
//                btLogin.setForeground(new Color(2, 189, 252));
//            }
//
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                btLogin.setBackground(new Color(2, 189, 252));
//                btLogin.setForeground(new Color(237, 148, 4));
//            }
//        });
        c.add(btLogin);

        JButton btSignUp = new JButton("Registrieren");
        btSignUp.addActionListener(e -> signUp());
        btSignUp.setBorder(null);
        btSignUp.setBorder(new LineBorder(Color.BLACK));
//        btSignUp.setFont(font);
//        btSignUp.setBorder(null);
//        btSignUp.setForeground(new Color(237, 148, 4));
//        btSignUp.setBackground(new Color(2, 189, 252));
//        btSignUp.addActionListener(e -> signUp());
//        btSignUp.setFont(new Font(font.getName(), Font.PLAIN, 35));
//        btSignUp.setBorder(new LineBorder(new Color(36, 37, 38)));
//        btSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                btSignUp.setBackground(new Color(237, 148, 4));
//                btSignUp.setForeground(new Color(2, 189, 252));
//            }
//
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                btSignUp.setBackground(new Color(2, 189, 252));
//                btSignUp.setForeground(new Color(237, 148, 4));
//            }
//        });
        c.add(btSignUp);
    }

    private void login() {
        this.setVisible(false);
        gui.openLoginDlg();
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

    public void setJOptionPane() {
        JOptionPane.showMessageDialog(this, "Login or registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
