package com.example.takblet.ui.stats;

public class QuizItem {
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public int getCheatsUsed() {
        return cheatsUsed;
    }

    private final int correctAnswers;
    private final int incorrectAnswers;
    private final int cheatsUsed;

    public QuizItem(int correctAnswers, int incorrectAnswers, int cheatsUsed) {
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.cheatsUsed = cheatsUsed;
    }


}
