package io.ylab.intensive.lesson02_OOP.snilsValidator;

public class SnilsValidatorTests {
    public static void main(String[] args) {
        System.out.println("SNILS 01468870570 is " + new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println("SNILS 90114404441 is " + new SnilsValidatorImpl().validate("90114404441")); //true
        System.out.println("SNILS dsajhas842s is " + new SnilsValidatorImpl().validate("dsajhas842s")); //false
        System.out.println("SNILS 199605529zh is " + new SnilsValidatorImpl().validate("199605529zh")); //false
        System.out.println("SNILS 19965930903293204 is " + new SnilsValidatorImpl().validate("19965930903293204")); //false
    }
}
