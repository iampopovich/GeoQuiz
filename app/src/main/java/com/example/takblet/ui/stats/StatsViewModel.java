package com.example.takblet.ui.stats;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatsViewModel extends ViewModel {

    private final MutableLiveData<List<StatsItem>> mStats;

    public StatsViewModel() {
        mStats = new MutableLiveData<>();
        mStats.setValue(new ArrayList<StatsItem>());
    }

    public void addEntity(StatsItem item){
        Objects.requireNonNull(mStats.getValue()).add(item);
    }

    public void clearEntities() {
        Objects.requireNonNull(mStats.getValue()).clear();
    }

    public List<StatsItem> getStats() {
        return mStats.getValue();
    }

}