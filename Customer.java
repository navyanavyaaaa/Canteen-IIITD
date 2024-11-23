import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer {
    private String username;
    private String password;
    private Cart cart;
    private String special_request;
    private List<Order> orderHistory;

    public Customer(String username, String password, Menu menu) {
        this.username = username;
        this.password = password;
        this.cart = new Cart(menu);
        this.orderHistory = new ArrayList<>();
    }

    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }
    public void checkoutProcess(Scanner scanner) {
        Cart cart = getCart();
        cart.checkoutProcess(scanner, this);
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Cart getCart() {
        return cart;
    }
}