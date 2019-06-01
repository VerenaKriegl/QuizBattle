package beans;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Tobias Wechtitsch
 */ 
public class Account implements Serializable{
    /* Datenhaltungsklasse für die Accounts */
    private String username, password, mailAddress;
    private int userid;
    private LocalDate dateOfBirth;
    private int highScore;

    public Account(String username, String password, String mailAddress, int userid, LocalDate dateOfBirth, int highScore) {
        this.username = username;
        this.password = password;
        this.mailAddress = mailAddress;
        this.userid = userid;
        this.dateOfBirth = dateOfBirth;
        this.highScore = highScore;
    } 

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
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
