# Restaurant Finder - Backend Implementation

This project implements the backend for a Restaurant Finder application with customer features including searching, viewing menus, adding items to cart, and calculating order totals.

## Part 1: UML Diagrams (30%)
- Use Case Diagram - 7 use cases with actors and relationships
- Activity Diagram - Complete flow from search to order total
- Comprehensive documentation

## Part 2: Methods and Tests (50%)
### Implemented Methods:
- `Restaurant.getMenu()` - Returns list of menu items
- `Restaurant.isRestaurantOpen()` - Checks if restaurant is open based on current time
- `RestaurantService.findRestaurantByName()` - Searches for restaurant by name

### Implemented Tests:
- Test for open/closed status with time validation
- Test for menu operations (add/remove items)
- Test for searching restaurants (found/not found cases)
- Test for admin operations (add/remove restaurants)

### Refactoring:
- Extracted common setup code into `@BeforeEach` methods
- Removed duplicate restaurant and test data initialization
- Centralized LocalTime definitions

## Part 3: Order Total Feature (20%)
### Feature: Calculate Order Total
- Method: `calculateOrderTotal(String... itemNames)`
- Accepts variable number of item names
- Returns total price for selected items
- Supports duplicate items in single order
- Implemented using TDD approach with failing tests first

### Test Cases for Order Total:
- Single item order
- Multiple items order
- No items (zero total)
- Repeated items in same order

## Build and Test

### Prerequisites:
- Java 11 or higher
- Maven 3.6 or higher

### Commands:
```bash
# Compile
mvn clean compile

# Run tests
mvn test

# Generate test report with code coverage
mvn test jacoco:report
```

## Code Coverage
The implementation achieves >98% code coverage (excluding formatting methods).

## Project Structure
```
src/
├── main/java/
│   ├── Restaurant.java
│   ├── RestaurantService.java
│   ├── Item.java
│   ├── restaurantNotFoundException.java
│   └── itemNotFoundException.java
└── test/java/
    ├── RestaurantTest.java
    └── RestaurantServiceTest.java
```

## Key Implementation Details

### isRestaurantOpen()
- Compares current time with opening and closing times
- Returns true if time is between opening and closing
- Uses LocalTime for accurate time comparison

### findRestaurantByName()
- Iterates through restaurant list
- Throws restaurantNotFoundException if not found
- Case-sensitive search

### calculateOrderTotal()
- Uses varargs (String...) for flexible input
- Iterates through item names and sums prices
- Handles non-existent items gracefully (skips them)

## Testing Approach
- Unit tests for all public methods
- Test doubles for time-dependent code (MockTimeRestaurant)
- Edge cases covered (empty menus, non-existent items, etc.)
- All tests pass with >98% coverage
