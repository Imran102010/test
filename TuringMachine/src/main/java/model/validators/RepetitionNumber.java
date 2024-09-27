package model.validators;

import model.Code;
import model.TurningMachineException;

public class RepetitionNumber extends Validator {

    /**
     * Creates a RepetitionNumber validator with the provided parameters.
     *
     * @param code       The Code to be validated.
     * @param codeSecret The secret Code used for validation.
     * @param nbValidator The number representing the type of validator.
     * @throws TurningMachineException If an invalid validator number is provided.
     */
    public RepetitionNumber(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret,nbValidator);
        if (nbValidator != 20) {
            throw new TurningMachineException("Invalid Validator.");
        }

    }

    /**
     * Checks whether the provided code satisfies the conditions of the RepetitionNumber validator.
     *
     * @return True if the occurrences of digits in the secret and provided codes match, false otherwise.
     */
    public boolean checkCodeWithValidator() {
        int count1 = countOccurrences(firstSecret, secondSecret, thirdSecret);
        int count2 = countOccurrences(first, second, third);

        return count1 == count2;
    }

    /**
     * Counts the occurrences of digits in the provided values.
     * If a digit appears more than once, it is counted accordingly.
     *
     * @param values The values to count occurrences from.
     * @return The count of occurrences.
     */
    private int countOccurrences(int... values) {
        int count = 1;
        for (int i = 1; i < values.length; i++) {
            if (values[i - 1] == values[i]) {
                count++;
            }
        }
        if(values[0]==values[2] && count<3){
            count++;
        }
        return count;
    }
}
