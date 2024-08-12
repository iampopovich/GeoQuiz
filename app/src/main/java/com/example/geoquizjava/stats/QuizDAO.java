package com.example.geoquizjava.stats;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuizDAO  {

    @Insert
    public void addQuizStats(QuizEntity entity);

    @Query("SELECT * FROM Statistics")
    public List<QuizEntity> getAllStats();

    @Query("SELECT * FROM Statistics ORDER BY id DESC LIMIT 1")
    public QuizEntity getLatestGameStats();
}
