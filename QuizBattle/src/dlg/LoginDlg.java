package dlg;

import beans.Account;
import client.Client;
import start.GUIBuilder;
import gui.LoadingView;
import gui.StartPage;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Alex Mauko2
 */
public class LoginDlg extends JDialog {

    private boolean ok;
    private Account loginAccount;
    private JPasswordField pfPassword;
    private JTextField tfUsername;
    private StartPage startPage;
    private Client client;
    private GUIBuilder guiBuilder;

    public LoginDlg(StartPage startPage, boolean modal, Client client, GUIBuilder gui) {
        super(startPage, modal);
        this.startPage = startPage;
        this.client = client;
        this.guiBuilder = gui;
        this.setTitle("Log-in Dialog");
        this.setLocationRelativeTo(null);
        this.setPreferredSize(new Dimension(500, 500));

        initComponents();

        this.pack();

        ok = false;
    }

    private void initComponents() {
        Container container = new Container();
        container.setLayout(new BorderLayout());

        JPanel plLogin = new JPanel();
        plLogin.setLayout(new GridLayout(3, 2));

        JLabel lbUsername = new JLabel("Username:");
        tfUsername = new JTextField();

        JLabel lbPass = new JLabel("Passwort:");
        pfPassword = new JPasswordField();

        JButton btOk = new JButton("OK");
        btOk.addActionListener(e -> onOk());
        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());

        plLogin.add(lbUsername);
        plLogin.add(tfUsername);
        plLogin.add(lbPass);
        plLogin.add(pfPassword);
        plLogin.add(btOk);
        plLogin.add(btCancel);

        container.add(plLogin, BorderLayout.CENTER);

        this.add(container);
    }

    public Account getLoginAccount() {
        loginAccount = new Account(tfUsername.getText(), pfPassword.getText(),
                "", 0, null);
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
