# JoshHack Coverage: `game/Inventory.java`

## Baseline

The project as submitted contains no tests, and `Inventory.java` has no Javadoc, so the expected behavior was taken from the implementation and from how `Creature` uses it (`add`, `remove`, `contains` and `isFull` guard picking up, dropping, equipping and eating items). With no tests to run, the baseline is zero coverage.

| Metric | Baseline |
|---|---|
| Statement/line coverage | 0% (0/24 lines) |
| Branch coverage | 0% (0/18 branches) |

## Final Coverage

| Metric | Final |
|---|---|
| Statement/line coverage | 100% (24/24 lines) |
| Branch coverage | 100% (18/18 branches) |
| Method coverage | 100% (7/7 methods) |

All 33 tests pass. Numbers were measured with JaCoCo 0.8.12, the same engine VS Code uses for Java coverage. The VS Code screenshot is saved at `JoshHack/testresults/` (see the file added there).

## Tests Added

All tests are in `JoshHack/src/game/InventoryTest.java` (JUnit 5, package `game`).

- **Constructor, `getItems` and `get`:** the array has the requested size and starts empty. A capacity of 0 gives no slots, and a negative capacity throws `NegativeArraySizeException`. `getItems()` returns the live backing array. `get` returns the item at an index and throws `ArrayIndexOutOfBoundsException` out of range.
- **`add`:** items fill the first free slot in order. A removed slot is reused first. Adding to a full inventory silently ignores the new item and leaves existing items untouched. Zero capacity does nothing. The same item can occupy two slots.
- **`remove`:** it clears the slot of the item. Removing an item that isn't present, or removing from an empty inventory, changes nothing. With duplicates only the first occurrence is removed. Removal uses identity (`==`), so a different `Item` object with identical fields is not removed.
- **`isFull`:** it is false when empty, partially filled, after a removal and when the gap is not at the end, and true when every slot is filled or when capacity is 0.
- **`contains`:** it is true for an item in the first or last slot and false for an absent item, an empty inventory, a removed item, a look-alike object and zero capacity. It uses identity, not `equals()`.

## Remaining Uncovered Behavior

No lines or branches are left uncovered. Three behaviors are worth knowing about:

1. **`contains(null)` returns true while any slot is empty,** because empty slots hold `null` and the comparison uses `==`. It is false only when the inventory is full. `Creature` never calls `contains(null)`, so this has no effect in the game, but `contains_null_isTrueOnlyWhileASlotIsEmpty` documents it as current behavior and should be updated if the method is changed.
2. **`add` on a full inventory fails silently.** `Creature` guards against this by calling `isFull()` first, so `add_whenFull_ignoresNewItem` documents the behavior and does not claim it is desirable.
3. **`getItems()` exposes the internal array,** so callers can modify the inventory directly. The test `getItems_returnsBackingArrayReflectingChanges` records this.

Coverage was not the only goal: tests such as the identity-versus-equality ones and the "gap not at the end" `isFull` case check behavior that a coverage number would not show.
