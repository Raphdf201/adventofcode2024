package net.raphdf201;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.raphdf201.Constants.leftList;
import static net.raphdf201.Constants.rightList;

public class DayOne {
    private static void sortLists() {
        Arrays.sort(leftList);
        Arrays.sort(rightList);
    }

    public static Map<Integer, Integer> getNumberOccurrences(int[] numbers) {
        // Create a HashMap to store the number and its occurrences
        Map<Integer, Integer> occurrences = new HashMap<>();

        // Iterate through the array of numbers
        for (int number : numbers) {
            // Increment the count if the number exists, or set to 1 if it doesn't
            occurrences.put(number, occurrences.getOrDefault(number, 0) + 1);
        }

        return occurrences;
    }

    public static void partOne() {
        System.out.println("Part one :");
        sortLists();
        int dist = 0;
        for (int i = 0; i < leftList.length; i++) {
            dist += Math.abs(leftList[i] - rightList[i]);
        }
        System.out.println(dist);
    }
    public static void partTwo() {
        System.out.println("Part two :");
        sortLists();
        int score = 0;
        Map<Integer, Integer> rightMap = getNumberOccurrences(rightList);
        for (int i = 0; i < leftList.length; i++) {
            int num = leftList[i];
            int occs;
            try {
                occs = rightMap.get(num);
            } catch (Exception e) {
                occs = 0;
            }
            score += num * occs;
        }
        System.out.println(score);
    }
}
