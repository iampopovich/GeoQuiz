package com.example.takblet.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.takblet.databinding.FragmentStatsBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class StatsFragment extends Fragment {

    private QuizDatabase quizDB;
    private FragmentStatsBinding binding;
    private StatsViewModel statsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        statsViewModel = new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        binding.quizResultsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        StatsAdapter adapter = new StatsAdapter(requireContext(), statsViewModel.getStats());
        binding.quizResultsRecyclerView.setAdapter(adapter);
        quizDB = Room.databaseBuilder(requireContext(), QuizDatabase.class, "Statistics").addCallback(myCallback).build();
        getStatsInBackground();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void getStatsInBackground() {
        statsViewModel.clearEntities();
        Executors.newSingleThreadExecutor().execute(() -> {
            for (QuizEntity entity : quizDB.getQuizDAO().getAllStats()) {
                if (entity == null) return;
                statsViewModel.addEntity(new QuizItem(entity.getCorrectAnswers(), entity.getIncorrectAnswers(), entity.getCheatsUsed()));
            }
            requireActivity().runOnUiThread(() -> {
                binding.quizResultsRecyclerView.getAdapter().notifyDataSetChanged();
            });
        });
    }

}