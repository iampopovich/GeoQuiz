package com.example.geoquizjava;

import androidx.lifecycle.ViewModel;

import java.util.Arrays;

public class QuizViewModel extends ViewModel {
    private final String TAG = "QuizViewModel";

    public int getCurrentIndex() {
        return currentIndex;
    }

    private int currentIndex = 0;
    private boolean isCheater = false;
    private int currentQuestionText;

    public boolean isCheater() {
        return isCheater;
    }

    public void isCheater(boolean value) {
        isCheater = value;
    }

    public boolean getCurrentAnswer() {
        return mQuestionBank[currentIndex].getAnswer();
    }

    public int getCurrentQuestionText() {
        return mQuestionBank[currentIndex].getTextResId();
    }

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false),
            new Question(R.string.question_antarctica, true, false),
            new Question(R.string.question_india, false, false),
            new Question(R.string.question_indonesia, true, false),
            new Question(R.string.question_pakistan, false, false),
            new Question(R.string.question_brazil, false, false),
            new Question(R.string.question_argentina, true, false),
            new Question(R.string.question_russia, false, false),
            new Question(R.string.question_japan, true, false),
            new Question(R.string.question_morocco, false, false),
    };

    public void setViewed() {
        mQuestionBank[currentIndex].setViewed(true);
    }

    public void moveToNext() {
        currentIndex = (currentIndex + 1) % mQuestionBank.length;
        isCheater = false;
    }

    public boolean isAnyQuestionAvailable() {
        return Arrays.stream(mQuestionBank).anyMatch(question -> !question.getViewed());
    }

}
