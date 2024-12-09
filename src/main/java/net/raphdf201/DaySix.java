package net.raphdf201;

import java.util.HashSet;
import java.util.Set;

import static net.raphdf201.Inputs.day6Map;

public class DaySix {
    private static final char[] officers = {'<', '>', '^', 'v'};
    private static final String[] mapArr = day6Map.split("\n");

    public static void partOne() {
        System.out.println("Part one :");
        int rows = mapArr.length;
        int cols = mapArr[0].length();
        byte[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int guardX = -1, guardY = -1, direction = -1;
        outer: for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = mapArr[i].charAt(j);
                for (int k = 0; k < officers.length; k++) {
                    if (c == officers[k]) {
                        guardX = j;
                        guardY = i;
                        direction = k;
                        break outer;
                    }
                }
            }
        }

        Set<String> visited = new HashSet<>();
        visited.add(guardY + "," + guardX);

        int steps = 0;
        while (true) {
            steps++;

            // Detect potential infinite loop (same position and direction repeating)
            if (steps > rows * cols * 5) {
                System.err.println("Error: Potential infinite loop detected.");
                break;
            }

            // Calculate the next position
            int nextX = guardX + directions[direction][1];
            int nextY = guardY + directions[direction][0];

            if (nextX < 0 || nextX >= cols || nextY < 0 || nextY >= rows || mapArr[nextY].charAt(nextX) == '#') {
                direction = (direction + 1) % 4;
                continue;
            }

            String nextPosition = nextY + "," + nextX;
            if (visited.contains(nextPosition)) {
                direction = (direction + 1) % 4;
                continue;
            }

            guardX = nextX;
            guardY = nextY;
            visited.add(nextPosition);

            if (guardX < 0 || guardX >= rows || guardY < 0 || guardY >= cols) {
                break;
            }

            System.out.println("Step " + steps + ": Position (" + guardY + ", " + guardX + "), Direction " + officers[direction]);
        }

        System.out.println(visited.size() + " positions");
    }

    public static void partTwo() {
        System.out.println("Part two :");
    }
}
