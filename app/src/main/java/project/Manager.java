package project;

import java.util.List;

public class Manager {
    public List<Activity> FinalListActivity;
    public List<Restaurant> FinalListRestaurant;

    public void Manage(String Weather, String Location, String Time, String bkf, String lunch, String dinner, String UserCType,
                       double PAvgPrice, double Prating, int pdistance, String UserActP, int Pambiance) {
        // Create instances of ActivityFinder and RestaurantFinder
        ActivityFinder af = new ActivityFinder();
        RestaurantFinder rf = new RestaurantFinder();

        // Load activities and restaurants
        rf.load();
        af.load();

        // Activity Recommendations
        ActivityRecommender Ar = new ActivityRecommender();
        UserPreferencesForActivities ActivityUser = new UserPreferencesForActivities(
                UserActP.equals("Outdoor"), Prating, PAvgPrice, pdistance
        );

        switch (Location) {
            case "Plateau Mont-Royal":
                FinalListActivity = Ar.recommendActivities(ActivityUser, af.plateauActivities);
                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
                break;
            case "Old Montreal":
                FinalListActivity = Ar.recommendActivities(ActivityUser, af.oldMontrealActivities);
                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
                break;
            case "Mile End":
                FinalListActivity = Ar.recommendActivities(ActivityUser, af.mileEndActivities);
                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
                break;
            case "Downtown Montreal":
                FinalListActivity = Ar.recommendActivities(ActivityUser, af.downtownActivities);
                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
                break;
            case "Griffintown":
                FinalListActivity = Ar.recommendActivities(ActivityUser, af.griffintownActivities);
                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
                break;
            default:
                System.out.println("No recommendations for activities or restaurants");
                break;
        }
    }

    // Helper method to fetch restaurant recommendations
    private List<Restaurant> fetchRestaurantRecommendations(String Location, RestaurantFinder rf, int pdistance,
                                                            String UserCType, double PAvgPrice, double Prating, int Pambiance) {
        WeightedCosineSimilarityRecommender recommender = new WeightedCosineSimilarityRecommender();
        double threshold = 0.9;

        UserPreferences preferences = new UserPreferences(pdistance, UserCType, PAvgPrice, Prating, Pambiance);

        switch (Location) {
            case "Plateau Mont-Royal":
                return recommender.recommendRestaurants(preferences, rf.plateauRestaurants, threshold);
            case "Old Montreal":
                return recommender.recommendRestaurants(preferences, rf.oldMontrealRestaurants, threshold);
            case "Mile End":
                return recommender.recommendRestaurants(preferences, rf.mileEndRestaurants, threshold);
            case "Downtown Montreal":
                return recommender.recommendRestaurants(preferences, rf.downtownRestaurants, threshold);
            case "Griffintown":
                return recommender.recommendRestaurants(preferences, rf.griffintownRestaurants, threshold);
            default:
                return null;
        }
    }
}
