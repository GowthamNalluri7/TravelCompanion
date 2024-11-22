package project;


import java.io.Serializable;

public class UserPreferencesForActivities implements Serializable {
    boolean preferredOutdoorActivity;  // User prefers outdoor or indoor activities
    double preferredRating;            // Minimum preferred rating (1.0 to 5.0)
    double preferredAvgCost;          // Maximum preferred average cost (in dollars)
    int preferredDistance;            // Maximum preferred distance in meters

    // Constructor to initialize user preferences
    public UserPreferencesForActivities(boolean preferredOutdoorActivity, double preferredRating, 
                                         double preferredAvgCost, int preferredDistance) {
        this.preferredOutdoorActivity = preferredOutdoorActivity;
        this.preferredRating = preferredRating;
        this.preferredAvgCost = preferredAvgCost;
        this.preferredDistance = preferredDistance;
    }

    @Override
    public String toString() {
        return String.format("Preferred Outdoor Activity: %b\nPreferred Rating: %.1f\nPreferred Avg Cost: $%.2f\nPreferred Distance: %d meters\n",
                preferredOutdoorActivity, preferredRating, preferredAvgCost, preferredDistance);
    }
}
