package com.example.takblet.ui.stats;

import androidx.lifecycle.LiveData;
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
        List<StatsItem> items = Objects.requireNonNull(mStats.getValue());
        items.add(item);
        mStats.postValue(items);
    }

    public void clearEntities() {
        Objects.requireNonNull(mStats.getValue()).clear();
    }

    public LiveData<List<StatsItem>> getStats() {
        return mStats;
    }

}