/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;

/**
 *
 * @author krieg
 */
public class Question {
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
