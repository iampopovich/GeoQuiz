package com.example.takblet.stats;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.takblet.R;
import com.example.takblet.databinding.ActivityStatsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class StatsActivity extends AppCompatActivity {

    private ActivityStatsBinding binding;
    private QuizDatabase quizDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RoomDatabase.Callback myCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        quizDB = Room.databaseBuilder(this, QuizDatabase.class, "Statistics").addCallback(myCallback).build();

        getStatsInBackground();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void getStatsInBackground() {
        List<QuizItem> quizItemList = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            for (QuizEntity entity : quizDB.getQuizDAO().getAllStats()) {
                if (entity == null) return;
                quizItemList.add(new QuizItem(entity.getCorrectAnswers(), entity.getIncorrectAnswers(), entity.getCheatsUsed()));
            }
            binding.quizResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.quizResultsRecyclerView.setAdapter(new StatsAdapter(quizItemList));
        });
    }
}