import static org.junit.jupiter.api.Assertions.*;
<<<<<<< HEAD

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
=======
import org.junit.jupiter.api.Test;

public class VendingMachineItemTest {
    

    @Test
    void testItemCreationAndGetters() {
        // Arrange
        String expectedName = "Soda";
        double expectedPrice = 1.50;

        // Act
        VendingMachineItem item = new VendingMachineItem(expectedName, expectedPrice);

        // Assert
        assertEquals(expectedName, item.getName());
        assertEquals(expectedPrice, item.getPrice(), 0.001);
    }


}
>>>>>>> b1c0d71865215ed224cbfc33f3e1948dc81e9ff3
