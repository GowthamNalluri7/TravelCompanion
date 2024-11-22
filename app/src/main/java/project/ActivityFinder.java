package project;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityFinder {
    Map<String, List<Activity>> neighborhoods = new HashMap<>();
    List<Activity> plateauActivities = new ArrayList<>();
    List<Activity> oldMontrealActivities = new ArrayList<>();
    List<Activity> mileEndActivities = new ArrayList<>();
    List<Activity> downtownActivities = new ArrayList<>();
    List<Activity> griffintownActivities = new ArrayList<>();

    // Activity class to store activity details
    
    public void load() {
        // Adding data for Plateau Mont-Royal
        plateauActivities.add(new Activity("Mount Royal Park Hike", true, 4.8, 0.00, 500)); // 500 meters
        plateauActivities.add(new Activity("Plateau Walking Tour", true, 4.5, 20.00, 1200)); // 1200 meters
        plateauActivities.add(new Activity("Bouldering Gym", false, 4.3, 25.00, 200)); // 200 meters
        plateauActivities.add(new Activity("Le Plateau Art Gallery", false, 4.2, 15.00, 800)); // 800 meters
        plateauActivities.add(new Activity("Sunday Tam-Tams", true, 4.6, 0.00, 1500)); // 1500 meters
        plateauActivities.add(new Activity("La Fontaine Park", true, 4.7, 0.00, 1000)); // 1000 meters
        plateauActivities.add(new Activity("Café Les Deux Amis", false, 4.4, 10.00, 600)); // 600 meters
        plateauActivities.add(new Activity("Bixi Bike Ride", true, 4.5, 10.00, 400)); // 400 meters
        plateauActivities.add(new Activity("Lachine Canal Walk", true, 4.6, 0.00, 1500)); // 1500 meters
        plateauActivities.add(new Activity("Le Plateau Poetry Night", false, 4.3, 5.00, 1000)); // 1000 meters
        plateauActivities.add(new Activity("Street Art Walking Tour", true, 4.7, 25.00, 1200)); // 1200 meters
        plateauActivities.add(new Activity("Visit St-Denis Street", true, 4.5, 0.00, 800)); // 800 meters
        plateauActivities.add(new Activity("Pop-up Art Exhibits", false, 4.4, 15.00, 600)); // 600 meters
        plateauActivities.add(new Activity("Montreal Museum of Contemporary Art", false, 4.8, 20.00, 900)); // 900 meters
        plateauActivities.add(new Activity("Bistro Picnic at Parc Jeanne-Mance", true, 4.5, 15.00, 1000)); // 1000 meters
        neighborhoods.put("Plateau Mont-Royal", plateauActivities);

        // Adding data for Old Montreal
        oldMontrealActivities.add(new Activity("Old Port Ice Skating", true, 4.7, 10.00, 800)); // 800 meters
        oldMontrealActivities.add(new Activity("Montreal Science Centre", false, 4.5, 18.00, 1200)); // 1200 meters
        oldMontrealActivities.add(new Activity("Historical Walking Tour", true, 4.8, 25.00, 1500)); // 1500 meters
        oldMontrealActivities.add(new Activity("Notre-Dame Basilica Tour", false, 4.9, 12.00, 600)); // 600 meters
        oldMontrealActivities.add(new Activity("Zipline over Old Port", true, 4.6, 30.00, 1000)); // 1000 meters
        oldMontrealActivities.add(new Activity("La Grande Roue Ferris Wheel", true, 4.7, 15.00, 400)); // 400 meters
        oldMontrealActivities.add(new Activity("St. Joseph's Oratory Visit", false, 4.8, 5.00, 2000)); // 2000 meters
        oldMontrealActivities.add(new Activity("Montreal Boat Tour", true, 4.7, 25.00, 1500)); // 1500 meters
        oldMontrealActivities.add(new Activity("Old Montreal Ghost Tour", true, 4.6, 20.00, 800)); // 800 meters
        oldMontrealActivities.add(new Activity("Explore Place Jacques-Cartier", true, 4.5, 0.00, 1000)); // 1000 meters
        oldMontrealActivities.add(new Activity("Museum of Pointe-à-Callière", false, 4.8, 18.00, 1500)); // 1500 meters
        oldMontrealActivities.add(new Activity("Montreal Zipline Adventure", true, 4.7, 30.00, 2000)); // 2000 meters
        oldMontrealActivities.add(new Activity("Bota Bota Spa", false, 4.9, 50.00, 400)); // 400 meters
        oldMontrealActivities.add(new Activity("Rue Saint-Paul Shopping", true, 4.6, 0.00, 900)); // 900 meters
        oldMontrealActivities.add(new Activity("Montreal Harbor Tour", true, 4.8, 20.00, 1200)); // 1200 meters
        neighborhoods.put("Old Montreal", oldMontrealActivities);

        // Adding data for Mile End
        mileEndActivities.add(new Activity("Bagel Factory Tour", false, 4.3, 10.00, 500)); // 500 meters
        mileEndActivities.add(new Activity("Street Art Walking Tour", true, 4.7, 20.00, 1200)); // 1200 meters
        mileEndActivities.add(new Activity("Mile End Rock Climbing", false, 4.5, 25.00, 800)); // 800 meters
        mileEndActivities.add(new Activity("Local Music Night", false, 4.4, 15.00, 600)); // 600 meters
        mileEndActivities.add(new Activity("Park Picnics", true, 4.6, 0.00, 1000)); // 1000 meters
        mileEndActivities.add(new Activity("Fairmount Bagel Tasting", false, 4.8, 10.00, 700)); // 700 meters
        mileEndActivities.add(new Activity("Mile End Library Visit", false, 4.2, 0.00, 400)); // 400 meters
        mileEndActivities.add(new Activity("Cycling at Parc Jeanne-Mance", true, 4.6, 10.00, 1500)); // 1500 meters
        mileEndActivities.add(new Activity("Brewery Tour", false, 4.5, 25.00, 800)); // 800 meters
        mileEndActivities.add(new Activity("Mile End Comedy Night", false, 4.4, 10.00, 500)); // 500 meters
        mileEndActivities.add(new Activity("Rock Climbing at Allez Up", false, 4.7, 30.00, 900)); // 900 meters
        mileEndActivities.add(new Activity("Picnic in Parc Outremont", true, 4.5, 0.00, 1200)); // 1200 meters
        mileEndActivities.add(new Activity("Outdoor Yoga in the Park", true, 4.6, 15.00, 600)); // 600 meters
        mileEndActivities.add(new Activity("Outdoor Movie Night", true, 4.8, 0.00, 1000)); // 1000 meters
        mileEndActivities.add(new Activity("Mile End Gallery Tour", false, 4.4, 12.00, 700)); // 700 meters
        neighborhoods.put("Mile End", mileEndActivities);

        // Adding data for Downtown Montreal
        downtownActivities.add(new Activity("Museum of Fine Arts", false, 4.9, 20.00, 1200)); // 1200 meters
        downtownActivities.add(new Activity("Downtown Food Tour", true, 4.8, 35.00, 1500)); // 1500 meters
        downtownActivities.add(new Activity("Indoor Trampoline Park", false, 4.5, 25.00, 400)); // 400 meters
        downtownActivities.add(new Activity("Underground City Exploration", false, 4.6, 0.00, 1000)); // 1000 meters
        downtownActivities.add(new Activity("Cinema Under the Stars", true, 4.7, 5.00, 800)); // 800 meters
        downtownActivities.add(new Activity("St. Catherine Street Shopping", true, 4.5, 0.00, 2000)); // 2000 meters
        downtownActivities.add(new Activity("Montreal Underground Art", false, 4.4, 10.00, 700)); // 700 meters
        downtownActivities.add(new Activity("Ice Skating at Atrium Le 1000", true, 4.6, 15.00, 500)); // 500 meters
        downtownActivities.add(new Activity("Visit the McGill University Campus", true, 4.8, 0.00, 1200)); // 1200 meters
        downtownActivities.add(new Activity("Brewery Tour in Downtown", false, 4.7, 20.00, 1000)); // 1000 meters
        downtownActivities.add(new Activity("Mount Royal Summit Hike", true, 4.9, 0.00, 1500)); // 1500 meters
        downtownActivities.add(new Activity("Comedy Club Night", false, 4.5, 15.00, 800)); // 800 meters
        downtownActivities.add(new Activity("Old Montreal Walking Tour", true, 4.6, 25.00, 1000)); // 1000 meters
        downtownActivities.add(new Activity("Bixi Bike Ride Downtown", true, 4.5, 10.00, 600)); // 600 meters
        downtownActivities.add(new Activity("Bar-hopping on Crescent Street", false, 4.4, 20.00, 500)); // 500 meters
        neighborhoods.put("Downtown Montreal", downtownActivities);

        // Adding data for Griffintown
        griffintownActivities.add(new Activity("Canal Walk", true, 4.5, 0.00, 900)); // 900 meters
        griffintownActivities.add(new Activity("Art Gallery Tour", false, 4.6, 15.00, 700)); // 700 meters
        griffintownActivities.add(new Activity("Restaurant Patio Dining", false, 4.7, 20.00, 1000)); // 1000 meters
        griffintownActivities.add(new Activity("Old Warehouse Tour", false, 4.4, 12.00, 1200)); // 1200 meters
        griffintownActivities.add(new Activity("Griffintown Distillery Visit", false, 4.8, 25.00, 800)); // 800 meters
        griffintownActivities.add(new Activity("Outdoor Market", true, 4.5, 10.00, 1500)); // 1500 meters
        griffintownActivities.add(new Activity("Le Village au Pied-du-Courant", true, 4.7, 0.00, 500)); // 500 meters
        griffintownActivities.add(new Activity("Outdoor Yoga", true, 4.6, 0.00, 600)); // 600 meters
        griffintownActivities.add(new Activity("Cycling Tour of Griffintown", true, 4.4, 15.00, 800)); // 800 meters
        griffintownActivities.add(new Activity("Festival Street Performance", true, 4.5, 5.00, 1000)); // 1000 meters
        griffintownActivities.add(new Activity("Monument Walk", true, 4.7, 0.00, 1200)); // 1200 meters
        griffintownActivities.add(new Activity("Brewery Tour", false, 4.8, 20.00, 900)); // 900 meters
        griffintownActivities.add(new Activity("Historic Building Tour", false, 4.6, 10.00, 500)); // 500 meters
        griffintownActivities.add(new Activity("Boat Tour", true, 4.7, 30.00, 1500)); // 1500 meters
        neighborhoods.put("Griffintown", griffintownActivities);
    }

    public static void main(String[] args) {
        ActivityFinder finder = new ActivityFinder();
        finder.load();

        // Example: Print all activities in Plateau Mont-Royal
        for (Activity activity : finder.neighborhoods.get("Plateau Mont-Royal")) {
            System.out.println(activity);
        }
    }
}
