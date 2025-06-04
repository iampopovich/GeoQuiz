package com.example.takblet.ui.stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatsViewModel extends ViewModel {

  private final MutableLiveData<List<StatsItem>> stats;

  public StatsViewModel() {
    stats = new MutableLiveData<>();
    stats.setValue(new ArrayList<>());
  }

  public void addEntity(StatsItem item) {
    List<StatsItem> items = Objects.requireNonNull(stats.getValue());
    items.add(item);
    stats.postValue(items);
  }

  public void clearEntities() {
    Objects.requireNonNull(stats.getValue()).clear();
  }

  public LiveData<List<StatsItem>> getStats() {
    return stats;
  }
}
