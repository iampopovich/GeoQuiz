package com.example.geoquizjava.results;

import static java.lang.String.format;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.geoquizjava.quiz.QuizActivity;
import com.example.geoquizjava.R;
import com.example.geoquizjava.databinding.ActivityResultBinding;
import com.example.geoquizjava.stats.StatsActivity;

public class ResultActivity extends AppCompatActivity {

    private static final String EXTRA_CORRECT_ANSWERS = "com.example.geoquizjava.correct_answers";
    private static final String EXTRA_INCORRECT_ANSWERS = "com.example.geoquizjava.incorrect_answers";
    private static final String EXTRA_CHEATS_USED = "com.example.geoquizjava.cheats_used";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResultBinding binding = ActivityResultBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            binding.versionText.setText(version);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.statsButton.setOnClickListener(view -> {
            Intent statsIntent = new Intent(this, StatsActivity.class);
            startActivity(statsIntent);
        });

        binding.resetButton.setOnClickListener(view -> {
            finish();
            Intent mainIntent = new Intent(this, QuizActivity.class);
            startActivity(mainIntent);
        });



        String correctAnswers = format("Correct: %d", getIntent().getIntExtra(EXTRA_CORRECT_ANSWERS, 0));
        String incorrectAnswers = format("Incorrect: %d", getIntent().getIntExtra(EXTRA_INCORRECT_ANSWERS, 0));
        String cheatsUsed = format("Cheats used: %d", getIntent().getIntExtra(EXTRA_CHEATS_USED, 0));
        binding.resultTextView.setText(format("%s\n%s\n%s", correctAnswers, incorrectAnswers, cheatsUsed));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static Intent newIntent(Context packageContext, int correctAnswers, int incorrectAnswers, int cheatsUsed) {
        return new Intent(packageContext, ResultActivity.class)
                .putExtra(EXTRA_CORRECT_ANSWERS, correctAnswers)
                .putExtra(EXTRA_INCORRECT_ANSWERS, incorrectAnswers)
                .putExtra(EXTRA_CHEATS_USED, cheatsUsed);
    }
}