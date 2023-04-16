package io.ylab.intensive.lesson01_basics;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int number = new Random().nextInt(99) + 1; // здесь загадывается число от 1 до 99
            int maxAttempts = 10; // здесь задается количество попыток
            System.out.println("Я загадал число от 1 до 99. У тебя " + maxAttempts + " попыток угадать.");
            int entered = 0;
            boolean is_guessed = false;
            for (int i = 1; i <= maxAttempts; ++i) {
                entered = scanner.nextInt();
                if (entered == number) {
                    System.out.println("Ты угадал с " + i + " попытки!");
                    is_guessed = true;
                    break;
                } else if (entered < number) {
                    System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - i) + " попыток");
                } else {
                    System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - i) + " попыток");
                }
            }
            if (!is_guessed) {
                System.out.println("Ты не угадал");
            }
        }
    }
}
