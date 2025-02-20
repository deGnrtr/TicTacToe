package entity;

import exception.WrongUserInputException;
import handler.FieldActionManager;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void move(Scanner scanner, GameSession session) throws WrongUserInputException {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        if (session.getActionManager().moveValidation(x, y, session)) {
            session.getField()[--x][--y] = this.getSignature();
            session.counter++;
        } else {
            throw new WrongUserInputException("Wrong position. Try again, please.");
        }
    }
}
