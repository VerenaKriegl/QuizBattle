package gui;

import client.Client;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
        container.setLayout(new GridLayout(3, 1));

        container.add(getHeadline());
        container.add(getPlayers());
        container.add(getPlayButton());
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

        return plScore;
    }

    private JLabel getHeadline() {
        JLabel lbHeadline = new JLabel("BattleOverview");
        lbHeadline.setFont(new Font("Serif", Font.BOLD, 28));
        return lbHeadline;
    }

    private JButton getPlayButton() {
        btPlay = new JButton("Play");
        btPlay.addActionListener(e -> onPlay());
        btPlay.setVisible(false);
        return btPlay;
    }

    private void onPlay() {
        client.getQuestion();
    }

    public void setPlayButton() {
        btPlay.setVisible(true);
    }
}
