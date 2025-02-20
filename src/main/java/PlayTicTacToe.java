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
            master.initGame();
            master.setPlayers();
            do {
                master.game();
            } while(master.askForAnotherOne());
        }catch (WrongUserInputException w){
            System.out.println(w.getMessage());
        }
    }
}
