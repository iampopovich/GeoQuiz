package com.example.geoquizjava.stats;

public class QuizItem {
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public int getCheatsUsed() {
        return cheatsUsed;
    }

    public void setCheatsUsed(int cheatsUsed) {
        this.cheatsUsed = cheatsUsed;
    }

    private int correctAnswers;
    private int incorrectAnswers;
    private int cheatsUsed;

    public QuizItem(int correctAnswers, int incorrectAnswers, int cheatsUsed) {
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.cheatsUsed = cheatsUsed;
    }


}
