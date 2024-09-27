package model.validators;

import model.Code;
import model.TurningMachineException;

public class CompareTwoNumber extends Validator {


    /**
     * Creates a CompareTwoNumber validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public CompareTwoNumber(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if(nbValidator>13 ||nbValidator<11){
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks the relationship between two numbers in the provided values and the corresponding
     * secret values.
     *
     * @return True if the conditions are met, false otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 11 -> {
                return checkValidators(first,second,firstSecret,secondSecret);
            }
            case 12 -> {
                return checkValidators(first,third,firstSecret,thirdSecret);
            }
            case 13 -> {
                return checkValidators(second,third,secondSecret,thirdSecret);
            }
            default -> throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks the relationship between two numbers in the provided values and the corresponding
     * secret values.
     *
     * @param nbOne    The first number in the code.
     * @param nbTwo    The second number in the code.
     * @param secret   The first number in the secret code.
     * @param secret2  The second number in the secret code.
     * @return True if the conditions are met, false otherwise.
     */
    private boolean checkValidators(int nbOne, int nbTwo, int secret, int secret2) {
        return (secret<secret2 && nbOne<nbTwo) ||
                (secret==secret2 && nbOne== nbTwo)||
                (secret>secret2 && nbOne>nbTwo);
    }
}
