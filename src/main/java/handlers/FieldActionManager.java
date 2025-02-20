package handlers;

import entities.GameSession;
import entities.Player;
import exceptions.WrongUserInputException;

public class FieldActionManager {

    public static void check(char signature, GameSession session){
        checkForDraw(session);
        checkForHorizontalWin(signature, session);
        checkForVerticalWin(signature, session);
        checkForLeftDiagonalWin(signature, session);
        checkForRightDiagonalWin(signature, session);
    }

    private static void checkForDraw(GameSession session){
        if (session.counter == Math.pow(session.getField()[0].length, 2)){
            session.setGameStatus(-1);
        }
    }

    private static void checkForHorizontalWin(char signature, GameSession session){
        for (int i = 0; i < session.getField().length; i++) {
            String result = "";
            for (int j = 0; j < session.getField().length; j++) {
                result = result.concat(String.valueOf(session.getField()[i][j]));
            }
            if (result.matches(".*[" + signature + "]{3}.*")){
                session.setGameStatus(1);
                break;
            }
        }
    }

    private static void checkForVerticalWin(char signature, GameSession session){
        for (int j = 0; j < session.getField().length; j++) {
            String result = "";
            for (int i = 0; i < session.getField().length; i++) {
                result = result.concat(String.valueOf(session.getField()[i][j]));
            }
            if (result.matches(".*[" + signature + "]{3}.*")){
                session.setGameStatus(1);
                break;
            }
        }
    }
    private static void checkForLeftDiagonalWin(char signature, GameSession session){
        for (int i = 0; i < session.getField().length; i++) {
            String result = "";
            result = result.concat(String.valueOf(session.getField()[i][i]));
            if (result.matches(".*[" + signature + "]{3}.*")){
                session.setGameStatus(1);
                break;
            }
        }
    }private static void checkForRightDiagonalWin(char signature, GameSession session){
        for (int i = session.getField().length - 1; i >= 0 ; i--) {
            String result = "";
            result = result.concat(String.valueOf(session.getField()[i][i]));
            if (result.matches(".*[" + signature + "]{3}.*")){
                session.setGameStatus(1);
                break;
            }
        }
    }

    public static boolean moveValidation(int x, int y, GameSession session){
        return (x > 0 && y > 0 &&
                x <= session.getField().length &&
                y <= session.getField().length &&
                session.getField()[--x][--y] == '\u00B7');
    }


}
