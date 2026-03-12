import java.util.*;

public class Problem6_DistributedRateLimiter {

    static class TokenBucket {
        int tokens;
        int maxTokens;
        double refillRate;
        long lastRefillTime;

        TokenBucket(int maxTokens, double refillRate) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }

        synchronized boolean allowRequest() {
            refill();

            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        void refill() {
            long now = System.currentTimeMillis();
            double tokensToAdd = ((now - lastRefillTime) / 1000.0) * refillRate;

            if (tokensToAdd > 0) {
                tokens = Math.min(maxTokens, tokens + (int) tokensToAdd);
                lastRefillTime = now;
            }
        }

        int getRemainingTokens() {
            refill();
            return tokens;
        }
    }

    static HashMap<String, TokenBucket> clients = new HashMap<>();

    static final int LIMIT = 1000;
    static final double REFILL_RATE = LIMIT / 3600.0;

    public static String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(LIMIT, REFILL_RATE));
        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            return "Allowed (" + bucket.getRemainingTokens() + " requests remaining)";
        } else {
            return "Denied (0 requests remaining)";
        }
    }

    public static void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            System.out.println("Client not found");
            return;
        }

        int remaining = bucket.getRemainingTokens();
        int used = LIMIT - remaining;

        System.out.println("{used: " + used + ", limit: " + LIMIT + ", remaining: " + remaining + "}");
    }

    public static void main(String[] args) {

        System.out.println(checkRateLimit("abc123"));
        System.out.println(checkRateLimit("abc123"));
        System.out.println(checkRateLimit("abc123"));

        getRateLimitStatus("abc123");
    }
}