import java.util.ArrayList; import java.util.Comparator; import java.util.List; import java.util.Scanner;

public class Menu { private ArrayList<FoodItem> foodItems;

    public Menu() {
        foodItems = new ArrayList<>();
    }

    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (FoodItem item : foodItems) {
            System.out.println(item);
        }
    }
    public List<FoodItem> getFoodItems() {
        return foodItems; // Return the list of food items
    }

    public void viewAllItems() {
        System.out.println("All Menu Items:");
        for (FoodItem item : foodItems) {
            System.out.println(item.getName() + ": " + item.getPrice() + " rs (" +
                    (item.isAvailable() ? "Available" : "Currently not available :/") + ") - " + item.getCategory());
        }
    }

    public void searchForItem(Scanner scanner) {
        System.out.print("Enter the name or keyword of the item you want to search: ");
        String keyword = scanner.nextLine().toLowerCase();
        boolean found = false;

        for (FoodItem item : foodItems) {
            if (item.getName().toLowerCase().contains(keyword)) {
                System.out.println(item.getName() + ": " + item.getPrice() + " rs (" +
                        (item.isAvailable() ? "Available" : "Currently not available :/") + ") - " + item.getCategory());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Item not available in the menu.");
        }
    }

    public void filterByCategory(Scanner scanner) {
        System.out.println("Filter by Category:");
        System.out.println("1. Main Course");
        System.out.println("2. Beverages");
        System.out.println("3. Side");
        System.out.println("4. Dessert");
        System.out.print("Enter the number for the category you want to filter by: ");
        String choice = scanner.nextLine();

        String category = "";
        switch (choice) {
            case "1":
                category = "Main Course";
                break;
            case "2":
                category = "Beverage";
                break;
            case "3":
                category = "Side";
                break;
            case "4":
                category = "Dessert";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Items in category " + category + ":");
        for (FoodItem item : foodItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                System.out.println(item.getName() + ": " + item.getPrice() + " rs (" +
                        (item.isAvailable() ? "Available" : "Currently not available :/") + ")");
            }
        }
    }

    public void sortByPrice(Scanner scanner) {
        System.out.println("Sort by Price:");
        System.out.println("Press 'a' for lowest to highest or 'd' for highest to lowest.");
        String choice = scanner.nextLine();

        if (choice.equals("a")) {
            foodItems.sort(Comparator.comparingDouble(FoodItem::getPrice));
        } else if (choice.equals("d")) {
            foodItems.sort(Comparator.comparingDouble(FoodItem::getPrice).reversed());
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.println("Sorted Menu Items:");
        viewAllItems();
    }

    public void browseMenu(Scanner scanner) {
        while (true) {
            System.out.println("Browse Menu:");
            System.out.println("1. View All Items");
            System.out.println("2. Search for an Item");
            System.out.println("3. Filter by Category");
            System.out.println("4. Sort by Price");
            System.out.println("Press any other key to return to the customer menu.");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewAllItems();
                    break;
                case "2":
                    searchForItem(scanner);
                    break;
                case "3":
                    filterByCategory(scanner);
                    break;
                case "4":
                    sortByPrice(scanner);
                    break;
                default:
                    return;
            }

            System.out.println("Press 'b' to return to browse menu, 'c' to return to customer menu, or any other key to return to main menu.");
            String returnChoice = scanner.nextLine();
            if (returnChoice.equals("b")) {
                continue;
            } else if (returnChoice.equals("c")) {
                return;
            } else {
                return;
            }
        }
    }
    public void menuManagement(Scanner scanner) {
        while (true) {
            System.out.println("Menu Management:");
            System.out.println("1. Add New Items to Menu");
            System.out.println("2. Update Existing Items");
            System.out.println("3. Remove Items");
            System.out.println("Press any other key to return to Admin Menu.");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addNewItem(scanner);
                    break;
                case "2":
                    updateExistingItem(scanner);
                    break;
                case "3":
                    removeItem(scanner);
                    break;
                default:
                    return;
            }
        }
    }

    private void addNewItem(Scanner scanner) {
        System.out.print("Enter the name of the item: ");
        String name = scanner.nextLine();

        System.out.print("Enter the price of the item: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the category of the item: ");
        String category = scanner.nextLine();

        System.out.print("Is the item available? (true/false): ");
        boolean available = Boolean.parseBoolean(scanner.nextLine());

        FoodItem newItem = new FoodItem(name, price, available, category);
        addFoodItem(newItem);

        System.out.println(name + " added to the menu.");
    }

    private void updateExistingItem(Scanner scanner) {
        displayMenu();
        System.out.print("Enter the name of the item you want to update: ");
        String itemName = scanner.nextLine();

        FoodItem itemToUpdate = null;
        for (FoodItem item : foodItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToUpdate = item;
                break;
            }
        }

        if (itemToUpdate == null) {
            System.out.println("Item not found in the menu.");
            return;
        }

        System.out.println("Current details: " + itemToUpdate);
        System.out.println("Press 'n' to update name, 'p' to update price, 'a' to update availability, 'c' to update category.");
        String updateChoice = scanner.nextLine();

        switch (updateChoice) {
            case "n":
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                itemToUpdate.setName(newName);
                break;
            case "p":
                System.out.print("Enter new price: ");
                double newPrice = Double.parseDouble(scanner.nextLine());
                itemToUpdate.setPrice(newPrice);
                break;
            case "a":
                System.out.print("Is the item available? (true/false): ");
                boolean newAvailability = Boolean.parseBoolean(scanner.nextLine());
                itemToUpdate.setAvailability(newAvailability);
                availabilityChange(itemToUpdate);
                break;
            case "c":
                System.out.print("Enter new category: ");
                String newCategory = scanner.nextLine();
                itemToUpdate.setCategory(newCategory);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
        System.out.println("Item updated successfully.");
    }

    private void removeItem(Scanner scanner) {
        displayMenu();
        System.out.print("Enter the name of the item you want to remove: ");
        String itemName = scanner.nextLine();

        FoodItem itemToRemove = null;
        for (FoodItem item : foodItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            itemToRemove.setAvailable(false);

            itemRemoval(itemToRemove);

            for (Order order : Main.orderQueue) {
                if (order.getItems().containsKey(itemToRemove)) {
                    order.setStatus("Denied");
                    System.out.println("Order denied because item " + itemToRemove.getName() + " was removed.");
                }
            }

            foodItems.remove(itemToRemove);
            System.out.println(itemName + " has been removed from the menu.");
        } else {
            System.out.println("Item not found in the menu.");
        }
    }
    private void availabilityChange(FoodItem item) {
        for (Customer customer : Main.getCustomers().values()) {
            customer.getCart().cleanUpCart();
        }
    }


    private void itemRemoval(FoodItem item) {
        for (Customer customer : Main.getCustomers().values()) {
            customer.getCart().removeItem(item);
        }
    }
}