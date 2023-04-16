package io.ylab.intensive.lesson02_OOP.statsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int count = 0;
    private int sum = 0;
    private double average = 0;

    @Override
    public void add(int value) {
        this.sum += value;
        ++this.count;
        if (value > this.max) {
            this.max = value;
        }
        if (value < this.min) {
            this.min = value;
        }
        this.average = (double) this.sum / this.count;
    }
    @Override
    public int getMin() {
        return this.min;
    }

    @Override
    public int getMax() {
        return this.max;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public Double getAvg() {
        return this.average;
    }
}
