package model.validators;

import model.Code;

public class ValidatorFactory {

    /**
     * Creates a Validator based on the provided parameters.
     *
     * @param code       The Code to be validated.
     * @param codeSecret The secret Code used for validation.
     * @param nbValidator The number representing the type of validator to create.
     * @return A Validator instance corresponding to the specified type.
     * @throws IllegalArgumentException If an invalid validator number is provided.
     */
    public static Validator createValidator(Code code, Code codeSecret, int nbValidator) {
        return switch (nbValidator) {
            case 1, 2, 3, 4 -> new CompareOneNumber(code, codeSecret, nbValidator);
            case 5, 6, 7 -> new CheckParity(code, codeSecret, nbValidator);
            case 8, 9, 10 -> new CountOneNumber(code, codeSecret, nbValidator);
            case 11, 12, 13 -> new CompareTwoNumber(code, codeSecret, nbValidator);
            case 14, 15 -> new ExtremumNumber(code, codeSecret, nbValidator);
            case 16 -> new MostFrequenceParity(code, codeSecret, nbValidator);
            case 17 -> new CountPair(code, codeSecret, nbValidator);
            case 18 -> new ParityOfSum(code, codeSecret, nbValidator);
            case 19 -> new CompareSumTwoNumbers(code, codeSecret, nbValidator);
            case 20 -> new RepetitionNumber(code, codeSecret, nbValidator);
            case 21 -> new TwinNumber(code, codeSecret, nbValidator);
            case 22 -> new OrderNumber(code, codeSecret, nbValidator);
            default -> throw new IllegalArgumentException("Invalid validator.");
        };
    }
}
