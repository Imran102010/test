package model.validators;

import model.Code;
import model.TurningMachineException;

public class OrderNumber extends Validator {

    /**
     * Creates an OrderNumber validator with the provided parameters.
     *
     * @param code       The Code to be validated.
     * @param codeSecret The secret Code used for validation.
     * @param nbValidator The number representing the type of validator.
     * @throws TurningMachineException If an invalid validator number is provided.
     */
    public OrderNumber(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if (nbValidator != 22) {
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks whether the provided code satisfies the conditions of the OrderNumber validator.
     *
     * @return True if the digits in the secret and provided codes are in the same ascending or descending order,
     *         false otherwise.
     */
    public boolean checkCodeWithValidator() {
        int count1 = countOccurrences(firstSecret, secondSecret, thirdSecret);
        int count2 = countOccurrences(first, second, third);

        return ((count1==-3)&&(count2==-3))||((count1==3)&&(count2==3));
    }

    /**
     * Counts the occurrences of the provided values and determines if they are in ascending or descending order.
     *
     * @param values The values to be analyzed.
     * @return If the values are in ascending order, returns the count; if in descending order, returns the negative count.
     */
    private int countOccurrences(int... values) {
        int croissant = 1;
        int decroissant = -1;
        for (int i = 1; i < values.length; i++) {
            if (values[i - 1] <= values[i]) {
                croissant++;
            } else {
                decroissant--;
            }
        }
        if (croissant==3){
            return croissant;
        }
        else {
            return decroissant;
        }
    }
}
