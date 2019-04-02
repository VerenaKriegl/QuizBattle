/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author kriegl
 */
public class Category {
    private String categoryname;
    private int categoryid;

    public Category(String categoryname, int categoryid) {
        this.categoryname = categoryname;
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public int getCategoryid() {
        return categoryid;
    }
    
    
}
