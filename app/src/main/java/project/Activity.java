package project;

import java.io.Serializable;

public class Activity implements Serializable {
    public String name;
    boolean isOutdoorActivity;
    public double rating;
    public double avgCost;
    public int distance; // Distance in meters

    public Activity(String name, boolean isOutdoorActivity, double rating, double avgCost, int distance) {
        this.name = name;
        this.isOutdoorActivity = isOutdoorActivity;
        this.rating = rating;
        this.avgCost = avgCost;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return String.format("Activity Name: %s\nOutdoor: %b\nRating: %.1f\nAverage Cost: $%.2f\nDistance: %d meters\n",
                name, isOutdoorActivity, rating, avgCost, distance);
    }
}
