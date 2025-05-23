package net.raphdf201.adventofcode2024;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.raphdf201.adventofcode2024.Inputs.day3Program;

public class DayThree {
    /**
     * <p>"Our computers are having issues, so I have no idea if we have any Chief Historians in stock! You're welcome to check the warehouse, though," says the mildly flustered shopkeeper at the <a href="https://adventofcode.com/2020/day/2">North Pole Toboggan Rental Shop</a>. The Historians head out to take a look.</p>
     * <p>The shopkeeper turns to you. "Any chance you can see why our computers are having issues again?"</p>
     * <p>The computer appears to be trying to run a program, but its memory (your puzzle input) is <em>corrupted</em>. All the instructions have been jumbled up!</p>
     * <p>It seems like the goal of the program is just to <em>multiply some numbers</em>. It does that with instructions like <code>mul(X,Y)</code>, where <code>X</code> and <code>Y</code> are each 1-3 digit numbers. For instance, <code>mul(44,46)</code> multiplies <code>44</code> by <code>46</code> to get a result of <code>2024</code>. Similarly, <code>mul(123,4)</code> would multiply <code>123</code> by <code>4</code>.</p>
     * <p>However, because the program's memory has been corrupted, there are also many invalid characters that should be <em>ignored</em>, even if they look like part of a <code>mul</code> instruction. Sequences like <code>mul(4*</code>, <code>mul(6,9!</code>, <code>?(12,34)</code>, or <code>mul ( 2 , 4 )</code> do <em>nothing</em>.</p>
     * <p>For example, consider the following section of corrupted memory:</p>
     * <pre>{@code xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))}</pre>
     * <p>Only the four highlighted sections are real <code>mul</code> instructions. Adding up the result of each instruction produces <code><em>161</em></code> (<code>2*4 + 5*5 + 11*8 + 8*5</code>).</p>
     * <p>Scan the corrupted memory for uncorrupted <code>mul</code> instructions. <strong>What do you get if you add up all the results of the multiplications?</strong></p>
     */
    public static void partOne() {
        System.out.print("3-1 : ");
        String regex = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(day3Program);
        int total = 0;

        while (matcher.find()) {
            String firstValue = matcher.group(1); // First number
            String secondValue = matcher.group(2); // Second number
            total += Integer.parseInt(firstValue) * Integer.parseInt(secondValue);
        }

        System.out.println(total);
    }

    /**
     * <p>As you scan through the corrupted memory, you notice that some of the conditional statements are also still intact. If you handle some of the uncorrupted conditional statements in the program, you might be able to get an even more accurate result.</p>
     * <p>There are two new instructions you'll need to handle:</p>
     * <ul>
     * <li>The <code>do()</code> instruction <em>enables</em> future <code>mul</code> instructions.</li>
     * <li>The <code>don't()</code> instruction <em>disables</em> future <code>mul</code> instructions.</li>
     * </ul>
     * <p>Only the <em>most recent</em> <code>do()</code> or <code>don't()</code> instruction applies. At the beginning of the program, <code>mul</code> instructions are <em>enabled</em>.</p>
     * <p>For example:</p>
     * <pre>{@code xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))}</pre>
     * <p>This corrupted memory is similar to the example from before, but this time the <code>mul(5,5)</code> and <code>mul(11,8)</code> instructions are <em>disabled</em> because there is a <code>don't()</code> instruction before them. The other <code>mul</code> instructions function normally, including the one at the end that gets re-<em>enabled</em> by a <code>do()</code> instruction.</p>
     * <p>This time, the sum of the results is <code><em>48</em></code> (<code>2*4 + 8*5</code>).</p>
     * <p>Handle the new instructions; <strong>what do you get if you add up all the results of just the enabled multiplications?</strong></p>
     */
    public static void partTwo() {
        System.out.print("3-2 : ");
        String regex = "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(day3Program);
        boolean isEnabled = true;
        int total = 0;

        while (matcher.find()) {
            String match = matcher.group();

            if (match.equals("do()")) {
                // Enable mul() instructions
                isEnabled = true;
            } else if (match.equals("don't()")) {
                // Disable mul() instructions
                isEnabled = false;
            } else if (match.startsWith("mul(") && isEnabled) {
                // Extract numbers and calculate the product
                int num1 = Integer.parseInt(matcher.group(1));
                int num2 = Integer.parseInt(matcher.group(2));
                total += num1 * num2;
            }
        }

        System.out.println(total);
    }
}
