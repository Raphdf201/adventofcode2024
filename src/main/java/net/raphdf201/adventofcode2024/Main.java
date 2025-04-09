package net.raphdf201.adventofcode2024;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter day : ");
        byte day = sc.nextByte();
        switch (day) {
            case 1:
                System.out.println("Day one :");
                DayOne.partOne();
                DayOne.partTwo();
                break;
            case 2:
                System.out.println("Day two :");
                DayTwo.partOne();
                DayTwo.partTwo();
                break;
            case 3:
                System.out.println("Day three :");
                DayThree.partOne();
                DayThree.partTwo();
                break;
            case 4:
                System.out.println("Day four :");
                DayFour.partOne();
                DayFour.partTwo();
                break;
            case 5:
                System.out.println("Day five :");
                DayFive.partOne();
                DayFive.partTwo();
                break;
            case 6:
                System.out.println("Day six :");
                DaySix.partOne();
                DaySix.partTwo();
                break;
            case 7:
                System.out.println("Day seven :");
                DaySeven.partOne();
                DaySeven.partTwo();
                break;
        }
    }
}