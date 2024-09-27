package model;

import model.validators.Validator;
import model.validators.ValidatorFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Game {
    private final Stack<Round> rounds;
    private int score;
    private final Problem problem;
    private Map<Integer, String> validators = new HashMap<>();

    /**
     * Constructs a game with a specified problem index.
     *
     * @param nbProblem The index of the problem.
     */
    public Game(int nbProblem) {
        rounds = new Stack<>();
        score = 0;
        List<Problem> gameProb = new GameProblem().getProblemes();
        this.problem = gameProb.get(nbProblem - 1);

        initializeValidators();
    }

    /**
     * Constructs a game with a random problem.
     */
    public Game() {
        rounds = new Stack<>();
        score = 0;
        List<Problem> GameProb = new GameProblem().getProblemes();
        int randomIndex = (int) (Math.random() * GameProb.size());
        problem = GameProb.get(randomIndex);

        initializeValidators();
    }

    /**
     * Chooses a validator based on the provided number.
     *
     * @param nbValidator The number of the validator to choose.
     * @return The chosen validator.
     * @throws TurningMachineException If the validator is not found in the current problem.
     */
    private Validator chooseValidator(int nbValidator) {
        List<Integer> validatorList = problem.getValidators();
        for (Integer integer : validatorList) {
            if (nbValidator == integer) {
                return ValidatorFactory.createValidator(rounds.get(rounds.size() - 1).getCodeProposal(), problem.getSecretCode(), nbValidator);
            }
        }
        throw new TurningMachineException("The validator is not in the proposed list");
    }

    /**
     * Verifies a validator and updates the game state accordingly.
     *
     * @param nbValidator The number of the validator to verify.
     * @return True if the validation is successful, false otherwise.
     * @throws TurningMachineException If conditions for validator verification are not met.
     */
    public boolean verifyValidator(int nbValidator) {
        if (rounds.isEmpty() || getActualRound().isRoundStarted()) {
            throw new TurningMachineException("you need to enter a code");
        }
        if (rounds.get(rounds.size() - 1).getScore() == 3) {
            throw new TurningMachineException("you already 3 validators");
        }
        Validator valid = chooseValidator(nbValidator);
        rounds.get(rounds.size() - 1).incrementScore();
        score++;

        if (valid.checkCodeWithValidator()) {
            validators.put(nbValidator, "green");
            return true;
        } else {
            validators.put(nbValidator, "red");
            return false;
        }
    }

    /**
     * Chooses a code for the current round.
     *
     * @param code The code to choose.
     * @throws TurningMachineException If conditions for choosing a code are not met.
     */
    public void chooseCode(int code) {
        if (rounds.isEmpty()) {
            rounds.add(new Round()); // si c'est la premiere manche du jeu
        }
        if (getActualRound().isRoundStarted()) {
            getActualRound().setProposedCode(new Code(code));
        } else {
            throw new TurningMachineException("You have to move to a next round to choose a new code.");
        }
    }

    /**
     * Unsets the proposed code for the current round.
     */
    public void unChooseCode() {
        getActualRound().unSetProposedCode();
    }

    /**
     * Gets the current problem of the game.
     *
     * @return The current problem.
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Gets the current round of the game.
     *
     * @return The current round.
     * @throws TurningMachineException If there are no rounds.
     */
    public Round getActualRound() {
        int index = rounds.size() - 1;
        if (index < 0) {
            throw new TurningMachineException("Rounds is empty");
        }
        return rounds.get(index);
    }

    /**
     * Gets the current score of the game.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Moves to the next round in the game.
     *
     * @throws TurningMachineException If conditions for moving to the next round are not met.
     */
    public void nextRound() {
        if (rounds.isEmpty() || getActualRound().getScore() == 0) {
            throw new TurningMachineException("You have to play before moving to another round");
        }
        rounds.push(new Round());

        initializeValidators();
    }

    /**
     * Reverts to the previous round state with the given validators.
     *
     * @param validators The validators to set for the previous round.
     */
    public void precedentRound(Map<Integer, String> validators) {
        rounds.pop();
        this.validators = new HashMap<>(validators);
    }

    /**
     * Checks if the provided code matches the secret code of the current problem.
     *
     * @param code The code to check.
     * @return True if the code is correct, false otherwise.
     */
    public boolean checkCode(int code) {
        return problem.getSecretCode().equals(new Code(code));
    }

    /**
     * Gets a copy of the current validators' states.
     *
     * @return A map representing the current state of validators.
     */
    public Map<Integer, String> getValidators() {
        return new HashMap<>(validators);
    }

    /**
     * Unselects a validator, setting its state to "white" and decreasing the score by 1.
     *
     * @param nbValidator The number of the validator to unselect.
     */
    public void unselectValidator(int nbValidator) {
        validators.put(nbValidator, "white");
        score -= 1;
    }

    /**
     * Initializes the validators map based on the current problem.
     */
    private void initializeValidators() {
        validators = new HashMap<>();
        for (Integer v : problem.getValidators()) {
            validators.put(v, "white");
        }
    }

    /**
     * Returns the number of rounds played in the game.
     *
     * @return The number of rounds played.
     */
    public int numberOfRound() {
        return rounds.size();
    }

    /**
     * Returns the number of the current problem in the game.
     *
     * @return The number of the current problem.
     */
    public int numberProblem() {
        return problem.getNumberProblem();
    }
}
