package beans;

import java.util.ArrayList;

/**
 *
 * @author Verena
 */
public class Question {
    /* Datenhaltungsklasse für alle Fragen mit den jeweiligen Antwortmöglichkeiten */
    private String question;
    private String rightAnswer; 
    private ArrayList<String> wrongAnwers;
    private int questionid;

    public Question(String question, String rightAnswer, ArrayList<String> wrongAnwers, int questionid) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.wrongAnwers = wrongAnwers;
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public ArrayList<String> getWrongAnwers() {
        return wrongAnwers;
    }

    public void setWrongAnwers(ArrayList<String> wrongAnwers) {
        this.wrongAnwers = wrongAnwers;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }  
}
