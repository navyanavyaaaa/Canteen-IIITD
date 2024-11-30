import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;


public class CartTest {
    private Cart cart;
    private Menu menu;
    private Customer customer;

    @Before
    public void setUp() {
        menu = new Menu();
        cart = new Cart(menu);
        customer = new Customer("testUser ", "testPassword", menu);
        customer.setCart(cart);
        initializeMenu();
    }

    private void initializeMenu() {
        menu.addFoodItem(new FoodItem("Burger", 100, true, "Main Course"));
        menu.addFoodItem(new FoodItem("Fries", 40, true, "Side"));
        menu.addFoodItem(new FoodItem("Ice Cream", 20, true, "Dessert"));
    }
//TESTCASE1: 2 burgers are added to the cart , total cart price should be 200
    @Test
    public void testAddItemToCart_UpdatesTotalPrice() {
        cart.addItem(menu.getFoodItems().get(0), 2);
        assertEquals("Total price should be updated correctly.", 200.0, cart.getTotalPrice(), 0.01);
    }
//TESTCASE2:2 burders are added using add items but then the quantity is modified using modify quantities functionality to 3 and then cart price is checked to see if its equal to 300
    @Test
    public void testModifyItemQuantity_UpdatesTotalPrice() {
        cart.addItem(menu.getFoodItems().get(0), 2);
        cart.modifyItemQuantity(menu.getFoodItems().get(0), 3);
        assertEquals("Total price should be updated correctly after modifying quantity.", 300.0, cart.getTotalPrice(), 0.01);
    }



}