
package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alex Mauko2
 */
public class BattleViewGUI extends JFrame {
    
    private JLabel lbScore;
    
    public BattleViewGUI() {
        setSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        initComponents();     
    }
    
    private void initComponents() {
        Container container = new Container();
        container.setLayout(new BorderLayout());
        
        container.add(getScore());
        
        this.add(container);
        this.pack();
    }
    
    private JPanel getScore() {
        JPanel plScore = new JPanel();
        
        ImageIcon iconUnchecked = new ImageIcon("src/icons/unchecked-checkbox.png");
        ImageIcon iconChecked = new ImageIcon("src/icons/checked-checkbox.png");
        lbScore = new JLabel(iconUnchecked);
        
        
        plScore.add(lbScore);
        
        return plScore;
    }
    
    public static void main(String[] args) {
        new BattleViewGUI().setVisible(true);
    }
}
