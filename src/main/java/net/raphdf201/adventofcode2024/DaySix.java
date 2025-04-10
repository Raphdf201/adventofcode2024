package net.raphdf201.adventofcode2024;

import java.util.HashSet;
import java.util.Set;

import static net.raphdf201.adventofcode2024.Inputs.day6Map;

public class DaySix {
    private static final char[] DIRECTIONS = {'^', '>', 'v', '<'}; // Up, Right, Down, Left
    private static final int[][] DELTAS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // row, col deltas

    public static void partOne() {
        System.out.print("6-1 : ");
        String[] map = day6Map.split("\n");
        int rows = map.length;
        int cols = map[0].length();

        int row = -1, col = -1, dir = -1;

        outer:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = map[i].charAt(j);
                for (int d = 0; d < DIRECTIONS.length; d++) {
                    if (c == DIRECTIONS[d]) {
                        row = i;
                        col = j;
                        dir = d;
                        break outer;
                    }
                }
            }
        }

        Set<String> visitedTiles = new HashSet<>();
        Set<String> visitedStates = new HashSet<>();

        visitedTiles.add(row + "," + col);
        visitedStates.add(state(row, col, dir));

        while (true) {
            int nextRow = row + DELTAS[dir][0];
            int nextCol = col + DELTAS[dir][1];

            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                break;
            }

            char nextCell = map[nextRow].charAt(nextCol);

            if (nextCell == '#') {
                dir = (dir + 1) % 4;
                continue;
            }

            String nextState = state(nextRow, nextCol, dir);
            if (visitedStates.contains(nextState)) {
                System.err.println("Loop detected at (" + nextRow + "," + nextCol + ") facing " + DIRECTIONS[dir]);
                break;
            }

            row = nextRow;
            col = nextCol;

            visitedTiles.add(row + "," + col);
            visitedStates.add(nextState);
        }

        System.out.println(visitedTiles.size());
    }

    public static void partTwo() {
        System.out.print("6-2 : ");
        String[] originalMap = day6Map.split("\n");
        int rows = originalMap.length;
        int cols = originalMap[0].length();

        int startRow = -1, startCol = -1, startDir = -1;

        // Find the guard's starting position and direction
        outer:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = originalMap[i].charAt(j);
                for (int d = 0; d < DIRECTIONS.length; d++) {
                    if (c == DIRECTIONS[d]) {
                        startRow = i;
                        startCol = j;
                        startDir = d;
                        break outer;
                    }
                }
            }
        }

        int loopCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (originalMap[i].charAt(j) != '.') continue;
                if (i == startRow && j == startCol) continue;

                // Make a copy of the map as a 2D char array
                char[][] mapCopy = new char[rows][cols];
                for (int r = 0; r < rows; r++) {
                    mapCopy[r] = originalMap[r].toCharArray();
                }

                // Add the potential obstruction
                mapCopy[i][j] = '#';

                if (simulatesLoop(mapCopy, startRow, startCol, startDir)) {
                    loopCount++;
                }
            }
        }

        System.out.println(loopCount);
    }

    private static String state(int row, int col, int dir) {
        return row + "," + col + "," + dir;
    }

    private static boolean simulatesLoop(char[][] map, int row, int col, int dir) {
        int rows = map.length;
        int cols = map[0].length;

        Set<String> visitedStates = new HashSet<>();

        visitedStates.add(state(row, col, dir));

        while (true) {
            int nextRow = row + DELTAS[dir][0];
            int nextCol = col + DELTAS[dir][1];

            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                return false; // Guard left the map
            }

            char nextCell = map[nextRow][nextCol];

            if (nextCell == '#') {
                dir = (dir + 1) % 4;
                continue;
            }

            String nextState = state(nextRow, nextCol, dir);
            if (visitedStates.contains(nextState)) {
                return true; // Loop detected
            }

            row = nextRow;
            col = nextCol;
            visitedStates.add(nextState);
        }
    }
}
