package gui;

import beans.Category;
import beans.Question;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author kriegl
 */
public class QuestionView extends JFrame {
    private Question question;

    public QuestionView(String title) {
        super(title);
        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.white);
        String question = "Frage: ";
        String rightAnswer = "rightAnswer";
        String firstFalseAnswer = "falseAnswer";
        String secondFalseAnswer = "falseAnswer";
        String thirdFalseAnswer = "falseAnswer";

        JLabel lbQuestion = new JLabel(question);
        lbQuestion.setFont(new Font("Serif", Font.BOLD, 28));
        JButton btRightAnswer = new JButton(rightAnswer);
        JButton btFirstFalseAnswer = new JButton(firstFalseAnswer);
        JButton btSecondFalseAnswer = new JButton(secondFalseAnswer);
        JButton btThirdFalseAnswer = new JButton(thirdFalseAnswer);

        c.add(lbQuestion, BorderLayout.NORTH);

        JPanel plAnswers = new JPanel(new GridLayout(2, 2));
        plAnswers.add(btRightAnswer);
        plAnswers.add(btFirstFalseAnswer);
        plAnswers.add(btSecondFalseAnswer);
        plAnswers.add(btThirdFalseAnswer);
        c.add(plAnswers, BorderLayout.CENTER);

        URL url = LoadingView.class.getResource("/images/timePanel.gif");
        ImageIcon imageIcon = new ImageIcon(url);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT));
        JLabel lbImageHolder = new JLabel(imageIcon);
        c.add(lbImageHolder, BorderLayout.SOUTH);
        Thread threadCountdownTimer = new Thread(new Timer());
        threadCountdownTimer.start();

    }

    public static void main(String[] args) {
        QuestionView questionView = new QuestionView("Question");
        questionView.setVisible(true);
        questionView.setDefaultCloseOperation(EXIT_ON_CLOSE);
        questionView.setLocationRelativeTo(null);
        questionView.setSize(800, 600);
    }

    class Timer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 15; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "Time passed!", "Deine Zeit ist abgelaufen!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
