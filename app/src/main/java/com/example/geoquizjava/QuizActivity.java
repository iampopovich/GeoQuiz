package com.example.geoquizjava;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquizjava.databinding.ActivityMainBinding;


public class QuizActivity extends AppCompatActivity {



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
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            Intent intent = CheatActivity.newIntent(this, answerIsTrue);
            requestCheat.launch(intent);
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
        } else if (answer != correctAnswer) {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            incorrectAnswers++;
        } else {
        }
        quizViewModel.setViewed();
        quizViewModel.moveToNext();
        updateQuestion();
    }

    private void updateQuestion() {
        if (quizViewModel.isAnyQuestionAvailable()) {
            binding.questionTextView.setText(quizViewModel.getCurrentQuestionText());
        } else {
            binding.questionTextView.setText(
                    String.format("No more questions! Correct: %d Incorrect: %d", correctAnswers, incorrectAnswers));
            binding.controlsLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.getCurrentIndex());
        savedInstanceState.putBoolean(KEY_CHEATER, quizViewModel.isCheater());
    }

}