import java.util.*;

public class Problem7_AutocompleteSystem {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Map<String, Integer> queries = new HashMap<>();
        boolean isEnd;
    }

    static TrieNode root = new TrieNode();
    static HashMap<String, Integer> frequencyMap = new HashMap<>();

    public static void addQuery(String query, int freq) {
        frequencyMap.put(query, frequencyMap.getOrDefault(query, 0) + freq);

        TrieNode node = root;

        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.queries.put(query, frequencyMap.get(query));
        }

        node.isEnd = true;
    }

    public static List<String> search(String prefix) {

        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return new ArrayList<>();
            node = node.children.get(c);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for (Map.Entry<String, Integer> entry : node.queries.entrySet()) {
            pq.offer(entry);
            if (pq.size() > 10) pq.poll();
        }

        List<String> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> e = pq.poll();
            result.add(e.getKey() + " (" + e.getValue() + ")");
        }

        Collections.reverse(result);
        return result;
    }

    public static void updateFrequency(String query) {
        addQuery(query, 1);
    }

    public static void main(String[] args) {

        addQuery("java tutorial", 1234567);
        addQuery("javascript", 987654);
        addQuery("java download", 456789);
        addQuery("java 21 features", 100);
        addQuery("java stream api", 50000);

        System.out.println("Suggestions for 'jav':");

        List<String> suggestions = search("jav");

        int i = 1;
        for (String s : suggestions) {
            System.out.println(i++ + ". " + s);
        }

        updateFrequency("java 21 features");
        updateFrequency("java 21 features");
        updateFrequency("java 21 features");
    }
}