package com.example.takblet.ui.trivia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.takblet.R;
import com.example.takblet.databinding.FragmentTriviaBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TriviaFragment extends Fragment {

    private FragmentTriviaBinding binding;
    private TriviaViewModel triviaViewModel;
    private RequestQueue requestQueue;
    private static final String FETCH_QUESTIONS = "FETCH_QUESTIONS";
    private MenuProvider menuProvider;
    private int questionsAmount;
    private String category;
    private String difficulty;


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
        TriviaAdapter adapter = new TriviaAdapter(getContext(), triviaViewModel.getQuestionList().getValue());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        triviaViewModel.getQuestionList().observe(getViewLifecycleOwner(), questions -> adapter.notifyDataSetChanged());
        requestQueue = Volley.newRequestQueue(this.requireContext());
        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.settings_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_settings) {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.settingsFragment);
                    return true;
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider);
        loadSettings();
        return root;
    }

    private void loadSettings() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireContext());
        questionsAmount = Integer.parseInt(pref.getString("amount_preference", "10"));
        category = pref.getString("category_preference", "");
        difficulty = pref.getString("difficulty_preference", "");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchQuestion();
    }

    private void fetchQuestion() {
        StringBuilder url = new StringBuilder("https://opentdb.com/api.php?")
                .append("amount=").append(questionsAmount)
                .append("&type=boolean");
        if (!category.isEmpty()) url.append("&category=").append(category);
        if (!difficulty.isEmpty()) url.append("&difficulty=").append(difficulty);
        StringRequest request = new StringRequest(url.toString(),
                response -> {
                    try {
                        JSONArray results = new JSONObject(response).getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            triviaViewModel.addQuestion(new TriviaQuestion(
                                    result.getString("question"),
                                    result.getString("correct_answer").equals("True"),
                                    result.getString("difficulty"),
                                    result.getString("category")
                            ));
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
        requireActivity().removeMenuProvider(menuProvider);
    }
}