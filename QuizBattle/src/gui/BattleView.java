package gui;

import client.Client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Verena Kriegl
 */
public class BattleView extends JFrame {

    private String player1;
    private String player2;
    private int scorePlayerOne, scorePlayerTwo;
    private JButton btPlay;
    private Client client;

    public BattleView(Client client, String username, String usernameFromOpponent, int scorePlayerOne, int scorePlayerTwo) {
        super("BattleView");
        this.client = client;
        setSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.player1 = username;
        this.player2 = usernameFromOpponent;
        this.scorePlayerOne = scorePlayerOne;
        this.scorePlayerTwo = scorePlayerTwo;

        initComponents();
    }

    private void initComponents() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        container.add(getHeadline(), BorderLayout.NORTH);
        container.add(getPlayers(), BorderLayout.CENTER);
        container.add(getPlayButton(), BorderLayout.SOUTH);
    }

    private JPanel getPlayers() {
        JPanel plScore = new JPanel();
        plScore.setLayout(new GridLayout(1, 5));

        JLabel lbPlayerOne = new JLabel(player1);
        JLabel lbPlayerTwo = new JLabel(player2);

        JLabel lbScorePlayerOne = new JLabel("" + client.getScorePlayerOne());
        JLabel lbScorePlayerTwo = new JLabel("" + client.getScorePlayerTwo());

        JLabel lbTextHolder = new JLabel("-");

        plScore.add(lbPlayerOne);
        plScore.add(lbScorePlayerOne);
        plScore.add(lbTextHolder);
        plScore.add(lbPlayerTwo);
        plScore.add(lbScorePlayerTwo);
        plScore.setBackground(new Color(145, 225, 255));
        plScore.setBorder(new LineBorder(Color.BLACK));
        return plScore;
    }
    
   

    private JLabel getHeadline() {
        JLabel lbHeadline = new JLabel("BattleOverview");
        lbHeadline.setFont(new Font("Serif", Font.BOLD, 70));
        lbHeadline.setOpaque(true);
        lbHeadline.setBackground(new Color(255, 174, 2));
        lbHeadline.setBorder(new LineBorder(Color.black));
        return lbHeadline;
    }

    private JButton getPlayButton() {
        btPlay = new JButton("Play");
        btPlay.addActionListener(e -> onPlay());
        btPlay.setVisible(false);
        btPlay.setBackground(new Color(2, 189, 252));
        btPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btPlay.setBackground(new Color(255, 174, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btPlay.setBackground(new Color(2, 189, 252));
            }
        });
        btPlay.setBorder(new LineBorder(Color.BLACK));
        
        return btPlay;
    }

    private void onPlay() {
        client.getQuestion();
    }

    public void setPlayButton() {
        btPlay.setVisible(true);
    }
}
