/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author krieg
 */
public class Question {
    private String question, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;
    private int questionid;

    public Question(String question, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, int questionid) {
        this.question = question;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionid() {
        return questionid;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }
}
