
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VendingMachineExceptionTest {

	@Test
	void defaultConstructor_hasNoMessage() {
		VendingMachineException ex = new VendingMachineException();
		assertNull(ex.getMessage());
	}

	@Test
	void messageConstructor_keepsMessage() {
		VendingMachineException ex = new VendingMachineException("oops");
		assertEquals("oops", ex.getMessage());
	}
}