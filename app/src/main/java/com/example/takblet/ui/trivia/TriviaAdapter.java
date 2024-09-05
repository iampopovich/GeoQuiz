package com.example.takblet.ui.trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takblet.R;

import java.util.List;

public class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.ViewHolder> {

    private Context context;
    private final List<TriviaQuestion> questionList;

    public TriviaAdapter(Context context, List<TriviaQuestion> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public TriviaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(
                R.layout.trivia_question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TriviaAdapter.ViewHolder holder, int position) {
        TriviaQuestion item = questionList.get(position);
        holder.question.setText(item.getQuestion());
        holder.trueButton.setOnClickListener(v -> {
            if (position < questionList.size()) {
                Toast.makeText(context, item.getAnswer() ? "correct" : "incorrect", Toast.LENGTH_SHORT).show();
                questionList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, questionList.size());
                item.setAnswered();
            }
        });
        holder.falseButton.setOnClickListener(v -> {
            if (position < questionList.size()) {
                Toast.makeText(context, item.getAnswer() ? "incorrect" : "correct", Toast.LENGTH_SHORT).show();
                questionList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, questionList.size());
                item.setAnswered();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView question;
        private final ImageButton trueButton;
        private final ImageButton falseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.questionTextView);
            trueButton = itemView.findViewById(R.id.trueButton);
            falseButton = itemView.findViewById(R.id.falseButton);
        }
    }
}
