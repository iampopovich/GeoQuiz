package com.example.geoquizjava.ui.trivia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.geoquizjava.databinding.FragmentTriviaBinding;

public class TriviaFragment extends Fragment {

    private FragmentTriviaBinding binding;
    private TriviaViewModel triviaViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        triviaViewModel = new ViewModelProvider(this).get(TriviaViewModel.class);

        binding = FragmentTriviaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            triviaViewModel.clearQuestions();
            fetchQuestion();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new TriviaAdapter(getContext(), triviaViewModel.getQuestionList()));
        return root;
    }

    private void fetchQuestion() {
        // запрос вопроса
        triviaViewModel.addQuestion(new TriviaQuestion("Вопрос", true));
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}