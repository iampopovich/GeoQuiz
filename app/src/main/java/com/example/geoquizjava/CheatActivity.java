package com.example.geoquizjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoquizjava.databinding.ActivityCheatBinding;
import com.example.geoquizjava.databinding.ActivityMainBinding;

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER_SHOWN = "comexample.geoquizjava.answer_shown";
    private static final String EXTRA_ANSWER_IS_TRUE = "comexample.geoquizjava.answer_is_true";
    private final String TAG = "CheatActivity";
    private final String KEY_CHEATED = "cheated";
    private final String KEY_ANSWER_TEXT = "answerText";

    private CheatViewModel cheatViewModel;
    private boolean answerIsTrue;
    private ActivityCheatBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        answerIsTrue = false;

        cheatViewModel = new ViewModelProvider(this).get(CheatViewModel.class);
        try {
            cheatViewModel.cheatApplied = savedInstanceState.getBoolean(KEY_CHEATED, false);
            cheatViewModel.answerText = savedInstanceState.getString(KEY_ANSWER_TEXT, "");
        } catch (Exception e) {
            cheatViewModel.cheatApplied = false;
            cheatViewModel.answerText = "";
        }

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        binding.answerTextView.setText(cheatViewModel.answerText);

        binding.backButton.setOnClickListener(view -> finish());
        binding.showAnswerButton.setOnClickListener(view -> {
            if (answerIsTrue) binding.answerTextView.setText(R.string.button_true);
            else binding.answerTextView.setText(R.string.button_false);
            setAnswerShown();
            int cx = binding.showAnswerButton.getWidth() / 2;
            int cy = binding.showAnswerButton.getHeight() / 2;
            float radius = binding.showAnswerButton.getWidth();
            Animator animator = ViewAnimationUtils.createCircularReveal(binding.showAnswerButton, cx, cy, radius, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.showAnswerButton.setVisibility(View.INVISIBLE);
                    binding.backButton.setVisibility(View.VISIBLE);
                }
            });
            animator.start();
        });
    }

    private void setAnswerShown() {
        Intent intent = new Intent().putExtra(EXTRA_ANSWER_SHOWN, true);
        cheatViewModel.cheatApplied = true;
        cheatViewModel.answerText = binding.answerTextView.getText().toString();
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_CHEATED, cheatViewModel.cheatApplied);
        savedInstanceState.putString(KEY_ANSWER_TEXT, cheatViewModel.answerText);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        return new Intent(packageContext, CheatActivity.class).putExtra(
                EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    }

}