package gui;

import beans.Category;
import client.Client;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Alex Mauko
 */
public class ChooseCategory extends JFrame {

    private ArrayList<Category> categories;
    private String cat = null;
    private ArrayList<Integer> listCategory = new ArrayList<>();
    private Client client;

    public ChooseCategory(ArrayList categories, Client client) {
        super("Choose Category");
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
        lbHeadline.setOpaque(true);
        lbHeadline.setBackground(new Color(255, 174, 2));
        c.add(lbHeadline);

        c.add(getCategories());
        c.add(getCategories());
        c.add(getCategories());
        listCategory.clear();
    }

    public JButton getCategories() {
        boolean isAlreadyInUse = true;
        JButton button = null;
        Random rand = new Random();

        while (isAlreadyInUse) {
            int categoryID = rand.nextInt((categories.size() - 1 - 0) + 0) + 0;
            if (listCategory.contains(categoryID)) {
                System.out.println("already in use");
            } else {
                listCategory.add(categoryID);
                String catName1 = categories.get(categoryID).getCategoryname();
                button = new JButton(categories.get(categoryID).getCategoryname());
                button.addActionListener(e -> setCategoryName(catName1));
                modifyButton(button);

                isAlreadyInUse = false;
            }
        }
        return button;
    }

    public void setCategoryName(String cat) {
        client.choosedCategoryName(cat);
    }

    private void modifyButton(JButton button) {
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
}
