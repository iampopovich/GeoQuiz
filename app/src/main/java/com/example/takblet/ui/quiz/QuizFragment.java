package com.example.takblet.ui.quiz;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.takblet.R;
import com.example.takblet.databinding.FragmentQuizBinding;
import com.example.takblet.ui.stats.StatsDatabase;
import com.example.takblet.ui.stats.StatsEntity;
import java.util.concurrent.Executors;

public class QuizFragment extends Fragment {

  private FragmentQuizBinding binding;
  private StatsDatabase statsDB;
  private QuizViewModel quizViewModel;
  private SharedPreferences pref;

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentQuizBinding.inflate(inflater, container, false);
    pref = PreferenceManager.getDefaultSharedPreferences(requireContext());
    quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
    quizViewModel.selectRandomQuestions(10);
    binding.trueButton.setOnClickListener(view -> checkAnswer(true));
    binding.falseButton.setOnClickListener(view -> checkAnswer(false));
    binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
    binding.nextButton.setOnClickListener(view -> fetchNextQuestion());
    binding.cheatButton.setOnClickListener(view -> showCurrentAnswer());
    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private void checkAnswer(boolean answer) {
    boolean correctAnswer = quizViewModel.getCurrentAnswer();
    if (quizViewModel.getAnswerIsViewed()) {
      quizViewModel.setCheatsUsed(quizViewModel.getmCheatsUsed().getValue() + 1);
      Toast.makeText(this.getContext(), R.string.judgment_toast, Toast.LENGTH_SHORT).show();
    } else if (answer == correctAnswer) {
      Toast.makeText(this.getContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
      quizViewModel.setCorrectAnswers(quizViewModel.getmCorrectAnswers().getValue() + 1);
    } else {
      Toast.makeText(this.getContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
      quizViewModel.setIncorrectAnswers(quizViewModel.getmIncorrectAnswers().getValue() + 1);
    }
    updateQuestion();
  }

  private void updateQuestion() {
    if (quizViewModel.isAnyQuestionAvailable()) {
      quizViewModel.moveToNextQuestion();
      binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
    } else {
      int correctAnswers = quizViewModel.getmCorrectAnswers().getValue();
      int incorrectAnswers = quizViewModel.getmIncorrectAnswers().getValue();
      int cheatsUsed = quizViewModel.getmCheatsUsed().getValue();
      new AlertDialog.Builder(this.getContext())
          .setTitle("Quiz result")
          .setMessage(
              correctAnswers
                  + " correct answers,\n"
                  + incorrectAnswers
                  + " incorrect answers,\n"
                  + cheatsUsed
                  + " cheats used")
          .setPositiveButton(
              "Reset",
              (dialog, which) -> {
                reset();
                dialog.dismiss();
              })
          .setOnCancelListener(dialog -> reset())
          .show();
      addStatsInBackground(new StatsEntity(correctAnswers, incorrectAnswers, cheatsUsed));
    }
  }

  public void addStatsInBackground(StatsEntity entity) {
    RoomDatabase.Callback myCallback =
        new RoomDatabase.Callback() {
          @Override
          public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
          }

          @Override
          public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
          }
        };
    Executors.newSingleThreadExecutor().execute(
        () ->
            Room.databaseBuilder(this.requireContext(), StatsDatabase.class, "Statistics")
                .addCallback(myCallback)
                .build()
                .getStatsDAO()
                .addQuizStats(entity));
  }

  public void reset() {
    quizViewModel.selectRandomQuestions(10);
    quizViewModel.setAnswerIsViewed(false);
    quizViewModel.setCorrectAnswers(0);
    quizViewModel.setIncorrectAnswers(0);
    quizViewModel.setCheatsUsed(0);
    binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
  }

  public void fetchNextQuestion() {
    quizViewModel.moveToNextQuestion();
    binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
  }

  public void showCurrentAnswer() {
    Toast.makeText(
            this.getContext(),
            quizViewModel.getCurrentAnswer() ? "true" : "false",
            Toast.LENGTH_SHORT)
        .show();
    quizViewModel.setAnswerIsViewed(true);
  }
}
