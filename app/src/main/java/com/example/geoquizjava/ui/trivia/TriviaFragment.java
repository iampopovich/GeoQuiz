package com.example.geoquizjava.ui.trivia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geoquizjava.databinding.FragmentTriviaBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TriviaFragment extends Fragment {

    private FragmentTriviaBinding binding;
    private TriviaViewModel triviaViewModel;
    private RequestQueue requestQueue;
    private static final String FETCH_QUESTIONS = "FETCH_QUESTIONS";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        triviaViewModel = new ViewModelProvider(this).get(TriviaViewModel.class);
        binding = FragmentTriviaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            triviaViewModel.clearQuestions();
            binding.recyclerView.getAdapter().notifyDataSetChanged();
            fetchQuestion();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new TriviaAdapter(getContext(), triviaViewModel.getQuestionList()));
        requestQueue = Volley.newRequestQueue(this.requireContext());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchQuestion();
    }

    private void fetchQuestion() {
        StringRequest request = new StringRequest("https://opentdb.com/api.php?amount=20&type=boolean",
                response -> {
                    try {
                        JSONArray results = new JSONObject(response)
                                .getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            triviaViewModel.addQuestion(new TriviaQuestion(
                                    result.getString("question"),
                                    result.getString("correct_answer").equals("True"),
                                    result.getString("difficulty"),
                                    result.getString("category")
                            ));
                            binding.recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
        });
        request.setTag(FETCH_QUESTIONS);
        requestQueue.add(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        requestQueue.stop();
        requestQueue.cancelAll(FETCH_QUESTIONS);
    }
}