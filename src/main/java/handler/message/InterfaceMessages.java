package handler.message;

public enum InterfaceMessages {

    WELCOMING("""
                Hello there \u263A! Wanna play a tic-tac-toe game?
                Please, choose the game mode:
                1 - PvP
                2 - PvC
                """),
    FIELD_SIZE_SETTING("Set a field size."),
    FIRST_PLAYER_SETTING("First player name is"),
    SECOND_PLAYER_SETTING("Second player name is:"),
    SYMBOL_SETTING("Choose your symbol, %s.\nX or O?"),
    SHOW_ASSIGNED_SYMBOLS("%s chose %c. %s got %c\n"),
    GAME_END_DRAW("It's a draw!"),
    GAME_END_VICTORY("%s won %d time(s).\n"),
    GAME_START("Game started!"),
    OFFER_TO_MOVE("%s, your turn (input row and column separately).\n"),
    MOVE_ACCEPTANCE("Accepted! %s made his(her) move.\n"),
    MOVE_REPORT("%s made its move.\n"),
    OFFER_TO_RETRY("""
                One more?
                1 - Yes
                2 - No
                """),
    GOODBYE("Goodbye!"),
    FIRST_MOVE_EXPECTING("And first move makes...");

    private final String MESSAGE;

    InterfaceMessages(String text){
        this.MESSAGE = text;
    }

    @Override
    public String toString(){
        return MESSAGE;
    }
}
