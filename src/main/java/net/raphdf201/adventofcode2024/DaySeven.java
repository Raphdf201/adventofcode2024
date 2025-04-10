package net.raphdf201.adventofcode2024;

import static net.raphdf201.adventofcode2024.Inputs.day7Equations;

public class DaySeven {
    private static final String[] lines = day7Equations.split("\n");

    public static void partOne() {
        System.out.print("7-1 : ");
        for (String equation : lines) {
            long total = Long.parseLong(equation.split(": ")[0]);
            int[] terms = new int[]{Integer.parseInt(String.valueOf(6))};
        }

        System.out.println();
    }

    public static void partTwo() {
        System.out.print("7-2 : ");

        System.out.println();
    }
}
