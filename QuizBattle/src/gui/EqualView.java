/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author kriegl
 */
public class EqualView extends JFrame {

    public EqualView() {
        setSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        JLabel lbEqual = new JLabel("Unentschieden!!");
        c.add(lbEqual);
    }

    public static void main(String[] args) {
        EqualView equalView = new EqualView();
        equalView.setVisible(true);
    }
}
