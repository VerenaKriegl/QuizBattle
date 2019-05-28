package beans;

import java.io.Serializable;

/**
 *
 * @author Verena
 */
public class Category implements Serializable{
    /* Datenhaltungsklasse für die verschiedenen Kategorien */
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
