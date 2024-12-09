package net.raphdf201;

import java.util.*;

import static net.raphdf201.Inputs.day5Rules;
import static net.raphdf201.Inputs.day5Updates;

public class DayFive {
    public static void partOne() {
        System.out.println("Part one :");
        System.out.println("Middle page total : " + solve(day5Rules, day5Updates));
    }

    public static void partTwo() {
        System.out.println("Part two :");
        System.out.println("Fixed middle page total : " + solvePartTwo(day5Rules, day5Updates));
    }

    private static int getMiddlePage(List<Integer> update) {
        int midIndex = update.size() / 2;
        return update.get(midIndex);
    }

    private static int solve(String rulesInput, String updatesInput) {
        String[] ruleLines = rulesInput.split("\n");
        String[] updateLines = updatesInput.split("\n");

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

    private static int solvePartTwo(String rulesInput, String updatesInput) {
        String[] ruleLines = rulesInput.split("\n");
        String[] updateLines = updatesInput.split("\n");

        Map<Integer, List<Integer>> graph = buildGraph(ruleLines);

        int fixedMiddleSum = 0;

        for (String updateLine : updateLines) {
            List<Integer> update = parseUpdate(updateLine);

            if (!isValidOrder(update, graph)) {
                update.sort(Collections.reverseOrder());
                fixedMiddleSum += getMiddlePage(update);
            }
        }
        return fixedMiddleSum;
    }


    private static Map<Integer, List<Integer>> buildGraph(String[] ruleLines) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (String rule : ruleLines) {
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
}
