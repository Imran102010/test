package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.CompareTwoNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompareTwoNumberTest {

        @Test
        public void testCompareFirstAndSecond() {
            Code secretCode = new Code(135); // Example secret code
            Code code = new Code(124); // Example code to test
            int nbValidator = 11; // Compare first and second

            CompareTwoNumber validator = new CompareTwoNumber(secretCode, code, nbValidator);
            assertTrue(validator.checkCodeWithValidator(), "Failed to compare first and second digits.");
        }

        @Test
        public void testCompareFirstAndThird() {
            Code secretCode = new Code(352); // Example secret code
            Code code = new Code(311); // Example code to test
            int nbValidator = 12; // Compare first and third

            CompareTwoNumber validator = new CompareTwoNumber(secretCode, code, nbValidator);
            assertTrue(validator.checkCodeWithValidator(), "Failed to compare first and third digits.");
        }

        @Test
        public void testCompareSecondAndThird() {
            Code secretCode = new Code(451); // Example secret code
            Code code = new Code(342); // Example code to test
            int nbValidator = 13; // Compare second and third

            CompareTwoNumber validator = new CompareTwoNumber(secretCode, code, nbValidator);
            assertTrue(validator.checkCodeWithValidator(), "Failed to compare second and third digits.");
        }

        @Test
        public void testInvalidValidatorNumber() {
            Code secretCode = new Code(123); // Example secret code
            Code code = new Code(452); // Example code to test
            int nbValidator = 14; // Invalid validator number

            assertThrows(TurningMachineException.class, () -> {
                new CompareTwoNumber(secretCode, code, nbValidator);
            }, "Expected TurningMachineException for invalid validator number.");
        }

        @Test
        public void testInvalidCode() {
            // Example invalid code with digit greater than 5
            assertThrows(TurningMachineException.class, () -> {
                new Code(678);
            }, "Expected TurningMachineException for invalid code.");
        }
    }


