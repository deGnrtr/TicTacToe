package handler.field;

import entity.game.GameSession;
import entity.game.GameStatus;

public class FieldActionManager {
    
    private final GameSession CURRENT_SESSION;
    private String winCondition;
    
    public FieldActionManager(GameSession CURRENT_SESSION) {
        this.CURRENT_SESSION = CURRENT_SESSION;
    }

    public void check(char signature) {
        winCondition = calculateWinCondition(signature);
        checkForDraw();
        checkForHorizontalWin();
        checkForVerticalWin();
        checkForLeftDiagonalWin();
        checkForRightDiagonalWin();
    }

    private void checkForDraw() {
        if (CURRENT_SESSION.moveCounter == Math.pow(CURRENT_SESSION.getField()[0].length, 2)) {
            CURRENT_SESSION.setCurrentGameStatus(GameStatus.DRAW);
        }
    }

    private String calculateWinCondition(char signature) {
        return ".*[" + signature + "]{ " + CURRENT_SESSION.getField()[0].length + ".*";
    }

    private void checkForHorizontalWin() {
        for (int i = 0; i < CURRENT_SESSION.getField().length; i++) {
            String result = "";
            for (int j = 0; j < CURRENT_SESSION.getField().length; j++) {
                result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][j]));
            }
            if (result.matches(winCondition)) {
                CURRENT_SESSION.setCurrentGameStatus(GameStatus.VICTORY);
                break;
            }
        }
    }

    private void checkForVerticalWin() {
        for (int j = 0; j < CURRENT_SESSION.getField().length; j++) {
            String result = "";
            for (int i = 0; i < CURRENT_SESSION.getField().length; i++) {
                result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][j]));
            }
            if (result.matches(winCondition)) {
                CURRENT_SESSION.setCurrentGameStatus(GameStatus.VICTORY);
                break;
            }
        }
    }

    private void checkForLeftDiagonalWin() {
        for (int i = 0; i < CURRENT_SESSION.getField().length; i++) {
            String result = "";
            result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][i]));
            if (result.matches(winCondition)) {
                CURRENT_SESSION.setCurrentGameStatus(GameStatus.VICTORY);
                break;
            }
        }
    }

    private void checkForRightDiagonalWin() {
        for (int i = CURRENT_SESSION.getField().length - 1; i >= 0; i--) {
            String result = "";
            result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][i]));
            if (result.matches(winCondition)) {
                CURRENT_SESSION.setCurrentGameStatus(GameStatus.VICTORY);
                break;
            }
        }
    }

    public boolean moveValidation(int x, int y) {
        return (x > 0 && y > 0 && x <= CURRENT_SESSION.getField().length && y <= CURRENT_SESSION.getField().length && CURRENT_SESSION.getField()[--x][--y] == CURRENT_SESSION.FIELD_FILLER);
    }
}
