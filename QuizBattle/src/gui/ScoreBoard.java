package gui;

import client.Client;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
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
 * @author Verena Kriegl
 */
public class ScoreBoard extends JFrame {

    private Client client;
    private StartGame startGameParent;
    private Map<String, Integer> map = new HashMap<>();
    private int counterForPlace = 0;

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

        map = client.getAllHighScores();
        JPanel plScores = new JPanel(new GridLayout(map.size() + 1, 3)); //Anzahl der User

        ImageIcon logo = new ImageIcon("src/images/highScore.png");
        JLabel lbHeadline = new JLabel(logo);

        JLabel lbHeadlinePlace = new JLabel("Places");
        plScores.add(lbHeadlinePlace);
        JLabel lbHeadlinePlayer = new JLabel("Player");
        plScores.add(lbHeadlinePlayer);
        JLabel lbHeadlineCoins = new JLabel("Coins");
        plScores.add(lbHeadlineCoins);

        for (String username : map.keySet()) {
            counterForPlace++;
            JLabel lbPlace = new JLabel(""+counterForPlace);
            JLabel lbUsername = new JLabel(username);
            JLabel lbHighScore = new JLabel("" + map.get(username));

            plScores.add(lbPlace);
            plScores.add(lbUsername);
            plScores.add(lbHighScore);
        }
        
        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());

        c.add(lbHeadline, BorderLayout.NORTH);
        c.add(plScores, BorderLayout.CENTER);
        c.add(btCancel, BorderLayout.SOUTH);
    }

    private void onCancel() {
        this.setVisible(false);
        startGameParent.setVisible(true);
    }
}
