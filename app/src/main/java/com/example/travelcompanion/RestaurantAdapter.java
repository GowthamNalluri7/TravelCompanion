package com.example.travelcompanion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> restaurants;

    public RestaurantAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.restaurantTitle.setText(restaurant.name + "| " + restaurant.cuisineType);
        holder.restaurantDetails.setText("Ambiance: " + restaurant.ambiance + " | Rating: " + restaurant.rating + " | Cost: $" + restaurant.averageCost);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantTitle, restaurantDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantTitle = itemView.findViewById(R.id.restaurantTitle);
            restaurantDetails = itemView.findViewById(R.id.restaurantDetails);
        }
    }
}