import java.util.*;

public class DayEight {
    private static final List<String> map = Arrays.stream(Inputs.day8Map.split("\n")).toList();

    /**
     * <p>You find yourselves on the <a href="https://adventofcode.com/2016/day/25">roof</a> of a top-secret Easter Bunny installation.</p>
     * <p>While The Historians do their thing, you take a look at the familiar <em>huge antenna</em>. Much to your surprise, it seems to have been reconfigured to emit a signal that makes people 0.1% more likely to buy Easter Bunny brand Imitation Mediocre Chocolate as a Christmas gift! Unthinkable!</p>
     * <p>Scanning across the city, you find that there are actually many such antennas. Each antenna is tuned to a specific <em>frequency</em> indicated by a single lowercase letter, uppercase letter, or digit. You create a map (your puzzle input) of these antennas. For example:</p>
     * <pre><code>
     * ............
     * ........0...
     * .....0......
     * .......0....
     * ....0.......
     * ......A.....
     * ............
     * ............
     * ........A...
     * .........A..
     * ............
     * ............
     * </code></pre>
     * <p>The signal only applies its nefarious effect at specific <em>antinodes</em> based on the resonant frequencies of the antennas. In particular, an antinode occurs at any point that is perfectly in line with two antennas of the same frequency - but only when one of the antennas is twice as far away as the other. This means that for any pair of antennas with the same frequency, there are two antinodes, one on either side of them.</p>
     * <p>So, for these two antennas with frequency <code>a</code>, they create the two antinodes marked with <code>#</code>:</p>
     * <pre><code>
     * ..........
     * ...#......
     * ..........
     * ....a.....
     * ..........
     * .....a....
     * ..........
     * ......#...
     * ..........
     * ..........
     * </code></pre>
     * <p>Adding a third antenna with the same frequency creates several more antinodes. It would ideally add four antinodes, but two are off the right side of the map, so instead it adds only two:</p>
     * <pre><code>
     * ..........
     * ...#......
     * #.........
     * ....a.....
     * ........a.
     * .....a....
     * ..#.......
     * ......#...
     * ..........
     * ..........
     * </code></pre>
     * <p>Antennas with different frequencies don't create antinodes; <code>A</code> and <code>a</code> count as different frequencies. However, antinodes <em>can</em> occur at locations that contain antennas. In this diagram, the lone antenna with frequency capital <code>A</code> creates no antinodes but has a lowercase-<code>a</code>-frequency antinode at its location:</p>
     * <pre><code>
     * ..........
     * ...#......
     * #.........
     * ....a.....
     * ........a.
     * .....a....
     * ..#.......
     * ......A...
     * ..........
     * ..........
     * </code></pre>
     * <p>The first example has antennas with two different frequencies, so the antinodes they create look like this, plus an antinode overlapping the topmost <code>A</code>-frequency antenna:</p>
     * <pre><code>
     * ......#....#
     * ...#....0...
     * ....#0....#.
     * ..#....0....
     * ....0....#..
     * .#....A.....
     * ...#........
     * #......#....
     * ........A...
     * .........A..
     * ..........#.
     * ..........#.
     * </code></pre>
     * <p>Because the topmost <code>A</code>-frequency antenna overlaps with a <code>0</code>-frequency antinode, there are <code><em>14</em></code> total unique locations that contain an antinode within the bounds of the map.</p>
     * <p>Calculate the impact of the signal. <strong>How many unique locations within the bounds of the map contain an antinode?</strong></p>
     */
    public static void partOne() {
        System.out.print("8-1 : ");

        System.out.println(countAntinodes());
    }

    /**
     * <p>Watching over your shoulder as you work, one of The Historians asks if you took the effects of resonant harmonics into your calculations.</p>
     * <p>Whoops!</p>
     * <p>After updating your model, it turns out that an antinode occurs at <em>any grid position</em> exactly in line with at least two antennas of the same frequency, regardless of distance. This means that some of the new antinodes will occur at the position of each antenna (unless that antenna is the only one of its frequency).</p>
     * <p>So, these three <code>T</code>-frequency antennas now create many antinodes:</p>
     * <pre><code>
     * T....#....
     * ...T......
     * .T....#...
     * .........#
     * ..#.......
     * ..........
     * ...#......
     * ..........
     * ....#.....
     * ..........
     * </code></pre>
     * <p>In fact, the three <code>T</code>-frequency antennas are all exactly in line with two antennas, so they are all also antinodes! This brings the total number of antinodes in the above example to <code><em>9</em></code>.</p>
     * <p>The original example now has <code><em>34</em></code> antinodes, including the antinodes that appear on every antenna:</p>
     * <pre><code>
     * ##....#....#
     * .#.#....0...
     * ..#.#0....#.
     * ..##...0....
     * ....0....#..
     * .#...#A....#
     * ...#..#.....
     * #....#.#....
     * ..#.....A...
     * ....#....A..
     * .#........#.
     * ...#......##
     * </code></pre>
     * <p>Calculate the impact of the signal using this updated model. <strong>How many unique locations within the bounds of the map contain an antinode?</strong></p>
     */
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
