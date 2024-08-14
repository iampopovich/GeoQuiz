package com.example.geoquizjava.cheats;

import androidx.lifecycle.ViewModel;

public class CheatViewModel extends ViewModel {
    private final String TAG = "CheatViewModel";
    boolean cheatApplied = false;
    String answerText = "";
    int answerTextResId = 0;
}
