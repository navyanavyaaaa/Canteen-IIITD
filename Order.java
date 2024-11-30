import java.util.Map;

public class Order {
    private String customerUsername;
    private Map<FoodItem, Integer> items;
    private double totalPrice;
    private String status;
    private long timestamp;
    private String specialRequest;

    public Order(String customerUsername, Map<FoodItem, Integer> items, double totalPrice, String specialRequest) {
        this.customerUsername = customerUsername;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = "Pending";
        this.timestamp = System.currentTimeMillis();
        this.specialRequest = specialRequest;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public String getCustomerUsername() {
        return customerUsername;
    }
    public String getSpecialRequest() {
        return specialRequest;
    }
    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
