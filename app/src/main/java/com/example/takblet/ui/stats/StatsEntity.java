package com.example.takblet.ui.stats;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Statistics")
public class StatsEntity {

    @ColumnInfo(name = "cheats_used")
    int cheatsUsed;
    @ColumnInfo(name = "correct_answers")
    int correctAnswers;
    @ColumnInfo(name = "incorrect_answers")
    int incorrectAnswers;
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    public StatsEntity(int cheatsUsed, int correctAnswers, int incorrectAnswers) {
        this.cheatsUsed = cheatsUsed;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public int getCheatsUsed() {
        return cheatsUsed;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }


}
