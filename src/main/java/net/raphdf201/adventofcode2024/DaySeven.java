package net.raphdf201.adventofcode2024;

import java.util.ArrayList;
import java.util.List;

import static net.raphdf201.adventofcode2024.Inputs.day7Equations;

public class DaySeven {
    private static final String[] lines = day7Equations.split("\n");

    public static void partOne() {
        System.out.print("7-1 : ");
        long total = 0;
        for (String equation : lines) {
            String[] eq = equation.split(": ");
            long target = Long.parseLong(eq[0]);
            int[] numbers = toIntArray(eq[1].split(" "));

            if (canReachTarget(target, numbers)) {
                total += target;
            }
        }

        System.out.println(total);
    }

    public static void partTwo() {
        System.out.print("7-2 : ");
        long total = 0;
        for (String equation : lines) {
            String[] eq = equation.split(": ");
            long target = Long.parseLong(eq[0]);
            int[] numbers = toIntArray(eq[1].split(" "));

            if (canReachTargetWithConcat(target, numbers)) {
                total += target;
            }
        }
        System.out.println(total);
    }

    public static boolean canReachTargetWithConcat(long target, int[] numbers) {
        int n = numbers.length;
        int totalCombinations = (int) Math.pow(3, n - 1); // +, *, ||

        for (int i = 0; i < totalCombinations; i++) {
            char[] ops = new char[n - 1];
            int combo = i;
            for (int j = 0; j < n - 1; j++) {
                int op = combo % 3;
                ops[j] = op == 0 ? '+' : (op == 1 ? '*' : '|');
                combo /= 3;
            }

            if (evaluateWithConcat(numbers, ops) == target) {
                return true;
            }
        }
        return false;
    }

    private static long evaluateWithConcat(int[] numbers, char[] ops) {
        List<Long> values = new ArrayList<>();
        List<Character> operations = new ArrayList<>();

        // Start with the first number
        long current = numbers[0];

        for (int i = 0; i < ops.length; i++) {
            char op = ops[i];
            int next = numbers[i + 1];

            if (op == '|') {
                // Concatenate numbers as strings and parse back to long
                current = Long.parseLong(String.valueOf(current) + next);
            } else {
                values.add(current);
                operations.add(op);
                current = next;
            }
        }

        values.add(current); // Add the last value

        // Now evaluate left-to-right using the values and operations
        long result = values.getFirst();
        for (int i = 0; i < operations.size(); i++) {
            char op = operations.get(i);
            long next = values.get(i + 1);
            result = (op == '+') ? result + next : result * next;
        }

        return result;
    }

    public static int[] toIntArray(String[] array) {
        int[] tmp = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            tmp[i] = Integer.parseInt(array[i]);
        }
        return tmp;
    }

    public static boolean canReachTarget(long target, int[] numbers) {
        int n = numbers.length;
        int totalCombinations = 1 << (n - 1);
        for (int i = 0; i < totalCombinations; i++) {
            char[] ops = new char[n - 1];
            for (int j = 0; j < ops.length; j++) {
                ops[j] = ((i >> j) & 1) == 0 ? '+' : '*';
            }
            if (evaluateLeftToRight(numbers, ops) == target) {
                return true;
            }
        }
        return false;
    }

    private static long evaluateLeftToRight(int[] numbers, char[] ops) {
        long result = numbers[0];
        for (int i = 0; i < ops.length; i++) {
            if (ops[i] == '+') {
                result += numbers[i + 1];
            } else {
                result *= numbers[i + 1];
            }
        }
        return result;
    }
}
