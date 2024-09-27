package model;

import java.util.ArrayList;
import java.util.List;

public class Problem {
    private final int numberProblem;
    private final Code secretCode;
    private final List<Integer> validators;

    /**
     * Constructs a new problem with the specified number, secret code, and validators.
     *
     * @param numberProblem The number associated with the problem.
     * @param secretCode    The secret code for the problem.
     * @param validators    The list of validators for the problem.
     */
    public Problem(int numberProblem, String secretCode, List<Integer> validators) {
        this.numberProblem = numberProblem;
        this.secretCode = new Code(Integer.parseInt(secretCode));
        this.validators = validators;
    }

    /**
     * Gets the number associated with the problem.
     *
     * @return The number associated with the problem.
     */
    public int getNumberProblem() {
        return numberProblem;
    }

    /**
     * Gets a copy of the secret code for the problem.
     *
     * @return A copy of the secret code for the problem.
     */
    public Code getSecretCode() {
        return new Code(secretCode);
    }

    /**
     * Gets a copy of the list of validators for the problem.
     *
     * @return A copy of the list of validators for the problem.
     */
    public List<Integer> getValidators() {
        return new ArrayList<>(validators);
    }
}
