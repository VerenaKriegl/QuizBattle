/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author tobia
 */
public class Test extends JFrame {
    public Test(){
        super("2nd Winner");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(600, 600);

        initComponents();
    }
    
    private void initComponents(){
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        JButton bt = new JButton("click");
        bt.addActionListener(e->onClick(bt));
        c.add(bt);
    }
    
    public static void main(String[] args) {
        Test test = new Test();
        test.setVisible(true);
    }

    private void onClick(JButton bt) {
         Timer blinkTimer = new Timer(500, new ActionListener() {
            private int count = 0;
            private int maxCount = 7;
            private boolean on = false;

            public void actionPerformed(ActionEvent e) {
                if (count >= maxCount) {
                    bt.setBackground(null);
                    ((Timer) e.getSource()).stop();
                } else {
                    bt.setBackground(on ? Color.DARK_GRAY : null);
                    on = !on;
                    count++;
                }
            }
        });
        blinkTimer.start();
        
        
    }
}
