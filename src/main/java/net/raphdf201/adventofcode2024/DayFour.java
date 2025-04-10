package net.raphdf201.adventofcode2024;

import static net.raphdf201.adventofcode2024.Inputs.day4Xmas;

public class DayFour {
    public static final String[] day4XmasArr = day4Xmas.split("\n");

    public static void partOne() {
        System.out.print("4-1 : ");
        System.out.println(countOccurrences());
    }

    public static void partTwo() {
        System.out.print("4-2 : ");
        System.out.println(countXMASPatterns());
    }

    private static int countOccurrences() {
        int rows = DayFour.day4XmasArr.length;
        int cols = DayFour.day4XmasArr[0].length();
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
                    if (matches(r, c, dir[0], dir[1], rows, cols)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean matches(int r, int c, int dr, int dc, int rows, int cols) {
        for (int i = 0; i < "XMAS".length(); i++) {
            int nr = r + i * dr;
            int nc = c + i * dc;

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols || DayFour.day4XmasArr[nr].charAt(nc) != "XMAS".charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static int countXMASPatterns() {
        int rows = DayFour.day4XmasArr.length;
        int cols = DayFour.day4XmasArr[0].length();
        int count = 0;

        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (DayFour.day4XmasArr[r].charAt(c) == 'A') {
                    if (isValidXMAS(r, c)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean isValidXMAS(int r, int c) {
        char topLeft = DayFour.day4XmasArr[r - 1].charAt(c - 1);
        char topRight = DayFour.day4XmasArr[r - 1].charAt(c + 1);
        char bottomLeft = DayFour.day4XmasArr[r + 1].charAt(c - 1);
        char bottomRight = DayFour.day4XmasArr[r + 1].charAt(c + 1);

        char middle = DayFour.day4XmasArr[r].charAt(c);

        return (isMAS(topLeft, middle, bottomRight) && isMAS(bottomLeft, middle, topRight));
    }

    private static boolean isMAS(char first, char middle, char last) {
        return (first == 'M' && middle == 'A' && last == 'S') ||
                (first == 'S' && middle == 'A' && last == 'M');
    }
}
