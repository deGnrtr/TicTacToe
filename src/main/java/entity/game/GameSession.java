package entity.game;

import entity.player.Player;

import java.util.HashMap;

public class GameSession {
    private final HashMap<String, Integer> winStat = new HashMap<>();
    private int moveCounter;
    private int gameMode;
    private char[][] field;
    private Player playerONE;
    private Player playerTWO;
    private GameStatus currentGameStatus = GameStatus.IN_PROGRESS;
    public static final char FIELD_FILLER = '\u00B7';

    public GameStatus getCurrentGameStatus() {
        return currentGameStatus;
    }

    public void setCurrentGameStatus(GameStatus currentGameStatus) {
        this.currentGameStatus = currentGameStatus;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public Player getPlayerONE() {
        return playerONE;
    }

    public void setPlayerONE(Player playerONE) {
        this.playerONE = playerONE;
        winStat.put(playerONE.getNAME(), 0);
    }

    public Player getPlayerTWO() {
        return playerTWO;
    }

    public void setPlayerTWO(Player playerTWO) {
        this.playerTWO = playerTWO;
        winStat.put(playerTWO.getNAME(), 0);
    }

    public char[][] getField() {
        return field;
    }

    public void setField(int size) {
        field = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = FIELD_FILLER;
            }
        }
    }

    public HashMap<String, Integer> getWinStat() {
        return winStat;
    }

    public void reset() {
        setField(field.length);
        moveCounter = 0;
        currentGameStatus = GameStatus.IN_PROGRESS;
    }

    public void showField() {
        System.out.println("  1 2 3");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.print("\n");
        }
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }
}
