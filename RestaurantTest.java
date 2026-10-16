import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        // Create a restaurant that is open at a specific time
        LocalTime earlyOpening = LocalTime.parse("08:00:00");
        LocalTime lateClos = LocalTime.parse("23:00:00");
        Restaurant testRestaurant = new MockTimeRestaurant("Test Cafe", "Chennai", earlyOpening, lateClos, LocalTime.parse("10:30:00"));

        assertTrue(testRestaurant.isRestaurantOpen(), "Restaurant should be open at 10:30");
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        // Create a restaurant with specific opening/closing times
        LocalTime earlyOpening = LocalTime.parse("10:00:00");
        LocalTime earlyClosing = LocalTime.parse("15:00:00");
        Restaurant testRestaurant = new MockTimeRestaurant("Test Cafe", "Chennai", earlyOpening, earlyClosing, LocalTime.parse("16:00:00"));

        assertFalse(testRestaurant.isRestaurantOpen(), "Restaurant should be closed at 16:00");
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL (PART 3)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calculating_order_total_for_single_item_should_return_item_price() {
        int total = restaurant.calculateOrderTotal("Sweet corn soup");
        assertEquals(119, total);
    }

    @Test
    public void calculating_order_total_for_multiple_items_should_return_sum_of_prices() {
        int total = restaurant.calculateOrderTotal("Sweet corn soup", "Vegetable lasagne");
        assertEquals(388, total);
    }

    @Test
    public void calculating_order_total_for_no_items_should_return_zero() {
        int total = restaurant.calculateOrderTotal();
        assertEquals(0, total);
    }

    @Test
    public void calculating_order_total_for_repeated_items_should_include_all() {
        int total = restaurant.calculateOrderTotal("Sweet corn soup", "Sweet corn soup", "Vegetable lasagne");
        assertEquals(507, total);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}

// Mock class to test time-dependent behavior
class MockTimeRestaurant extends Restaurant {
    private LocalTime mockTime;

    public MockTimeRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime, LocalTime mockTime) {
        super(name, location, openingTime, closingTime);
        this.mockTime = mockTime;
    }

    @Override
    public LocalTime getCurrentTime() {
        return mockTime;
    }
}

import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {

    RestaurantService service;

    @BeforeEach
    public void setUp() {
        service = new RestaurantService();
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        Restaurant foundRestaurant = service.findRestaurantByName("Amelie's cafe");
        assertEquals(restaurant.getName(), foundRestaurant.getName());
        assertEquals(2, foundRestaurant.getMenu().size());
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);

        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("Pantry d'or"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);

        assertThrows(restaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}
    

    //>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL (PART 3)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calculating_order_total_for_single_item_should_return_item_price() {
        int total = restaurant.calculateOrderTotal("Sweet corn soup");
        assertEquals(119, total);
    }

    @Test
    public void calculating_order_total_for_multiple_items_should_return_sum_of_prices() {
        int total = restaurant.calculateOrderTotal("Sweet corn soup", "Vegetable lasagne");
        assertEquals(388, total);
    }

    @Test
    public void calculating_order_total_for_no_items_should_return_zero() {
        int total = restaurant.calculateOrderTotal();
        assertEquals(0, total);
    }

    @Test
    public void calculating_order_total_for_repeated_items_should_include_all() {
        int total = restaurant.calculateOrderTotal("Sweet corn soup", "Sweet corn soup", "Vegetable lasagne");
        assertEquals(507, total);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
