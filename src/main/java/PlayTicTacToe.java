import entity.game.GameSession;
import exception.WrongUserInputException;
import handler.field.GameMaster;

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
