package com.example.takblet.ui.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.takblet.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuizViewModel extends ViewModel {
  private final List<Question> questionBank =
      Arrays.asList(
          new Question(R.string.question_australia, true),
          new Question(R.string.question_oceans, true),
          new Question(R.string.question_mideast, false),
          new Question(R.string.question_africa, false),
          new Question(R.string.question_americas, true),
          new Question(R.string.question_asia, true),
          new Question(R.string.question_antarctica, true),
          new Question(R.string.question_india, false),
          new Question(R.string.question_indonesia, true),
          new Question(R.string.question_pakistan, false),
          new Question(R.string.question_brazil, false),
          new Question(R.string.question_argentina, true),
          new Question(R.string.question_russia, false),
          new Question(R.string.question_japan, true),
          new Question(R.string.question_morocco, false),
          new Question(R.string.question_lake_baikal, true),
          new Question(R.string.question_mount_everest, true),
          new Question(R.string.question_amazon_rainforest, true),
          new Question(R.string.question_sahara_desert, true),
          new Question(R.string.question_great_barrier_reef, true),
          new Question(R.string.question_nile_river, false), // Правильный ответ — Амазонка
          new Question(R.string.question_greenland, true),
          new Question(R.string.question_vatican_city, true),
          new Question(R.string.question_gobi_desert, true),
          new Question(R.string.question_ganges_river, true),
          new Question(R.string.question_rocky_mountains, true),
          new Question(R.string.question_sydney_opera_house, true),
          new Question(R.string.question_great_wall_of_china, true),
          new Question(R.string.question_borneo_island, true),
          new Question(R.string.question_polar_bears, true),
          new Question(R.string.question_niagara_falls, true),
          new Question(R.string.question_angel_falls, true),
          new Question(R.string.question_mediterranean_sea, true),
          new Question(R.string.question_atacama_desert, true),
          new Question(R.string.question_kilimanjaro, true));

  private final ArrayList<Question> availableQuestions = new ArrayList<>();
  private final MutableLiveData<Boolean> answerIsViewed = new MutableLiveData<>();
  private final MutableLiveData<Question> currentQuestion = new MutableLiveData<>();
  private final MutableLiveData<Integer> correctAnswers = new MutableLiveData<>();
  private final MutableLiveData<Integer> incorrectAnswers = new MutableLiveData<>();
  private final MutableLiveData<Integer> cheatsUsed = new MutableLiveData<>();

  public QuizViewModel() {
    answerIsViewed.setValue(false);
    correctAnswers.setValue(0);
    incorrectAnswers.setValue(0);
    cheatsUsed.setValue(0);
  }

  private final String TAG = "QuizViewModel";

  public MutableLiveData<Integer> getCorrectAnswers() {
    return correctAnswers;
  }

  public MutableLiveData<Integer> getIncorrectAnswers() {
    return incorrectAnswers;
  }

  public MutableLiveData<Integer> getCheatsUsed() {
    return cheatsUsed;
  }

  public boolean getAnswerIsViewed() {
    return Objects.requireNonNull(answerIsViewed.getValue());
  }

  public int getCurrentIndex() {
    return availableQuestions.indexOf(currentQuestion.getValue());
  }

  public void setAnswerIsViewed(boolean value) {
    answerIsViewed.setValue(value);
  }

  public boolean getCurrentAnswer() {
    return Objects.requireNonNull(currentQuestion.getValue()).getAnswer();
  }

  public int getCurrentQuestionText() {
    return Objects.requireNonNull(currentQuestion.getValue()).getTextResId();
  }

  public void moveToNextQuestion() {
    if (getCurrentIndex() < availableQuestions.size() - 1) {
      currentQuestion.setValue(availableQuestions.get(getCurrentIndex() + 1));
    } else if (getCurrentIndex() == availableQuestions.size() - 1) {
      currentQuestion.setValue(availableQuestions.get(0));
    } else return;
    answerIsViewed.setValue(false);
  }

  public boolean isAnyQuestionAvailable() {
    availableQuestions.remove(currentQuestion.getValue());
    return !availableQuestions.isEmpty();
  }

  public void selectRandomQuestions(int limit) {
    this.availableQuestions.clear();
    this.availableQuestions.addAll(
        questionBank.stream()
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> {
                      Collections.shuffle(collected);
                      return collected.stream();
                    }))
            .limit(limit)
            .collect(Collectors.toList()));
    currentQuestion.setValue(availableQuestions.get(0));
  }

  public void setCheatsUsed(int i) {
    cheatsUsed.setValue(i);
  }

  public void setCorrectAnswers(int i) {
    correctAnswers.setValue(i);
  }

  public void setIncorrectAnswers(int i) {
    incorrectAnswers.setValue(i);
  }
}
