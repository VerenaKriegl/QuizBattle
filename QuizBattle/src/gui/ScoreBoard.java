/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import client.Client;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author kriegl
 */
public class ScoreBoard extends JFrame {

    private Client client;
    private StartGame startGameParent;
    private Map<String, Integer> map = new HashMap<>();

    public ScoreBoard(String title, Client client, StartGame startGameParent) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(850, 800);
        this.client = client;
        this.startGameParent = startGameParent;

        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());

        UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(40));
        SwingUtilities.updateComponentTreeUI(this);

        map = client.getAllHighScores();
        System.out.println(map.size());
        JPanel plScores = new JPanel(new GridLayout(map.size() + 1, 2)); //Anzahl der User

        ImageIcon logo = new ImageIcon("src/images/highScore.png");
        JLabel lbHeadline = new JLabel(logo);

        JLabel lbHeadlinePlayer = new JLabel("Player");
        plScores.add(lbHeadlinePlayer);
        JLabel lbHeadlineCoins = new JLabel("Coins");
        plScores.add(lbHeadlineCoins);

        for (String username : map.keySet()) {
            JLabel lbUsername = new JLabel(username);
            JLabel lbHighScore = new JLabel("" + map.get(username));

            plScores.add(lbUsername);
            plScores.add(lbHighScore);
        }
        
        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());

        c.add(lbHeadline, BorderLayout.NORTH);
        c.add(plScores, BorderLayout.CENTER);
        c.add(btCancel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        ScoreBoard scoreBoard = new ScoreBoard("Scoreboard", null, null);
        scoreBoard.setVisible(true);
    }

    private void onCancel() {
        this.setVisible(false);
        startGameParent.setVisible(true);
    }
}
