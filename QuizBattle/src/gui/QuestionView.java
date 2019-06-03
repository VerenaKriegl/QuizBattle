package gui;

import beans.Question;
import client.Client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Client client;

    public QuestionView(String title, Question question, Client client) {
        super(title);
        this.question = question;
        this.client = client;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);
        initComponents();
    }

    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.white);
        String question = this.question.getQuestion();
        String rightAnswer = this.question.getRightAnswer();
        ArrayList<String> wrongAnswers = this.question.getWrongAnwers();
        String firstFalseAnswer = wrongAnswers.get(0);
        String secondFalseAnswer = wrongAnswers.get(1);
        String thirdFalseAnswer = wrongAnswers.get(2);

        JLabel lbQuestion = new JLabel(question);
        lbQuestion.setFont(new Font("Serif", Font.BOLD, 28));
        c.add(lbQuestion, BorderLayout.NORTH);

        Random rand = new Random();
        int randomNumber = rand.nextInt((4 - 1) + 1) + 1;

        JButton btRightAnswer = new JButton(rightAnswer);
        btRightAnswer.addActionListener(e -> onRightAnswerClicked(rightAnswer, btRightAnswer));
        JButton btFirstFalseAnswer = new JButton(firstFalseAnswer);
        btFirstFalseAnswer.addActionListener(e -> onWrongAnswer(wrongAnswers.get(0), btFirstFalseAnswer));
        JButton btSecondFalseAnswer = new JButton(secondFalseAnswer);
        btSecondFalseAnswer.addActionListener(e -> onWrongAnswer(wrongAnswers.get(1), btSecondFalseAnswer));
        JButton btThirdFalseAnswer = new JButton(thirdFalseAnswer);
        btThirdFalseAnswer.addActionListener(e -> onWrongAnswer(wrongAnswers.get(2), btThirdFalseAnswer));

        JPanel plAnswers = new JPanel(new GridLayout(2, 2));
        if (randomNumber == 4) {
            plAnswers.add(btThirdFalseAnswer);
            plAnswers.add(btFirstFalseAnswer);
            plAnswers.add(btSecondFalseAnswer);
            plAnswers.add(btRightAnswer);
        } else if (randomNumber == 3) {
            plAnswers.add(btThirdFalseAnswer);
            plAnswers.add(btFirstFalseAnswer);
            plAnswers.add(btRightAnswer);
            plAnswers.add(btSecondFalseAnswer);
        } else if (randomNumber == 2) {
            plAnswers.add(btThirdFalseAnswer);
            plAnswers.add(btRightAnswer);
            plAnswers.add(btFirstFalseAnswer);
            plAnswers.add(btSecondFalseAnswer);
        } else {
            plAnswers.add(btRightAnswer);
            plAnswers.add(btThirdFalseAnswer);
            plAnswers.add(btFirstFalseAnswer);
            plAnswers.add(btSecondFalseAnswer);
        }

        c.add(plAnswers, BorderLayout.CENTER);

        URL url = LoadingView.class.getResource("/images/timePanel.gif");
        ImageIcon imageIcon = new ImageIcon(url);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT));
        JLabel lbImageHolder = new JLabel(imageIcon);
        c.add(lbImageHolder, BorderLayout.SOUTH);
        sw = new StopWatch(this);
    }
    private StopWatch sw;

    public static void main(String[] args) {
        QuestionView questionView = new QuestionView("Question", null, null);
        questionView.setVisible(true);
        questionView.setDefaultCloseOperation(EXIT_ON_CLOSE);
        questionView.setLocationRelativeTo(null);
        questionView.setSize(800, 600);
    }

    private void onRightAnswerClicked(String rightAnswer, JButton button) {

        try {
            buttonClicked = true;
            JOptionPane.showMessageDialog(this, "Right Answer clicked!", "Right Answer!", JOptionPane.INFORMATION_MESSAGE);
            client.setAnswer(rightAnswer);
        } catch (IOException ex) {
            Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void onWrongAnswer(String wrongAnswer, JButton button) {
        try {
            buttonClicked = true;
            JOptionPane.showMessageDialog(this, "False Answer clicked!", "False Answer!", JOptionPane.INFORMATION_MESSAGE);
            client.setAnswer(wrongAnswer);
            this.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isButtonClicked() {
        return buttonClicked;
    }
    private boolean buttonClicked = false;
    private int count = 16;

    class StopWatch {

        private QuestionView questionView;

        public StopWatch(QuestionView questionView) {
            this.questionView = questionView;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(count);
                    if (count > 0) {
                        count--;
                    }
                    if (buttonClicked) {
                        this.cancel();
                    }

                    if (count == 0) {
                        try {
                            JOptionPane.showMessageDialog(questionView, "time is up", "Error", JOptionPane.INFORMATION_MESSAGE);
                            client.setAnswer("wrong");
                            this.cancel();

                        } catch (IOException ex) {
                            Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }
}
