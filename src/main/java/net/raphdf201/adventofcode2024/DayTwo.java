package net.raphdf201.adventofcode2024;

import java.util.Arrays;

import static net.raphdf201.adventofcode2024.Inputs.day2Reports;

public class DayTwo {
    public static void partOne() {
        System.out.println("Part one");
        String[] rows = day2Reports.split("\n");
        int[][] serialized = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] levels = rows[i].split(" ");
            serialized[i] = Arrays.stream(levels)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        long safeCount = Arrays.stream(serialized)
                .filter(DayTwo::isSafeReport)
                .count();
        System.out.println("Number of safe reports: " + safeCount);
    }

    public static void partTwo() {
        System.out.println("Part two");
        String[] rows = day2Reports.split("\n");
        int[][] serialized = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] levels = rows[i].split(" ");
            serialized[i] = Arrays.stream(levels)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        long safeCount = Arrays.stream(serialized)
                .filter(DayTwo::isSafeWithDampener)
                .count();
        System.out.println("Number of safe reports: " + safeCount);
    }

    private static boolean isSafeReport(int[] report) {
        Boolean increasing = null;
        for (int i = 0; i < report.length - 1; i++) {
            int diff = report[i + 1] - report[i];
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }
            if (increasing == null) {
                if (diff > 0) {
                    increasing = true;
                } else if (diff < 0) {
                    increasing = false;
                }
            } else {
                // Check monotonicity consistency
                if (increasing && diff < 0) {
                    return false;
                }
                if (!increasing && diff > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSafeWithDampener(int[] report) {
        if (isSafeReport(report)) {
            return true;
        }
        for (int i = 0; i < report.length; i++) {
            int[] reducedReport = new int[report.length - 1];
            System.arraycopy(report, 0, reducedReport, 0, i);
            System.arraycopy(report, i + 1, reducedReport, i, report.length - i - 1);
            if (isSafeReport(reducedReport)) {
                return true;
            }
        }
        return false;
    }
}
