package io.ylab.intensive.lesson02_OOP.complexNumbers;

public class ComplexNumbers {
    private double real;
    private double imagine;

    public ComplexNumbers(double real) {
        this.real = real;
        this.imagine = 0;
    }

    public ComplexNumbers(double real, double imagine) {
        this.real = real;
        this.imagine = imagine;
    }

    public ComplexNumbers addition(ComplexNumbers number) {
        return new ComplexNumbers(this.real + number.real,
                this.imagine + number.imagine);
    }

    public ComplexNumbers subtraction(ComplexNumbers number) {
        return new ComplexNumbers(this.real - number.real,
                this.imagine - number.imagine);
    }

    public ComplexNumbers multiplication(ComplexNumbers number) {
        return new ComplexNumbers(this.real * number.real - this.imagine * number.imagine,
                this.real * number.imagine + this.imagine * number.real);
    }

    public double module() {
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imagine, 2));
    }

    @Override
    public String toString() {
        if (imagine >= 0) {
            return real + " + " + imagine + "i";
        }
        return real + " - " + -imagine + "i";
    }
}
