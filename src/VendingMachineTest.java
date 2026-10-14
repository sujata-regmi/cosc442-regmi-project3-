import static org.junit.jupiter.api.Assertions.*;
<<<<<<< HEAD

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for VendingMachine.
 * Must be in the same package as VendingMachine (default package here) so the
 * protected members getItem() and balance are accessible.
 *
 * Tests assert the DOCUMENTED behavior of VendingMachine. Against the buggy
 * source in the zip, some tests fail on purpose (see bug list in the reply).
 */
class VendingMachineTest {

	private static final double DELTA = 0.0001;

	private VendingMachine vm;
	private VendingMachineItem chips;
	private VendingMachineItem soda;

	@BeforeEach
	void setUp() {
		vm = new VendingMachine();
		chips = new VendingMachineItem("Chips", 1.25);
		soda = new VendingMachineItem("Soda", 2.00);
	}

	// ---------- Constructor ----------

	@Test
	void constructor_startsWithZeroBalance() {
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	void constructor_allSlotsStartEmpty() {
		assertNull(vm.getItem(VendingMachine.A_CODE));
		assertNull(vm.getItem(VendingMachine.B_CODE));
		assertNull(vm.getItem(VendingMachine.C_CODE));
		assertNull(vm.getItem(VendingMachine.D_CODE));
	}

	@Test
	void constants_haveExpectedValues() {
		assertEquals(4, VendingMachine.NUM_SLOTS);
		assertEquals("A", VendingMachine.A_CODE);
		assertEquals("B", VendingMachine.B_CODE);
		assertEquals("C", VendingMachine.C_CODE);
		assertEquals("D", VendingMachine.D_CODE);
	}

	// ---------- addItem / getItem / getSlotIndex ----------

	@Test
	void addItem_slotA() {
		vm.addItem(chips, "A");
		assertSame(chips, vm.getItem("A"));
	}

	@Test
	void addItem_slotB() {
		vm.addItem(chips, "B");
		assertSame(chips, vm.getItem("B"));
	}

	@Test
	void addItem_slotC() {
		vm.addItem(chips, "C");
		assertSame(chips, vm.getItem("C"));
	}

	@Test
	void addItem_slotD() {
		vm.addItem(chips, "D");
		assertSame(chips, vm.getItem("D"));
	}

	@Test
	void addItem_slotsAreIndependent() {
		vm.addItem(chips, "A");
		vm.addItem(soda, "B");
		assertSame(chips, vm.getItem("A"));
		assertSame(soda, vm.getItem("B"));
		assertNull(vm.getItem("C"));
		assertNull(vm.getItem("D"));
	}

	@Test
	void addItem_occupiedSlot_throwsWithMessage() {
		vm.addItem(chips, "A");
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> vm.addItem(soda, "A"));
		assertEquals("Slot A already occupied", ex.getMessage());
		// original item must be untouched
		assertSame(chips, vm.getItem("A"));
	}

