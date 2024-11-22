package project;
import java.util.ArrayList;
import java.util.List;

public class ActivityRecommender {

    // Method to calculate cosine similarity between two vectors
    public static double calculateCosineSimilarity(double[] vector1, double[] vector2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        // Calculate dot product and magnitudes
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            magnitude1 += Math.pow(vector1[i], 2);
            magnitude2 += Math.pow(vector2[i], 2);
        }

        // Compute the cosine similarity
        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }

    // Method to recommend activities based on user preferences and cosine similarity
    public  List<Activity> recommendActivities(UserPreferencesForActivities userPreferences, List<Activity> activities) {
        List<Activity> recommendedActivities = new ArrayList<>();

        // Prepare user preference vector (with normalized values)
        double[] userPreferenceVector = new double[4];
        
        // Outdoor Activity: Binary (1 for outdoor, 0 for indoor)
        userPreferenceVector[0] = userPreferences.preferredOutdoorActivity ? 1 : 0;

        // Average Cost: Normalize by assuming max cost as 100
        userPreferenceVector[1] = userPreferences.preferredAvgCost / 100.0;  // Assuming $100 is max cost for normalization

        // Rating: Normalize by assuming max rating is 5
        userPreferenceVector[2] = userPreferences.preferredRating / 5.0;

        // Distance: Normalize by assuming max distance is 5000 meters
        userPreferenceVector[3] = userPreferences.preferredDistance / 5000.0;

        // Loop over all activities to calculate cosine similarity
        for (Activity activity : activities) {
            // Prepare activity vector (with normalized values)
            double[] activityVector = new double[4];

            // Outdoor Activity: Binary (1 for outdoor, 0 for indoor)
            activityVector[0] = activity.isOutdoorActivity ? 1 : 0;

            // Average Cost: Normalize by assuming max cost as 100
            activityVector[1] = activity.avgCost / 100.0;

            // Rating: Normalize by assuming max rating is 5
            activityVector[2] = activity.rating / 5.0;

            // Distance: Normalize by assuming max distance is 5000 meters
            activityVector[3] = activity.distance / 5000.0;

            // Calculate cosine similarity between user preferences and the activity
            double cosineSimilarity = calculateCosineSimilarity(userPreferenceVector, activityVector);

            // Apply weights based on the priority order: Outdoor Activity (4), Average Cost (3), Rating (2), Distance (1)
            double weightedScore = (cosineSimilarity * 4) +  // Outdoor Activity weight
                                   (cosineSimilarity * 3) +  // Average Cost weight
                                   (cosineSimilarity * 2) +  // Rating weight
                                   (cosineSimilarity * 1);   // Distance weight

            // Print the similarity score
            System.out.println("Activity: " + activity.name + " - Similarity Score: " + weightedScore);

            // If the cosine similarity score is above the threshold of 0.9, add to recommended activities
            if (weightedScore > 9) {  // Threshold set to 0.9 for high match
                recommendedActivities.add(activity);
            }
        }

        return recommendedActivities;
    }

    // public static void main(String[] args) {
    //     // Sample Activities
    //     List<Activity> activities = new ArrayList<>();
    //     ActivityFinder af=new ActivityFinder();
    //     af.load();
    //     // activities.add(new Activity("Hiking in the Mountains", true, 4.5, 30.00, 800));
    //     // activities.add(new Activity("Indoor Rock Climbing", false, 4.3, 20.00, 500));
    //     // activities.add(new Activity("City Park Jogging", true, 4.0, 10.00, 300));
    //     // activities.add(new Activity("Museum Visit", false, 4.2, 15.00, 1000));
    //     // activities.add(new Activity("Cycling in the Park", true, 4.7, 25.00, 1200));

    //     // Sample User Preferences
    //     // UserPreferencesForActivities userPrefs = new UserPreferencesForActivities(true, 4.0, 25.00, 1000);

    //     // // Get recommendations based on user preferences
    //     // List<Activity> recommendedActivities = recommendActivities(userPrefs,af.griffintownActivities);

    //     // // Print recommended activities
    //     // System.out.println("\nRecommended Activities:");
    //     // for (Activity activity : recommendedActivities) {
    //     //     System.out.println(activity);
    //     }
   // }
}
