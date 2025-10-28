package y2024;

import java.util.Arrays;

public class DayTwo {
    /**
     * <p>Fortunately, the first location The Historians want to search isn't a long walk from the Chief Historian's office.</p>
     * <p>While the <a href="https://adventofcode.com/2015/day/19">Red-Nosed Reindeer nuclear fusion/fission plant</a> appears to contain no sign of the Chief Historian, the engineers there run up to you as soon as they see you. Apparently, they <em>still</em> talk about the time Rudolph was saved through molecular synthesis from a single electron.</p>
     * <p>They're quick to add that - since you're already here - they'd really appreciate your help analyzing some unusual data from the Red-Nosed reactor. You turn to check if The Historians are waiting for you, but they seem to have already divided into groups that are currently searching every corner of the facility. You offer to help with the unusual data.</p>
     * <p>The unusual data (your puzzle input) consists of many <em>reports</em>, one report per line. Each report is a list of numbers called <em>levels</em> that are separated by spaces. For example:</p>
     * <pre><code>
     * 7 6 4 2 1
     * 1 2 7 8 9
     * 9 7 6 2 1
     * 1 3 2 4 5
     * 8 6 4 4 1
     * 1 3 6 7 9
     * </code></pre>
     * <p>This example data contains six reports each containing five levels.</p>
     * <p>The engineers are trying to figure out which reports are <em>safe</em>. The Red-Nosed reactor safety systems can only tolerate levels that are either gradually increasing or gradually decreasing. So, a report only counts as safe if both of the following are true:</p>
     * <ul>
     * <li>The levels are either <em>all increasing</em> or <em>all decreasing</em>.</li>
     * <li>Any two adjacent levels differ by <em>at least one</em> and <em>at most three</em>.</li>
     * </ul>
     * <p>In the example above, the reports can be found safe or unsafe by checking those rules:</p>
     * <ul>
     * <li><code>7 6 4 2 1</code>: <em>Safe</em> because the levels are all decreasing by 1 or 2.</li>
     * <li><code>1 2 7 8 9</code>: <em>Unsafe</em> because <code>2 7</code> is an increase of 5.</li>
     * <li><code>9 7 6 2 1</code>: <em>Unsafe</em> because <code>6 2</code> is a decrease of 4.</li>
     * <li><code>1 3 2 4 5</code>: <em>Unsafe</em> because <code>1 3</code> is increasing but <code>3 2</code> is decreasing.</li>
     * <li><code>8 6 4 4 1</code>: <em>Unsafe</em> because <code>4 4</code> is neither an increase nor a decrease.</li>
     * <li><code>1 3 6 7 9</code>: <em>Safe</em> because the levels are all increasing by 1, 2, or 3.</li>
     * </ul>
     * <p>So, in this example, <code><em>2</em></code> reports are <em>safe</em>.</p>
     * <p>Analyze the unusual data from the engineers. <strong>How many reports are safe?</strong></p>
     */
    public static Long partOne() {
        String[] rows = Inputs.day2Reports.split("\n");
        int[][] serialized = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] levels = rows[i].split(" ");
            serialized[i] = Arrays.stream(levels)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        return Arrays.stream(serialized)
                .filter(DayTwo::isSafeReport)
                .count();
    }

    /**
     * <p>The engineers are surprised by the low number of safe reports until they realize they forgot to tell you about the Problem Dampener.</p>
     * <p>The Problem Dampener is a reactor-mounted module that lets the reactor safety systems <em>tolerate a single bad level</em> in what would otherwise be a safe report. It's like the bad level never happened!</p>
     * <p>Now, the same rules apply as before, except if removing a single level from an unsafe report would make it safe, the report instead counts as safe.</p>
     * <p>More of the above example's reports are now safe:</p>
     * <ul>
     * <li><code>7 6 4 2 1</code>: <em>Safe</em> without removing any level.</li>
     * <li><code>1 2 7 8 9</code>: <em>Unsafe</em> regardless of which level is removed.</li>
     * <li><code>9 7 6 2 1</code>: <em>Unsafe</em> regardless of which level is removed.</li>
     * <li><code>1 <em>3</em> 2 4 5</code>: <em>Safe</em> by removing the second level, <code>3</code>.</li>
     * <li><code>8 6 <em>4</em> 4 1</code>: <em>Safe</em> by removing the third level, <code>4</code>.</li>
     * <li><code>1 3 6 7 9</code>: <em>Safe</em> without removing any level.</li>
     * </ul>
     * <p>Thanks to the Problem Dampener, <code><em>4</em></code> reports are actually <em>safe</em>!</p>
     * <p>Update your analysis by handling situations where the Problem Dampener can remove a single level from unsafe reports. <strong>How many reports are now safe?</strong></p>
     */
    public static Long partTwo() {
        String[] rows = Inputs.day2Reports.split("\n");
        int[][] serialized = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] levels = rows[i].split(" ");
            serialized[i] = Arrays.stream(levels)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        return Arrays.stream(serialized)
                .filter(DayTwo::isSafeWithDampener)
                .count();
    }

    private static boolean isSafeReport(int[] report) {
        Boolean increasing = null;
        for (int i = 0; i < report.length - 1; i++) {
            int diff = report[i + 1] - report[i];
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }
            if (increasing == null) {
                if (diff > 0) {
                    increasing = true;
                } else if (diff < 0) {
                    increasing = false;
                }
            } else {
                // Check monotonicity consistency
                if (increasing && diff < 0) {
                    return false;
                }
                if (!increasing && diff > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSafeWithDampener(int[] report) {
        if (isSafeReport(report)) {
            return true;
        }
        for (int i = 0; i < report.length; i++) {
            int[] reducedReport = new int[report.length - 1];
            System.arraycopy(report, 0, reducedReport, 0, i);
            System.arraycopy(report, i + 1, reducedReport, i, report.length - i - 1);
            if (isSafeReport(reducedReport)) {
                return true;
            }
        }
        return false;
    }
}
