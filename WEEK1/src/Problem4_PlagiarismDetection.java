import java.util.*;

public class Problem4_PlagiarismDetection {

    static int N = 5; // n-gram size

    static Map<String, Set<String>> ngramIndex = new HashMap<>();
    static Map<String, List<String>> documentNgrams = new HashMap<>();

    public static List<String> generateNgrams(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }
            ngrams.add(sb.toString().trim());
        }
        return ngrams;
    }

    public static void addDocument(String docId, String text) {
        List<String> ngrams = generateNgrams(text);
        documentNgrams.put(docId, ngrams);

        for (String gram : ngrams) {
            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }

    public static void analyzeDocument(String docId) {
        List<String> grams = documentNgrams.get(docId);
        Map<String, Integer> matchCount = new HashMap<>();

        for (String gram : grams) {
            if (ngramIndex.containsKey(gram)) {
                for (String otherDoc : ngramIndex.get(gram)) {
                    if (!otherDoc.equals(docId)) {
                        matchCount.put(otherDoc, matchCount.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Extracted " + grams.size() + " n-grams");

        for (String otherDoc : matchCount.keySet()) {
            int matches = matchCount.get(otherDoc);
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Found " + matches + " matching n-grams with \"" + otherDoc + "\"");
            System.out.println("Similarity: " + String.format("%.2f", similarity) + "%");

            if (similarity > 50) {
                System.out.println("PLAGIARISM DETECTED");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        String essay1 = "data structures and algorithms are important for computer science students learning algorithms improves problem solving skills";
        String essay2 = "data structures and algorithms are important for computer science students learning algorithms improves programming skills";
        String essay3 = "machine learning models require large datasets and proper training for accurate predictions";

        addDocument("essay_089.txt", essay1);
        addDocument("essay_092.txt", essay2);
        addDocument("essay_123.txt", essay3);

        analyzeDocument("essay_123.txt");
    }
}