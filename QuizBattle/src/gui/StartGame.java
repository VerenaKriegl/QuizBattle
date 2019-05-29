/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import client.Client;
import java.awt.Dimension;
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
        this.client = client;
        this.setTitle("StartGame");
        this.setSize(new Dimension(500, 300));
        JButton startButton = new JButton("Spiel starten");
        startButton.addActionListener(e -> onStartGame());
        this.add(startButton);
    }

    private void onStartGame() {
        client.startGame();
        this.setVisible(false);
    }
    
}
