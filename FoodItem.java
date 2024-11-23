import java.util.Objects;

public class FoodItem { private String name; private double price; private boolean availability; private String category;

    public FoodItem(String name, double price, boolean availability, String category) {
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.category = category;

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return availability;
    }

    public String getCategory() {
        return category;
    }


    // toString method for easy display
    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodItem)) return false;
        FoodItem foodItem = (FoodItem) o;
        return Double.compare(foodItem.price, price) == 0 &&
                availability == foodItem.availability &&
                Objects.equals(name, foodItem.name) &&
                Objects.equals(category, foodItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, availability, category);
    }

    public void setName(String newName) {
        this.name=newName;
    }
    public void setCategory(String newName) {
        this.category=newName;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public void setAvailability(boolean bo){
        this.availability=bo;
    }

    public void setAvailable(boolean b) {
        this.availability=b;
    }
}