package io.ylab.intensive.lesson03_collections_and_files.passwordValidator;

public class WrongLoginException extends Exception {
    public WrongLoginException () {}
    public WrongLoginException (String message) {
        super(message);
    }
}
