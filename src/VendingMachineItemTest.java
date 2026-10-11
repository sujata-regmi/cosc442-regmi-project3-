import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testItemCreationNegativePrice() {
        // Arrange
        String name = "Invalid";
        double negativePrice = -0.50;

        // Act & Assert
        assertThrows(VendingMachineException.class, () -> {
            new VendingMachineItem(name, negativePrice);
        });
    }
}