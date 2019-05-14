package beans;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Tobias
 */ 
public class Account implements Serializable{
    /* Datenhaltungsklasse für die Accounts */
    private String username, password, mailAddress;
    private int userid;
    private LocalDate dateOfBirth;

    public Account(String username, String password, String mailAddress, int userid, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.mailAddress = mailAddress;
        this.userid = userid;
        this.dateOfBirth = dateOfBirth;
    } 

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public int getUserid() {
        return userid;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
