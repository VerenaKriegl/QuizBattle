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

        Random rand = new Random();

        int categoryID = rand.nextInt((6 - 1) + 1) + 1; //7 Kategorien
        String catName1 = categories.get(categoryID).getCategoryname();
        JButton btCategoryOne = new JButton(categories.get(categoryID).getCategoryname());
        btCategoryOne.addActionListener(e -> setCategoryName(catName1));

        categoryID = rand.nextInt((6 - 1) + 1) + 1; //7 Kategorien
        String catName2 = categories.get(categoryID).getCategoryname();
        JButton btCategoryTwo = new JButton(categories.get(categoryID).getCategoryname());
        btCategoryTwo.addActionListener(e -> setCategoryName(catName2));

        categoryID = rand.nextInt((6 - 1) + 1) + 1; //7 Kategorien
        String catName3 = categories.get(categoryID).getCategoryname();
        JButton btCategoryThree = new JButton(categories.get(categoryID).getCategoryname());
        btCategoryThree.addActionListener(e -> setCategoryName(catName3));

        c.add(btCategoryThree);
        c.add(btCategoryOne);
        c.add(btCategoryTwo);
    }

    public void setCategoryName(String cat) {
        client.choosedCategoryName(cat);
    }

    public static void main(String[] args) {
        ChooseCategory cat = new ChooseCategory("cat", null, null);
    }
}
