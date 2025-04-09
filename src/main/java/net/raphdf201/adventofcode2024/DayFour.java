package net.raphdf201.adventofcode2024;

import static net.raphdf201.adventofcode2024.Inputs.day4Xmas;

public class DayFour {
    public static final String[] day4XmasArr = day4Xmas.split("\n");

    public static void partOne() {
        System.out.println("Part one :");
        System.out.println("Total XMAS occurences : " + countOccurrences(day4XmasArr, "XMAS"));
    }

    public static void partTwo() {
        System.out.println("Part two :");
        System.out.println("Total X-MAS occurences : " + countXMASPatterns(day4XmasArr));
    }

    private static int countOccurrences(String[] lines, String target) {
        int rows = lines.length;
        int cols = lines[0].length();
        int count = 0;

        int[][] directions = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
                {1, 1},
                {-1, -1},
                {1, -1},
                {-1, 1}
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int[] dir : directions) {
                    if (matches(lines, target, r, c, dir[0], dir[1], rows, cols)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean matches(String[] lines, String target, int r, int c, int dr, int dc, int rows, int cols) {
        for (int i = 0; i < target.length(); i++) {
            int nr = r + i * dr;
            int nc = c + i * dc;

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols || lines[nr].charAt(nc) != target.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static int countXMASPatterns(String[] lines) {
        int rows = lines.length;
        int cols = lines[0].length();
        int count = 0;

        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (lines[r].charAt(c) == 'A') {
                    if (isValidXMAS(lines, r, c)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean isValidXMAS(String[] lines, int r, int c) {
        char topLeft = lines[r - 1].charAt(c - 1);
        char topRight = lines[r - 1].charAt(c + 1);
        char bottomLeft = lines[r + 1].charAt(c - 1);
        char bottomRight = lines[r + 1].charAt(c + 1);

        char middle = lines[r].charAt(c);

        return (isMAS(topLeft, middle, bottomRight) && isMAS(bottomLeft, middle, topRight));
    }

    private static boolean isMAS(char first, char middle, char last) {
        return (first == 'M' && middle == 'A' && last == 'S') ||
                (first == 'S' && middle == 'A' && last == 'M');
    }
}
