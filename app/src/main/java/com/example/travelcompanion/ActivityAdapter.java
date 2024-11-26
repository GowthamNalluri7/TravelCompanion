package com.example.travelcompanion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.Activity;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<Activity> activities;

    public ActivityAdapter(List<Activity> activities) {
        this.activities = activities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.activityTitle.setText(activity.name + "| " + activity.distance + "M away");
        holder.activityDetails.setText("Rating: " + activity.rating + " | Cost: $" + activity.avgCost);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityTitle, activityDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityTitle = itemView.findViewById(R.id.activityTitle);
            activityDetails = itemView.findViewById(R.id.activityDetails);
        }
    }
}
