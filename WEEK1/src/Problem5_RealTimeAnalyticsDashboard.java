import java.util.*;

public class Problem5_RealTimeAnalyticsDashboard {

    static HashMap<String, Integer> pageViews = new HashMap<>();
    static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    static HashMap<String, Integer> trafficSources = new HashMap<>();

    public static void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    public static void getDashboard() {

        System.out.println("Top Pages:");

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        int count = 0;
        while (!pq.isEmpty() && count < 10) {

            Map.Entry<String, Integer> entry = pq.poll();
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println((count + 1) + ". " + url + " - " + views +
                    " views (" + unique + " unique)");

            count++;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int v : trafficSources.values()) total += v;

        for (String source : trafficSources.keySet()) {
            int countSource = trafficSources.get(source);
            double percent = (countSource * 100.0) / total;

            System.out.println(source + ": " + String.format("%.2f", percent) + "%");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        processEvent("/article/breaking-news", "user_123", "Google");
        processEvent("/article/breaking-news", "user_456", "Facebook");
        processEvent("/sports/championship", "user_111", "Direct");
        processEvent("/article/breaking-news", "user_789", "Google");
        processEvent("/sports/championship", "user_222", "Google");
        processEvent("/tech/ai-future", "user_333", "Direct");
        processEvent("/article/breaking-news", "user_123", "Google");

        Thread.sleep(5000);

        getDashboard();
    }
}
