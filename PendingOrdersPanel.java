import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class PendingOrdersPanel extends JPanel {
    private JTable ordersTable;

    public PendingOrdersPanel() {
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"Customer Name", "Order Summary", "Total", "Status"}, 0);
        ordersTable = new JTable(model);
        populateOrdersTable(model);

        add(new JScrollPane(ordersTable), BorderLayout.CENTER);
    }

    private void populateOrdersTable(DefaultTableModel model) {
        if (Main.orderQueue.isEmpty()) {
            return;
        }

        for (Order order : Main.orderQueue) {
            StringBuilder orderSummary = new StringBuilder();
            for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                orderSummary.append(entry.getKey().getName()).append(" x ").append(entry.getValue()).append(", ");
            }
            String summary = orderSummary.toString();
            if (summary.endsWith(", ")) {
                summary = summary.substring(0, summary.length() - 2);
            }

            String status = order.getStatus();

            model.addRow(new Object[]{
                    order.getCustomerUsername(),
                    summary,
                    order.getTotalPrice() + " rs",
                    status
            });
        }
    }
}