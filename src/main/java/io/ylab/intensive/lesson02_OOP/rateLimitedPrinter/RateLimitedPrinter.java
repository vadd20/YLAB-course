package io.ylab.intensive.lesson02_OOP.rateLimitedPrinter;

public class RateLimitedPrinter {
    private long lastPrintTime;
    private int interval;
    public RateLimitedPrinter(int interval) throws InterruptedException {
        lastPrintTime = 0;
        this.interval = interval;
    }

    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastPrintTime >= this.interval) {
            System.out.println(message);
            lastPrintTime = currentTime;
        }
    }
}
