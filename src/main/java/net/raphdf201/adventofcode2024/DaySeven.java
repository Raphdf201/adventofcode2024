package net.raphdf201.adventofcode2024;

import java.math.BigInteger;

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
        BigInteger total = BigInteger.ZERO;
        for (String equation : lines) {
            String[] eq = equation.split(": ");
            BigInteger target = new BigInteger(eq[0]);
            int[] numbers = toIntArray(eq[1].split(" "));

            if (canReachTargetWithConcat(target, numbers)) {
                total = total.add(target);
            }
        }
        System.out.println(total);
    }


    public static boolean canReachTargetWithConcat(BigInteger target, int[] numbers) {
        return dfs(BigInteger.valueOf(numbers[0]), 1, numbers, target);
    }

    private static boolean dfs(BigInteger currentValue, int index, int[] numbers, BigInteger target) {
        if (index == numbers.length) {
            return currentValue.equals(target);
        }

        BigInteger next = BigInteger.valueOf(numbers[index]);

        // Try +
        if (dfs(currentValue.add(next), index + 1, numbers, target)) {
            return true;
        }

        // Try *
        if (dfs(currentValue.multiply(next), index + 1, numbers, target)) {
            return true;
        }

        // Try ||
        BigInteger concat = new BigInteger(currentValue + next.toString());
        return dfs(concat, index + 1, numbers, target);
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
