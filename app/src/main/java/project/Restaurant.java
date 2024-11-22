package project;

import java.io.Serializable;

public class Restaurant implements Serializable {
    public String name;
    String priceRange;
    boolean reservations;
    public int ambiance;
    public double averageCost;
    public double rating;
    String cuisineType;  // Cuisine type as a string
    Integer distance;    // Distance from the reference point (in meters)

    public Restaurant(String name, String priceRange, boolean reservations, int ambiance, double averageCost, double rating, String cuisineType, Integer distance) {
        this.name = name;
        this.priceRange = priceRange;
        this.reservations = reservations;
        this.ambiance = ambiance;
        this.averageCost = averageCost;
        this.rating = rating;
        this.cuisineType = cuisineType;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nPrice Range: %s\nReservations: %b\nAmbiance: %d\nAverage Cost: $%.2f\nRating: %.1f\nCuisine Type: %s\nDistance: %d meters\n",
                name, priceRange, reservations, ambiance, averageCost, rating, cuisineType, distance);
    }
}
