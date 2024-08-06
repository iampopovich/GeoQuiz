package com.example.geoquiz;

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

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER_SHOWN = "comexample.geoquiz.answer_shown";
    private static final String EXTRA_ANSWER_IS_TRUE = "comexample.geoquiz.answer_is_true";
    private final String TAG = "CheatActivity";
    private final String KEY_CHEATED = "cheated";
    private final String KEY_ANSWER_TEXT = "answerText";

    private TextView answerTextView;
    private Button showAnswerButton;
    private CheatViewModel cheatViewModel;
    private boolean answerIsTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

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

        answerTextView = findViewById(R.id.answer_text_view);
        answerTextView.setText(cheatViewModel.answerText);

        showAnswerButton = findViewById(R.id.show_answer_button);
        showAnswerButton.setOnClickListener(view -> {
            if (answerIsTrue) answerTextView.setText(R.string.button_true);
            else answerTextView.setText(R.string.button_false);
            setAnswerShown();
            int cx = showAnswerButton.getWidth() / 2;
            int cy = showAnswerButton.getHeight() / 2;
            float radius = showAnswerButton.getWidth();
            Animator animator = ViewAnimationUtils.createCircularReveal(showAnswerButton, cx, cy, radius, 0);
            animator.addListener(new AnimatorListenerAdapter(){
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    showAnswerButton.setVisibility(View.INVISIBLE);
                }
            });
            animator.start();
        });

    }

    private void setAnswerShown() {
        Intent intent = new Intent().putExtra(EXTRA_ANSWER_SHOWN, true);
        cheatViewModel.cheatApplied = true;
        cheatViewModel.answerText = answerTextView.getText().toString();
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