package handler.message;

public enum ErrorMessages {

    WRONG_CHOICE("Choose from listed options!"),
    SMALL_FIELD("Field size must be greater then 3."),
    NOT_A_NUMBER("Number required!"),
    WRONG_COORDINATES("Only numeric coordinates are acceptable!"),
    MACHINE_THINKING("Thinking..."),
    WRONG_POSITION("Wrong position. Try again, please.");

    private final String MESSAGE;

    ErrorMessages(String text){
        this.MESSAGE = text;
    }

    @Override
    public String toString(){
        return MESSAGE;
    }
}
