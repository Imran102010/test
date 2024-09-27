package model.validators;

import model.Code;
import model.TurningMachineException;

public class CheckParity extends Validator {

    /**
     * Creates a CheckParity validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public CheckParity(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if(nbValidator>7 ||nbValidator<5){
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks the parity of a digit in the code compared to the corresponding digit in the secret code.
     *
     * @return True if the conditions are met, false otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 5 -> {
                return checkValidators(firstSecret, first);
            }
            case 6 -> {
                return checkValidators(secondSecret, second);
            }
            case 7 -> {
                return checkValidators(thirdSecret, third);
            }
            default -> throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks the parity of two digits.
     *
     * @param digit_secret_code The digit from the secret code.
     * @param digit_code        The digit from the code for comparison.
     * @return True if both digits have the same parity, false otherwise.
     */
    boolean checkValidators(int digit_secret_code, int digit_code){
        return (digit_code % 2 == 0 && digit_secret_code%2 == 0) ||
                (digit_code % 2 != 0 && digit_secret_code%2 != 0);
    }
}
