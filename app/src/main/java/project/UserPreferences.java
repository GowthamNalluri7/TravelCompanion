package project;

import java.io.Serializable;

class UserPreferences implements Serializable {
    int preferredDistance; // in meters
    String preferredCuisineType; // e.g., "Italian", "French"
    double preferredAvgPrice; // in dollars
    double preferredCustomerRating; // Range 1.0 to 5.0
    int preferredAmbiance; // Range 1 to 5

    public UserPreferences(int preferredDistance, String preferredCuisineType, double preferredAvgPrice, 
                           double preferredCustomerRating, int preferredAmbiance) {
        this.preferredDistance = preferredDistance;
        this.preferredCuisineType = preferredCuisineType;
        this.preferredAvgPrice = preferredAvgPrice;
        this.preferredCustomerRating = preferredCustomerRating;
        this.preferredAmbiance = preferredAmbiance;
    }
}
