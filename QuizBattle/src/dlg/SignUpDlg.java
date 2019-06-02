package dlg;

import beans.Account;
import client.Client;
import gui.LoadingView;
import gui.StartPage;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author Alex Mauko2
 */
public class SignUpDlg extends JDialog {

    private Account newAccount;
    private JTextField tfUsername;
    private JTextField tfMail;
    private JPasswordField pfPass;
    private JPasswordField pfConfirmPass;
    private JSpinner dateOfBirth;
    private boolean ok;
    private StartPage startPage;
    private Client client;

    public SignUpDlg(StartPage startPage, boolean modal, Client client) {
        super(startPage, modal);

        this.startPage = startPage;
        this.client = client;
        this.pack();
        this.setTitle("Sign Up");
        this.setSize(new Dimension(500, 600));
        this.setLocationRelativeTo(null);

        initComponents();

        ok = false;
    }

    private void initComponents() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon("src/images/signup.png");
        JLabel lbRegistrationLogo = new JLabel(icon);

        container.add(lbRegistrationLogo, BorderLayout.NORTH);
        container.add(getSignUpMenu(), BorderLayout.CENTER); 
    }

    private JPanel getSignUpMenu() {
        JPanel plMenu = new JPanel();
        plMenu.setLayout(new GridLayout(6, 2));

        JLabel lbUsername = new JLabel("Username:");
        tfUsername = new JTextField();
        plMenu.add(lbUsername);
        plMenu.add(tfUsername);

        JLabel lbMail = new JLabel("Mail:");
        tfMail = new JTextField();
        plMenu.add(lbMail);
        plMenu.add(tfMail);

        JLabel lbPass = new JLabel("Password:");
        pfPass = new JPasswordField();
        plMenu.add(lbPass);
        plMenu.add(pfPass);

        JLabel lbConfirmPass = new JLabel("Confirm password:");
        pfConfirmPass = new JPasswordField();
        plMenu.add(lbConfirmPass);
        plMenu.add(pfConfirmPass);

        JLabel lbDateOfBirth = new JLabel("Date of birth:");
        dateOfBirth = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor d = new JSpinner.DateEditor(dateOfBirth, "dd.MM.yyyy");
        dateOfBirth.setEditor(d);
        plMenu.add(lbDateOfBirth);
        plMenu.add(dateOfBirth);

        JButton btAdd = new JButton("Sign up");
        btAdd.addActionListener(e -> onSignUp());

        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());

        plMenu.add(btAdd);
        plMenu.add(btCancel);

        return plMenu;
    }

    private void onSignUp() {
        if (pfPass.getText().equals(pfConfirmPass.getText())) {
            try {
                client.signup(getNewAccount());
                this.setVisible(false);
            } catch (ParseException ex) {
                Logger.getLogger(SignUpDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Password doesn't match!",
                    "Failed", 1);
        }
    }

    private void onCancel() {
        ok = false;
        setVisible(false);
        startPage.setVisible(true);
    }

    public Account getNewAccount() throws ParseException {
        java.util.Date date = (java.util.Date) dateOfBirth.getValue();
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        newAccount = new Account(tfUsername.getText(), pfPass.getText(),
                tfMail.getText(), 1, localDate, 0);
        return newAccount;
    }

    public Boolean isOK() {
        return ok;
    }
}
