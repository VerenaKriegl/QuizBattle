/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import client.Client;
import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author tobia
 */
public class StartGame extends JFrame{
    private Client client;
    public StartGame(Client client)
    {
        this.setLocationRelativeTo(null);
        this.client = client;
        this.setTitle("StartGame");
        this.setSize(new Dimension(500, 300));
        this.setLayout(new GridLayout(1,2));
        JButton btHighScore = new JButton("Rangliste");
        btHighScore.addActionListener(e -> onShowHighScoreList());
        JButton btStart = new JButton("Spiel starten");
        btStart.addActionListener(e -> onStartGame());
        this.add(btHighScore);
        this.add(btStart);
    }

    private void onStartGame() {
        client.startGame();
        this.setVisible(false);
    }

    private void onShowHighScoreList() {
        this.setVisible(false);
        client.highScoreMap();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        ScoreBoard scoreBoard = new ScoreBoard("ScoreBoard", client, this);
        scoreBoard.setVisible(true);
    }
    
}
