package gui;

import start.GUIBuilder;
import client.Client;
import dlg.SignUpDlg;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
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
 * @author Tobias Wechtitsch
 */
public class StartPage extends JFrame {

    private Client client;
    private GUIBuilder gui;
    private JPanel plImage;

    public StartPage(String title, Client client, GUIBuilder gui) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
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
        plImage = new JPanel();
        plImage.setLayout(new BorderLayout());
        plImage.add(lbImage);
        plImage.setBackground(new Color(255, 174, 2));
        plImage.setBorder(new LineBorder(Color.BLACK));

        c.add(plImage);
        c.add(createLoginButton());
        c.add(createRegistrateButton());
    }

    private JButton createLoginButton() {
        JButton btLogin = new JButton("Login");
        btLogin.addActionListener(e -> login());
        btLogin.setBorder(new LineBorder(Color.BLACK));

        btLogin.setBackground(new Color(2, 189, 252));
        btLogin.setBorder(new LineBorder(new Color(36, 37, 38)));
        btLogin.setForeground(new Color(0, 0, 0));
        btLogin.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        btLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btLogin.setBackground(new Color(255, 174, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btLogin.setBackground(new Color(2, 189, 252));
            }
        });
        return btLogin;
    }

    private JButton createRegistrateButton() {
        JButton btSignUp = new JButton("Registrieren");
        btSignUp.setBorder(null);
        btSignUp.setBorder(new LineBorder(Color.BLACK));

        btSignUp.setBorder(null);
        btSignUp.setForeground(new Color(0, 0, 0));
        btSignUp.setBackground(new Color(2, 189, 252));
        btSignUp.addActionListener(e -> signUp());
        btSignUp.setBorder(new LineBorder(new Color(36, 37, 38)));
        btSignUp.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        btSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btSignUp.setBackground(new Color(255, 174, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btSignUp.setBackground(new Color(2, 189, 252));
            }
        });
        return btSignUp;
    }

    private void login() {
        this.setVisible(false);
        gui.openLoginDlg();
    }

    private void signUp() {
        this.setVisible(false);
        gui.openSignUpDlg();
    }

    public void setJOptionPane() {
        JOptionPane.showMessageDialog(this, "Login or registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
