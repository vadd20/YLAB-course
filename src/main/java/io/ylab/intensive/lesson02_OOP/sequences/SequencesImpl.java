package io.ylab.intensive.lesson02_OOP.sequences;

public class SequencesImpl implements Sequences {
    @Override
    public void a(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            System.out.print(i * 2 + " ");
        }
        System.out.println();
    }

    @Override
    public void b(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            System.out.print((i * 2 - 1) + " ");
        }
        System.out.println();
    }

    @Override
    public void c(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            System.out.print(i * i + " ");
        }
        System.out.println();
    }

    @Override
    public void d(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            System.out.print(i * i * i + " ");
        }
        System.out.println();
    }

    @Override
    public void e(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            if (i % 2 == 1) {
                System.out.print("1 ");
            } else {
                System.out.print("-1 ");
            }
        }
        System.out.println();
    }
    @Override
    public void f(int n) {

        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            if (i % 2 == 1) {
                System.out.print(i + " ");
            } else {
                System.out.print(-i + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void g(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            if (i % 2 == 1) {
                System.out.print(i * i + " ");
            } else {
                System.out.print(-i * i + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void h(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            if (i % 2 == 1) {
                System.out.print((i + 1) / 2 + " ");
            } else {
                System.out.print("0 ");
            }
        }
        System.out.println();
    }

    @Override
    public void i(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        for (int i = 1; i <= n; ++i) {
            int factorialNumber = i;
            int j = i;
            while (j > 1) {
                factorialNumber = factorialNumber * (j - 1);
                --j;
            }
            System.out.print(factorialNumber + " ");
        }
        System.out.println();
    }

    @Override
    public void j(int n) {
        if (n < 1) {
            System.out.println("Number < 0");
            return;
        }
        int lastNum = 1;
        int penultimateNum = 0;
        int temp = 0;
        for (int i = 1; i <= n; ++i) {
            if (i == 1) {
                System.out.print("1 ");
            } else {
                temp = lastNum;
                lastNum = lastNum + penultimateNum;
                penultimateNum = temp;
                System.out.print(lastNum + " ");
            }
        }
        System.out.println();
    }
}
