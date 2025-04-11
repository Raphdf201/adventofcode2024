package net.raphdf201.adventofcode2024;

import java.util.*;

import static net.raphdf201.adventofcode2024.Inputs.day8Map;

public class DayEight {
    private static final List<String> map = Arrays.stream(day8Map.split("\n")).toList();
    public static void partOne() {
        System.out.print("8-1 : ");

        System.out.println(countAntinodes());
    }

    public static void partTwo() {
        System.out.print("8-2 : ");

        System.out.println(countResonantAntinodes());
    }

    private static int countAntinodes() {
        int rows = DayEight.map.size();
        int cols = DayEight.map.getFirst().length();
        Map<Character, List<Point>> antennas = new HashMap<>();
        Set<Point> antinodes = new HashSet<>();

        // 1. Collect antennas by frequency
        for (int y = 0; y < rows; y++) {
            String line = DayEight.map.get(y);
            for (int x = 0; x < cols; x++) {
                char ch = line.charAt(x);
                if (Character.isLetterOrDigit(ch)) {
                    antennas.computeIfAbsent(ch, k -> new ArrayList<>()).add(new Point(x, y));
                }
            }
        }

        // 2. For each frequency group, check every pair of antennas
        for (List<Point> points : antennas.values()) {
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point a = points.get(i);
                    Point b = points.get(j);
                    int dx = b.x - a.x;
                    int dy = b.y - a.y;

                    Point p1 = new Point(b.x + dx, b.y + dy);
                    Point p2 = new Point(a.x - dx, a.y - dy);

                    if (inBounds(p1, cols, rows)) antinodes.add(p1);
                    if (inBounds(p2, cols, rows)) antinodes.add(p2);
                }
            }
        }

        return antinodes.size();
    }

    private static int countResonantAntinodes() {
        int rows = DayEight.map.size();
        int cols = DayEight.map.getFirst().length();
        Map<Character, List<Point>> antennas = new HashMap<>();
        Set<Point> antinodes = new HashSet<>();

        // Collect antennas by frequency
        for (int y = 0; y < rows; y++) {
            String line = DayEight.map.get(y);
            for (int x = 0; x < cols; x++) {
                char ch = line.charAt(x);
                if (Character.isLetterOrDigit(ch)) {
                    antennas.computeIfAbsent(ch, k -> new ArrayList<>()).add(new Point(x, y));
                }
            }
        }

        for (List<Point> points : antennas.values()) {
            int n = points.size();
            if (n < 2) continue;

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    Point a = points.get(i);
                    Point b = points.get(j);

                    int dx = b.x - a.x;
                    int dy = b.y - a.y;

                    int gcd = gcd(Math.abs(dx), Math.abs(dy));
                    dx /= gcd;
                    dy /= gcd;

                    // Step from A backwards
                    int x = a.x;
                    int y = a.y;
                    while (inBounds(new Point(x, y), cols, rows)) {
                        antinodes.add(new Point(x, y));
                        x -= dx;
                        y -= dy;
                    }

                    // Step from B forwards
                    x = b.x;
                    y = b.y;
                    while (inBounds(new Point(x, y), cols, rows)) {
                        antinodes.add(new Point(x, y));
                        x += dx;
                        y += dy;
                    }
                }
            }
        }

        return antinodes.size();
    }

    private static boolean inBounds(Point p, int cols, int rows) {
        return p.x >= 0 && p.x < cols && p.y >= 0 && p.y < rows;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private record Point(int x, int y) {}
}
