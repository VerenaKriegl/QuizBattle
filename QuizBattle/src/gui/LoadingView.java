package gui;

import client.Client;
import java.awt.BorderLayout;
import java.awt.Container;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

public class LoadingView extends JFrame {
    private Client client;

    public LoadingView(String title, Client client) {
        super(title);
        this.setVisible(true);
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
        c.add(lbHeadline, BorderLayout.NORTH);
        URL url = LoadingView.class.getResource("/images/loading.gif");
        ImageIcon imageIcon = new ImageIcon(url);
        JLabel label = new JLabel(imageIcon);
        c.add(label, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        LoadingView loadingView = new LoadingView("Wait", null);
        loadingView.setVisible(true);
        loadingView.setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadingView.setLocationRelativeTo(null);
        loadingView.setSize(500, 500);
    }
}
