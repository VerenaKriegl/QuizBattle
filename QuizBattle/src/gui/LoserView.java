/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author kriegl
 */
public class LoserView extends JFrame {

    public LoserView() {
        super("2nd Winner");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(600, 600);

        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.white);
        ImageIcon logo = new ImageIcon("src/images/loser.gif");
        JLabel lbImage = new JLabel(logo);
        c.add(lbImage);
    }

    public static void main(String[] args) {
        LoserView loserView = new LoserView();
        loserView.setVisible(true);
    }
}
