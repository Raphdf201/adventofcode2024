package net.raphdf201;

import static net.raphdf201.Inputs.day7Equations;

public class DaySeven {
    private static final String[] lines = day7Equations.split("\n");

    public static void partOne() {
        int grandTotal = 0;
        for (String str : lines) {
            String[] parts = str.split(":");
            int total = Integer.parseInt(parts[0]);
            int solved = 0;
            int[] factors = new int[parts.length - 1];
            for (int i = 1; i < parts.length - 1; i++) {
                factors[i - 1] = Integer.parseInt(parts[i]);
            }
            if (checkEqual(solved, total)) {
                grandTotal += total;
            }
        }
    }

    public static void partTwo() {}

    private static boolean checkEqual(int number, int total) {
        return number == total;
    }
}