	@Test
	void addItem_invalidCode_throwsWithMessage() {
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> vm.addItem(chips, "E"));
		assertEquals("Invalid code for vending machine item", ex.getMessage());
	}

	@Test
	void getItem_invalidCode_throws() {
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> vm.getItem("Z"));
		assertEquals("Invalid code for vending machine item", ex.getMessage());
	}

	@Test
	void getItem_lowercaseCode_isInvalid() {
		assertThrows(VendingMachineException.class, () -> vm.getItem("a"));
	}

	// ---------- removeItem ----------

	@Test
	void removeItem_returnsItemAndEmptiesSlot() {
		vm.addItem(chips, "C");
		VendingMachineItem removed = vm.removeItem("C");
		assertSame(chips, removed);
		assertNull(vm.getItem("C"));
	}

	@Test
	void removeItem_emptySlot_throwsWithMessage() {
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> vm.removeItem("B"));
		assertEquals("Slot B is empty -- cannot remove item", ex.getMessage());
	}

	@Test
	void removeItem_invalidCode_throws() {
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> vm.removeItem("X"));
		assertEquals("Invalid code for vending machine item", ex.getMessage());
	}

	@Test
	void removeItem_thenSlotCanBeReused() {
		vm.addItem(chips, "D");
		vm.removeItem("D");
		vm.addItem(soda, "D");
		assertSame(soda, vm.getItem("D"));
	}

	// ---------- insertMoney / getBalance ----------

	@Test
	void insertMoney_increasesBalance() {
		vm.insertMoney(5.0);
		assertEquals(5.0, vm.getBalance(), DELTA);
	}

	@Test
	void insertMoney_accumulates() {
		vm.insertMoney(1.0);
		vm.insertMoney(2.5);
		assertEquals(3.5, vm.getBalance(), DELTA);
	}

	@Test
	void insertMoney_zero_isAccepted() {
		// Javadoc: precondition amount >= 0
		vm.insertMoney(0);
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	void insertMoney_fractionOfDollar_isAccepted() {
		vm.insertMoney(0.25);
		assertEquals(0.25, vm.getBalance(), DELTA);
	}

	@Test
	void insertMoney_negative_throwsWithMessage() {
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> vm.insertMoney(-1.0));
		assertEquals("Invalid amount.  Amount must be >= 0", ex.getMessage());
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	void insertMoney_justBelowZero_throws() {
		assertThrows(VendingMachineException.class, () -> vm.insertMoney(-0.01));
	}

	@Test
	void getBalance_doesNotChangeBalance() {
		vm.insertMoney(3.0);
		vm.getBalance();
		vm.getBalance();
		assertEquals(3.0, vm.getBalance(), DELTA);
	}

	// ---------- makePurchase ----------

	@Test
	void makePurchase_enoughMoney_returnsTrueAndDeductsPrice() {
		vm.addItem(chips, "A"); // 1.25
		vm.insertMoney(3.0);
		assertTrue(vm.makePurchase("A"));
		assertEquals(1.75, vm.getBalance(), DELTA);
	}

	@Test
	void makePurchase_exactMoney_returnsTrueAndBalanceZero() {
		vm.addItem(soda, "B"); // 2.00
		vm.insertMoney(2.0);
		assertTrue(vm.makePurchase("B"));
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	void makePurchase_notEnoughMoney_returnsFalseAndKeepsBalance() {
		vm.addItem(soda, "B"); // 2.00
		vm.insertMoney(1.0);
		assertFalse(vm.makePurchase("B"));
		assertEquals(1.0, vm.getBalance(), DELTA);
	}

	@Test
	void makePurchase_emptySlot_returnsFalseAndKeepsBalance() {
		vm.insertMoney(10.0);
		assertFalse(vm.makePurchase("C"));
		assertEquals(10.0, vm.getBalance(), DELTA);
	}

	@Test
	void makePurchase_invalidCode_throws() {
		vm.insertMoney(10.0);
		assertThrows(VendingMachineException.class, () -> vm.makePurchase("Q"));
	}

	@Test
	void makePurchase_success_emptiesSlot() {
		vm.addItem(chips, "A");
		vm.insertMoney(5.0);
		assertTrue(vm.makePurchase("A"));
		assertNull(vm.getItem("A"));
	}

	@Test
	void makePurchase_failure_keepsItemInSlot() {
		vm.addItem(soda, "B");
		vm.insertMoney(1.0);
		assertFalse(vm.makePurchase("B"));
		assertSame(soda, vm.getItem("B"));
	}

	// ---------- returnChange ----------

	@Test
	void returnChange_returnsBalanceAndResetsToZero() {
		vm.insertMoney(4.5);
		assertEquals(4.5, vm.returnChange(), DELTA);
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	void returnChange_whenBalanceZero_returnsZero() {
		assertEquals(0.0, vm.returnChange(), DELTA);
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	@Test
	void returnChange_afterPurchase() {
		vm.addItem(chips, "A");
		vm.insertMoney(3.0);
		vm.makePurchase("A");
		assertEquals(1.75, vm.returnChange(), DELTA);
		assertEquals(0.0, vm.getBalance(), DELTA);
	}

	// ---------- protected field access (same package) ----------

	@Test
	void balanceField_reflectsInsertedMoney() {
		vm.insertMoney(2.0);
		assertEquals(2.0, vm.balance, DELTA);
	}
=======
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
        assertNotNull(machine.getItem("A"));
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

    @Test 
    void testInsertMoney_LessThanOne_ThrowsException() {
        // Arrange 
        VendingMachine vendingMachine = new VendingMachine();
        
        assertThrows(VendingMachineException.class, () -> {
            vendingMachine.insertMoney(0.99);
        });
    }

    @Test
    void testInsertMoney_GreaterThanOne_UpdatesBalance() throws VendingMachineException {
        // Arrange
        VendingMachine vendingMachine = new VendingMachine();
        
        // Act
        vendingMachine.insertMoney(5.00);
        
        // Assert
        assertEquals(5.00, vendingMachine.getBalance(), 0.001); 
    }
>>>>>>> b1c0d71865215ed224cbfc33f3e1948dc81e9ff3
}
