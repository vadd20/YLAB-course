package io.ylab.intensive.lesson03_collections_and_files.passwordValidator;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {}
    public WrongPasswordException(String message) {
        super(message);
    }
}
