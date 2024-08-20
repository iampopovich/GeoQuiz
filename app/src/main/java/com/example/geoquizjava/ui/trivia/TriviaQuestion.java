package com.example.geoquizjava.ui.trivia;

public class TriviaQuestion {

    private final String question;
    private final boolean answer;
    private boolean userAnswer;

    public String getQuestion() {
        return question;
    }

    public TriviaQuestion(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public boolean getAnswer() {
        return answer;
    }
}
