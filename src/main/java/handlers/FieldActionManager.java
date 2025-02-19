package handlers;

import entities.GameSession;
import entities.Player;
import exceptions.WrongUserInputException;

public class FieldActionManager {
    public static boolean move(int x, int y, Player player, GameSession session) throws WrongUserInputException {
        if (moveValidation(x, y, session)){
            session.getField()[--x][--y] = player.getSignature();
            System.out.printf("Accepted! %s made his move.", player.getName());
            session.counter++;
            return true;
        } else {
            throw new WrongUserInputException("Wrong position. Try again, please.");
        }
    }

    public static void check(Player player, int gameStatus, GameSession session){
        checkForDraw(player, gameStatus, session);
        checkForHorizontalWin(player.getSignature(), session, gameStatus);
        checkForVerticalWin(player.getSignature(), session, gameStatus);
        checkForDiagonalWin(player.getSignature(), session, gameStatus);
    }

    private static void checkForDraw(Player player, int gameStatus, GameSession session){
        if (session.counter == Math.pow(session.getSize(), 2)){
            gameStatus = -1;
        }
    }

    private static boolean moveValidation(int x, int y, GameSession session){
        return (x > 0 && y > 0 &&
                x < session.getField().length &&
                y < session.getField().length &&
                session.getField()[--x][--y] == ' ');
    }

    private static void checkForHorizontalWin(char signature, GameSession session, int gameStatus){
        for (int i = 0; i < session.getSize(); i++) {
            String result = null;
            for (int j = 0; j < session.getSize(); j++) {
                result = result.concat(String.valueOf(session.getField()[i][j]));
            }
            if (result.matches(".*[" + signature + "]{3}.*")){
                gameStatus = 1;
                break;
            }
        }
    }
    private static void checkForVerticalWin(char signature, GameSession session, int gameStatus){
        for (int j = 0; j < session.getSize(); j++) {
            String result = null;
            for (int i = 0; i < session.getSize(); i++) {
                result = result.concat(String.valueOf(session.getField()[i][j]));
            }
            if (result.matches(".*[" + signature + "]{3}.*")){
                gameStatus = 1;
                break;
            }
        }
    }

    private static void checkForDiagonalWin(char signature, GameSession session, int gameStatus){
        for (int i = 0; i < session.getSize(); i++) {
            String result = null;
            result = result.concat(String.valueOf(session.getField()[i][i]));
            if (result.matches(".*[" + signature + "]{3}.*")){
                gameStatus = 1;
                break;
            }
        }
    }


}
