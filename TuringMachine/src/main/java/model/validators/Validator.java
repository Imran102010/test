package model.validators;


import model.Code;
import model.TurningMachineException;

public abstract class Validator {

    protected final int nbValidator;

    protected final int firstSecret;

    protected final int secondSecret;

    protected final int thirdSecret;

    protected final int first;

    protected final int second;

    protected final int third;

    /**
     * Creates a Validator with the provided parameters.
     *
     * @param code       The Code to be validated.
     * @param codeSecret The secret Code used for validation.
     * @param nbValidator The number representing the type of validator.
     * @throws TurningMachineException If an invalid validator number is provided.
     */
    public Validator(Code code, Code codeSecret, int nbValidator) {

        if (nbValidator > 22 || nbValidator < 1) {
            throw new TurningMachineException("Third digit must be between 1 and 5");
        }
        this.nbValidator = nbValidator;
        this.firstSecret = codeSecret.getFirst();
        this.secondSecret = codeSecret.getSecond();
        this.thirdSecret = codeSecret.getThird();
        this.first = code.getFirst();
        this.second = code.getSecond();
        this.third = code.getThird();
    }

    /**
     * Abstract method to be implemented by subclasses.
     * Checks whether the provided code satisfies the conditions of the validator.
     *
     * @return True if the code is valid according to the validator, false otherwise.
     */
    public abstract boolean checkCodeWithValidator();

}
