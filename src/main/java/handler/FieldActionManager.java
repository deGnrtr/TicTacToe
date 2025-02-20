package handler;

import entity.GameSession;

public class FieldActionManager {
    
    private final GameSession CURRENT_SESSION;
    
    public FieldActionManager(GameSession CURRENT_SESSION) {
        this.CURRENT_SESSION = CURRENT_SESSION;
    }

    public void check(char signature) {
        checkForDraw();
        checkForHorizontalWin(signature);
        checkForVerticalWin(signature);
        checkForLeftDiagonalWin(signature);
        checkForRightDiagonalWin(signature);
    }

    private void checkForDraw() {
        if (CURRENT_SESSION.counter == Math.pow(CURRENT_SESSION.getField()[0].length, 2)) {
            CURRENT_SESSION.setGameStatus(-1);
        }
    }

    private String calculateWinCondition(char signature) {
        return ".*[" + signature + "]{ " + CURRENT_SESSION.getField()[0].length + ".*";
    }

    private void checkForHorizontalWin(char signature) {
        for (int i = 0; i < CURRENT_SESSION.getField().length; i++) {
            String result = "";
            for (int j = 0; j < CURRENT_SESSION.getField().length; j++) {
                result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][j]));
            }
            if (result.matches(calculateWinCondition(signature))) {
                CURRENT_SESSION.setGameStatus(1);
                break;
            }
        }
    }

    private void checkForVerticalWin(char signature) {
        for (int j = 0; j < CURRENT_SESSION.getField().length; j++) {
            String result = "";
            for (int i = 0; i < CURRENT_SESSION.getField().length; i++) {
                result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][j]));
            }
            if (result.matches(calculateWinCondition(signature))) {
                CURRENT_SESSION.setGameStatus(1);
                break;
            }
        }
    }

    private void checkForLeftDiagonalWin(char signature) {
        for (int i = 0; i < CURRENT_SESSION.getField().length; i++) {
            String result = "";
            result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][i]));
            if (result.matches(calculateWinCondition(signature))) {
                CURRENT_SESSION.setGameStatus(1);
                break;
            }
        }
    }

    private void checkForRightDiagonalWin(char signature) {
        for (int i = CURRENT_SESSION.getField().length - 1; i >= 0; i--) {
            String result = "";
            result = result.concat(String.valueOf(CURRENT_SESSION.getField()[i][i]));
            if (result.matches(calculateWinCondition(signature))) {
                CURRENT_SESSION.setGameStatus(1);
                break;
            }
        }
    }

    public boolean moveValidation(int x, int y) {
        return (x > 0 && y > 0 && x <= CURRENT_SESSION.getField().length && y <= CURRENT_SESSION.getField().length && CURRENT_SESSION.getField()[--x][--y] == CURRENT_SESSION.FIELD_FILLER);
    }
}
