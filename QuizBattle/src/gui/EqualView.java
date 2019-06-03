package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author Alex Mauko
 */
public class EqualView extends JFrame {

    public EqualView() {
        super("Unentschieden!");
        setSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        ImageIcon image = new ImageIcon("src/images/twoWinners.jpg");        
        JLabel lbEqual = new JLabel(image);
        c.add(lbEqual);
    }
}
