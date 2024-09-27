package controller.commands;

import model.SuperGame;


public class ValidatorCommand implements Command {
    private final SuperGame gameFacade;
    private final int nbValidator;

    /**
     * Constructs a ValidatorCommand with the specified SuperGame and validator number.
     *
     * @param gameFacade The SuperGame instance associated with the command.
     * @param nbValidator The number of the validator to be verified.
     */
    public ValidatorCommand(SuperGame gameFacade, int nbValidator) {
        this.gameFacade = gameFacade;
        this.nbValidator = nbValidator;
    }


    /**
     * Executes the validator command by checking the specified validator in the game.
     */
    @Override
    public void execute() {
        gameFacade.checkValidator(nbValidator);
    }

    /**
     * Unexecutes the validator command by unselecting the specified validator in the game.
     */
    @Override
    public void unexecute() {
        gameFacade.unselectValidator(nbValidator);
    }
}
