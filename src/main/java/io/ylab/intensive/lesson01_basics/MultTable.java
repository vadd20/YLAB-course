package io.ylab.intensive.lesson01_basics;

public class MultTable {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                System.out.println((i + 1) + " x " + (j + 1) + " = " + (i + 1) * (j + 1));
            }
        }
    }
}
