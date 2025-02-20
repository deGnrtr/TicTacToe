package handler;

import entity.GameSession;
import entity.HumanPlayer;
import entity.Player;
import entity.RobotPlayer;
import exception.WrongUserInputException;

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

    public void initGame() {
        fieldActionManager = new FieldActionManager(CURRENT_SESSION);
        System.out.println("""
                Hello there \u263A! Wanna play a tic-tac-toe game?
                Please, choose the game mode:
                1 - PvP
                2 - PvC
                """);

        while (INPUT_SCANNER.hasNextInt()) {
            int chosen = INPUT_SCANNER.nextInt();
            try {
                if (chosen == 1 || chosen == 2) {
                    CURRENT_SESSION.setGameMode(chosen);
                    break;
                } else throw new WrongUserInputException("Please, choose correctly.");
            } catch (WrongUserInputException w) {
                System.out.println(w.getMessage());
            }
        }
        System.out.println("Set a field size.");
        while (INPUT_SCANNER.hasNextInt()) {
            try {
                try {
                    int size = INPUT_SCANNER.nextInt();
                    if (size < 3) {
                        throw new WrongUserInputException("Field size must be greater then 3.");
                    } else {
                        CURRENT_SESSION.setField(size);
                        break;
                    }
                } catch (InputMismatchException i) {
                    throw new WrongUserInputException("Number required!");
                }
            } catch (WrongUserInputException w) {
                System.out.println(w.getMessage());
            }
        }
    }

    public void setPlayers() {
        System.out.println("First player name is:");
        CURRENT_SESSION.setPlayerONE(new HumanPlayer(INPUT_SCANNER.next()));
        if (CURRENT_SESSION.getGameMode() == 2) {
            CURRENT_SESSION.setPlayerTWO(new RobotPlayer());
        } else {
            System.out.println("Second player name is:");
            String name = INPUT_SCANNER.next();
            Player player = CURRENT_SESSION.getPlayerONE();
            name = name.equals(player.NAME) ? name.concat("_2") : name; // add name different from the first player
            CURRENT_SESSION.setPlayerTWO(new HumanPlayer(name));
        }
        Player player2 = CURRENT_SESSION.getPlayerONE();
        System.out.printf("Choose your symbol, %s.\n", player2.NAME);
        System.out.println("X or O?");
        while (INPUT_SCANNER.hasNext()) {
            String chosen = INPUT_SCANNER.next().toUpperCase();
            try {
                if (chosen.equals("X") || chosen.equals("O")) {
                    CURRENT_SESSION.getPlayerONE().setSignature(chosen.charAt(0));
                    break;
                } else throw new WrongUserInputException("Please, choose correctly.");
            } catch (WrongUserInputException w) {
                System.out.println(w.getMessage());
            }
        }
        CURRENT_SESSION.getPlayerTWO().setSignature(CURRENT_SESSION.getPlayerONE().getSignature() == 'X' ? 'O' : 'X');
        Player player = CURRENT_SESSION.getPlayerTWO();
        Player player1 = CURRENT_SESSION.getPlayerONE();
        System.out.printf("%s chose %c. %s got %c\n", player1.NAME, CURRENT_SESSION.getPlayerONE().getSignature(), player.NAME, CURRENT_SESSION.getPlayerTWO().getSignature());
    }

    public void game() {
        String winner = action(randomizer());
        if (CURRENT_SESSION.getGameStatus() == 1) {
            CURRENT_SESSION.getWinStat().put(winner, (CURRENT_SESSION.getWinStat().get(winner) + 1));
            System.out.println(winner + " won " + CURRENT_SESSION.getWinStat().get(winner) + " time(s)");
        } else if (CURRENT_SESSION.getGameStatus() == -1) {
            System.out.println("It's a draw!");
        }
    }

    private String action(int firstMove) {
        Player[] players = {CURRENT_SESSION.getPlayerONE(), CURRENT_SESSION.getPlayerTWO()};
        System.out.println("Game started!");
        String winner = "Friendship";
        int i = firstMove;
        while (CURRENT_SESSION.getGameStatus() == 0) {
            try {
                if (players[i] instanceof HumanPlayer) {
                    System.out.printf("%s, your turn (input row and column separately).\n", players[i].NAME);
                    try {
                        players[i].move(INPUT_SCANNER, fieldActionManager, CURRENT_SESSION);
                    } catch (InputMismatchException n) {
                        throw new WrongUserInputException("Only numeric coordinates are acceptable!");
                    }
                    System.out.printf("Accepted! %s made his(her) move.\n", players[i].NAME);
                } else if (players[i] instanceof RobotPlayer) {
                    players[i].move(INPUT_SCANNER, fieldActionManager, CURRENT_SESSION);
                    System.out.printf("%s made its move.\n", players[i].NAME);
                }
                CURRENT_SESSION.showField();
                fieldActionManager.check(players[i].getSignature());
                winner = players[i].NAME;
                i++;
            } catch (WrongUserInputException w) {
                System.out.println(w.getMessage());
            } catch (ArrayIndexOutOfBoundsException a) {
                i = 0;
            }
        }
        return winner;
    }

    public boolean askForAnotherOne() throws WrongUserInputException {
        System.out.println("""
                One more?
                1 - Yes
                2 - No
                """);
        int chosen = INPUT_SCANNER.nextInt();
        if (chosen == 1) {
            CURRENT_SESSION.reset();
            return true;
        } else if (chosen == 2) {
            System.out.println("Goodbye!");
        } else {
            throw new WrongUserInputException("Choose from listed options!");
        }
        return false;
    }

    private int randomizer() {
        System.out.println("And first move makes...");
        int firstMove = RandomGenerator.getDefault().nextInt(2);
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.println("*");
            }
            Thread.sleep(1000);
            Player player = CURRENT_SESSION.getPlayerTWO();
            Player player1 = CURRENT_SESSION.getPlayerONE();
            System.out.println(firstMove == 0 ? player1.NAME : player.NAME);
            System.out.println("---------------------------");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return firstMove;
    }
}
