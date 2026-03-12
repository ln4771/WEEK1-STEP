import java.util.*;

public class Problem10_MultiLevelCache {

    static class VideoData {
        String videoId;
        String content;

        VideoData(String id, String content) {
            this.videoId = id;
            this.content = content;
        }
    }

    static final int L1_CAPACITY = 10000;
    static final int L2_CAPACITY = 100000;

    static LinkedHashMap<String, VideoData> L1 = new LinkedHashMap<>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, VideoData> e) {
            return size() > L1_CAPACITY;
        }
    };

    static LinkedHashMap<String, VideoData> L2 = new LinkedHashMap<>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, VideoData> e) {
            return size() > L2_CAPACITY;
        }
    };

    static HashMap<String, VideoData> L3 = new HashMap<>();

    static HashMap<String, Integer> accessCount = new HashMap<>();

    static int l1Hits = 0;
    static int l2Hits = 0;
    static int l3Hits = 0;

    static final int PROMOTE_THRESHOLD = 3;

    public static VideoData getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            l1Hits++;
            return L1.get(videoId);
        }

        if (L2.containsKey(videoId)) {
            l2Hits++;
            VideoData v = L2.get(videoId);

            int count = accessCount.getOrDefault(videoId, 0) + 1;
            accessCount.put(videoId, count);

            if (count >= PROMOTE_THRESHOLD) {
                L1.put(videoId, v);
            }

            return v;
        }

        if (L3.containsKey(videoId)) {
            l3Hits++;
            VideoData v = L3.get(videoId);

            L2.put(videoId, v);
            accessCount.put(videoId, 1);

            return v;
        }

        return null;
    }

    public static void addToDatabase(String videoId, String content) {
        L3.put(videoId, new VideoData(videoId, content));
    }

    public static void invalidate(String videoId) {
        L1.remove(videoId);
        L2.remove(videoId);
        L3.remove(videoId);
        accessCount.remove(videoId);
    }

    public static void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        double l1Rate = total == 0 ? 0 : (l1Hits * 100.0) / total;
        double l2Rate = total == 0 ? 0 : (l2Hits * 100.0) / total;
        double l3Rate = total == 0 ? 0 : (l3Hits * 100.0) / total;

        System.out.println("L1 Hit Rate: " + String.format("%.2f", l1Rate) + "%");
        System.out.println("L2 Hit Rate: " + String.format("%.2f", l2Rate) + "%");
        System.out.println("L3 Hit Rate: " + String.format("%.2f", l3Rate) + "%");
        System.out.println("Total Requests: " + total);
    }

    public static void main(String[] args) {

        addToDatabase("video_123", "Video Content A");
        addToDatabase("video_456", "Video Content B");
        addToDatabase("video_999", "Video Content C");

        System.out.println(getVideo("video_123").content);
        System.out.println(getVideo("video_123").content);
        System.out.println(getVideo("video_123").content);
        System.out.println(getVideo("video_456").content);
        System.out.println(getVideo("video_999").content);

        getStatistics();
    }
}