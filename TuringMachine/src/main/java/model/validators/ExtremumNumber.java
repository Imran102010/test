package model.validators;

import model.Code;
import model.TurningMachineException;

public class ExtremumNumber extends Validator {

    /**
     * Creates an ExtremumNumber validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public ExtremumNumber(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if(nbValidator>15 ||nbValidator<14){
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks if the extremum value among the provided values is consistent with the extremum value
     * of the corresponding secret values.
     *
     * @return True if the conditions are met, false otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 14 -> {
                return checkValidatorsMin() ;
            }
            case 15 -> {
                return checkValidatorsMax();
            }
            default -> throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks if the extremum value among the provided values is the maximum value.
     *
     * @return True if the conditions are met, false otherwise.
     */
    private boolean checkValidatorsMax() {
        int count1 = indexMax(firstSecret, secondSecret, thirdSecret);
        int count2 = indexMax(first, second, third);

        return count1 == count2;
    }

    /**
     * Checks if the extremum value among the provided values is the minimum value.
     *
     * @return True if the conditions are met, false otherwise.
     */
    private boolean checkValidatorsMin() {
        int count1 = indexMin(firstSecret, secondSecret, thirdSecret);
        int count2 = indexMin(first, second, third);

        return count1 == count2;
    }

    /**
     * Finds the index of the minimum value among the provided values.
     *
     * @param values The values to be analyzed.
     * @return The index of the minimum value.
     */
    private int indexMin(int... values) {
        int minIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Finds the index of the maximum value among the provided values.
     *
     * @param values The values to be analyzed.
     * @return The index of the maximum value.
     */
    private int indexMax(int... values) {
        int maxIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}

