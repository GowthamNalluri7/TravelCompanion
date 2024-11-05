package com.example.travelcompanion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private final List<String> recommendations;

    public RecommendationAdapter(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String recommendation = recommendations.get(position);
        holder.recommendationTextView.setText(recommendation);
    }

    @Override
    public int getItemCount() {
        return recommendations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView recommendationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendationTextView = itemView.findViewById(R.id.recommendationTextView);
        }
    }
}
