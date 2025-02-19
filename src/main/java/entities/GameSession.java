package entities;

import java.util.HashMap;

public class GameSession {
    private int gameMode;
    private int size;
    private char[][] field;
    public Player playerONE;
    public Player playerTWO;
    public int counter;

    private HashMap<String, Integer> winStat;

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
                    field[i][j] = '~';
                }
            }
        }
    }

    public void reset(){
        field = null;
        counter = 0;
    }
    public char[][] getField() {
        return field;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public HashMap<String, Integer> getWinStat() {
        return winStat;
    }

    public void showField(){
        System.out.println(" 1 2 3");
        for (int i = 0; i < field.length; i++) {
            System.out.print(String.valueOf(i + 1));
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.print("\n");
        }
    }
}
