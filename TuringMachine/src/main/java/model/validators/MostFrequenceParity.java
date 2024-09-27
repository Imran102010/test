package model.validators;

import model.Code;
import model.TurningMachineException;

public class MostFrequenceParity extends Validator {

    /**
     * Creates a MostFrequenceParity validator with the given codes and validator number.
     *
     * @param code       The code to be validated.
     * @param codeSecret The secret code for comparison.
     * @param nbValidator The validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public MostFrequenceParity(Code code, Code codeSecret, int nbValidator) {
        super(code, codeSecret, nbValidator);
        if (nbValidator != 16) {
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Counts the number of even values among the provided values.
     *
     * @param values The values to be analyzed.
     * @return The count of even values.
     */
    public boolean checkCodeWithValidator(){
        int count=0;
        int counts=0;
        if(firstSecret%2==0){
            count++;
        }
        if (secondSecret%2==0){
            count++;
        }
        if (thirdSecret%2==0){
            count++;
        }
        if(first%2==0){
            counts++;
        }
        if (second%2==0){
            counts++;
        }
        if (third%2==0){
            counts++;
        }
        return ((count)>(3-count) && (counts)>(3-counts)) || ((count)<(3-count) && (counts)<(3-counts));
    }
}
