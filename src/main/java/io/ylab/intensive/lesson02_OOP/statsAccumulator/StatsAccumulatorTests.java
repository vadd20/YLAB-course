package io.ylab.intensive.lesson02_OOP.statsAccumulator;

public class StatsAccumulatorTests {
    public static void main(String[] args) {
        StatsAccumulator s = new StatsAccumulatorImpl();

        s.add(1);
        s.add(2);
        System.out.println("Average: " + s.getAvg()); // 1.5 - среднее арифметическое

        s.add(0);
        System.out.println("Min number: " + s.getMin()); // 0 - минимальное из переданных чисел

        s.add(3);
        s.add(8);
        System.out.println("Min number: " + s.getMax()); // 8 - максимальный из переданных чисел
        System.out.println("Counter: " + s.getCount()); // 5 - количество
    }
}
