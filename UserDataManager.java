import java.io.*;
import java.util.*;

public class UserDataManager {
    private static final String CUSTOMERS_FILE = "src/main/resources/signed_up_customers.txt";
    private static final String ADMINS_FILE = "src/main/resources/signed_up_admins.txt";
    public static final String ORDER_HISTORY_FILE = "src/main/resources/order_history.txt";

    public static void saveCustomerData(String username, String password, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE, true))) {
            writer.write(username + "," + password + "," + name);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving customer data: " + e.getMessage());
        }
    }

    public static boolean customerExists(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && (password == null || parts[1].equals(password))) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customer data: " + e.getMessage());
        }
        return false;
    }

    public static void saveAdminData(String username, String password, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMINS_FILE, true))) {
            writer.write(username + "," + password + "," + name);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving admin data: " + e.getMessage());
        }
    }

    public static boolean adminExists(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMINS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && (password == null || parts[1].equals(password))) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading admin data: " + e.getMessage());
        }
        return false;
    }

    public static void saveOrderHistory(String customerUsername, Map<FoodItem, Integer> items, double totalPrice) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_HISTORY_FILE, true))) {
            StringBuilder orderDetails = new StringBuilder(customerUsername + ": ");
            for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
                orderDetails.append(entry.getKey().getName())
                        .append(" (")
                        .append(entry.getValue())
                        .append("), ");
            }
            orderDetails.append("Total Price: ").append(totalPrice).append(" rs");

            writer.write(orderDetails.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving order history: " + e.getMessage());
        }
    }

    public static void initializeFiles() {
        try {
            File customersFile = new File(CUSTOMERS_FILE);
            File adminsFile = new File(ADMINS_FILE);
            File orderHistoryFile = new File(ORDER_HISTORY_FILE);

            customersFile.getParentFile().mkdirs();
            adminsFile.getParentFile().mkdirs();
            orderHistoryFile.getParentFile().mkdirs();

            customersFile.createNewFile();
            adminsFile.createNewFile();
            orderHistoryFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Error initializing files: " + e.getMessage());
        }
    }
}