import java.util.*;

public class Problem9_FinancialTwoSum {

    static class Transaction {
        int id;
        int amount;
        String merchant;
        String account;
        long time;

        Transaction(int id, int amount, String merchant, String account, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
            this.time = time;
        }
    }

    static List<Transaction> transactions = new ArrayList<>();

    public static void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public static void findTwoSum(int target) {

        Map<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction other = map.get(complement);
                System.out.println("Pair Found: (" + other.id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }

    public static void findTwoSumWithinTime(int target, long windowMillis) {

        Map<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                Transaction other = map.get(complement);

                if (Math.abs(t.time - other.time) <= windowMillis) {
                    System.out.println("Time Window Pair: (" + other.id + ", " + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }
    }

    public static void detectDuplicates() {

        Map<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {
            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {
            List<Transaction> list = map.get(key);

            if (list.size() > 1) {
                System.out.print("Duplicate: " + key + " Accounts: ");

                for (Transaction t : list) {
                    System.out.print(t.account + " ");
                }

                System.out.println();
            }
        }
    }

    public static void findKSum(int k, int target) {
        kSumHelper(new ArrayList<>(), 0, k, target);
    }

    static void kSumHelper(List<Transaction> current, int start, int k, int target) {

        if (k == 0 && target == 0) {
            System.out.print("K-Sum Found: ");
            for (Transaction t : current) {
                System.out.print(t.id + " ");
            }
            System.out.println();
            return;
        }

        if (k <= 0 || target < 0) return;

        for (int i = start; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            current.add(t);
            kSumHelper(current, i + 1, k - 1, target - t.amount);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {

        long now = System.currentTimeMillis();

        addTransaction(new Transaction(1, 500, "StoreA", "acc1", now));
        addTransaction(new Transaction(2, 300, "StoreB", "acc2", now + 1000));
        addTransaction(new Transaction(3, 200, "StoreC", "acc3", now + 2000));
        addTransaction(new Transaction(4, 500, "StoreA", "acc4", now + 3000));

        System.out.println("Two Sum:");
        findTwoSum(500);

        System.out.println("\nTwo Sum Within 1 Hour:");
        findTwoSumWithinTime(500, 3600000);

        System.out.println("\nDuplicates:");
        detectDuplicates();

        System.out.println("\nK Sum (k=3 target=1000):");
        findKSum(3, 1000);
    }
}