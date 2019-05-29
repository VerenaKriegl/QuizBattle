/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author tobia
 */
public class PlayerWait extends JFrame{
    public PlayerWait()
    {
        this.setSize(new Dimension(500, 500));
        JLabel background=new JLabel(new ImageIcon("src/images/backgroundImage.png"));
        this.setLayout(new GridLayout(1, 1));
        this.add(background);
    }
    public static void main(String[] args) {
        new PlayerWait().setVisible(true);
    }
}
