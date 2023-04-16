package io.ylab.intensive.lesson03_collections_and_files.passwordValidator;

public class PasswordValidator {
    public static boolean passwordIsTrue(String login, String password, String confirmPassword)
            throws WrongLoginException, WrongPasswordException {
        try {
            if (!login.matches("^[a-zA-Z0-9_]+$")) {
                throw new WrongLoginException("Login contains invalid characters.");
            }
            if (login.length() >= 20) {
                throw new WrongLoginException("Login is too long.");
            }

            if (!password.matches("^[a-zA-Z0-9_]+$")) {
                throw new WrongPasswordException("Password contains invalid symbols");
            }
            if (password.length() >= 20) {
                throw new WrongPasswordException("Password is too long.");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Password and confirmation do not match.");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
