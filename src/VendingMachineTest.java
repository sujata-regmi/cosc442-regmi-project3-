import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {
    @Test
    void testAddItem_validItem_success() {
        // Arrange
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Soda", 1.50);

        // Act
        machine.addItem(item, "A");

        // Assert
        assertEquals(item, machine.getItem("A"));
        assertEquals("Soda", machine.getItem("A").getName());
        assertEquals(1.50, machine.getItem("A").getPrice(), 0.001);
    }

    @Test
    void testAddItem_occupiedSlot_throwsException() {
        // Arrange
        VendingMachine machine = new VendingMachine();

        // Act
        assertEquals(0.0, machine.getBalance(), 0.001);
        machine.insertMoney(1.00);
        
        // Assert
        assertEquals(1.00, machine.getBalance(), 0.001);
    }

    @Test
    void testGetItem_existingSlot_returnsItem() {
        // Arrange
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Candy", 0.75);
        machine.addItem(item, "B");

        // Act
        VendingMachineItem retrievedItem = machine.getItem("B");

        // Assert
        assertNotNull(retrievedItem);
        assertEquals("Candy", retrievedItem.getName());
        assertEquals(0.75, retrievedItem.getPrice(), 0.001);
    }

    @Test
    void testInsertMoney() {
        // Arrange
        VendingMachine machine = new VendingMachine();

        // Act
        machine.insertMoney(2.00);

        // Assert
        assertEquals(2.00, machine.getBalance(), 0.001);
    }
    @Test
    void testRemoveItem() throws VendingMachineException {
        // Arrange
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Chips", 1.25);
        machine.addItem(item, "C");

        // Act
        VendingMachineItem removed = machine.removeItem("C");

        // Assert
        assertEquals(item, removed);
        assertThrows(VendingMachineException.class, () -> machine.removeItem("C"));
    }

    @Test
    void testMakePurchase() throws VendingMachineException {
        // Arrange
        VendingMachine machine = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Water", 1.00);
        machine.addItem(item, "A");
        machine.insertMoney(2.00);

        // Act
        boolean success = machine.makePurchase("A");

        // Assert
        assertTrue(success);
        assertEquals(1.00, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
    }

    @Test
    void testReturnChange() throws VendingMachineException {
        // Arrange
        VendingMachine machine = new VendingMachine();
        machine.insertMoney(3.50);

        // Act
        double change = machine.returnChange();

        // Assert
        assertEquals(3.50, change, 0.001);
        assertEquals(0.0, machine.getBalance(), 0.001);
    }
}