package com.example.takblet.ui.stats;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StatsEntity.class}, version = 1, exportSchema = false)
public abstract class StatsDatabase extends RoomDatabase {

    public abstract StatsDAO getStatsDAO();
}
