package pom.capgemini.exception;

public class ShortCodeNotFoundException extends RuntimeException {

    public ShortCodeNotFoundException(String shortCode) {
        super("Short code '" + shortCode + "' not found");
    }
}

