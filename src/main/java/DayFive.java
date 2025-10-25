import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;

public class DayFive {
    private static final String[] ruleLines = Inputs.day5Rules.split("\n");
    private static final String[] updateLines = Inputs.day5Updates.split("\n");

    /**
     * <p>Satisfied with their search on Ceres, the squadron of scholars suggests subsequently scanning the stationery stacks of sub-basement 17.</p>
     * <p>The North Pole printing department is busier than ever this close to Christmas, and while The Historians continue their search of this historically significant facility, an Elf operating a <a href="https://adventofcode.com/2017/day/1">very familiar printer</a> beckons you over.</p>
     * <p>The Elf must recognize you, because they waste no time explaining that the new <em>sleigh launch safety manual</em> updates won't print correctly. Failure to update the safety manuals would be dire indeed, so you offer your services.</p>
     * <p>Safety protocols clearly indicate that new pages for the safety manuals must be printed in a <em>very specific order</em>. The notation <code>X|Y</code> means that if both page number <code>X</code> and page number <code>Y</code> are to be produced as part of an update, page number <code>X</code> <em>must</em> be printed at some point before page number <code>Y</code>.</p>
     * <p>The Elf has for you both the <em>page ordering rules</em> and the <em>pages to produce in each update</em> (your puzzle input), but can't figure out whether each update has the pages in the right order.</p>
     * <p>For example:</p>
     * <pre><code>
     * 47|53
     * 97|13
     * 97|61
     * 97|47
     * 75|29
     * 61|13
     * 75|53
     * 29|13
     * 97|29
     * 53|29
     * 61|53
     * 97|53
     * 61|29
     * 47|13
     * 75|47
     * 97|75
     * 47|61
     * 75|61
     * 47|29
     * 75|13
     * 53|13
     *
     * 75,47,61,53,29
     * 97,61,53,29,13
     * 75,29,13
     * 75,97,47,61,53
     * 61,13,29
     * 97,13,75,29,47
     * </code></pre>
     * <p>The first section specifies the <em>page ordering rules</em>, one per line. The first rule, <code>47|53</code>, means that if an update includes both page number 47 and page number 53, then page number 47 <em>must</em> be printed at some point before page number 53. (47 doesn't necessarily need to be <em>immediately</em> before 53; other pages are allowed to be between them.)</p>
     * <p>The second section specifies the page numbers of each <em>update</em>. Because most safety manuals are different, the pages needed in the updates are different too. The first update, <code>75,47,61,53,29</code>, means that the update consists of page numbers 75, 47, 61, 53, and 29.</p>
     * <p>To get the printers going as soon as possible, start by identifying <em>which updates are already in the right order</em>.</p>
     * <p>In the above example, the first update (<code>75,47,61,53,29</code>) is in the right order:</p>
     * <ul>
     * <li><code>75</code> is correctly first because there are rules that put each other page after it: <code>75|47</code>, <code>75|61</code>, <code>75|53</code>, and <code>75|29</code>.</li>
     * <li><code>47</code> is correctly second because 75 must be before it (<code>75|47</code>) and every other page must be after it according to <code>47|61</code>, <code>47|53</code>, and <code>47|29</code>.</li>
     * <li><code>61</code> is correctly in the middle because 75 and 47 are before it (<code>75|61</code> and <code>47|61</code>) and 53 and 29 are after it (<code>61|53</code> and <code>61|29</code>).</li>
     * <li><code>53</code> is correctly fourth because it is before page number 29 (<code>53|29</code>).</li>
     * <li><code>29</code> is the only page left and so is correctly last.</li>
     * </ul>
     * <p>Because the first update does not include some page numbers, the ordering rules involving those missing page numbers are ignored.</p>
     * <p>The second and third updates are also in the correct order according to the rules. Like the first update, they also do not include every page number, and so only some of the ordering rules apply - within each update, the ordering rules that involve missing page numbers are not used.</p>
     * <p>The fourth update, <code>75,97,47,61,53</code>, is <em>not</em> in the correct order: it would print 75 before 97, which violates the rule <code>97|75</code>.</p>
     * <p>The fifth update, <code>61,13,29</code>, is also <em>not</em> in the correct order, since it breaks the rule <code>29|13</code>.</p>
     * <p>The last update, <code>97,13,75,29,47</code>, is <em>not</em> in the correct order due to breaking several rules.</p>
     * <p>For some reason, the Elves also need to know the <em>middle page number</em> of each update being printed. Because you are currently only printing the correctly-ordered updates, you will need to find the middle page number of each correctly-ordered update. In the above example, the correctly-ordered updates are:</p>
     * <pre><code>
     * 75,47,(61),53,29
     * 97,61,(53),29,13
     * 75,(29),13
     * </code></pre>
     * <p>These have middle page numbers of <code>61</code>, <code>53</code>, and <code>29</code> respectively. Adding these page numbers together gives <code><em>143</em></code>.</p>
     * <p>Of course, you'll need to be careful: the actual list of <em>page ordering rules</em> is bigger and more complicated than the above example.</p>
     * <p>Determine which updates are already in the correct order. <strong>What do you get if you add up the middle page number from those correctly-ordered updates?</strong></p>
     */
    public static int partOne() {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (String rule : ruleLines) {
            String[] parts = rule.split("\\|");
            int before = Integer.parseInt(parts[0]);
            int after = Integer.parseInt(parts[1]);

            graph.computeIfAbsent(before, k -> new ArrayList<>()).add(after);
        }

        int sumOfMiddlePages = 0;

        for (String updateLine : updateLines) {
            List<Integer> update = new ArrayList<>();
            for (String pageStr : updateLine.split(",")) {
                update.add(Integer.parseInt(pageStr));
            }

            if (isValidOrder(update, graph)) {
                sumOfMiddlePages += getMiddlePage(update);
            }
        }
        return sumOfMiddlePages;
    }

