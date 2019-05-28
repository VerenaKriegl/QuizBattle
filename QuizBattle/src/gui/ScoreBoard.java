/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author kriegl
 */
public class ScoreBoard extends JFrame {
    public ScoreBoard(String title){
        super(title);
        initComponents();
    }
    
    private void initComponents(){
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        
        JPanel plScores = new JPanel(new GridLayout(15,2)); //Anazhl der User
        JLabel lbHeadline = new JLabel("High Scores");
        JLabel lbHeadlinePlayer = new JLabel("Player");
        JLabel lbHeadlineCoins= new JLabel("Coins");
        plScores.add(lbHeadlinePlayer);
        plScores.add(lbHeadlineCoins);
        
        c.add(lbHeadline, BorderLayout.NORTH);
        c.add(plScores, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        ScoreBoard scoreBoard = new ScoreBoard("Scoreboard");
        scoreBoard.setVisible(true);
        scoreBoard.setDefaultCloseOperation(EXIT_ON_CLOSE);
        scoreBoard.setLocationRelativeTo(null);
        scoreBoard.setSize(500, 500);
    }
}
