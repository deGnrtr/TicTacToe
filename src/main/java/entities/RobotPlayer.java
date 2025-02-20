package entities;

import exceptions.WrongUserInputException;
import handlers.FieldActionManager;

import java.util.Scanner;
import java.util.random.RandomGenerator;

public class RobotPlayer extends Player{

    public RobotPlayer() {
        super("T-800");
    }

    @Override
    public void move(Scanner scan, GameSession session) throws WrongUserInputException {
        RandomGenerator rand = RandomGenerator.getDefault();
        int x = rand.nextInt(1, 4);
        int y = rand.nextInt(1, 4);
        if (FieldActionManager.moveValidation(x, y, session)){
            session.getField()[--x][--y] = this.getSignature();
            session.counter++;
        } else {
            throw new WrongUserInputException("Thinking...");
        }
    }
}
