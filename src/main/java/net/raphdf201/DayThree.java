package net.raphdf201;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.raphdf201.Inputs.day3Program;

public class DayThree {
    public static void partOne() {
        System.out.println("Part one :");
        String regex = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(day3Program);
        int total = 0;

        while (matcher.find()) {
            String firstValue = matcher.group(1); // First number
            String secondValue = matcher.group(2); // Second number
            total += Integer.parseInt(firstValue) * Integer.parseInt(secondValue);
        }

        System.out.println("Total : " + total);
    }

    public static void partTwo() {
        System.out.println("Part two :");
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

        System.out.println("Total : " + total);
    }
}
