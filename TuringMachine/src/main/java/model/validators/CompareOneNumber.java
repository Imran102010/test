package model.validators;

import model.Code;
import model.TurningMachineException;

public class CompareOneNumber extends Validator {

    /**
     * Creates a CompareOneNumber validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public CompareOneNumber(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if (nbValidator > 4 || nbValidator < 1) {
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Compares the values of one digit in the code with the corresponding digit in the secret code.
     *
     * @return True if the conditions are met, false otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 1 -> {
                return checkValidators(firstSecret, first, 1);
            }
            case 2 -> {
                return checkValidators(firstSecret, first, 3);
            }
            case 3 -> {
                return checkValidators(secondSecret, second, 3);
            }
            case 4 -> {
                return checkValidators(secondSecret, second, 4);
            }
            default -> throw new TurningMachineException("Invalid Validator.");
        }
    }

    private boolean checkValidators(int digit_code_Secret, int digit_Code, int i) {
        return (digit_code_Secret > i && digit_Code > i) ||
                (digit_Code < i && digit_code_Secret < i) ||
                (digit_Code == i && digit_code_Secret == i);
    }
}
