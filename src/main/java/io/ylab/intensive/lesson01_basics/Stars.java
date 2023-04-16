package io.ylab.intensive.lesson01_basics;

import java.util.Scanner;
public class Stars {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();
            if (n < 1 || m < 1) {
                System.out.println("Введенное число столбцов или строк меньше 1.");
            } else {
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m - 1; ++j) {
                        System.out.print(template + ' ');
                    }
                    System.out.print(template);
                    System.out.println();
                }
            }
        }
    }
}