import java.util.HashMap; import java.util.Map; import java.util.Scanner;

public class Cart { private Map<FoodItem, Integer> items; private Menu menu;private String special_request;

    public Cart(Menu menu) {
        items = new HashMap<>();
        this.menu=menu;

    }

    public void addItem(FoodItem item, int quantity) {
        if (item.isAvailable()) {
            items.put(item, items.getOrDefault(item, 0) + quantity);
        } else {
            System.out.println(item.getName() + " is no longer available and cannot be added to the cart.");
        }
    }

    public void modifyItemQuantity(FoodItem item, int newQuantity) {
        if (newQuantity <= 0) {
            items.remove(item);
        } else {
            items.put(item, newQuantity);
        }
    }

    public void removeItem(FoodItem item) {
        items.remove(item);
    }

    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
    public void cleanUpCart() {
        items.entrySet().removeIf(entry -> !entry.getKey().isAvailable() ||
                !Main.menu.getFoodItems().contains(entry.getKey()));
    }

    public void clear() {
        items.clear();
        System.out.println("All items have been removed from your cart.");
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
    public void displayCart(Customer customer) {
        cleanUpCart();

        if (isEmpty()) {
            System.out.println(customer.getUsername() + "'s cart is empty.");
            return;
        }

        System.out.println(customer.getUsername() + "'s Cart:");
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(item.getName() + " (" + item.getCategory() + ") - " +
                    quantity + " - " + (item.getPrice() * quantity) + " rs");
        }
    }
    public void cartOperations(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("Cart Operations:");
            System.out.println("1. Add Items");
            System.out.println("2. Modify Quantities");
            System.out.println("3. Remove Items");
            System.out.println("4. View Total");
            System.out.println("5. Checkout Process");
            System.out.println("Press any other key to return to the customer menu.");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addItemToCart(scanner, customer);
                    break;
                case "2":
                    modifyQuantitiesInCart(scanner, customer);
                    break;
                case "3":
                    removeItemFromCart(scanner, customer);
                    break;
                case "4":
                    viewTotalInCart(customer);
                    break;
                case "5":
                    checkoutProcess(scanner, customer);
                    break;
                default:
                    return; // Exit cart operations
            }
        }
    }

    private void addItemToCart(Scanner scanner, Customer customer) {
        menu.viewAllItems();
        System.out.print("Enter the name of the food item you want to add: ");
        String itemName = scanner.nextLine();

        FoodItem itemToAdd = null;
        for (FoodItem item : menu.getFoodItems()) {
            if (item.getName().equalsIgnoreCase(itemName) && item.isAvailable()) {
                itemToAdd = item;
                break;
            }
        }

        if (itemToAdd == null) {
            System.out.println("Item not available.");
            return;
        }

        System.out.print("Specify quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        addItem(itemToAdd, quantity);
        System.out.println(itemToAdd.getName() + " - " + quantity + " has been added to your cart.");

        System.out.println("Press 'a' to add another item, 'o' to go to cart operations menu, 'c' to go to customer menu, or any other key to go to main menu.");
        String nextAction = scanner.nextLine();
        if (nextAction.equals("o")) {
            return;
        } else if (nextAction.equals("c")) {
            Main.displayCustomerMenu(scanner);
        }
    }

    private void modifyQuantitiesInCart(Scanner scanner, Customer customer) {
        Cart cart = customer.getCart();
        cleanUpCart();

        if (cart.isEmpty()) {
            System.out.println(customer.getUsername() + "'s cart is empty.");
            return;
        }

        System.out.println(customer.getUsername() + "'s Cart:");
        for (Map.Entry<FoodItem, Integer> entry : cart.getItems().entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(item.getName() + " (" + item.getCategory() + ") - " + quantity + " - " + (item.getPrice() * quantity) + " rs");
        }

        System.out.print("Type the name of the item you want to modify quantities for: ");
        String itemName = scanner.nextLine();

        FoodItem itemToModify = null;
        for (Map.Entry<FoodItem, Integer> entry : cart.getItems().entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(itemName)) {
                itemToModify = entry.getKey();
                break;
            }
        }

        if (itemToModify == null) {
            System.out.println("Item not in cart.");
            System.out.println("Press 'o' to go to cart operations menu, 'c' to go to customer menu, or any other key to go to main menu.");
            String nextAction = scanner.nextLine();
            if (nextAction.equals("o")) {
                return;
            } else if (nextAction.equals("c")) {
                Main.displayCustomerMenu(scanner);
            }
            return;
        }

        System.out.print("Enter the new quantity for " + itemToModify.getName() + ": ");
        int newQuantity = Integer.parseInt(scanner.nextLine());

        if (newQuantity <= 0) {
            cart.removeItem(itemToModify);
            System.out.println(itemToModify.getName() + " has been removed from your cart.");
        } else {
            cart.modifyItemQuantity(itemToModify, newQuantity);
            System.out.println(itemToModify.getName() + " quantity updated to " + newQuantity + ".");
        }

        System.out.println("Press 'o' to go to cart operations menu, 'c' to go to customer menu, or any other key to go to main menu.");
        String nextAction = scanner.nextLine();
        if (nextAction.equals("o")) {
            return;
        } else if (nextAction.equals("c")) {
            Main.displayCustomerMenu(scanner);
        }}
    private void removeItemFromCart(Scanner scanner, Customer customer) {
        Cart cart = customer.getCart();
        cleanUpCart();
        System.out.println(customer.getUsername() + "'s Cart:");
        for (Map.Entry<FoodItem, Integer> entry : cart.getItems().entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(item.getName() + " (" + item.getCategory() + ") - " + quantity + " - " + (item.getPrice() * quantity) + " rs");
        }

        System.out.print("Enter the name of the item you want to remove: ");
        String itemName = scanner.nextLine();

        FoodItem itemToRemove = null;
        for (Map.Entry<FoodItem, Integer> entry : cart.getItems().entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(itemName)) {
                itemToRemove = entry.getKey();
                break;
            }
        }

        if (itemToRemove == null) {
            System.out.println("Item not in cart.");
            System.out.println("Press 'o' to go to cart operations menu, 'c' to go to customer menu, or any other key to go to main menu.");
            String nextAction = scanner.nextLine();
            if (nextAction.equals("o")) {
                return;
            } else if (nextAction.equals("c")) {
                Main.displayCustomerMenu(scanner);
            }
            return;
        }

        cart.removeItem(itemToRemove);
        System.out.println(itemToRemove.getName() + " removed from your cart!");

        System.out.println("Press 'o' to go to cart operations menu, 'c' to go to customer menu, or any other key to go to main menu.");
        String nextAction = scanner.nextLine();
        if (nextAction.equals("o")) {
        } else if (nextAction.equals("c")) {
            Main.displayCustomerMenu(scanner);
        }
    }
    private void viewTotalInCart(Customer customer) {

        Cart cart = customer.getCart();
        cleanUpCart();

        if (cart.isEmpty()) {
            System.out.println(customer.getUsername() + "'s cart is empty.");
            return;
        }

        double totalPrice = 0.0;

        System.out.println(customer.getUsername() + "'s Cart:");
        for (Map.Entry<FoodItem, Integer> entry : cart.getItems().entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            double itemTotalPrice = item.getPrice() * quantity;
            totalPrice += itemTotalPrice;
            System.out.println(item.getName() + " (" + item.getCategory() + ") - " + quantity + " - " + itemTotalPrice + " rs");
        }

        System.out.println("Total Price: " + totalPrice + " rs");
    }
    void checkoutProcess(Scanner scanner, Customer customer) {
        Cart cart = customer.getCart();
        cleanUpCart();
        if (cart.isEmpty()) {
            System.out.println(customer.getUsername() + "'s cart is empty. Cannot proceed to checkout.");
            return;
        }
        double totalPrice = 0.0;

        System.out.println(customer.getUsername() + "'s Cart:");
        for (Map.Entry<FoodItem, Integer> entry : cart.getItems().entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            double itemTotalPrice = item.getPrice() * quantity;
            totalPrice += itemTotalPrice;
            System.out.println(item.getName() + " (" + item.getCategory() + ") - " + quantity + " - " + itemTotalPrice + " rs");
        }

        System.out.println("Total Price: " + totalPrice + " rs");

        System.out.print("Enter hostel name: ");
        String hostelName = scanner.nextLine();

        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Enter special request: ");
        this.special_request= scanner.nextLine();

        // Prompt for payment
        System.out.print("Press 'p' to pay " + totalPrice + " rs: ");
        String paymentAction = scanner.nextLine();

        if (paymentAction.equalsIgnoreCase("p")) {
            System.out.println("Checkout complete! Thank you for your purchase.");

            Main.placeOrder(customer);
            cart.clear();
        } else {
            System.out.println("Returning to cart operations menu.");
        }
    }

    public String getSpecialRequest() {
        return this.special_request;
    }
}