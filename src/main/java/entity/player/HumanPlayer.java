package entity.player;

import entity.game.GameSession;
import exception.WrongUserInputException;
import handler.field.FieldActionManager;
import handler.message.ErrorMessages;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void move(Scanner scanner, FieldActionManager actionManager, GameSession session) throws WrongUserInputException {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        if (actionManager.moveValidation(x, y)) {
            session.getField()[--x][--y] = this.getSignature();
            session.moveCounter++;
        } else {
            throw new WrongUserInputException(ErrorMessages.WRONG_POSITION.toString());
        }
    }
}
