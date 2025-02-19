package exceptions;

import java.io.IOException;

public class WrongUserInputException extends IOException {
    public WrongUserInputException() {
    }

    public WrongUserInputException(String message) {
        super(message);
    }
}
