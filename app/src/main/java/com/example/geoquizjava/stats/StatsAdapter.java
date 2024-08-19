package com.example.geoquizjava.stats;

import static java.lang.String.format;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoquizjava.R;

import java.util.List;


public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {


    private final List<QuizItem> quizItems;

    public StatsAdapter(List<QuizItem> quizItems) {
        this.quizItems = quizItems;
    }

    @NonNull
    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_stats_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsAdapter.ViewHolder holder, int position) {
        QuizItem newsItem = quizItems.get(position);
        holder.correctAnswersText.setText(format("correct answers: %d", newsItem.getCorrectAnswers()));
        holder.incorrectAnswersText.setText(format("incorrect answers: %d", newsItem.getIncorrectAnswers()));
        holder.cheatsUsedText.setText(format("cheats used: %d", newsItem.getCheatsUsed()));
    }

    @Override
    public int getItemCount() {
        return this.quizItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView correctAnswersText;
        private final TextView incorrectAnswersText;
        private final TextView cheatsUsedText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            correctAnswersText = itemView.findViewById(R.id.correctAnswersText);
            incorrectAnswersText = itemView.findViewById(R.id.incorrectAnswersText);
            cheatsUsedText = itemView.findViewById(R.id.cheatsUsedText);
        }
    }
}
