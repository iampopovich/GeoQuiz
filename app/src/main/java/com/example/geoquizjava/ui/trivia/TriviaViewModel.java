package com.example.geoquizjava.ui.trivia;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TriviaViewModel extends ViewModel {

    private final MutableLiveData<List<TriviaQuestion>> mQuestionList = new MutableLiveData<>(new ArrayList<>());

    public void addQuestion(TriviaQuestion question) {
        Objects.requireNonNull(mQuestionList.getValue()).add(question);
    }

    public void clearQuestions() {
        Objects.requireNonNull(mQuestionList.getValue()).clear();
    }

    public List<TriviaQuestion> getQuestionList() {
        return mQuestionList.getValue();
    }

    public void setQuestions(List<TriviaQuestion> questions) {
        mQuestionList.setValue(questions);
    }
}
