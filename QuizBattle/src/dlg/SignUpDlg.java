package dlg;

import beans.Account;
import client.Client;
import gui.StartPage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.border.LineBorder;
  
/**
 *
 * @author Alex Mauko
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

    public SignUpDlg(StartPage startPage,Client client) {
        super(startPage);
        this.startPage = startPage;
        this.setLocationRelativeTo(null);
        this.client = client;
        this.pack();
        this.setTitle("Sign Up");
        this.setSize(new Dimension(530, 600));
        this.setLocationRelativeTo(null);

        initComponents();

        ok = false;
    }

    private void initComponents() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JLabel lbRegistrationLogo = new JLabel("SignUp", JLabel.CENTER);
        modifyLabel(lbRegistrationLogo, new Color(255, 174, 2), 120);

        container.add(lbRegistrationLogo, BorderLayout.NORTH);
        container.add(getSignUpMenu(), BorderLayout.CENTER);
    }

    private void modifyLabel(JLabel label, Color color, int size)
    {
        label.setOpaque(true);
        label.setFont(new Font("Arial", Font.ROMAN_BASELINE, size));
        label.setForeground(Color.BLACK);
        label.setBackground(color);
        label.setBorder(new LineBorder(Color.BLACK));
    }
    private void modifyTextField(JTextField textField)
    {
        textField.setBackground(new Color(145, 225, 255));
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textField.setBackground(new Color(255, 255, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                textField.setBackground(new Color(145, 225, 255));
            }
        });
        textField.setBorder(new LineBorder(Color.BLACK));
    }
    private void modifyButton(JButton button)
    {
        button.setBackground(new Color(2, 189, 252));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 174, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(2, 189, 252));
            }
        });
        button.setBorder(new LineBorder(Color.BLACK));
    }
    
    private JPanel getSignUpMenu() {
        JPanel plMenu = new JPanel();
        plMenu.setLayout(new GridLayout(6, 2));

        JLabel lbUsername = new JLabel("Username:", JLabel.CENTER);
        modifyLabel(lbUsername, new Color(255, 212, 86), 30);
        
        tfUsername = new JTextField();
        modifyTextField(tfUsername);
        plMenu.add(lbUsername);
        plMenu.add(tfUsername);

        JLabel lbMail = new JLabel("Mail:", JLabel.CENTER);
        modifyLabel(lbMail, new Color(255, 212, 86), 30);
        
        tfMail = new JTextField();
        modifyTextField(tfMail);
        plMenu.add(lbMail);
        plMenu.add(tfMail);

        JLabel lbPass = new JLabel("Password:", JLabel.CENTER);
        modifyLabel(lbPass, new Color(255,212,86), 30);
        
        pfPass = new JPasswordField();
        modifyTextField(pfPass);
        plMenu.add(lbPass);
        plMenu.add(pfPass);

        JLabel lbConfirmPass = new JLabel("Confirm password:", JLabel.CENTER);
        modifyLabel(lbConfirmPass, new Color(255,212,86), 30);
    
        pfConfirmPass = new JPasswordField();
        modifyTextField(pfConfirmPass);
        plMenu.add(lbConfirmPass);
        plMenu.add(pfConfirmPass);

        JLabel lbDateOfBirth = new JLabel("Date of birth:", JLabel.CENTER);
        modifyLabel(lbDateOfBirth, new Color(255,212,86), 30);
        
        dateOfBirth = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor d = new JSpinner.DateEditor(dateOfBirth, "dd.MM.yyyy");
        dateOfBirth.setEditor(d);
        dateOfBirth.setOpaque(true);
        dateOfBirth.getEditor().getComponent(0).setBackground(new Color(145, 225, 255));
        
        plMenu.add(lbDateOfBirth);
        plMenu.add(dateOfBirth);

        JButton btAdd = new JButton("Sign up");
        btAdd.addActionListener(e -> onSignUp());
        modifyButton(btAdd);

        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());
        modifyButton(btCancel);
        
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
            JOptionPane.showMessageDialog(this, "Password doesn't match!", "Failed", 1);
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

        newAccount = new Account(
                tfUsername.getText(), 
                pfPass.getText(),
                tfMail.getText(), 
                1, 
                localDate, 
                0);
        return newAccount;
    }

    public Boolean isOK() {
        return ok;
    }
}
