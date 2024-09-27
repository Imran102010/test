package model;

/**
 * Represents a code in the game.
 */
public class Code {

    protected int first;
    protected int second;
    protected int third;

    /**
     * Constructs a Code object based on the provided code.
     *
     * @param code The integer code.
     * @throws TurningMachineException If the provided code is invalid.
     */
    public Code(int code) {
        third = code % 10;
        if (third > 5 || third < 1) {
            throw new TurningMachineException("The code proposed is invalid.");
        }
        code /= 10;
        second = code % 10;
        if (second > 5 || second < 1) {
            throw new TurningMachineException("The code proposed is invalid.");
        }
        code /= 10;
        first = code % 10;
        if (first > 5 || first < 1) {
            throw new TurningMachineException("The code proposed is invalid.");
        }
    }

    /**
     * Constructs a Code object based on the provided Code object.
     *
     * @param code The Code object to copy.
     */
    public Code(Code code){
        first=code.getFirst();
        second=code.getSecond();
        third=code.getThird();
    }

    /**
     * Gets the value of the first digit in the code.
     *
     * @return The value of the first digit.
     */
    public int getFirst() {
        return first;
    }

    /**
     * Gets the value of the second digit in the code.
     *
     * @return The value of the second digit.
     */
    public int getSecond() {
        return second;
    }

    /**
     * Gets the value of the third digit in the code.
     *
     * @return The value of the third digit.
     */
    public int getThird() {
        return third;
    }

    /**
     * Checks if the current code is equal to the provided code.
     *
     * @param code The Code object to compare.
     * @return True if the codes are equal, false otherwise.
     */
    public boolean equals(Code code) {
        return first==code.getFirst() && second==code.getSecond() && third==code.getThird();
    }
}
