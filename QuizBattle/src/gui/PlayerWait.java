/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tobia
 */
public class PlayerWait extends JFrame{
    private JPanel panelScore;
    private JPanel panelImage;
    public PlayerWait(int scorePlayerOne,int scorePlayerTwo)
    {
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(500, 500));
        JLabel background=new JLabel(new ImageIcon("src/images/backgroundImage.png"));
        this.setLayout(new BorderLayout());
        panelScore = new JPanel();
        panelScore.setLayout(new GridLayout(2, 1));
        panelScore.add(new JLabel("Your score: "+scorePlayerOne));
        panelScore.add(new JLabel("Score of your enemy: "+scorePlayerTwo));
        panelImage = new JPanel();
        panelImage.add(background);
        this.add(panelScore, BorderLayout.NORTH);
        this.add(panelImage, BorderLayout.CENTER);
    }
    
}
