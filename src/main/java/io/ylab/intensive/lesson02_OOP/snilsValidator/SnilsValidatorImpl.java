package io.ylab.intensive.lesson02_OOP.snilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {
        if (snils.length() != 11) {
            return false;
        }
        int sum = 0;
        int checkSum = 0;
        for (int i = 0; i < 11; ++i) {
            if (Character.digit(snils.charAt(i), 10) == -1) {
                return false;
            }
            if (i < 9) {
                sum += (9 - i) * Character.digit(snils.charAt(i), 10);
            } else if (i == 9) {
                checkSum += 10 * Character.digit(snils.charAt(i), 10);
            } else {
                checkSum += Character.digit(snils.charAt(i), 10);
            }
        }
        if (sum < 100) {
            return sum == checkSum;
        } else if (sum == 100) {
            return checkSum == 0;
        } else {
            if (sum % 101 == 0) {
                return checkSum == 0;
            } else {
                return checkSum == sum % 101;
            }
        }
    }
}
