package model.validators;

import model.Code;
import model.TurningMachineException;

public class CountOneNumber extends Validator {

    /**
     * Creates a CountOneNumber validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public CountOneNumber(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if(nbValidator>10 ||nbValidator<8){
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks if the count of a specific number in the provided values is consistent with the count
     * of the same number in the corresponding secret values.
     *
     * @return True if the conditions are met, false otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 8 -> {
                return checkValidators(1);
            }
            case 9 -> {
                return checkValidators(3);
            }
            case 10 -> {
                return checkValidators(4);
            }
            default -> throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks if the count of a specific number in the provided values is consistent with the count
     * of the same number in the corresponding secret values.
     *
     * @param number The number to be counted.
     * @return True if the conditions are met, false otherwise.
     */
    private boolean checkValidators(int number) {
        int count1 = countOccurrences(number, firstSecret, secondSecret, thirdSecret);
        int count2 = countOccurrences(number, first, second, third);

        return count1 == count2;
    }


    /**
     * Counts the occurrences of a specific number in the provided values.
     *
     * @param number The number to be counted.
     * @param values The values to be checked.
     * @return The count of occurrences.
     */
    private int countOccurrences(int number, int... values) {
        int count = 0;
        for (int value : values) {
            if (value == number) {
                count++;
            }
        }
        return count;
    }
}
