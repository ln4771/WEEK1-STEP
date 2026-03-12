import java.util.*;

public class Problem2_FlashSaleInventoryManager {

    // HashMap to store product stock
    static HashMap<String, Integer> stockMap = new HashMap<>();

    // Waiting list (FIFO)
    static LinkedHashMap<String, List<Integer>> waitingList = new LinkedHashMap<>();

    // Check stock availability
    public static int checkStock(String productId) {

        return stockMap.getOrDefault(productId, 0);
    }

    // Purchase item (thread safe)
    public static synchronized String purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {
            stockMap.put(productId, stock - 1);
            return "Success, " + (stock - 1) + " units remaining";
        }
        else {

            waitingList.putIfAbsent(productId, new ArrayList<>());
            waitingList.get(productId).add(userId);

            int position = waitingList.get(productId).size();

            return "Added to waiting list, position #" + position;
        }
    }

    // Display waiting list
    public static void showWaitingList(String productId) {

        List<Integer> list = waitingList.get(productId);

        if (list == null || list.isEmpty()) {
            System.out.println("No waiting users.");
        }
        else {
            System.out.println("Waiting List: " + list);
        }
    }

    public static void main(String[] args) {

        // Initialize stock
        stockMap.put("IPHONE15_256GB", 100);

        // Check stock
        System.out.println("Stock: " + checkStock("IPHONE15_256GB") + " units available");

        // Simulate purchases
        System.out.println(purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(purchaseItem("IPHONE15_256GB", 67890));

        // Simulate stock running out
        stockMap.put("IPHONE15_256GB", 0);

        System.out.println(purchaseItem("IPHONE15_256GB", 99999));

        // Show waiting list
        showWaitingList("IPHONE15_256GB");
    }
}