package gui;

import client.Client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Tobias Wechtitsch
 */
public class StartGame extends JFrame {

    private Client client;
    private int counterForPlace = 0;
    private Map<String, Integer> mapHighScore;

    public StartGame(Client client, Map<String, Integer> mapHighScore) {
        this.setLocationRelativeTo(null);
        this.setTitle("StartGame");
        this.mapHighScore = mapHighScore;
        
        this.setSize(new Dimension(700, 520));
        this.setResizable(false);
        this.setLayout(new GridLayout(1, 2));
        this.client = client;
        
        this.setLayout(new BorderLayout());
        
        client.highScoreMap();
        mapHighScore = client.getAllHighScores();
        JPanel plScores = new JPanel(new GridLayout(mapHighScore.size() + 1, 3));
        plScores.setBackground(new Color(255, 174, 2));
        JLabel lbHeadlinePlace = new JLabel("Places");
        lbHeadlinePlace.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        plScores.add(lbHeadlinePlace);
        
        JLabel lbHeadlinePlayer = new JLabel("Player");
        lbHeadlinePlayer.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        plScores.add(lbHeadlinePlayer);
        
        JLabel lbHeadlineCoins = new JLabel("Coins");
        lbHeadlineCoins.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        plScores.add(lbHeadlineCoins);

        for (String username : mapHighScore.keySet()) {
            counterForPlace++;
            JLabel lbPlace = new JLabel(""+counterForPlace);
            JLabel lbUsername = new JLabel(username);
            JLabel lbHighScore = new JLabel("" + mapHighScore.get(username));

            plScores.add(lbPlace);
            plScores.add(lbUsername);
            plScores.add(lbHighScore);
        }
        plScores.setBorder(new LineBorder(Color.black));
       
        JButton btStart = new JButton("Neues Spiel starten");
        btStart.addActionListener(e -> onStartGame());
        btStart.setBackground(new Color(2, 189, 252));
        btStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btStart.setBackground(new Color(255, 174, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btStart.setBackground(new Color(2, 189, 252));
            }
        });
        btStart.setBorder(new LineBorder(Color.BLACK));
        
        this.add(plScores, BorderLayout.NORTH);
        this.add(btStart, BorderLayout.CENTER);
    }  

    private void onStartGame() {
        client.startGame();
        this.setVisible(false);
    }

    private void onShowHighScoreList() {
        this.setVisible(false);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Exception in StartGame: Thread.sleep does not work");
        }
        ScoreBoard scoreBoard = new ScoreBoard("ScoreBoard", client, this);
        scoreBoard.setVisible(true);
    }
    
}
