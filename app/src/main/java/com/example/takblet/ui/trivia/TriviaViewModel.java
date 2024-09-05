package com.example.takblet.ui.trivia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TriviaViewModel extends ViewModel {

    private final MutableLiveData<List<TriviaQuestion>> mQuestionList = new MutableLiveData<>(new ArrayList<>());

    public void addQuestion(TriviaQuestion question) {
        List<TriviaQuestion> questions = Objects.requireNonNull(mQuestionList.getValue());
        questions.add(question);
        mQuestionList.postValue(questions);
    }

    public void clearQuestions() {
        Objects.requireNonNull(mQuestionList.getValue()).clear();
    }

    public void removeQuestion(TriviaQuestion question) {
        List<TriviaQuestion> questions = Objects.requireNonNull(mQuestionList.getValue());
        questions.remove(question);
        mQuestionList.postValue(questions);
    }

    public LiveData<List<TriviaQuestion>> getQuestionList() {
        return mQuestionList;
    }

    public void setQuestions(List<TriviaQuestion> questions) {
        mQuestionList.setValue(questions);
    }
}
