
// 1. Initial and Final Coverage 
// | Source File | Initial Line Coverage | Initial Branch Coverage | Final Line Coverage | Final Branch Coverage |
// | VendingMachine.java| 83.33% (38/43) | 65% (13/20) | 96.43% | 14/20 branches |
// | VendingMachineItem.java| 82.35% (11/13) | 100% | 100% | 100% |

// 2. I added targeted unit tests to cover previously untested execution paths:
// testInsertMoney_LessThanOne_ThrowsException () : Verifies that inputs under \$1.00 correctly trigger an exception.
// testInsertMoney_GreaterThanOne_UpdatesBalance () : Verifies that valid monetary amounts accurately increment the machine's balance.

// 3. Condition-Coverage 
In my test suite, `testMakePurchase()` is the only method that exercises this decision, causing both individual conditions (`item != null` and `this.balance >= item.getPrice()`) to evaluate to true because a valid item is added and \$2.00 is inserted for a \$1.00 item. .

// 4. Test-Sensitivity Experiment 
When I intentally changed (this.balance >= item.getPrice() to (this.balance <= item.getPrice(), it caused few of my test cases to fail which shows that that the test suite is sensitive to logic regressions and capable of detecting defects. 
