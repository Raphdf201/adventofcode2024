package net.raphdf201.adventofcode2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.raphdf201.adventofcode2024.Inputs.day1LeftList;
import static net.raphdf201.adventofcode2024.Inputs.day1RightList;

public class DayOne {
    /**
     * <p>The <em>Chief Historian</em> is always present for the big Christmas sleigh launch, but nobody has seen him in months! Last anyone heard, he was visiting locations that are historically significant to the North Pole; a group of Senior Historians has asked you to accompany them as they check the places they think he was most likely to visit.</p>
     * <p>As each location is checked, they will mark it on their list with a <em class="star">star</em>. They figure the Chief Historian <em>must</em> be in one of the first fifty places they'll look, so in order to save Christmas, you need to help them get <em class="star">fifty stars</em> on their list before Santa takes off on December 25th.</p>
     * <p>Collect stars by solving puzzles.  Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first.  Each puzzle grants <em class="star">one star</em>. Good luck!</p>
     * <p>You haven't even left yet and the group of Elvish Senior Historians has already hit a problem: their list of locations to check is currently <em>empty</em>. Eventually, someone decides that the best place to check first would be the Chief Historian's office.</p>
     * <p>Upon pouring into the office, everyone confirms that the Chief Historian is indeed nowhere to be found. Instead, the Elves discover an assortment of notes and lists of historically significant locations! This seems to be the planning the Chief Historian was doing before he left. Perhaps these notes can be used to determine which locations to search?</p>
     * <p>Throughout the Chief's office, the historically significant locations are listed not by name but by a unique number called the <em>location ID</em>. To make sure they don't miss anything, The Historians split into two groups, each searching the office and trying to create their own complete list of location IDs.</p>
     * <p>There's just one problem: by holding the two lists up <em>side by side</em> (your puzzle input), it quickly becomes clear that the lists aren't very similar. Maybe you can help The Historians reconcile their lists?</p>
     * <p>For example:</p>
     * <pre><code>
     * 3   4
     * 4   3
     * 2   5
     * 1   3
     * 3   9
     * 3   3
     * </code></pre>
     * <p>Maybe the lists are only off by a small amount! To find out, pair up the numbers and measure how far apart they are. Pair up the <em>smallest number in the left list</em> with the <em>smallest number in the right list</em>, then the <em>second-smallest left number</em> with the <em>second-smallest right number</em>, and so on.</p>
     * <p>Within each pair, figure out <em>how far apart</em> the two numbers are; you'll need to <em>add up all of those distances</em>. For example, if you pair up a <code>3</code> from the left list with a <code>7</code> from the right list, the distance apart is <code>4</code>; if you pair up a <code>9</code> with a <code>3</code>, the distance apart is <code>6</code>.</p>
     * <p>In the example list above, the pairs and distances would be as follows:</p>
     * <ul>
     * <li>The smallest number in the left list is <code>1</code>, and the smallest number in the right list is <code>3</code>. The distance between them is <code><em>2</em></code>.</li>
     * <li>The second-smallest number in the left list is <code>2</code>, and the second-smallest number in the right list is another <code>3</code>. The distance between them is <code><em>1</em></code>.</li>
     * <li>The third-smallest number in both lists is <code>3</code>, so the distance between them is <code><em>0</em></code>.</li>
     * <li>The next numbers to pair up are <code>3</code> and <code>4</code>, a distance of <code><em>1</em></code>.</li>
     * <li>The fifth-smallest numbers in each list are <code>3</code> and <code>5</code>, a distance of <code><em>2</em></code>.</li>
     * <li>Finally, the largest number in the left list is <code>4</code>, while the largest number in the right list is <code>9</code>; these are a distance <code><em>5</em></code> apart.</li>
     * </ul>
     * <p>To find the <em>total distance</em> between the left list and the right list, add up the distances between all the pairs you found. In the example above, this is <code>2 + 1 + 0 + 1 + 2 + 5</code>, a total distance of <code><em>11</em></code>!</p>
     * <p>Your actual left and right lists contain many location IDs. <strong>What is the total distance between your lists?</strong></p>
     */
    public static void partOne() {
        System.out.print("1-1 : ");
        sortLists();
        int dist = 0;
        for (int i = 0; i < day1LeftList.length; i++) {
            dist += Math.abs(day1LeftList[i] - day1RightList[i]);
        }

        System.out.println(dist);
    }

    /**
     * <p>Your analysis only confirmed what everyone feared: the two lists of location IDs are indeed very different.</p>
     * <p>Or are they?</p>
     * <p>The Historians can't agree on which group made the mistakes <em>or</em> how to read most of the Chief's handwriting, but in the commotion you notice an interesting detail: a lot of location IDs appear in both lists! Maybe the other numbers aren't location IDs at all but rather misinterpreted handwriting.</p>
     * <p>This time, you'll need to figure out exactly how often each number from the left list appears in the right list. Calculate a total <em>similarity score</em> by adding up each number in the left list after multiplying it by the number of times that number appears in the right list.</p>
     * <p>Here are the same example lists again:</p>
     * <pre><code>
     * 3   4
     * 4   3
     * 2   5
     * 1   3
     * 3   9
     * 3   3
     * </code></pre>
     * <p>For these example lists, here is the process of finding the similarity score:</p>
     * <ul>
     * <li>The first number in the left list is <code>3</code>. It appears in the right list three times, so the similarity score increases by <code>3 * 3 = <em>9</em></code>.</li>
     * <li>The second number in the left list is <code>4</code>. It appears in the right list once, so the similarity score increases by <code>4 * 1 = <em>4</em></code>.</li>
     * <li>The third number in the left list is <code>2</code>. It does not appear in the right list, so the similarity score does not increase (<code>2 * 0 = 0</code>).</li>
     * <li>The fourth number, <code>1</code>, also does not appear in the right list.</li>
     * <li>The fifth number, <code>3</code>, appears in the right list three times; the similarity score increases by <code><em>9</em></code>.</li>
     * <li>The last number, <code>3</code>, appears in the right list three times; the similarity score again increases by <code><em>9</em></code>.</li>
     * </ul>
     * <p>So, for these example lists, the similarity score at the end of this process is <code><em>31</em></code> (<code>9 + 4 + 0 + 0 + 9 + 9</code>).</p>
     * <p>Once again consider your left and right lists. <strong>What is their similarity score?</strong></p>
     */
    public static void partTwo() {
        System.out.print("1-2 : ");
        sortLists();
        int score = 0;
        Map<Integer, Integer> rightMap = getNumberOccurrences();
        for (int num : day1LeftList) {
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

    private static void sortLists() {
        Arrays.sort(day1LeftList);
        Arrays.sort(day1RightList);
    }

    private static Map<Integer, Integer> getNumberOccurrences() {
        // Create a HashMap to store the number and its occurrences
        Map<Integer, Integer> occurrences = new HashMap<>();

        // Iterate through the array of numbers
        for (int number : Inputs.day1RightList) {
            // Increment the count if the number exists, or set to 1 if it doesn't
            occurrences.put(number, occurrences.getOrDefault(number, 0) + 1);
        }

        return occurrences;
    }
}
