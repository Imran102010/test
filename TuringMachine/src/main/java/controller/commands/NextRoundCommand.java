package controller.commands;

import model.SuperGame;

import java.util.Map;

public class NextRoundCommand implements Command{

    private final SuperGame gameFacade;
    private Map<Integer, String> validators;


    /**
     * Constructs a NextRoundCommand with the specified SuperGame.
     *
     * @param gameFacade The SuperGame instance associated with the command.
     */
    public NextRoundCommand(SuperGame gameFacade){
        this.gameFacade=gameFacade;
    }

    /**
     * Executes the next round command by moving to the next round in the game.
     * It also saves the current state of validators before the execution.
     */
    @Override
    public void execute() {
        validators = gameFacade.getValidators();
        gameFacade.nextRoundGame();
    }

    /**
     * Unexecutes the next round command by reverting to the previous round's state.
     */
    @Override
    public void unexecute() {
        gameFacade.precedentRound(validators);
    }
}


