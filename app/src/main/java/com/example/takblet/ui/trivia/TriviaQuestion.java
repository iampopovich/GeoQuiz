package com.example.takblet.ui.trivia;

public class TriviaQuestion {

    private final String question;
    private final boolean answer;
    private boolean userAnswer;
    private final String difficulty;
    private final String category;

    public String getQuestion() {
        return question;
    }

    public TriviaQuestion(String question, boolean answer, String difficulty, String category) {
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
        this.category = category;
    }

    public boolean getAnswer() {
        return answer;
    }
}
