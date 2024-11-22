package project;

import java.util.*;

public class RestaurantFinder {
    Map<String, List<Restaurant>> neighborhoods = new HashMap<>();
    List<Restaurant> plateauRestaurants = new ArrayList<>();
    List<Restaurant> oldMontrealRestaurants = new ArrayList<>();
    List<Restaurant> mileEndRestaurants = new ArrayList<>();
    List<Restaurant> downtownRestaurants = new ArrayList<>();
    List<Restaurant> griffintownRestaurants = new ArrayList<>();
    // Restaurant class to store restaurant details
    
    public void load() {
        // Map to store neighborhood data
        

        // Adding data for Plateau Mont-Royal with balanced cuisine types and distances
        
        plateauRestaurants.add(new Restaurant("Le Plateau Bistro", "Casual", true, 3, 25.50, 4.2, "FRENCH", 500));
        plateauRestaurants.add(new Restaurant("La Belle Province", "Cheap", false, 2, 15.00, 3.8, "QUEBECOISE", 300));
        plateauRestaurants.add(new Restaurant("Chez Lavigne", "Moderate", true, 4, 40.00, 4.5, "FRENCH", 450));
        plateauRestaurants.add(new Restaurant("Bar Le Lab", "Moderate", true, 4, 35.00, 4.3, "ITALIAN", 550));
        plateauRestaurants.add(new Restaurant("La Poutine Place", "Cheap", false, 2, 12.50, 3.9, "QUEBECOISE", 600));
        plateauRestaurants.add(new Restaurant("Le Chien Fumant", "Moderate", true, 4, 50.00, 4.6, "FRENCH", 400));
        plateauRestaurants.add(new Restaurant("Bistro de Paris", "Fancy", true, 5, 75.00, 4.8, "FRENCH", 350));
        plateauRestaurants.add(new Restaurant("Le Petit Plateau", "Moderate", false, 3, 30.00, 4.1, "AMERICAN", 200));
        plateauRestaurants.add(new Restaurant("Café du Marché", "Cheap", false, 2, 10.00, 3.7, "QUEBECOISE", 250));
        plateauRestaurants.add(new Restaurant("Le P'tit Coin", "Casual", false, 3, 20.00, 3.9, "AMERICAN", 700));
        plateauRestaurants.add(new Restaurant("L'Express", "Fancy", true, 5, 80.00, 4.9, "FRENCH", 450));
        plateauRestaurants.add(new Restaurant("La Taverne Square", "Moderate", true, 4, 45.00, 4.4, "ITALIAN", 500));
        plateauRestaurants.add(new Restaurant("Le Comptoir", "Cheap", false, 2, 12.00, 3.6, "QUEBECOISE", 800));
        plateauRestaurants.add(new Restaurant("Restaurant Les 3 Près", "Moderate", false, 3, 35.00, 4.0, "AMERICAN", 600));
        plateauRestaurants.add(new Restaurant("Le Pizzaiolo", "Moderate", true, 4, 30.00, 4.2, "ITALIAN", 700));
        neighborhoods.put("Plateau Mont-Royal", plateauRestaurants);

        // Adding data for Old Montreal with balanced cuisine types and distances
        
        oldMontrealRestaurants.add(new Restaurant("Le Vieux-Port Steakhouse", "Fancy", true, 5, 90.00, 4.7, "AMERICAN", 200));
        oldMontrealRestaurants.add(new Restaurant("Taverne Gaspar", "Moderate", true, 4, 40.00, 4.2, "FRENCH", 150));
        oldMontrealRestaurants.add(new Restaurant("Café Olé", "Cheap", false, 2, 8.50, 3.5, "QUEBECOISE", 250));
        oldMontrealRestaurants.add(new Restaurant("Le Bremner", "Fancy", true, 5, 95.00, 4.8, "ITALIAN", 100));
        oldMontrealRestaurants.add(new Restaurant("Maison Christian Faure", "Fancy", false, 5, 85.00, 4.6, "FRENCH", 300));
        oldMontrealRestaurants.add(new Restaurant("La Méditerranée", "Moderate", true, 4, 45.00, 4.3, "ITALIAN", 400));
        oldMontrealRestaurants.add(new Restaurant("Modavie", "Moderate", true, 4, 50.00, 4.5, "FRENCH", 350));
        oldMontrealRestaurants.add(new Restaurant("Les Deux Gamins", "Moderate", false, 3, 35.00, 4.0, "AMERICAN", 500));
        oldMontrealRestaurants.add(new Restaurant("The Keg Steakhouse", "Fancy", true, 5, 75.00, 4.8, "AMERICAN", 200));
        oldMontrealRestaurants.add(new Restaurant("Santos Tapas Bar", "Moderate", true, 4, 50.00, 4.4, "ITALIAN", 150));
        oldMontrealRestaurants.add(new Restaurant("L'Atelier de Joël Robuchon", "Fancy", true, 5, 100.00, 4.9, "FRENCH", 100));
        oldMontrealRestaurants.add(new Restaurant("Le Jardin Nelson", "Moderate", false, 3, 40.00, 4.1, "QUEBECOISE", 600));
        oldMontrealRestaurants.add(new Restaurant("Marché de l'Ouest", "Cheap", false, 2, 12.50, 3.7, "QUEBECOISE", 700));
        oldMontrealRestaurants.add(new Restaurant("La Chasse Galerie", "Moderate", false, 3, 38.00, 4.0, "AMERICAN", 800));
        oldMontrealRestaurants.add(new Restaurant("Le Saint-Amour", "Fancy", true, 5, 95.00, 4.9, "FRENCH", 150));
        neighborhoods.put("Old Montreal", oldMontrealRestaurants);

        // Adding data for Mile End with balanced cuisine types and distances
        
        mileEndRestaurants.add(new Restaurant("Bagel St-Viateur", "Cheap", false, 2, 8.00, 4.0, "QUEBECOISE", 300));
        mileEndRestaurants.add(new Restaurant("Café Myriade", "Moderate", false, 3, 20.00, 3.8, "FRENCH", 450));
        mileEndRestaurants.add(new Restaurant("Bistro La Merveilleuse", "Moderate", false, 3, 30.00, 4.1, "FRENCH", 500));
        mileEndRestaurants.add(new Restaurant("Le Pois Penché", "Moderate", true, 4, 45.00, 4.3, "ITALIAN", 350));
        mileEndRestaurants.add(new Restaurant("The Sparrow", "Moderate", false, 3, 30.00, 4.0, "AMERICAN", 400));
        mileEndRestaurants.add(new Restaurant("Fairmount Bagel", "Cheap", false, 2, 8.50, 4.1, "QUEBECOISE", 250));
        mileEndRestaurants.add(new Restaurant("Dantes", "Fancy", false, 5, 80.00, 4.5, "ITALIAN", 700));
        mileEndRestaurants.add(new Restaurant("Bar à Beurre", "Moderate", true, 4, 35.00, 4.4, "AMERICAN", 600));
        mileEndRestaurants.add(new Restaurant("Mile End Deli", "Moderate", false, 3, 25.00, 4.2, "QUEBECOISE", 500));
        mileEndRestaurants.add(new Restaurant("Café Olimpico", "Cheap", false, 2, 12.00, 3.9, "ITALIAN", 300));
        mileEndRestaurants.add(new Restaurant("Pizzeria Napoletana", "Fancy", true, 5, 60.00, 4.8, "ITALIAN", 200));
        mileEndRestaurants.add(new Restaurant("La Distillerie", "Moderate", false, 3, 40.00, 4.3, "AMERICAN", 350));
        mileEndRestaurants.add(new Restaurant("The Shady Grove", "Moderate", true, 4, 50.00, 4.4, "FRENCH", 400));
        mileEndRestaurants.add(new Restaurant("Café d'Anjou", "Cheap", false, 2, 10.00, 3.8, "QUEBECOISE", 600));
        mileEndRestaurants.add(new Restaurant("Bistro Le Verre Bouteille", "Moderate", true, 4, 45.00, 4.6, "ITALIAN", 700));
        neighborhoods.put("Mile End", mileEndRestaurants);

        // Adding data for Downtown Montreal with balanced cuisine types and distances
        
        downtownRestaurants.add(new Restaurant("Le Toqué!", "Fancy", true, 5, 120.00, 4.9, "FRENCH", 150));
        downtownRestaurants.add(new Restaurant("Restaurant Jun I", "Moderate", true, 4, 50.00, 4.6, "AMERICAN", 400));
        downtownRestaurants.add(new Restaurant("Bistro L'Entrecôte", "Moderate", true, 4, 40.00, 4.5, "FRENCH", 200));
        downtownRestaurants.add(new Restaurant("The Keg", "Fancy", true, 5, 70.00, 4.7, "AMERICAN", 300));
        downtownRestaurants.add(new Restaurant("Olea", "Moderate", true, 4, 45.00, 4.3, "ITALIAN", 450));
        downtownRestaurants.add(new Restaurant("La Croissanterie", "Cheap", false, 2, 10.00, 3.9, "QUEBECOISE", 100));
        downtownRestaurants.add(new Restaurant("La Cage aux Sports", "Cheap", false, 3, 12.50, 3.6, "AMERICAN", 500));
        downtownRestaurants.add(new Restaurant("Jatoba", "Fancy", true, 5, 95.00, 4.8, "FRENCH", 350));
        downtownRestaurants.add(new Restaurant("Le Primeur", "Moderate", false, 4, 40.00, 4.4, "ITALIAN", 600));
        downtownRestaurants.add(new Restaurant("Café Cherrier", "Moderate", true, 3, 30.00, 4.2, "AMERICAN", 700));
        downtownRestaurants.add(new Restaurant("L'Avenue", "Casual", true, 3, 25.00, 4.1, "FRENCH", 250));
        downtownRestaurants.add(new Restaurant("Tandoor", "Moderate", true, 4, 50.00, 4.3, "INDIAN", 500));
        downtownRestaurants.add(new Restaurant("La Belle et la Boeuf", "Moderate", true, 3, 30.00, 4.0, "AMERICAN", 550));
        downtownRestaurants.add(new Restaurant("Shawarma King", "Cheap", false, 2, 8.00, 3.8, "AMERICAN", 600));
        neighborhoods.put("Downtown Montreal", downtownRestaurants);

        // Adding data for Griffintown with balanced cuisine types and distances
        
        griffintownRestaurants.add(new Restaurant("Joe Beef", "Fancy", true, 5, 120.00, 4.9, "FRENCH", 250));
        griffintownRestaurants.add(new Restaurant("Le Richmond", "Moderate", true, 4, 50.00, 4.5, "FRENCH", 300));
        griffintownRestaurants.add(new Restaurant("McKibbin's", "Casual", false, 3, 20.00, 3.9, "AMERICAN", 400));
        griffintownRestaurants.add(new Restaurant("Griffintown Café", "Moderate", false, 3, 35.00, 4.2, "FRENCH", 500));
        griffintownRestaurants.add(new Restaurant("Taverne F", "Moderate", true, 4, 45.00, 4.3, "AMERICAN", 600));
        griffintownRestaurants.add(new Restaurant("Bar Le Manoir", "Moderate", false, 3, 25.00, 4.0, "QUEBECOISE", 550));
        griffintownRestaurants.add(new Restaurant("Boucherie Inez", "Fancy", true, 5, 80.00, 4.8, "ITALIAN", 700));
        griffintownRestaurants.add(new Restaurant("Griffintown Pizza", "Cheap", false, 2, 10.00, 3.7, "AMERICAN", 650));
        griffintownRestaurants.add(new Restaurant("Wilderton", "Moderate", true, 4, 40.00, 4.3, "FRENCH", 500));
        griffintownRestaurants.add(new Restaurant("Barbecue Resto", "Casual", false, 2, 15.00, 3.6, "QUEBECOISE", 600));
        griffintownRestaurants.add(new Restaurant("Bistro Rive Gauche", "Moderate", true, 4, 50.00, 4.4, "ITALIAN", 700));
        griffintownRestaurants.add(new Restaurant("Café Cherrier", "Moderate", true, 3, 40.00, 4.1, "AMERICAN", 500));
        griffintownRestaurants.add(new Restaurant("La Boucherie", "Fancy", true, 5, 85.00, 4.8, "FRENCH", 450));
        griffintownRestaurants.add(new Restaurant("Le Mignon", "Moderate", false, 3, 30.00, 4.0, "ITALIAN", 400));
        neighborhoods.put("Griffintown", griffintownRestaurants);

        // User input
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter a neighborhood (e.g., Plateau Mont-Royal, Old Montreal, Mile End, Downtown Montreal, Griffintown):");
        // String neighborhood = scanner.nextLine();

        // // Display restaurants in the specified neighborhood
        // if (neighborhoods.containsKey(neighborhood)) {
        //     System.out.println("Restaurants in " + neighborhood + ":");
        //     for (Restaurant restaurant : neighborhoods.get(neighborhood)) {
        //         System.out.println(restaurant);
        //     }
        // } else {
        //     System.out.println("Neighborhood not found!");
        // }
    }
}
