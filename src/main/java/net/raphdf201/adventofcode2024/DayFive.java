package net.raphdf201.adventofcode2024;

import java.util.*;

public class DayFive {
    private static final String[] ruleLines = Inputs.day5Rules.split("\n");
    private static final String[] updateLines = Inputs.day5Updates.split("\n");

    public static void partOne() {
        System.out.print("5-1 : ");
        System.out.println(solve());
    }

    public static void partTwo() {
        System.out.print("5-2 : ");
        System.out.println(solvePartTwo());
    }

    private static int getMiddlePage(List<Integer> update) {
        int midIndex = update.size() / 2;
        return update.get(midIndex);
    }

    private static int solve() {
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

    private static int solvePartTwo() {
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
