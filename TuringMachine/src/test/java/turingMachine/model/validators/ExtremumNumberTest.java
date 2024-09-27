package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.ExtremumNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtremumNumberTest {
    @Test
    void testFindMin() {
        Code secretCode = new Code(123);  // Replace with your secret code (values between 1 and 5)
        Code code = new Code(254);  // Replace with your test code (values between 1 and 5)
        ExtremumNumber extremumNumber = new ExtremumNumber(secretCode, code, 14);

        assertTrue(extremumNumber.checkCodeWithValidator());
    }

    @Test
    void testFindMax() {
        Code secretCode = new Code(543);  // Replace with your secret code (values between 1 and 5)
        Code code = new Code(321);  // Replace with your test code (values between 1 and 5)
        ExtremumNumber extremumNumber = new ExtremumNumber(secretCode, code, 15);

        assertTrue(extremumNumber.checkCodeWithValidator());
    }

    @Test
    void testInvalidValidatorNumber() {
        Code secretCode = new Code(123);  // Replace with your secret code (values between 1 and 5)
        Code code = new Code(454);  // Replace with your test code (values between 1 and 5)
        TurningMachineException exception = assertThrows(TurningMachineException.class,
                () -> new ExtremumNumber(secretCode, code, 16));

        assertEquals("Invalid Validator.", exception.getMessage());
    }

    @Test
    void testInvalidFindMaxMin() {
        Code secretCode = new Code(123);  // Replace with your secret code (values between 1 and 5)
        Code code = new Code(423);  // Replace with your test code (values between 1 and 5)
        ExtremumNumber extremumNumber = new ExtremumNumber(secretCode, code, 14);

        assertFalse(extremumNumber.checkCodeWithValidator());
    }

}