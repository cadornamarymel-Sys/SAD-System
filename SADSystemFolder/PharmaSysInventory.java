import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}

public class PharmaSysInventory extends JPanel {

    Color blue = new Color(79, 107, 201);
    Color bgGray = new Color(208, 217, 232);

    private void openAddItemDialog(DefaultTableModel model) {

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField genericField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField expiryField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        panel.add(new JLabel("Medicine ID:"));
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Generic Name:"));
        panel.add(genericField);

        panel.add(new JLabel("Category:"));
        panel.add(categoryField);

        panel.add(new JLabel("Stock:"));
        panel.add(stockField);

        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        panel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        panel.add(expiryField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Add New Medicine Item", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            model.addRow(new Object[]{
                    idField.getText(),
                    nameField.getText(),
                    genericField.getText(),
                    categoryField.getText(),
                    Integer.parseInt(stockField.getText()),
                    priceField.getText(),
                    statusField.getText(),
                    expiryField.getText(),
                    "✏ 🗑"
            });
        }
    }

    public PharmaSysInventory() {

        setLayout(new BorderLayout());
        setBackground(bgGray);
        setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = new JLabel("Inventory Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel subtitle = new JLabel("Manage your medicine stock and inventory");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        header.add(title);
        header.add(subtitle);
        header.add(Box.createVerticalStrut(15));

        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JPanel tools = new JPanel();
        tools.setLayout(new BoxLayout(tools, BoxLayout.X_AXIS));
        tools.setOpaque(false);
        tools.setBorder(new EmptyBorder(0, 0, 10, 5));

        String placeholder = "Search medicine inventory...";
        JTextField search = new JTextField(placeholder);
        search.setMaximumSize(new Dimension(438, 35));
        search.setPreferredSize(new Dimension(438, 35));
        search.setForeground(Color.GRAY);
        search.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JComboBox<String> category = new JComboBox<>();
        category.setPreferredSize(new Dimension(200, 35));
        category.setMaximumSize(new Dimension(200, 35));
        category.addItem("    All Categories");
        category.addItem("    Antibiotics");
        category.addItem("    Analgesics");
        category.addItem("    Antidiabetic");
        category.addItem("    Antihypertensive");
        category.addItem("    Antihistamine");
        category.addItem("    Antacids");

        JButton addBtn = new JButton("+ Add New Item");
        addBtn.setPreferredSize(new Dimension(140, 35));
        addBtn.setMaximumSize(new Dimension(140, 35));
        addBtn.setBackground(new Color(25, 52, 214));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));

        tools.add(search);
        tools.add(Box.createHorizontalGlue());
        tools.add(category);
        tools.add(Box.createHorizontalStrut(15));
        tools.add(addBtn);

        centerPanel.add(tools, BorderLayout.NORTH);

        String[] cols = {
                "Medicine ID", "Name", "Generic Name", "Category",
                "Stock", "Price", "Status", "Expiry Date", "Action"
        };

        Object[][] rows = {
                {"MED001", "Amoxicillin 500mg", "Amoxicillin", "Antibiotics", 150, "₱8.50", "Good", "2026-03-15", "✏ 🗑"},
                {"MED002", "Paracetamol 500mg", "Acetaminophen", "Analgesics", 300, "₱2.50", "Good", "2026-06-20", "✏ 🗑"},
                {"MED003", "Metformin 500mg", "Metformin HCl", "Antidiabetic", 15, "₱5.00", "Critical", "2026-01-10", "✏ 🗑"},
                {"MED004", "Losartan 50mg", "Losartan Potassium", "Antihypertensive", 28, "₱12.00", "Low", "2026-04-25", "✏ 🗑"},
                {"MED005", "Cetirizine 10mg", "Cetirizine", "Antihistamine", 180, "₱3.50", "Good", "2025-12-10", "✏ 🗑"},
                {"MED006", "Omeprazole 20mg", "Omeprazole", "Antacids", 12, "₱6.00", "Critical", "2026-02-15", "✏ 🗑"},
                {"MED007", "Ibuprofen 200mg", "Ibuprofen", "Analgesics", 210, "₱6.00", "Good", "2026-08-15", "✏ 🗑"}
        };

        DefaultTableModel model = new DefaultTableModel(rows, cols);
        JTable table = new JTable(model);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = search.getText();
                if (text.trim().isEmpty() || text.equals(placeholder)) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (search.getText().equals(placeholder)) {
                    search.setText("");
                    search.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (search.getText().trim().isEmpty()) {
                    search.setText(placeholder);
                    search.setForeground(Color.GRAY);
                    sorter.setRowFilter(null);
                }
            }
        });

        addBtn.addActionListener(e -> openAddItemDialog(model));

        table.setRowHeight(65);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                c.setForeground(new Color(25, 52, 214));
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = value.toString();

                c.setFont(new Font("Segoe UI", Font.BOLD, 12));

                if (status.equalsIgnoreCase("Good"))
                    c.setForeground(new Color(0, 150, 0));
                else if (status.equalsIgnoreCase("Low"))
                    c.setForeground(new Color(255, 140, 0));
                else if (status.equalsIgnoreCase("Critical"))
                    c.setForeground(Color.RED);
                else
                    c.setForeground(Color.BLACK);

                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        RoundedPanel tableContainer = new RoundedPanel(15);
        tableContainer.setLayout(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
        tableContainer.add(scroll, BorderLayout.CENTER);

        centerPanel.add(tableContainer, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }
}
