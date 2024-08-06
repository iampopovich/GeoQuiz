package com.example.geoquiz;

import androidx.lifecycle.ViewModel;

import java.util.Arrays;

public class CheatViewModel extends ViewModel {
    private final String TAG = "CheatViewModel";
    boolean cheatApplied = false;
    String answerText = "";
    int answerTextResId = 0;
}
