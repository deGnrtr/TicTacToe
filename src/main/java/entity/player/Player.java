package entity.player;

import entity.game.GameSession;
import exception.WrongUserInputException;
import handler.field.FieldActionManager;

import java.util.Scanner;

public abstract class Player {
    private final String NAME;
    private char signature;

    protected Player(String name) {
        this.NAME = name;
    }

    public char getSignature() {
        return signature;
    }

    public void setSignature(char signature) {
        this.signature = signature;
    }

    abstract public void move(Scanner scan, FieldActionManager actionManager, GameSession session) throws WrongUserInputException;

    public String getNAME() {
        return NAME;
    }
}
