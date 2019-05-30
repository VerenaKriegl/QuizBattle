/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Category;
import client.Client;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author krieg
 */
public class ChooseCategory extends JFrame {

    private ArrayList<Category> categories;
    private String cat = null;
    private ArrayList<Integer> listCategory = new ArrayList<>();
    private Client client;

    public ChooseCategory(String title, ArrayList categories, Client client) {
        super(title);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        this.categories = categories;
        this.client = client;
        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(4, 1));

        JLabel lbHeadline = new JLabel("Choose Category: ");
        c.add(lbHeadline);

        c.add(getCategories());
        c.add(getCategories());
        c.add(getCategories());
    }

    public JButton getCategories()
    {
        boolean isAlreadyInUse = true;
        JButton button=null;
        Random rand = new Random();
        
        while(isAlreadyInUse)
        {
            int categoryID = rand.nextInt((categories.size() - 1) + 1) + 1; 
            if(listCategory.contains(categoryID))
            {
                System.out.println("already in use");
            }
            else
            {
                listCategory.add(categoryID);
                String catName1 = categories.get(categoryID).getCategoryname();
                button = new JButton(categories.get(categoryID).getCategoryname());
                button.addActionListener(e -> setCategoryName(catName1));
                isAlreadyInUse = false;
            }
        }
        listCategory.clear();
        return button;
    }
    
    public void setCategoryName(String cat) {
        client.choosedCategoryName(cat);
    }

    public static void main(String[] args) {
        ChooseCategory cat = new ChooseCategory("cat", null, null);
    }
}
