package com.example.takblet.ui.stats;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatsViewModel extends ViewModel {

    private final MutableLiveData<List<QuizItem>> mStats;

    public StatsViewModel() {
        mStats = new MutableLiveData<>();
        mStats.setValue(new ArrayList<QuizItem>());
    }

    public void addEntity(QuizItem item){
        Objects.requireNonNull(mStats.getValue()).add(item);
    }

    public void clearEntities() {
        Objects.requireNonNull(mStats.getValue()).clear();
    }

    public List<QuizItem> getStats() {
        return mStats.getValue();
    }

}