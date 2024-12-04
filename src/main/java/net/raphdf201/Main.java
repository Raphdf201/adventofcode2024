package net.raphdf201;

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
        }
    }
}