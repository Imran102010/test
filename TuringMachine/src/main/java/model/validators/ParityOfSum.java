package model.validators;

import model.Code;
import model.TurningMachineException;

public class ParityOfSum extends Validator {


    /**
     * Creates a ParityOfSum validator with the provided parameters.
     *
     * @param code       The Code to be validated.
     * @param codeSecret The secret Code used for validation.
     * @param nbValidator The number representing the type of validator.
     * @throws TurningMachineException If an invalid validator number is provided.
     */
    public ParityOfSum(Code code, Code codeSecret, int nbValidator) {

        super(code, codeSecret, nbValidator);
        if (nbValidator != 18){
            throw new TurningMachineException("Invalid Validator.");
        }
    }

    /**
     * Checks whether the provided code satisfies the conditions of the ParityOfSum validator.
     *
     * @return True if the sum of digits in the secret and provided codes has the same parity, false otherwise.
     */
    public boolean checkCodeWithValidator(){
        int sumSecret= firstSecret+secondSecret+thirdSecret;
        int sum=first+second+third;
    return (sum%2==0)==(sumSecret%2==0);
    }
}