    /**
     * <p>While the Elves get to work printing the correctly-ordered updates, you have a little time to fix the rest of them.</p>
     * <p>For each of the <em>incorrectly-ordered updates</em>, use the page ordering rules to put the page numbers in the right order. For the above example, here are the three incorrectly-ordered updates and their correct orderings:</p>
     * <ul>
     * <li><code>75,97,47,61,53</code> becomes <code>97,75,<em>47</em>,61,53</code>.</li>
     * <li><code>61,13,29</code> becomes <code>61,<em>29</em>,13</code>.</li>
     * <li><code>97,13,75,29,47</code> becomes <code>97,75,<em>47</em>,29,13</code>.</li>
     * </ul>
     * <p>After taking <em>only the incorrectly-ordered updates</em> and ordering them correctly, their middle page numbers are <code>47</code>, <code>29</code>, and <code>47</code>. Adding these together produces <code><em>123</em></code>.</p>
     * <p>Find the updates which are not in the correct order. <strong>What do you get if you add up the middle page numbers after correctly ordering just those updates?</strong></p>
     */
    public static int partTwo() {
        Map<Integer, List<Integer>> graph = buildGraph();

        int fixedMiddleSum = 0;

        for (String updateLine : updateLines) {
            List<Integer> update = parseUpdate(updateLine);

            if (!isValidOrder(update, graph)) {
                List<Integer> sorted = topologicalSort(update, graph);
                fixedMiddleSum += getMiddlePage(sorted);
            }
        }
        return fixedMiddleSum;
    }

    private static int getMiddlePage(List<Integer> update) {
        int midIndex = update.size() / 2;
        return update.get(midIndex);
    }

    private static boolean isValidOrder(List<Integer> update, Map<Integer, List<Integer>> graph) {
        Set<Integer> updateSet = new HashSet<>(update);

        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            int before = entry.getKey();
            for (int after : entry.getValue()) {
                if (updateSet.contains(before) && updateSet.contains(after)) {
                    if (update.indexOf(before) > update.indexOf(after)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static Map<Integer, List<Integer>> buildGraph() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (String rule : DayFive.ruleLines) {
            String[] parts = rule.split("\\|");
            int before = Integer.parseInt(parts[0]);
            int after = Integer.parseInt(parts[1]);

            graph.computeIfAbsent(before, k -> new ArrayList<>()).add(after);
        }
        return graph;
    }

    private static List<Integer> parseUpdate(String updateLine) {
        List<Integer> update = new ArrayList<>();
        for (String pageStr : updateLine.split(",")) {
            update.add(Integer.parseInt(pageStr));
        }
        return update;
    }

    private static List<Integer> topologicalSort(List<Integer> update, Map<Integer, List<Integer>> fullGraph) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        // Initialize graph and in-degree
        for (int page : update) {
            graph.put(page, new ArrayList<>());
            inDegree.put(page, 0);
        }

        // Build graph for this update
        for (int from : update) {
            List<Integer> toList = fullGraph.getOrDefault(from, List.of());
            for (int to : toList) {
                if (update.contains(to)) {
                    graph.get(from).add(to);
                    inDegree.put(to, inDegree.get(to) + 1);
                }
            }
        }

        // Topological sort (Kahn's algorithm)
        Queue<Integer> queue = new LinkedList<>();
        for (int node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                queue.add(node);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);
            for (int neighbor : graph.get(node)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

}
