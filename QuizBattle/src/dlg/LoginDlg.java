package dlg;

import beans.Account;
import client.Client;
import start.GUIBuilder;
import gui.StartPage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Alex Mauko
 */
public class LoginDlg extends JDialog {

    private boolean ok;
    private Account loginAccount;
    private JPasswordField pfPassword;
    private JTextField tfUsername;
    private StartPage startPage;
    private Client client;
    private GUIBuilder guiBuilder;

    public LoginDlg(StartPage startPage, Client client, GUIBuilder gui) {
        super(startPage);
        this.startPage = startPage;
        this.client = client;
        this.guiBuilder = gui;
        this.setTitle("Log-in Dialog");
        this.setLocationRelativeTo(null);
        this.setPreferredSize(new Dimension(500, 600));
        this.setResizable(false);
        this.pack();
        initComponents();

        ok = false;
    }

    private void initComponents() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        
        JPanel plLogin = new JPanel();
        plLogin.setLayout(new GridLayout(3, 2));
        
        JLabel lbLogin = new JLabel("LogIn", JLabel.CENTER);
        lbLogin.setOpaque(true);
        lbLogin.setFont(new Font("Arial", Font.ROMAN_BASELINE, 120));
        lbLogin.setForeground(Color.BLACK);
        lbLogin.setBackground(new Color(255, 174, 2));
    
        tfUsername = new JTextField();
        tfUsername.setBorder(new LineBorder(Color.BLACK));
        tfUsername.setOpaque(true);
        tfUsername.setBackground(new Color(145, 225, 255));
        tfUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tfUsername.setBackground(new Color(255, 255, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                tfUsername.setBackground(new Color(145, 225, 255));
            }
        });

        
        pfPassword = new JPasswordField();
        pfPassword.setBorder(new LineBorder(Color.BLACK));
        pfPassword.setBackground(new Color(145, 225, 255));
        pfPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pfPassword.setBackground(new Color(255, 255, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                pfPassword.setBackground(new Color(145, 225, 255));
            }
        });

        plLogin.add(createLabelUsername());
        plLogin.add(tfUsername);
        plLogin.add(createLabelPass());
        plLogin.add(pfPassword);
        plLogin.add(createButtonOk());
        plLogin.add(createButtonCancel());

       
        container.add(lbLogin, BorderLayout.NORTH);
        container.add(plLogin, BorderLayout.CENTER);
    }
    
    private JLabel createLabelPass()
    {
        JLabel lbPass = new JLabel("Passwort: ", JLabel.CENTER);
        lbPass.setOpaque(true);
        lbPass.setBackground(new Color(255, 212, 86));
        lbPass.setBorder(new LineBorder(Color.BLACK));
        lbPass.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        return lbPass;
    }
    
    private JLabel createLabelUsername()
    {
        JLabel lbUsername = new JLabel("Username: ", JLabel.CENTER);
        lbUsername.setOpaque(true);
        lbUsername.setBackground(new Color(255, 212, 86));
        lbUsername.setBorder(new LineBorder(Color.BLACK));
        lbUsername.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        return lbUsername;
    }
    
    private JButton createButtonOk()
    {
        JButton btOk = new JButton("OK");
        btOk.addActionListener(e -> onOk());
        
        btOk.setBorder(new LineBorder(Color.BLACK));
        btOk.setBackground(new Color(2, 189, 252));
        btOk.setForeground(new Color(0, 0, 0));
        btOk.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        btOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btOk.setBackground(new Color(255, 174, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btOk.setBackground(new Color(2, 189, 252));
            }
        });
        
        return btOk;
    }
    private JButton createButtonCancel()
    {
        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());
        
        btCancel.setBorder(new LineBorder(Color.BLACK));
        btCancel.setBackground(new Color(2, 189, 252));
        btCancel.setForeground(new Color(0, 0, 0));
        btCancel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        btCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btCancel.setBackground(new Color(255, 174, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btCancel.setBackground(new Color(2, 189, 252));
            }
        });
        
        return btCancel;
    }

    public Account getLoginAccount() {
        loginAccount = new Account(tfUsername.getText(), pfPassword.getText(),
                "", 0, null, 0);
        return loginAccount;
    }

    private void onOk() {
        if (!tfUsername.getText().equals("") || !pfPassword.getText().equals("")) {
            setVisible(false);
            client.login(getLoginAccount());
            } else {
            JOptionPane.showMessageDialog(this, "Fill all the fields!",
                    "Unable to proceed", 1);
        }
    }

    private void onCancel() {
        ok = false;
        setVisible(false);
        startPage.setVisible(true);
    }

    public boolean isOk() {
        return ok;
    }
}
