package entity;

import exception.WrongUserInputException;
import handler.FieldActionManager;

import java.util.Scanner;
import java.util.random.RandomGenerator;

public class RobotPlayer extends Player {

    public RobotPlayer() {
        super("T-800");
    }

    @Override
    public void move(Scanner scan, FieldActionManager actionManager, GameSession session) throws WrongUserInputException {
        RandomGenerator rand = RandomGenerator.getDefault();
        int x = rand.nextInt(1, 4);
        int y = rand.nextInt(1, 4);
        if (actionManager.moveValidation(x, y)) {
            session.getField()[--x][--y] = this.getSignature();
            session.counter++;
        } else {
            throw new WrongUserInputException("Thinking...");
        }
    }
}
