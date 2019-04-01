/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;

/**
 *
 * @author tobia
 */ 
public class Account implements Serializable{
    private String name; 
    public Account(String name)
    {
        
        this.name = name;
    }
}
