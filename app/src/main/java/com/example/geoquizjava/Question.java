package com.example.geoquizjava;

import androidx.annotation.StringRes;

public class Question {
    private @StringRes int textResId;
    private boolean answer;
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

    public boolean getViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
