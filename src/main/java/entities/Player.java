package entities;

import exceptions.WrongUserInputException;

import java.util.Scanner;

public abstract class Player {
    private String name;
    private char signature;

    protected Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public char getSignature() {
        return signature;
    }

    public void setSignature(char signature) {
        this.signature = signature;
    }

    abstract public void move(Scanner scan, GameSession session) throws WrongUserInputException;
}
