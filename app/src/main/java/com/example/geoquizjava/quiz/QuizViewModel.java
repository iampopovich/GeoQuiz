package com.example.geoquizjava.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.geoquizjava.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuizViewModel extends ViewModel {
    private final List<Question> mQuestionBank = Arrays.asList(
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
            new Question(R.string.question_lake_baikal, true, false),
            new Question(R.string.question_mount_everest, true, false),
            new Question(R.string.question_amazon_rainforest, true, false),
            new Question(R.string.question_sahara_desert, true, false),
            new Question(R.string.question_great_barrier_reef, true, false),
            new Question(R.string.question_nile_river, false, false), // Правильный ответ — Амазонка
            new Question(R.string.question_greenland, true, false),
            new Question(R.string.question_vatican_city, true, false),
            new Question(R.string.question_gobi_desert, true, false),
            new Question(R.string.question_ganges_river, true, false),
            new Question(R.string.question_rocky_mountains, true, false),
            new Question(R.string.question_sydney_opera_house, true, false),
            new Question(R.string.question_great_wall_of_china, true, false),
            new Question(R.string.question_borneo_island, true, false),
            new Question(R.string.question_polar_bears, true, false),
            new Question(R.string.question_niagara_falls, true, false),
            new Question(R.string.question_angel_falls, true, false),
            new Question(R.string.question_mediterranean_sea, true, false),
            new Question(R.string.question_atacama_desert, true, false),
            new Question(R.string.question_kilimanjaro, true, false)
    );
    private final ArrayList<Question> availableQuestions = new ArrayList<>(
            mQuestionBank.stream().collect(
                    Collectors.collectingAndThen(Collectors.toList(), collected -> {
                        Collections.shuffle(collected);
                        return collected.stream();
                    })).limit(7).collect(Collectors.toList()));

    private final MutableLiveData<Boolean> mAnswerIsViewed = new MutableLiveData<>();
    private final MutableLiveData<Question> mCurrentQuestion = new MutableLiveData<>();


    public QuizViewModel() {
        mAnswerIsViewed.setValue(false);
        mCurrentQuestion.setValue(availableQuestions.get(0));
    }

    private final String TAG = "QuizViewModel";

    public Question getCurrentQuestion() {
        return mCurrentQuestion.getValue();
    }

    public boolean getAnswerIsViewed() {
        return Objects.requireNonNull(mAnswerIsViewed.getValue());
    }

    public int getCurrentIndex() {
        return availableQuestions.indexOf(mCurrentQuestion.getValue());
    }

    public void setAnswerIsViewed(boolean value) {
        mAnswerIsViewed.setValue(value);
    }

    public boolean getCurrentAnswer() {
        return Objects.requireNonNull(mCurrentQuestion.getValue()).getAnswer();
    }

    public int getCurrentQuestionText() {
        return Objects.requireNonNull(mCurrentQuestion.getValue()).getTextResId();
    }

    public void moveToNext() {
        if (getCurrentIndex() < availableQuestions.size() - 1) {
            mCurrentQuestion.setValue(availableQuestions.get(getCurrentIndex() + 1));
        } else {
            mCurrentQuestion.setValue(availableQuestions.get(0));
        }
        mAnswerIsViewed.setValue(false);
    }

    public boolean isAnyQuestionAvailable() {
        availableQuestions.remove(mCurrentQuestion.getValue());
        return !availableQuestions.isEmpty();
    }

}
