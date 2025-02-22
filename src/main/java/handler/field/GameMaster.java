package handler.field;

import entity.game.GameSession;
import entity.game.GameStatus;
import entity.player.HumanPlayer;
import entity.player.Player;
import entity.player.RobotPlayer;
import exception.WrongUserInputException;
import handler.message.ErrorMessages;
import handler.message.InterfaceMessages;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.random.RandomGenerator;

public class GameMaster {

    private final GameSession CURRENT_SESSION;
    private final Scanner INPUT_SCANNER;
    private FieldActionManager fieldActionManager;

    public GameMaster(GameSession session, Scanner scan) {
        this.CURRENT_SESSION = session;
        this.INPUT_SCANNER = scan;
    }

    public void initGame(){
        fieldActionManager = new FieldActionManager(CURRENT_SESSION);
        System.out.println(InterfaceMessages.WELCOMING);
        while (INPUT_SCANNER.hasNextInt()) {
            try{
                try {
                    int chosen = INPUT_SCANNER.nextInt();
                    if (chosen == 1 || chosen == 2) {
                        CURRENT_SESSION.setGameMode(chosen);
                        break;
                    } else throw new WrongUserInputException(ErrorMessages.WRONG_CHOICE.toString());
                } catch (InputMismatchException i) {
                    throw new WrongUserInputException(ErrorMessages.WRONG_CHOICE.toString());
                }
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
        }
        System.out.println(InterfaceMessages.FIELD_SIZE_SETTING);
        while (INPUT_SCANNER.hasNextInt()) {
            try{
                try {
                    int size = INPUT_SCANNER.nextInt();
                    if (size >= 3) {
                        CURRENT_SESSION.setField(size);
                        break;
                    } else {
                        throw new WrongUserInputException(ErrorMessages.SMALL_FIELD.toString());
                    }
                } catch (InputMismatchException i) {
                    throw new WrongUserInputException(ErrorMessages.NOT_A_NUMBER.toString());
                }
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
        }
    }

    public void setPlayers() {
        System.out.println(InterfaceMessages.FIRST_PLAYER_SETTING);
        CURRENT_SESSION.setPlayerONE(new HumanPlayer(INPUT_SCANNER.next()));
        Player firstNewPlayer = CURRENT_SESSION.getPlayerONE();
        if (CURRENT_SESSION.getGameMode() == 2) {
            CURRENT_SESSION.setPlayerTWO(new RobotPlayer());
            System.out.println(InterfaceMessages.ROBOT_INTRODUCE);
        } else {
            System.out.println(InterfaceMessages.SECOND_PLAYER_SETTING);
            String name = INPUT_SCANNER.next();
            Player player = CURRENT_SESSION.getPlayerONE();
            name = name.equals(player.NAME) ? name.concat("_2") : name; // add name different from the first player
            CURRENT_SESSION.setPlayerTWO(new HumanPlayer(name));
        }
        Player secondNewPlayer = CURRENT_SESSION.getPlayerTWO();
        System.out.printf(InterfaceMessages.SYMBOL_SETTING.toString(), firstNewPlayer.NAME);
        char firstPlayableSymbol = 'X';
        char secondPlayableSymbol = 'O';
        while (INPUT_SCANNER.hasNext()) {
            try{
                char chosenSymbol = INPUT_SCANNER.next().toUpperCase().charAt(0);
                if (chosenSymbol == firstPlayableSymbol || chosenSymbol == secondPlayableSymbol) {
                    firstNewPlayer.setSignature(chosenSymbol);
                    break;
                }else throw new WrongUserInputException(ErrorMessages.WRONG_CHOICE.toString());
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
        }
        secondNewPlayer.setSignature(firstNewPlayer.getSignature() == firstPlayableSymbol ? secondPlayableSymbol : firstPlayableSymbol);
        System.out.printf(InterfaceMessages.SHOW_ASSIGNED_SYMBOLS.toString(), firstNewPlayer.NAME, firstNewPlayer.getSignature(),
                secondNewPlayer.NAME, secondNewPlayer.getSignature());
    }

    public void game() throws WrongUserInputException {
        String winner = action(randomizer());
        if (CURRENT_SESSION.getCurrentGameStatus().equals(GameStatus.VICTORY)) {
            CURRENT_SESSION.getWinStat().put(winner, (CURRENT_SESSION.getWinStat().get(winner) + 1));
            System.out.printf(InterfaceMessages.GAME_END_VICTORY.toString(), winner, CURRENT_SESSION.getWinStat().get(winner));
        } else if (CURRENT_SESSION.getCurrentGameStatus().equals(GameStatus.DRAW)) {
            System.out.println(InterfaceMessages.GAME_END_DRAW);
        }
    }

    private String action(boolean whoIsFirst) {
        System.out.println(InterfaceMessages.GAME_START);
        boolean marker = whoIsFirst;
        String winner = "Friendship";
        while (CURRENT_SESSION.getCurrentGameStatus().equals(GameStatus.IN_PROGRESS)) {
            Player currentPlayer = playerChanger(marker);
            try{
                if (currentPlayer instanceof HumanPlayer) {
                    System.out.printf(InterfaceMessages.OFFER_TO_MOVE.toString(), currentPlayer.NAME);
                    try {
                        currentPlayer.move(INPUT_SCANNER, fieldActionManager, CURRENT_SESSION);
                    } catch (InputMismatchException n) {
                        throw new WrongUserInputException(ErrorMessages.WRONG_COORDINATES.toString());
                    }
                    System.out.printf(InterfaceMessages.MOVE_ACCEPTANCE.toString(), currentPlayer.NAME);
                } else if (currentPlayer instanceof RobotPlayer) {
                    currentPlayer.move(INPUT_SCANNER, fieldActionManager, CURRENT_SESSION);
                    System.out.printf(InterfaceMessages.MOVE_REPORT.toString(), currentPlayer.NAME);
                }
            } catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
            CURRENT_SESSION.showField();
            fieldActionManager.check(currentPlayer.getSignature());
            winner = currentPlayer.NAME;
            marker = !marker;
            }
        return winner;
    }

    public boolean askForAnotherOne() throws WrongUserInputException {
        System.out.println(InterfaceMessages.OFFER_TO_RETRY);
        int chosen;
        try{
            chosen = INPUT_SCANNER.nextInt();
        }catch (InputMismatchException i){
            throw new WrongUserInputException(ErrorMessages.WRONG_CHOICE.toString());
        }
        if (chosen == 1) {
            CURRENT_SESSION.reset();
            return true;
        } else if (chosen == 2) {
            System.out.println(InterfaceMessages.GOODBYE);
            return false;
        } else {
            throw new WrongUserInputException(ErrorMessages.WRONG_CHOICE.toString());
        }
    }

    private boolean randomizer() {
        String filler = "*";
        String divider = "---------------------------";
        System.out.println(InterfaceMessages.FIRST_MOVE_EXPECTING);
        int firstMove = RandomGenerator.getDefault().nextInt(2);
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.println(filler);
            }
            Thread.sleep(1000);
            Player playerONE = CURRENT_SESSION.getPlayerONE();
            Player playerTWO = CURRENT_SESSION.getPlayerTWO();
            System.out.println(firstMove == 0 ? playerONE.NAME : playerTWO.NAME);
            System.out.println(divider);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return firstMove == 0;
    }
    private Player playerChanger(boolean marker) {
        return marker ? CURRENT_SESSION.getPlayerONE() : CURRENT_SESSION.getPlayerTWO();
    }
}
