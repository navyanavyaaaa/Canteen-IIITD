import javax.swing.*;
import java.awt.*;

public class GUI_screen extends JFrame {
    private JTabbedPane tabbedPane;

    public GUI_screen() {
        setTitle("Canteen System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        MenuPanel menuPanel = new MenuPanel();
        tabbedPane.addTab("Menu", menuPanel);

        PendingOrdersPanel pendingOrdersPanel = new PendingOrdersPanel();
        tabbedPane.addTab("Pending Orders", pendingOrdersPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to CLI");
        backButton.addActionListener(e -> {
            dispose();
            Main.showCLI();
        });
        add(backButton, BorderLayout.SOUTH);
    }


}