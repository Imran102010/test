package model.validators;

import model.Code;
import model.TurningMachineException;

public class CountPair extends Validator {

    /**
     * Creates a CountPair validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public CountPair(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if (nbValidator != 17){
        throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks if the count of even numbers in the provided values is consistent with the count
     * of even numbers in the corresponding secret values.
     *
     * @return True if the conditions are met, false otherwise.
     */
    public boolean checkCodeWithValidator(){
        int count=0;
        int count1=0;
        if(first%2==0){
            count++;
        }
        if(second%2==0){
            count++;
        }
        if(third%2==0){
            count++;
        }
        if(firstSecret%2==0){
            count1++;
        }
        if(secondSecret%2==0){
            count1++;
        }
        if(thirdSecret%2==0){
            count1++;
        }
        return count==count1;
    }
}
