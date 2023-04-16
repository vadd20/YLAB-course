package io.ylab.intensive.lesson02_OOP.rateLimitedPrinter;

public class RateLimitedPrinterTests {
    public static void main(String[] args) throws InterruptedException {
        RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(1000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }
    }
}
