package io.ylab.intensive.lesson01_basics;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            if (n == 0) {
                System.out.println(0);
            } else if (n == 1) {
                System.out.println(1);
            } else if (n < 0 || n > 30) {
                System.out.println("Введенное число меньше 0 или больше 30.");
            } else {
                int pellNumber = 0;
                int penultNumber = 0;
                int lastNumber = 1;
                for (int i = 2; i <= n; ++i) {
                    pellNumber = 2 * lastNumber + penultNumber;
                    penultNumber = lastNumber;
                    lastNumber = pellNumber;
                }
                System.out.println(pellNumber);
            }
        }
    }
}