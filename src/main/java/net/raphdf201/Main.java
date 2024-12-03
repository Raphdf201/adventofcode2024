package net.raphdf201;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter day :");
        byte day = sc.nextByte();
        switch (day) {
            case 1:
                DayOne.partOne();
                DayOne.partTwo();
                break;
        }
    }
}