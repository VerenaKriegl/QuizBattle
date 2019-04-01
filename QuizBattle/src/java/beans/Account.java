/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author tobia
 */ 
public class Account implements Serializable{
    private String username, password, mailAddress;
    private int userid;
    private Date dateOfBirth;

    public Account(String username, String password, String mailAddress, int userid, Date dateOfBirth) {
        this.username = username;
        this.password = password;
        this.mailAddress = mailAddress;
        this.userid = userid;
        this.dateOfBirth = dateOfBirth;
    }
}
