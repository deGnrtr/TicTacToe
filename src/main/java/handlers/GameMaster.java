package handlers;

import entities.GameSession;
import entities.HumanPlayer;
import entities.Player;
import entities.RobotPlayer;
import exceptions.WrongUserInputException;

import java.util.Scanner;
import java.util.random.RandomGenerator;

public class GameMaster {

    private GameSession session;
    private Scanner scan;

    public GameMaster(GameSession session, Scanner scan) {
        this.session = session;
        this.scan = scan;
    }

    private void initGame(){
        System.out.println("""
                Hello there \u263A! Wanna play a tic-tac-toe game?
                Please, choose the game mode:
                1 - PvP
                2 - PvC
                """);

        while (scan.hasNextInt()){
            int chosen = scan.nextInt();
            try {
                if (chosen == 1 || chosen == 2){
                    session.setGameMode(chosen);
                    break;
                }else throw new WrongUserInputException("Please, choose correctly.");
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
        }
    }

    private void setPlayers(String playerFirst, String playerSecond){
        System.out.printf("%s name is:", playerFirst);
        session.playerONE = new HumanPlayer(scan.next());
        if (playerSecond.equals("robot")){
            session.playerTWO = new RobotPlayer();
        }else {
            System.out.printf("%s name is:", playerSecond);
            String name = scan.next();
            name = name.equals(session.playerONE.getName()) ? name.concat("_2") : name;
            session.playerTWO = new HumanPlayer(name); // add name different from the first player
        }
        System.out.printf("Choose your symbol, %s.\n", session.playerONE.getName());
        System.out.println("X or O?");
        while (scan.hasNext()){
            String chosen = scan.next().toUpperCase();
            try {
                if (chosen.equals("X") || chosen.equals("O")){
                    session.playerONE.setSignature(chosen.charAt(0));
                    break;
                }else throw new WrongUserInputException("Please, choose correctly.");
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
        }
        session.playerTWO.setSignature(session.playerONE.getSignature() == 'X' ? 'O' : 'X');
        System.out.printf("%s chose %c. %s got %c\n", session.playerONE.getName(),
                session.playerONE.getSignature(), session.playerTWO.getName(),
                session.playerTWO.getSignature());
    }

    public void game(){
        initGame();
        if (session.getGameMode() == 1){
            setPlayers("First player", "Second player");
        } else if (session.getGameMode() == 2) {
            setPlayers("Your", "Robot");
        }
        int firstMove = randomizer();
        System.out.println(firstMove == 1 ? session.playerONE.getName() : session.playerTWO.getName());
        humanOpponentMode(firstMove);
    }

    private void humanOpponentMode(int firstMove){
        Player[] players = {session.getPlayerONE(), session.getPlayerTWO()};
        System.out.println("Set a field size.");
        session.setField(scan.nextInt());
        System.out.println("Game started!");
        session.showField();
        String winner = "Friendship";
        int gameStatus = 0; //-1 draw, 0 in progress, 1 victory
        int i = firstMove;
        while(gameStatus == 0){
            try{
                System.out.printf("%s, your turn.", players[i].getName());
                FieldActionManager.move(scan.nextInt(), scan.nextInt(), players[i], session);
                session.showField();
                FieldActionManager.check(session.playerONE, gameStatus, session);
                winner = players[i].getName();
                i++;
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }catch (ArrayIndexOutOfBoundsException a){
                i = 0;
            }
        }
        String gameResult = winner + " won " + session.getWinStat().get(winner) + " time(s)";
        System.out.println(gameStatus == 1 ? gameResult: "It's a draw!");
    }

    private int randomizer (){
        System.out.println("And first move makes...");
        try{
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.println("* ");
            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return RandomGenerator.getDefault().nextInt(2);
    }
}
