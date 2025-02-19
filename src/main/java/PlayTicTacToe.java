import entities.GameSession;
import exceptions.WrongUserInputException;
import handlers.GameMaster;

import java.io.IOException;
import java.util.Scanner;

public class PlayTicTacToe {
    public static void main(String[] args) {
        GameMaster master;
        GameSession session = new GameSession();
        try (Scanner sc = new Scanner(System.in)){
            master = new GameMaster(session, sc);
            master.game();
            System.out.println("""
                One more?
                1 - Yes
                2 - No
                """);
            while(sc.hasNext()){
                int chosen = sc.nextInt();
                if (chosen == 1){
                    session.reset();
                    master.game();
                }else if (chosen == 2){
                    System.out.println("Goodbye!");
                }else throw new WrongUserInputException("Please, choose correctly.");
            }
        }catch (WrongUserInputException w){
            System.out.println(w.getMessage());
        }
    }
}
