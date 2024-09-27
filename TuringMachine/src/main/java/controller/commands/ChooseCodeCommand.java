package controller.commands;

import model.Game;

public class ChooseCodeCommand implements Command {

    private final int code;

    private final Game game;


    /**
     * Constructs a ChooseCodeCommand with the specified code and game.
     *
     * @param code The code to be chosen.
     * @param game The Game instance associated with the command.
     */
    public ChooseCodeCommand(int code, Game game) {
        this.game = game;
        this.code = code;
    }

   /**
    * Executes the choose code command by selecting the specified code in the game.
    */
    @Override
    public void execute() {
        game.chooseCode(code);
    }

    /**
     * Unexecutes the choose code command by undoing the selection of the code.
     */
    @Override
    public void unexecute() {
        game.unChooseCode();

    }
}
