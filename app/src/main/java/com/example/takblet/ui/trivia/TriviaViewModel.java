package com.example.takblet.ui.trivia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TriviaViewModel extends ViewModel {

  private final MutableLiveData<List<TriviaQuestion>> questionList =
      new MutableLiveData<>(new ArrayList<>());

  public void addQuestion(TriviaQuestion question) {
    List<TriviaQuestion> questions = Objects.requireNonNull(questionList.getValue());
    questions.add(question);
    questionList.postValue(questions);
  }

  public void clearQuestions() {
    List<TriviaQuestion> questions = Objects.requireNonNull(questionList.getValue());
    questions.clear();
    questionList.postValue(questions);
  }

  public void removeQuestion(TriviaQuestion question) {
    List<TriviaQuestion> questions = Objects.requireNonNull(questionList.getValue());
    questions.remove(question);
    questionList.postValue(questions);
  }

  public LiveData<List<TriviaQuestion>> getQuestionList() {
    return questionList;
  }

  public void setQuestions(List<TriviaQuestion> questions) {
    questionList.setValue(questions);
  }
}
