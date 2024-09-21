package com.example.takblet.ui.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.takblet.R;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

  private final List<StatsItem> statsItems;

  public StatsAdapter(Context context, List<StatsItem> statsItems) {
    this.statsItems = statsItems;
  }

  @NonNull
  @Override
  public StatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_stats_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull StatsAdapter.ViewHolder holder, int position) {
    StatsItem statsItem = statsItems.get(position);
    holder.correctAnswersText.setText(
        holder.correctAnswersText.getContext().getString(R.string.stats_item_correct_answers)
            + statsItem.getCorrectAnswers());
    holder.incorrectAnswersText.setText(
        holder.incorrectAnswersText.getContext().getString(R.string.stats_item_wrong_answers)
            + statsItem.getIncorrectAnswers());
    holder.cheatsUsedText.setText(
        holder.cheatsUsedText.getContext().getString(R.string.stats_item_cheats_used)
            + statsItem.getCheatsUsed());
  }

  @Override
  public int getItemCount() {
    return this.statsItems.size();
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
