import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuPanel extends JPanel {
    private JTable menuTable;

    public MenuPanel() {
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"Food Item", "Price", "Availability", "Category"}, 0);
        menuTable = new JTable(model);
        populateMenuTable(model);

        add(new JScrollPane(menuTable), BorderLayout.CENTER);
    }
    private void populateMenuTable(DefaultTableModel model) {
        List<FoodItem> foodItems = Main.menu.getFoodItems();
        for (FoodItem item : foodItems) {
            model.addRow(new Object[]{
                    item.getName(),
                    item.getPrice(),
                    item.isAvailable() ? "Available" : "Not Available",
                    item.getCategory()
            });
        }
    }
}