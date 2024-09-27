package model.validators;

import model.Code;
import model.TurningMachineException;

public class TwinNumber extends Validator {

    /**
     * Creates a TwinNumber validator with the provided parameters.
     *
     * @param code       The Code to be validated.
     * @param codeSecret The secret Code used for validation.
     * @param nbValidator The number representing the type of validator.
     * @throws TurningMachineException If an invalid validator number is provided.
     */
    public TwinNumber(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if (nbValidator != 21) {
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks whether the provided code satisfies the conditions of the TwinNumber validator.
     *
     * @return True if the specified number of digits appear in exactly two positions in both the secret and provided codes, false otherwise.
     */

    public boolean checkCodeWithValidator() {
        int count1 = countOccurrences(firstSecret, secondSecret, thirdSecret);
        int count2 = countOccurrences(first, second, third);

        return (count1==2)==(count2==2);
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
