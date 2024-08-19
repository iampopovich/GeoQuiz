package com.example.geoquizjava.quiz;

import androidx.annotation.StringRes;

public class Question {
    private final @StringRes int textResId;
    private final boolean answer;
    private boolean viewed;


    public Question(@StringRes int textResId, boolean answer, boolean viewed) {
        this.textResId = textResId;
        this.answer = answer;
        this.viewed = viewed;
    }

    public int getTextResId() {
        return textResId;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
