package com.example.takblet.ui.quiz;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.takblet.R;
import com.example.takblet.databinding.FragmentQuizBinding;
import com.example.takblet.ui.stats.QuizDatabase;
import com.example.takblet.ui.stats.QuizEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;
    private QuizDatabase quizDB;
    private QuizViewModel quizViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RoomDatabase.Callback myCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        quizDB = Room.databaseBuilder(
                this.requireContext(),
                QuizDatabase.class,
                "Statistics").addCallback(myCallback).build();

        binding.trueButton.setOnClickListener(view -> checkAnswer(true));
        binding.falseButton.setOnClickListener(view -> checkAnswer(false));
        binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
        binding.nextButton.setOnClickListener(view -> {
            quizViewModel.moveToNextQuestion();
            binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
        });

        binding.cheatButton.setOnClickListener(view -> {
            boolean answer = quizViewModel.getCurrentAnswer();
            Toast.makeText(this.getContext(), answer ? "true" : "false", Toast.LENGTH_SHORT).show();
            quizViewModel.setAnswerIsViewed(true);
        });
        return root;
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
            QuizEntity entity = new QuizEntity(correctAnswers, incorrectAnswers, cheatsUsed);
            addStatsInBackground(entity);

            new AlertDialog.Builder(this.getContext())
                    .setTitle("Quiz result")
                    .setMessage(correctAnswers + " correct answers,\n" + incorrectAnswers + " incorrect answers,\n" + cheatsUsed + " cheats used")
                    .setPositiveButton("Reset", (dialog, which) -> {
                        reset();
                        dialog.dismiss();
                    }).setOnCancelListener(dialog -> reset()).show();
        }
    }

    public void addStatsInBackground(QuizEntity entity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> quizDB.getQuizDAO().addQuizStats(entity));
    }

    public void reset() {
        quizViewModel.selectRandomQuestions();
        quizViewModel.setAnswerIsViewed(false);
        quizViewModel.setCorrectAnswers(0);
        quizViewModel.setIncorrectAnswers(0);
        quizViewModel.setCheatsUsed(0);
        binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
    }
}