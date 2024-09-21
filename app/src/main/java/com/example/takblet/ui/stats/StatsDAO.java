package com.example.takblet.ui.stats;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface StatsDAO {

  @Insert
  void addQuizStats(StatsEntity entity);

  @Query("SELECT * FROM Statistics")
  List<StatsEntity> getAllStats();

  @Query("SELECT * FROM Statistics ORDER BY id DESC LIMIT 1")
  StatsEntity getLatestGameStats();
}
