package gui;

import client.Client;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author Verena Kriegl
 */
public class LoadingView extends JFrame {

    private Client client;

    public LoadingView(Client client) {
        super("Wait for player ...");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        this.client = client;

        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());

        JLabel lbHeadline = new JLabel("Wait for player ...");
        ImageIcon imageIcon = new ImageIcon("src/images/loading.gif");
        JLabel label = new JLabel(imageIcon);

        c.add(lbHeadline, BorderLayout.NORTH);
        c.add(label, BorderLayout.CENTER);
    }
}
