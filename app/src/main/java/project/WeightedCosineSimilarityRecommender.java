package project;

import java.util.*;

public class WeightedCosineSimilarityRecommender {

    public List<Restaurant> recommendRestaurants(UserPreferences preferences, List<Restaurant> restaurants, double threshold) {
        List<Restaurant> recommendedRestaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            // Pre-filter based on exact cuisine type match
            if (!restaurant.cuisineType.equalsIgnoreCase(preferences.preferredCuisineType)) {
                continue;
            }

            // Calculate cosine similarity
            double similarity = calculateWeightedCosineSimilarity(preferences, restaurant);

            // Only recommend restaurants above the threshold
            if (similarity >= threshold) {
                recommendedRestaurants.add(restaurant);
            }
        }

        // Sort by similarity score in descending order
        recommendedRestaurants.sort((a, b) -> Double.compare(
            calculateWeightedCosineSimilarity(preferences, b),
            calculateWeightedCosineSimilarity(preferences, a)
        ));

        return recommendedRestaurants;
    }

    // Method to calculate cosine similarity with higher weight for Cuisine Type, Average Price, and Customer Rating
    private double calculateWeightedCosineSimilarity(UserPreferences preferences, Restaurant restaurant) {
        // Define feature vectors with weights for different features
        double[] userVector = {
            preferences.preferredDistance,                         // Distance (weight = 1)
            preferences.preferredAvgPrice * 2,                     // Average Price (weight = 2)
            preferences.preferredCustomerRating * 2,               // Customer Rating (weight = 2)
            preferences.preferredAmbiance,                         // Ambiance (weight = 1)
            getCuisineTypeEncoding(preferences.preferredCuisineType) * 3 // Cuisine Type (weight = 3)
        };

        double[] restaurantVector = {
            restaurant.distance,                                   // Distance (weight = 1)
            restaurant.averageCost * 2,                             // Average Price (weight = 2)
            restaurant.rating * 2,                                  // Customer Rating (weight = 2)
            restaurant.ambiance,                                    // Ambiance (weight = 1)
            getCuisineTypeEncoding(restaurant.cuisineType) * 3     // Cuisine Type (weight = 3)
        };

        // Calculate dot product and magnitudes
        double dotProduct = 0.0;
        double userMagnitude = 0.0;
        double restaurantMagnitude = 0.0;

        for (int i = 0; i < userVector.length; i++) {
            dotProduct += userVector[i] * restaurantVector[i];
            userMagnitude += userVector[i] * userVector[i];
            restaurantMagnitude += restaurantVector[i] * restaurantVector[i];
        }

        userMagnitude = Math.sqrt(userMagnitude);
        restaurantMagnitude = Math.sqrt(restaurantMagnitude);

        // Prevent division by zero
        if (userMagnitude == 0 || restaurantMagnitude == 0) return 0.0;

        // Return cosine similarity
        return dotProduct / (userMagnitude * restaurantMagnitude);
    }

    // Helper method to encode cuisine type as a binary value
    private int getCuisineTypeEncoding(String cuisineType) {
        Map<String, Integer> cuisineEncoding = new HashMap<>();
        cuisineEncoding.put("FRENCH", 1);
        cuisineEncoding.put("ITALIAN", 2);
        cuisineEncoding.put("QUEBECOISE", 3);
        cuisineEncoding.put("AMERICAN", 4);
        cuisineEncoding.put("INDIAN", 5);

        return cuisineEncoding.getOrDefault(cuisineType.toUpperCase(), 0);
    }

    // public static void main(String[] args) {
    //     // Load restaurants
    //     RestaurantFinder finder = new RestaurantFinder();
    //     finder.load();

    //     // User preferences
    //     UserPreferences preferences = new UserPreferences(500, "FRENCH", 10.00, 4.0, 4);

    //     // Get list of restaurants from a specific neighborhood
    //     List<Restaurant> plateauRestaurants = finder.neighborhoods.get("Plateau Mont-Royal");

    //     // Create recommender and get recommendations
    //     WeightedCosineSimilarityRecommender recommender = new WeightedCosineSimilarityRecommender();
    //     double threshold = 0.9; // Set a high similarity threshold
    //     List<Restaurant> recommendations = recommender.recommendRestaurants(preferences, plateauRestaurants, threshold);

    //     // Print recommended restaurants
    //     System.out.println("Top Recommended Restaurants (Similarity >= " + threshold + "):");
    //     for (Restaurant restaurant : recommendations) {
    //         System.out.println(restaurant);
    //     }
    // }
}
