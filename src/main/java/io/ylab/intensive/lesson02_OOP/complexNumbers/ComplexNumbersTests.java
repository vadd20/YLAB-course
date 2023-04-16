package io.ylab.intensive.lesson02_OOP.complexNumbers;

public class ComplexNumbersTests {
    public static void main(String[] args) {
        ComplexNumbers complexNumber1 = new ComplexNumbers(7.5);
        ComplexNumbers complexNumber2 = new ComplexNumbers(2.1, -7.6);

        System.out.println("First Number: " + complexNumber1.toString());
        System.out.println("Second Number: " + complexNumber2.toString());

        ComplexNumbers number = complexNumber1.addition(complexNumber2);
        System.out.println("Addition: " + number.toString());

        number = complexNumber1.subtraction(complexNumber2);
        System.out.println("Subtraction: " + number.toString());

        number = complexNumber1.multiplication(complexNumber2);
        System.out.println("Multiplication: " + number.toString());

        double numberModule = complexNumber2.module();
        System.out.println("Module: " + numberModule);
    }
}
