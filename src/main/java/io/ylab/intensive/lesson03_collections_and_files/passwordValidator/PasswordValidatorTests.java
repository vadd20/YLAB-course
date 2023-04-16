package io.ylab.intensive.lesson03_collections_and_files.passwordValidator;

import java.util.Scanner;

public class PasswordValidatorTests {
    public static void main(String[] args) throws WrongLoginException, WrongPasswordException {
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        String password = scanner.nextLine();
        String confirmPassword = scanner.nextLine();
        System.out.println(PasswordValidator.passwordIsTrue(login, password, confirmPassword));
    }
}
