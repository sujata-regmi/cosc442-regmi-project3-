import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VendingMachineItemTest {

	@Test
	void constructor_setsNameAndPrice() {
		VendingMachineItem item = new VendingMachineItem("Chips", 1.25);
		assertEquals("Chips", item.getName());
		assertEquals(1.25, item.getPrice(), 0.0001);
	}

	@Test
	void constructor_zeroPrice_isAllowed() {
		VendingMachineItem item = new VendingMachineItem("Free", 0);
		assertEquals(0.0, item.getPrice(), 0.0001);
	}

	@Test
	void constructor_negativePrice_throwsWithMessage() {
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> new VendingMachineItem("Bad", -0.01));
		assertEquals("Price cannot be less than zero", ex.getMessage());
	}
}
