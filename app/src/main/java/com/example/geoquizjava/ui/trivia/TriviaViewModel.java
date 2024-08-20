package com.example.geoquizjava.ui.trivia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TriviaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TriviaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}