package model;

import viewConsole.View;
import Util.Observable;
import Util.Observer;
import controller.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class SuperGame implements Observable {

    private final CommandManager manager;
    private final Game game;
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Constructs a new `SuperGame` instance with a default game.
     */
    public SuperGame() {
        game = new Game();
        manager = new CommandManager();
    }

    /**
     * Constructs a new `SuperGame` instance with a game initialized with a specific problem number.
     *
     * @param nbProblem The problem number to initialize the game.
     */
    public SuperGame(int nbProblem) {
        game = new Game(nbProblem);
        manager = new CommandManager();
    }

    /**
     * Chooses a code in the game.
     *
     * @param code The code to be chosen.
     */
    public void chooseCode(int code) {
        Command command = new ChooseCodeCommand(code, game);
        manager.executeCommand(command);
    }

    /**
     * Initiates the validation process for a specific validator.
     *
     * @param nbValidator The validator number to be verified.
     */
    public void verifyValidator(int nbValidator) {
        Command command = new ValidatorCommand(this, nbValidator);
        manager.executeCommand(command);
    }

    /**
     * Checks the validator with the specified number.
     *
     * @param nbValidator The validator number to be checked.
     */
    public void checkValidator(int nbValidator) {
        game.verifyValidator(nbValidator);
        notifyObservers();
    }

    /**
     * Unselects a validator, decrementing the round score.
     *
     * @param nbValidator The validator number to be unselected.
     */
    public void unselectValidator(int nbValidator) {
        game.getActualRound().decrementScore();
        game.unselectValidator(nbValidator);
        notifyObservers();
    }

    /**
     * Initiates the next round in the game.
     */
    public void nextRound() {
        Command command = new NextRoundCommand(this);
        manager.executeCommand(command);
    }

    /**
     * Retrieves the current state of validators in the game.
     *
     * @return A map containing validator numbers and their states.
     */
    public Map<Integer, String> getValidators() {
        return game.getValidators();
    }

    /**
     * Initiates the next round in the game without executing a command.
     */
    public void nextRoundGame() {
        game.nextRound();
        notifyObservers();
    }

    /**
     * Reverts to the previous round state.
     *
     * @param validators The map of validators from the previous round.
     */
    public void precedentRound(Map<Integer, String> validators) {
        game.precedentRound(validators);
        notifyObservers();
    }

    /**
     * Retrieves the command manager associated with the game.
     *
     * @return The command manager.
     */
    public CommandManager getManager() {
        return manager;
    }

    /**
     * Retrieves the list of validators associated with the current problem.
     *
     * @return The list of validators.
     */
    public List<Integer> validatorList() {
        return game.getProblem().getValidators();
    }

    /**
     * Verifies whether a given code matches the secret code of the current problem.
     *
     * @param code The code to be verified.
     * @return `true` if the code is correct, otherwise `false`.
     */
    public boolean verifyCode(int code) {
        return game.checkCode(code);
    }

    /**
     * Retrieves the current score of the game.
     *
     * @return The current score.
     */
    public int getScore() {
        return game.getScore();
    }

    /**
     * Retrieves the current round number in the game.
     *
     * @return The current round number.
     */
    public int getRound(){
        return game.numberOfRound();
    }

    /**
     * Retrieves the total number of problems available in the game.
     *
     * @return The total number of problems.
     */
    public int numberProblem(){
        return game.numberProblem();
    }


    /**
     * Adds an observer to the list of observers if it is not already present.
     *
     * @param observer The observer to be added.
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer The observer to be removed.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers by calling their `update` method.
     */
    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    /**
     * Checks if a code has been entered in the current round.
     *
     * @return `true` if a code has been entered, otherwise `false`.
     */
    public boolean hasEnteredCode(){
        return game.getActualRound().getCodeProposal() !=null;
    }

    /**
     * Retrieves the proposed code in the current round.
     *
     * @return The proposed code in the current round.
     */
    public Code getProposedCode() {
        return game.getActualRound().getCodeProposal();
    }
}

