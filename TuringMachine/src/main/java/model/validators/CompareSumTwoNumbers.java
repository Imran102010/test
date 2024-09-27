package model.validators;

import model.Code;
import model.TurningMachineException;

public class CompareSumTwoNumbers extends Validator {

    /**
     * Creates a CompareSumTwoNumbers validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public CompareSumTwoNumbers(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if (nbValidator != 19){
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Compares the sum of two numbers in the provided values with the sum of the corresponding
     * secret values.
     *
     * @return True if the conditions are met, false otherwise.
     */
    public boolean checkCodeWithValidator() {
        int sumSecret = firstSecret + secondSecret;
        int sum = first + second;
        return (sumSecret < 6 && sum < 6) ||
                (sumSecret == 6 && sum == 6) ||
                (sumSecret > 6 && sum > 6);
    }
}

