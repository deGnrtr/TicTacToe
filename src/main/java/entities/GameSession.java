package entities;

import java.util.HashMap;

public class GameSession {
    private int gameMode;
    private char[][] field;
    private Player playerONE;
    private Player playerTWO;
    public int counter;
    private int gameStatus = 0; //-1 draw, 0 in progress, 1 victory

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    private HashMap<String, Integer> winStat = new HashMap<>();

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
        winStat.put(playerONE.getName(), 0);
    }

    public Player getPlayerTWO() {
        return playerTWO;
    }

    public void setPlayerTWO(Player playerTWO) {
        this.playerTWO = playerTWO;
        winStat.put(playerTWO.getName(), 0);
    }

    public void setField(int size){
        if (field == null){
            field = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    field[i][j] = '\u00B7';
                }
            }
        }
    }

    public void reset(){
        field = null;
        counter = 0;
        playerONE = null;
        playerTWO = null;
        gameStatus = 0;
    }
    public char[][] getField() {
        return field;
    }

    public HashMap<String, Integer> getWinStat() {
        return winStat;
    }

    public void showField(){
        System.out.println("  1 2 3");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.print("\n");
        }
    }
}
