package project;

import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Manager {
    public List<Activity> FinalListActivity;
    public List<Restaurant> FinalListRestaurant;

    Logger logger = Logger.getLogger(Manager.class.getName());
    public void Manage(String Weather, String Location, String Time, String bkf, String lunch, String dinner, String UserCType,
                       double PAvgPrice, double Prating, int pdistance, String UserActP, int Pambiance) {
        // Create instances of ActivityFinder and RestaurantFinder
        ActivityFinder af = new ActivityFinder();
        RestaurantFinder rf = new RestaurantFinder();


        // Load activities and restaurants
        rf.load();
        af.load();

      //logger.info("displaying bkf " +bkf);
        // Activity Recommendations
//        ActivityRecommender Ar = new ActivityRecommender();
//        UserPreferencesForActivities ActivityUser = new UserPreferencesForActivities(
//                UserActP.equals("Outdoor"), Prating, PAvgPrice, pdistance
//        );


        // if( ABS(TIME-BKF)<=1 || ABS(TIME-LUNCH)<=1 || )

//        logger.info("TIMES DISPLAY: "+Time + bkf + lunch + dinner + Weather);


        try {
            // Parse times
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            Date currentTime = sdf.parse(Time);
            Date breakfastTime = sdf.parse(bkf);
            Date lunchTime = sdf.parse(lunch);
            Date dinnerTime = sdf.parse(dinner);

            // Calculate time differences in minutes
            long diffWithBreakfast = Math.abs(currentTime.getTime() - breakfastTime.getTime()) / (60 * 1000);
            long diffWithLunch = Math.abs(currentTime.getTime() - lunchTime.getTime()) / (60 * 1000);
            long diffWithDinner = Math.abs(currentTime.getTime() - dinnerTime.getTime()) / (60 * 1000);

            // Log time differences for debugging
//            System.out.println("Time difference with Breakfast: " + diffWithBreakfast + " mins");
//            System.out.println("Time difference with Lunch: " + diffWithLunch + " mins");
//            System.out.println("Time difference with Dinner: " + diffWithDinner + " mins");

            // Check if any time difference is <= 60 minutes
            if (diffWithBreakfast <= 60 || diffWithLunch <= 60 || diffWithDinner <= 60) {
                // Execute Restaurant Recommendations (2)
                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
                FinalListActivity = fetchActivitiesRecommendations(Location, af, pdistance, PAvgPrice, Prating, UserActP);
                FinalListActivity.clear();
            } else {
                // Else condition
                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
                FinalListRestaurant.clear();
                if ("Outdoor".equalsIgnoreCase(UserActP) || Objects.equals(Weather, "Sunny") || Objects.equals(Weather, "Cloudy")) {
                    // User chose Outdoor, execute Activity Recommendations (1)
                    FinalListActivity = fetchActivitiesRecommendations(Location, af, pdistance, PAvgPrice, Prating, UserActP);
                } else {
                    // User did not choose Outdoor, execute Activity Recommendations with "Indoor"
                    FinalListActivity = fetchActivitiesRecommendations(Location, af, pdistance, PAvgPrice, Prating, "Indoor");
                }
            }
        } catch (ParseException e) {
            // Handle parsing errors
            System.err.println("Error parsing time: " + e.getMessage());
        }
//String kkk="act";
//
//        switch (kkk) {
//            case "act":
//                FinalListActivity = fetchActivitiesRecommendations(Location,af, pdistance,PAvgPrice,Prating, UserActP);
//              FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
//                break;
//
//            case "rest":
//                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
//
//                break;
//

//            case "Old Montreal":
//
//                boolean ft=true;
//                if(ft)
//                {
//                    FinalListActivity = fetchActivitiesRecommendations(Location,af, pdistance,PAvgPrice,Prating, UserActP);
//                }
//                else
//                {
//                    FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
//                }
//
//                break;
//            case "Mile End":
//                FinalListActivity = fetchActivitiesRecommendations(Location,af, pdistance,PAvgPrice,Prating, UserActP);
//                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
//                break;
//            case "Downtown Montreal":
//                FinalListActivity = fetchActivitiesRecommendations(Location,af, pdistance,PAvgPrice,Prating, UserActP);
//                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
//                break;
//            case "Griffintown":
//                FinalListActivity = fetchActivitiesRecommendations(Location,af, pdistance,PAvgPrice,Prating, UserActP);
//                FinalListRestaurant = fetchRestaurantRecommendations(Location, rf, pdistance, UserCType, PAvgPrice, Prating, Pambiance);
//                break;
//            default:
//                System.out.println("No recommendations for activities or restaurants");
//                break;
//
//
//
//        }
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

    private List<Activity> fetchActivitiesRecommendations(String Location, ActivityFinder af, int pdistance,
                                                            double PAvgPrice, double Prating,String UserActP) {
//        WeightedCosineSimilarityRecommender recommender = new WeightedCosineSimilarityRecommender();
//        double threshold = 0.9;
        ActivityRecommender ar =new ActivityRecommender();


        UserPreferencesForActivities ActivityUser = new UserPreferencesForActivities(UserActP.equals("Outdoor"), Prating, PAvgPrice, pdistance);


        switch (Location) {
            case "Plateau Mont-Royal":



                return ar.recommendActivities(ActivityUser, af.plateauActivities);

            case "Old Montreal":
//                logger.info("OLD MONTREAL CHECK activities " + af.oldMontrealActivities);
                return ar.recommendActivities(ActivityUser, af.oldMontrealActivities);

            case "Mile End":
                return ar.recommendActivities(ActivityUser, af.mileEndActivities);

            case "Downtown Montreal":
                return ar.recommendActivities(ActivityUser, af.downtownActivities);

            case "Griffintown":
                return ar.recommendActivities(ActivityUser, af.griffintownActivities);

            default:
                return null;
        }
    }

}
