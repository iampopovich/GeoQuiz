package com.example.takblet.ui.stats;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {QuizEntity.class}, version = 1, exportSchema = false)
public abstract class QuizDatabase extends RoomDatabase {

    public abstract QuizDAO getQuizDAO();
}
