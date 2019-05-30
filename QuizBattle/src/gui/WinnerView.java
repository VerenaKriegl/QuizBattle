package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author kriegl
 */
public class WinnerView extends JFrame {
    public WinnerView(){
        super("Winner");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        
        initComponents();
    }
    
    private void initComponents(){
        Container c = this.getContentPane();
        c.setLayout(new GridLayout());
        c.setBackground(Color.white);
        ImageIcon logo = new ImageIcon("src/images/winner.gif");
        JLabel lbImage = new JLabel(logo);
        c.add(lbImage);
    }
    
    public static void main(String[] args) {
        WinnerView winnerview = new WinnerView();
        winnerview.setVisible(true);
    }
}
