import java.util.*;

public class Problem1_UsernameChecker {

    // HashMap to store registered usernames
    static HashMap<String, Integer> usernameMap = new HashMap<>();

    // HashMap to track username attempt frequency
    static HashMap<String, Integer> attemptFrequency = new HashMap<>();

    // Function to check availability
    public static boolean checkAvailability(String username) {

        // Update attempt frequency
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);

        // Check if username exists
        return !usernameMap.containsKey(username);
    }

    // Function to suggest alternatives
    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        // Add numbers
        for (int i = 1; i <= 3; i++) {
            String newName = username + i;

            if (!usernameMap.containsKey(newName)) {
                suggestions.add(newName);
            }
        }

        // Replace underscore with dot
        if (username.contains("_")) {
            String dotName = username.replace("_", ".");

            if (!usernameMap.containsKey(dotName)) {
                suggestions.add(dotName);
            }
        }

        return suggestions;
    }

    // Function to get most attempted username
    public static String getMostAttempted() {

        String maxUser = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {

            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxUser = entry.getKey();
            }
        }

        return maxUser + " (" + maxCount + " attempts)";
    }

    public static void main(String[] args) {

        // Pre-existing users
        usernameMap.put("john_doe", 101);
        usernameMap.put("admin", 102);
        usernameMap.put("alex", 103);

        // Check usernames
        System.out.println("john_doe available: " + checkAvailability("john_doe"));
        System.out.println("jane_smith available: " + checkAvailability("jane_smith"));

        // Suggestions
        System.out.println("Suggestions for john_doe: " + suggestAlternatives("john_doe"));

        // Simulate multiple attempts
        checkAvailability("admin");
        checkAvailability("admin");
        checkAvailability("admin");
        checkAvailability("john_doe");

        // Most attempted username
        System.out.println("Most attempted username: " + getMostAttempted());
    }
}