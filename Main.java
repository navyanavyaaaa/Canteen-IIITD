import java.util.*;

public class Main {
    private static Map<String, Customer> customers = new HashMap<>();
    private static Map<String, Admin> admins = new HashMap<>();
    static Menu menu = new Menu();
    private static Customer loggedInCustomer;
    static PriorityQueue<Order> orderQueue = new PriorityQueue<>((o1, o2) -> {
        return Long.compare(o1.getTimestamp(), o2.getTimestamp()); // sorting orders based on timestamp
    });

    public static void main(String[] args) {
        initializeMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to IIITD Canteen!");

        while (true) {
            System.out.println("Please choose an option:");
            System.out.println("1. Sign up as Customer");
            System.out.println("2. Log in as Customer");
            System.out.println("3. Sign up as Admin");
            System.out.println("4. Log in as Admin");
            System.out.println("Press any key to exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    signUpAsCustomer(scanner);
                    break;
                case "2":
                    logInAsCustomer(scanner);
                    break;
                case "3":
                    signUpAsAdmin(scanner);
                    break;
                case "4":
                    logInAsAdmin(scanner);
                    break;
                default:
                    System.out.println("BYEEE");
                    return;
            }
        }
    }
    private static void initializeMenu(){

        menu.addFoodItem(new FoodItem("Pav Bhaji", 50, true, "Main Course"));
        menu.addFoodItem(new FoodItem("Burger", 100, true, "Main Course"));
        menu.addFoodItem(new FoodItem("Momos", 40, true, "Side"));
        menu.addFoodItem(new FoodItem("Pasta",40 , true, "Main Course"));
        menu.addFoodItem(new FoodItem("Ice Cream", 20, true, "Dessert"));
        menu.addFoodItem(new FoodItem("Juice", 10, true, "Beverage"));
        menu.addFoodItem(new FoodItem("Coffee", 15, true, "Beverage"));
        menu.addFoodItem(new FoodItem("Noodles", 30, false, "Main Course"));
        menu.addFoodItem(new FoodItem("Fries", 40, true, "Side"));
        menu.addFoodItem(new FoodItem("Brownie", 20, true, "Dessert"));
    }
    private static void signUpAsCustomer(Scanner scanner) { System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        String username = name + "iiitd"; System.out.println("Your username is: " + username);
        System.out.print("Set a password: "); String password = scanner.nextLine();
        customers.put(username, new Customer(username, password,menu));
        System.out.println("Customer signed up successfully!");
    }

    private static void logInAsCustomer(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        if (!customers.containsKey(username)) {
            System.out.println("This user doesn't exist.");
            return;
        }
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (!customers.get(username).getPassword().equals(password)) {
            System.out.println("Username and password do not match.");
            return;
        }
        System.out.println("You are logged in as customer: " + username);
        loggedInCustomer = customers.get(username);
        displayCustomerMenu(scanner);
    }

    private static void signUpAsAdmin(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        String username = name + "iiitd";
        System.out.println("Your username is: " + username);
        String password = "1234";
        admins.put(username, new Admin(username, password));
        System.out.println("Admin signed up successfully! Your password is: " + password);
    }

