package com.example.geoquizjava.quiz;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.geoquizjava.R;
import com.example.geoquizjava.cheats.CheatActivity;
import com.example.geoquizjava.databinding.ActivityMainBinding;
import com.example.geoquizjava.results.ResultActivity;
import com.example.geoquizjava.stats.QuizDatabase;
import com.example.geoquizjava.stats.QuizEntity;
import com.example.geoquizjava.stats.StatsActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class QuizActivity extends AppCompatActivity {


    private QuizDatabase quizDB;
    private ActivityMainBinding binding;
    private final String TAG = "MainActivity";
    private final String KEY_INDEX = "index";
    private final String KEY_CHEATER = "cheater";

    private QuizViewModel quizViewModel;

    private int questionTextResId;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int cheatsUsed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        quizDB = Room.databaseBuilder(this, QuizDatabase.class, "Statistics").addCallback(myCallback).build();

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        questionTextResId = quizViewModel.getCurrentQuestionText();
        binding.trueButton.setOnClickListener(view -> checkAnswer(true));
        binding.falseButton.setOnClickListener(view -> checkAnswer(false));
        binding.questionTextView.setText(questionTextResId);
        binding.nextButton.setOnClickListener(view -> {
            quizViewModel.moveToNext();
            updateQuestion();
        });

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            binding.versionText.setText(version);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActivityResultLauncher<Intent> requestCheat =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        boolean isCheater = data != null && data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
                        quizViewModel.isCheater(isCheater);
                    }
                });

        binding.cheatButton.setOnClickListener(view -> {
            boolean answerIsTrue = quizViewModel.getCurrentAnswer();
            Intent cheatIntent = CheatActivity.newIntent(this, answerIsTrue);
            requestCheat.launch(cheatIntent);
        });
        binding.statsButton.setOnClickListener(view -> {
            Intent statsIntent = new Intent(this, StatsActivity.class);
            startActivity(statsIntent);
        });
    }

    private void checkAnswer(boolean answer) {
        boolean correctAnswer = quizViewModel.getCurrentAnswer();
        if (quizViewModel.isCheater()) {
            cheatsUsed++;
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show();
        } else if (answer == correctAnswer) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            correctAnswers++;
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            incorrectAnswers++;
        }
        quizViewModel.setViewed();
        quizViewModel.moveToNext();
        updateQuestion();
    }

    private void updateQuestion() {
        if (quizViewModel.isAnyQuestionAvailable()) {
            binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
        } else {
            QuizEntity entity = new QuizEntity(cheatsUsed, correctAnswers, incorrectAnswers);
            addStatsInBackground(entity);
            finish();
            Intent result = ResultActivity.newIntent(this, correctAnswers, incorrectAnswers, cheatsUsed);
            startActivity(result);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.getCurrentIndex());
        savedInstanceState.putBoolean(KEY_CHEATER, quizViewModel.isCheater());
    }

    public void addStatsInBackground(QuizEntity entity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> quizDB.getQuizDAO().addQuizStats(entity));
    }

}