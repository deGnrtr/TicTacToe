package entities;

import java.util.Objects;

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
}
