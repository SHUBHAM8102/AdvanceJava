package pom.capgemini.exception;

public class ShortCodeAlreadyExistsException extends RuntimeException {

    public ShortCodeAlreadyExistsException(String shortCode) {
        super("Short code '" + shortCode + "' already exists");
    }
}

