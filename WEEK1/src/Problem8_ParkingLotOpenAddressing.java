import java.util.*;

public class Problem8_ParkingLotOpenAddressing {

    static class ParkingSpot {
        String licensePlate;
        long entryTime;
        String status; // EMPTY, OCCUPIED, DELETED

        ParkingSpot() {
            status = "EMPTY";
        }
    }

    static int SIZE = 500;
    static ParkingSpot[] table = new ParkingSpot[SIZE];

    static int totalProbes = 0;
    static int totalParks = 0;

    static {
        for (int i = 0; i < SIZE; i++) {
            table[i] = new ParkingSpot();
        }
    }

    static int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    public static void parkVehicle(String plate) {
        int index = hash(plate);
        int probes = 0;

        while (table[index].status.equals("OCCUPIED")) {
            index = (index + 1) % SIZE;
            probes++;
        }

        table[index].licensePlate = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].status = "OCCUPIED";

        totalProbes += probes;
        totalParks++;

        System.out.println("Assigned spot #" + index + " (" + probes + " probes)");
    }

    public static void exitVehicle(String plate) {

        int index = hash(plate);
        int start = index;

        while (!table[index].status.equals("EMPTY")) {

            if (table[index].status.equals("OCCUPIED") &&
                    table[index].licensePlate.equals(plate)) {

                long duration = System.currentTimeMillis() - table[index].entryTime;
                double hours = duration / (1000.0 * 60 * 60);
                double fee = hours * 5;

                table[index].status = "DELETED";

                System.out.println("Spot #" + index + " freed, Duration: "
                        + String.format("%.2f", hours) + "h, Fee: $" + String.format("%.2f", fee));
                return;
            }

            index = (index + 1) % SIZE;

            if (index == start) break;
        }

        System.out.println("Vehicle not found");
    }

    public static void getStatistics() {

        int occupied = 0;

        for (ParkingSpot s : table) {
            if (s.status.equals("OCCUPIED")) occupied++;
        }

        double occupancy = (occupied * 100.0) / SIZE;
        double avgProbes = totalParks == 0 ? 0 : (double) totalProbes / totalParks;

        System.out.println("Occupancy: " + String.format("%.2f", occupancy) + "%");
        System.out.println("Avg Probes: " + String.format("%.2f", avgProbes));
    }

    public static void main(String[] args) {

        parkVehicle("ABC-1234");
        parkVehicle("ABC-1235");
        parkVehicle("XYZ-9999");

        try { Thread.sleep(2000); } catch (Exception e) {}

        exitVehicle("ABC-1234");

        getStatistics();
    }
}