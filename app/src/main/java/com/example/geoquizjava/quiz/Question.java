package com.example.geoquizjava.quiz;

import androidx.annotation.StringRes;

public class Question {
    private final @StringRes int textResId;
    private final boolean answer;


    public Question(@StringRes int textResId, boolean answer) {
        this.textResId = textResId;
        this.answer = answer;
    }

    public int getTextResId() {
        return textResId;
    }

    public boolean getAnswer() {
        return answer;
    }

}