    private static void logInAsAdmin(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        if (!admins.containsKey(username)) {
            System.out.println("This user doesn't exist.");
            return;
        }
        String password = "1234"; //password for all admins
        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();
        if (!inputPassword.equals(password)) {
            System.out.println("Username and password do not match.");
            return;
        }
        System.out.println("You are logged in as admin: " + username);
        displayAdminMenu(scanner);
    }
    public static void displayCustomerMenu(Scanner scanner) {
        System.out.println("Customer Menu:");
        System.out.println("1. Browse Menu");
        System.out.println("2. Cart Operations");
        System.out.println("3. Order Tracking");

        System.out.print("Choose an option: ");

        String choice1 = scanner.nextLine();

        switch (choice1) {
            case "1":
                menu.browseMenu(scanner);
                break;
            case "2":
                if (loggedInCustomer != null) {
                    loggedInCustomer.getCart().cartOperations(scanner, loggedInCustomer);
                } else {
                    System.out.println("No customer is logged in.");
                }
                break;

            case "3":
                orderTrackingMenu(scanner);
                break;

            default:
                System.out.println("Invalid option...");
                return;
        }

    }
    private static void orderTrackingMenu(Scanner scanner) {
        while (true) {
            System.out.println("Order Tracking Menu:");
            System.out.println("1. View Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. Order History");
            System.out.println("Press any other key to return to the customer menu.");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewOrderStatus(scanner);
                    break;
                case "2":
                    cancelOrder(scanner);
                    break;
                case "3":
                    orderHistory(scanner);
                    break;
                default:
                    return;
            }
        }
    }

    private static void viewOrderStatus(Scanner scanner) {
        if (Main.orderQueue.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        Order currentOrder = Main.orderQueue.peek();
        System.out.println("Order details for " + currentOrder.getCustomerUsername() + ":");
        for (Map.Entry<FoodItem, Integer> entry : currentOrder.getItems().entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("  " + item.getName() + " - " + quantity);
        }
        System.out.println("  Total Price: " + currentOrder.getTotalPrice() + " rs");
        System.out.println("  Status: " + currentOrder.getStatus());
        System.out.println("  Special Request: " + currentOrder.getSpecialRequest());
    }

    private static void cancelOrder(Scanner scanner) {
        if (Main.orderQueue.isEmpty()) {
            System.out.println("No pending orders to cancel.");
            return;
        }

        Order currentOrder = Main.orderQueue.peek();
        String status = currentOrder.getStatus();

        if (status.equals("Pending")) {
            Main.orderQueue.poll(); // Remove the order from the queue
            System.out.println("Order has been cancelled.");
        } else if (status.equals("Processing") || status.equals("Delivered")) {
            System.out.println("Order cannot be cancelled as the status is: " + status);
        } else if (status.equals("Denied")) {
            System.out.println("Order has been denied by admin.");
        }
    }

    private static void orderHistory(Scanner scanner) {
        if (Main.loggedInCustomer == null) {
            System.out.println("No customer is logged in.");
            return;
        }

        System.out.println("Order History for " + Main.loggedInCustomer.getUsername() + ":");
        for (Order order : Main.orderQueue) {
            if (order.getCustomerUsername().equals(Main.loggedInCustomer.getUsername())) {
                System.out.print("Order: ");
                for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                    FoodItem item = entry.getKey();
                    int quantity = entry.getValue();
                    System.out.print(item.getName() + " (" + quantity + "), ");
                }
                System.out.println("- Total Price: " + order.getTotalPrice() + " rs - Status: " + order.getStatus());
            }
        }
    }
    private static void displayAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Menu Management");
            System.out.println("2. Order Management");
            System.out.println("3. Generate Report");

            System.out.println("Press any other key to exit.");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    menu.menuManagement(scanner);
                    break;
                case "2":
                    orderManagementMenu(scanner);
                    break;
                case "3":
                    generateReport(scanner);
                    break;

                default:
                    return;
            }
        }
    }

    private static void generateReport(Scanner scanner) {
        int deliveredCount = 0;
        int pendingCount = 0;
        int deniedCount = 0;
        double totalSales = 0.0;
        Map<FoodItem, Integer> itemPopularity = new HashMap<>();

        for (Order order : orderQueue) {
            String status = order.getStatus();
            if (status.equals("Delivered")) {
                deliveredCount++;
                totalSales += order.getTotalPrice();
                for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                    itemPopularity.put(entry.getKey(), itemPopularity.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
            } else if (status.equals("Pending")) {
                pendingCount++;
            } else if (status.equals("Denied")) {
                deniedCount++;
            }
        }

        System.out.println("Daily Sales Report:");
        System.out.println("Total Orders Delivered: " + deliveredCount);
        System.out.println("Total Orders Pending: " + pendingCount);
        System.out.println("Total Orders Denied: " + deniedCount);
        System.out.println("Total Sales: " + totalSales + " rs");

        System.out.println("Most Popular Items Sold:");
        itemPopularity.entrySet().stream().sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())).forEach(entry -> System.out.println(entry.getKey().getName() + ": " + entry.getValue() + " sold"));
    }

    private static void orderManagementMenu(Scanner scanner) {
        while (true) {
            System.out.println("Order Management:");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Handle Order");
            System.out.println("Press any other key to return to the admin menu.");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewPendingOrders();
                    break;
                case "2":
                    handleOrder(scanner);
                    break;
                default:
                    return;
            }
        }
    }

    public static Map<String, Customer> getCustomers() {
        return customers;
    }
    public static void placeOrder(Customer customer) {
        Cart cart = customer.getCart();
        double totalPrice = cart.getTotalPrice();
        Map<FoodItem, Integer> orderItems = new HashMap<>(cart.getItems());
        String specialRequest = cart.getSpecialRequest();
        Order newOrder = new Order(customer.getUsername(), orderItems, totalPrice, specialRequest);
        orderQueue.offer(newOrder);
        System.out.println("Order placed successfully!");
        cart.clear();
    }
    public static void viewPendingOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        System.out.println("Pending Orders:");
        for (Order order : orderQueue) {
            System.out.println(order.getCustomerUsername() + ": ");
            for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                FoodItem item = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("  " + item.getName() + " - " + quantity);
            }
            System.out.println("  Total Price: " + order.getTotalPrice() + " rs");
            System.out.println("  Status: " + order.getStatus());
            System.out.println("  Special Request: " + order.getSpecialRequest());
        }
    }
    public static void handleOrder(Scanner scanner) {
        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        Order currentOrder = orderQueue.peek();
        System.out.println("Handling order for " + currentOrder.getCustomerUsername());
        System.out.println("Order details:");
        for (Map.Entry<FoodItem, Integer> entry : currentOrder.getItems().entrySet()) {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("  " + item.getName() + " - " + quantity);
        }
        System.out.println("  Total Price: " + currentOrder.getTotalPrice() + " rs");
        System.out.println("  Status: " + currentOrder.getStatus());
        System.out.println("  Special Request: " + currentOrder.getSpecialRequest());

        if (currentOrder.getStatus().equals("Denied")) {
            processRefund(currentOrder);
            orderQueue.poll();
            return;
        }

        System.out.println("Select status to set:");
        System.out.println("1. Processing");
        System.out.println("2. Delivered");
        System.out.println("3. Denied");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                currentOrder.setStatus("Processing");
                System.out.println("Status set to Processing.");
                System.out.println("Handling special request: " + currentOrder.getSpecialRequest());
                break;
            case "2":
                currentOrder.setStatus("Delivered");
                System.out.println("Status set to Delivered.");
                orderQueue.poll(); // Dequeue
                break;
            case "3":
                currentOrder.setStatus("Denied");
                System.out.println("order denied");
                processRefund(currentOrder);
                orderQueue.poll();
                break;
            default:
                System.out.println("Invalid choice. Returning to order management menu.");
                return;
        }
    }

    private static void processRefund(Order order) {
        System.out.println("Refund process initiated for order " + order.getCustomerUsername());
        System.out.println("Order denied because item was either removed or unavailable.");
    }



}