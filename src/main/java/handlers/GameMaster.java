package handlers;

import entities.GameSession;
import entities.HumanPlayer;
import entities.Player;
import entities.RobotPlayer;
import exceptions.WrongUserInputException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.random.RandomGenerator;

public class GameMaster {

    private GameSession session;
    private Scanner scan;

    public GameMaster(GameSession session, Scanner scan) {
        this.session = session;
        this.scan = scan;
    }

    public void initGame(){
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
        System.out.println("Set a field size.");
        while(scan.hasNextInt()){
            try {
                try{
                    int size = scan.nextInt();
                    if (size < 3){
                        throw new WrongUserInputException("Field size must be greater then 3.");
                    }else {
                        session.setField(size);
                        break;
                    }
                }catch (InputMismatchException i){
                    throw new WrongUserInputException("Number required!");
                }
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
        }
    }

    public void setPlayers(){
        System.out.println("First player name is:");
        session.setPlayerONE(new HumanPlayer(scan.next()));
        if (session.getGameMode() == 2){
            session.setPlayerTWO(new RobotPlayer());
        }else {
            System.out.println("Second player name is:");
            String name = scan.next();
            name = name.equals(session.getPlayerONE().getName()) ?
                    name.concat("_2") : name; // add name different from the first player
            session.setPlayerTWO(new HumanPlayer(name));
        }
        System.out.printf("Choose your symbol, %s.\n", session.getPlayerONE().getName());
        System.out.println("X or O?");
        while (scan.hasNext()){
            String chosen = scan.next().toUpperCase();
            try {
                if (chosen.equals("X") || chosen.equals("O")){
                    session.getPlayerONE().setSignature(chosen.charAt(0));
                    break;
                }else throw new WrongUserInputException("Please, choose correctly.");
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }
        }
        session.getPlayerTWO().setSignature(session.getPlayerONE().getSignature() == 'X' ? 'O' : 'X');
        System.out.printf("%s chose %c. %s got %c\n", session.getPlayerONE().getName(),
                session.getPlayerONE().getSignature(), session.getPlayerTWO().getName(),
                session.getPlayerTWO().getSignature());
    }

    public void game(){
        String winner = action(randomizer());
        if (session.getGameStatus() == 1){
            session.getWinStat().put(winner, (session.getWinStat().get(winner) + 1));
            System.out.println(winner + " won " + session.getWinStat().get(winner) + " time(s)");
        } else if (session.getGameStatus() == -1){
            System.out.println("It's a draw!");
        }
    }

    private String action(int firstMove){
        Player[] players = {session.getPlayerONE(), session.getPlayerTWO()};
        System.out.println("Game started!");
        String winner = "Friendship";
        int i = firstMove;
        while(session.getGameStatus() == 0){
            try{
                if (players[i] instanceof HumanPlayer){
                    System.out.printf("%s, your turn (input row and column separately).\n", players[i].getName());
                    try{
                        players[i].move(scan, session);
                    }catch (InputMismatchException n){
                        throw new WrongUserInputException("Only numeric coordinates are acceptable!");
                    }
                    System.out.printf("Accepted! %s made his(her) move.\n", players[i].getName());
                }else if(players[i] instanceof RobotPlayer){
                    players[i].move(scan, session);
                    System.out.printf("%s made its move.\n", players[i].getName());
                }
                session.showField();
                FieldActionManager.check(players[i].getSignature(), session);
                winner = players[i].getName();
                i++;
            }catch (WrongUserInputException w){
                System.out.println(w.getMessage());
            }catch (ArrayIndexOutOfBoundsException a){
                i = 0;
            }
        }
        return winner;
    }

    public boolean askForAnotherOne() throws WrongUserInputException{
        System.out.println("""
                One more?
                1 - Yes
                2 - No
                """);
        int chosen = scan.nextInt();
        if (chosen == 1){
            session.reset();
            return true;
        }else if (chosen == 2) {
            System.out.println("Goodbye!");
        }else {
            throw new WrongUserInputException("Choose from listed options!");
        }
        return false;
    }

    private int randomizer (){
        System.out.println("And first move makes...");
        int firstMove = RandomGenerator.getDefault().nextInt(2);;
        try{
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.println("*");
            }
            Thread.sleep(1000);
            System.out.println(firstMove == 0 ? session.getPlayerONE().getName() : session.getPlayerTWO().getName());
            System.out.println("---------------------------");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return firstMove;
    }
}
