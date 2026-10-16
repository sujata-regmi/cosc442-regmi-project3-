package game;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for game.Inventory.
 *
 * Inventory.java has no Javadoc, so the expected behavior below is derived from
 * the implementation and from how Creature uses it (add/remove/contains/isFull
 * guard pickup, drop, equip and eat). Items are compared by identity (==), not
 * equals(), and add() silently ignores items when the inventory is full.
 */
class InventoryTest {

	private Inventory inventory;
	private Item sword;
	private Item potion;
	private Item bread;

	private static Item newItem(String name) {
		return new Item('!', Color.WHITE, name, null);
	}

	@BeforeEach
	void setUp() {
		inventory = new Inventory(3);
		sword = newItem("sword");
		potion = newItem("potion");
		bread = newItem("bread");
	}

	// ---------- constructor / getItems / get ----------

	@Test
	void constructor_createsArrayOfRequestedSize() {
		assertEquals(3, inventory.getItems().length);
	}

	@Test
	void constructor_startsEmpty() {
		for (int i = 0; i < 3; i++) {
			assertNull(inventory.get(i));
		}
	}

	@Test
	void constructor_zeroCapacity_hasNoSlots() {
		Inventory empty = new Inventory(0);
		assertEquals(0, empty.getItems().length);
	}

	@Test
	void constructor_negativeCapacity_throws() {
		assertThrows(NegativeArraySizeException.class, () -> new Inventory(-1));
	}

	@Test
	void getItems_returnsBackingArrayReflectingChanges() {
		Item[] items = inventory.getItems();
		inventory.add(sword);
		assertSame(sword, items[0]);
	}

	@Test
	void get_returnsItemAtIndex() {
		inventory.add(sword);
		inventory.add(potion);
		assertSame(sword, inventory.get(0));
		assertSame(potion, inventory.get(1));
	}

	@Test
	void get_outOfRange_throws() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> inventory.get(3));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> inventory.get(-1));
	}

	// ---------- add ----------

	@Test
	void add_placesItemInFirstSlot() {
		inventory.add(sword);
		assertSame(sword, inventory.get(0));
		assertNull(inventory.get(1));
	}

	@Test
	void add_fillsSlotsInOrder() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		assertSame(sword, inventory.get(0));
		assertSame(potion, inventory.get(1));
		assertSame(bread, inventory.get(2));
	}

	@Test
	void add_whenFull_ignoresNewItem() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		Item extra = newItem("extra");

		inventory.add(extra);

		assertFalse(inventory.contains(extra));
		assertSame(sword, inventory.get(0));
		assertSame(potion, inventory.get(1));
		assertSame(bread, inventory.get(2));
	}

	@Test
	void add_reusesFirstFreedSlot() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		inventory.remove(potion);

		Item extra = newItem("extra");
		inventory.add(extra);

		assertSame(extra, inventory.get(1));
	}

	@Test
	void add_zeroCapacity_doesNothing() {
		Inventory empty = new Inventory(0);
		empty.add(sword);
		assertFalse(empty.contains(sword));
	}

	@Test
	void add_sameItemTwice_occupiesTwoSlots() {
		inventory.add(sword);
		inventory.add(sword);
		assertSame(sword, inventory.get(0));
		assertSame(sword, inventory.get(1));
	}

	// ---------- remove ----------

	@Test
	void remove_clearsSlotOfItem() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.remove(potion);
		assertNull(inventory.get(1));
		assertSame(sword, inventory.get(0));
	}

	@Test
	void remove_lastSlotItem() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		inventory.remove(bread);
		assertNull(inventory.get(2));
		assertFalse(inventory.contains(bread));
	}

	@Test
	void remove_itemNotPresent_leavesInventoryUnchanged() {
		inventory.add(sword);
		inventory.remove(potion);
		assertSame(sword, inventory.get(0));
		assertNull(inventory.get(1));
		assertNull(inventory.get(2));
	}

	@Test
	void remove_fromEmptyInventory_doesNothing() {
		inventory.remove(sword);
		assertFalse(inventory.contains(sword));
	}

	@Test
	void remove_duplicateItem_removesOnlyFirstOccurrence() {
		inventory.add(sword);
		inventory.add(sword);
		inventory.remove(sword);
		assertNull(inventory.get(0));
		assertSame(sword, inventory.get(1));
	}

	@Test
	void remove_usesIdentityNotEquality() {
		Item twin = newItem("sword"); // same fields as sword, different object
		inventory.add(sword);
		inventory.remove(twin);
		assertSame(sword, inventory.get(0));
	}

	// ---------- isFull ----------

	@Test
	void isFull_emptyInventory_isFalse() {
		assertFalse(inventory.isFull());
	}

	@Test
	void isFull_partiallyFilled_isFalse() {
		inventory.add(sword);
		inventory.add(potion);
		assertFalse(inventory.isFull());
	}

	@Test
	void isFull_allSlotsFilled_isTrue() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		assertTrue(inventory.isFull());
	}

	@Test
	void isFull_afterRemoval_isFalse() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		inventory.remove(potion);
		assertFalse(inventory.isFull());
	}

	@Test
	void isFull_whenGapIsNotAtEnd_countsOnlyOccupiedSlots() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		inventory.remove(sword); // gap at slot 0, others still occupied
		assertFalse(inventory.isFull());
	}

	@Test
	void isFull_zeroCapacity_isTrue() {
		assertTrue(new Inventory(0).isFull());
	}

	// ---------- contains ----------

	@Test
	void contains_presentItem_isTrue() {
		inventory.add(sword);
		inventory.add(potion);
		assertTrue(inventory.contains(potion));
	}

	@Test
	void contains_itemInLastSlot_isTrue() {
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		assertTrue(inventory.contains(bread));
	}

	@Test
	void contains_absentItem_isFalse() {
		inventory.add(sword);
		assertFalse(inventory.contains(potion));
	}

	@Test
	void contains_emptyInventory_isFalse() {
		assertFalse(inventory.contains(sword));
	}

	@Test
	void contains_afterRemoval_isFalse() {
		inventory.add(sword);
		inventory.remove(sword);
		assertFalse(inventory.contains(sword));
	}

	@Test
	void contains_usesIdentityNotEquality() {
		Item twin = newItem("sword");
		inventory.add(sword);
		assertFalse(inventory.contains(twin));
	}

	@Test
	void contains_zeroCapacity_isFalse() {
		assertFalse(new Inventory(0).contains(sword));
	}

	/**
	 * Documents a quirk of the current implementation: because empty slots hold
	 * null and contains() compares with ==, contains(null) is true whenever at
	 * least one slot is empty, and false only when the inventory is full.
	 */
	@Test
	void contains_null_isTrueOnlyWhileASlotIsEmpty() {
		assertTrue(inventory.contains(null));
		inventory.add(sword);
		inventory.add(potion);
		inventory.add(bread);
		assertFalse(inventory.contains(null));
	}
}
