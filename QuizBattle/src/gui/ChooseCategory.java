/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Category;
import java.awt.Container;
import java.awt.GridLayout;
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
    private String categoryName;

    public ChooseCategory(String title, ArrayList categories) {
        super(title);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        this.categories = categories;
        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(4, 1));

        JLabel lbHeadline = new JLabel("Choose Category: ");
        c.add(lbHeadline);

        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            int categoryID = rand.nextInt((6 - 1) + 1) + 1; //7 Kategorien
            JButton btCategory = new JButton(categories.get(categoryID).getCategoryname());
            btCategory.addActionListener(e -> clickedCategoryName());
            categoryName = categories.get(categoryID).getCategoryname();
            c.add(btCategory);
        }
    }

    public String clickedCategoryName() {
        return categoryName;
    }
}
