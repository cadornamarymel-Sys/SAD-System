package SAD;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PharmaSysInventory extends JPanel {

    public PharmaSysInventory(){

        setLayout(new BorderLayout());
        setBackground(new Color(208,217,232));
        setBorder(new EmptyBorder(0,0,0,0));

        //---------------- HEADER (INVENTORY FRAME ONLY) ----------------//
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = new JLabel("Inventory Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel subtitle = new JLabel("Manage your medicine stock and inventory");
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 12));

        header.add(title);
        header.add(subtitle);
        header.add(Box.createVerticalStrut(15));

        //---------------- SEARCH + FILTER BAR ----------------//
        JPanel tools = new JPanel();
        tools.setLayout(new BoxLayout(tools, BoxLayout.X_AXIS));
        tools.setOpaque(false);
        tools.setBorder(new EmptyBorder(0,0,10,5)); // match tableCard's 10px padding

        // LEFT SIDE: Search
        String placeholder = "  Search medicine inventory...";
        JTextField search = new JTextField(placeholder);
        search.setMaximumSize(new Dimension(438, 30));
        search.setPreferredSize(new Dimension(438, 30));
        search.setForeground(Color.GRAY);

        // Remove placeholder only when user types
                search.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyPressed(java.awt.event.KeyEvent e) {
                        if (search.getText().equals(placeholder)) {
                            search.setText("");
                            search.setForeground(Color.BLACK);
                        }
                    }
                });

                // GLOBAL click detector → restores placeholder when clicking ANYWHERE else
                Toolkit.getDefaultToolkit().addAWTEventListener(event -> {

                    if (!(event instanceof java.awt.event.MouseEvent)) return;

                    java.awt.event.MouseEvent me = (java.awt.event.MouseEvent) event;

                    // Only react on real click
                    if (me.getID() != java.awt.event.MouseEvent.MOUSE_PRESSED) return;

                    // Ignore clicks inside the search box itself
                    if (SwingUtilities.isDescendingFrom(me.getComponent(), search)) return;

                    // Restore placeholder when clicking elsewhere
                    if (search.getText().trim().isEmpty()) {
                        search.setText(placeholder);
                        search.setForeground(Color.GRAY);
                    }

                }, AWTEvent.MOUSE_EVENT_MASK);

        // RIGHT SIDE: Category + Add Button
        JComboBox<String> category = new JComboBox<>();
        category.setPreferredSize(new Dimension(250, 30));  
        category.setMaximumSize(new Dimension(180, 30));    
        category.addItem("     All Categories");
        category.addItem("     Antibiotics");
        category.addItem("     Analgesics");
        category.addItem("     Antidiabetic");
        category.addItem("     Antihypertensive");
        category.addItem("     Antihistamine");
        category.addItem("     Antacids");

        JButton addBtn = new JButton("+ Add New Item");
        addBtn.setPreferredSize(new Dimension(130, 30));
        addBtn.setMaximumSize(new Dimension(130, 30));
        addBtn.setBackground(new Color(25, 52, 214));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        // ADD COMPONENTS IN PROPER STRETCHING ORDER
        tools.add(search);
        tools.add(Box.createHorizontalGlue()); // pushes next items to the right
        tools.add(category);
        tools.add(Box.createHorizontalStrut(30));
        tools.add(addBtn);

        //---------------- INVENTORY TABLE ----------------//
        String[] cols = {
                "Medicine ID",
                "Name",
                "Generic Name",
                "Category",
                "Stock",
                "Price",
                "Status",
                "Expiry Date",
                "Action"
        };

        Object[][] rows = {
                {"MED001","Amoxicillin 500mg","Amoxicillin","Antibiotics",150,"₱8.50","Good","2026-03-15","✏ 🗑"},
                {"MED002","Paracetamol 500mg","Acetaminophen","Analgesics",300,"₱2.50","Good","2026-06-20","✏ 🗑"},
                {"MED003","Metformin 500mg","Metformin HCl","Antidiabetic",15,"₱5.00","Critical","2026-01-10","✏ 🗑"},
                {"MED004","Losartan 50mg","Losartan Potassium","Antihypertensive",28,"₱12.00","Low","2026-04-25","✏ 🗑"},
                {"MED005","Cetirizine 10mg","Cetirizine","Antihistamine",180,"₱3.50","Good","2025-12-10","✏ 🗑"},
                {"MED006","Omeprazole 20mg","Omeprazole","Antacids",12,"₱6.00","Critical","2026-02-15","✏ 🗑"}
        };

        JTable table = new JTable(new DefaultTableModel(rows, cols));
        table.setRowHeight(80);
        table.setShowGrid(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // Medicine ID column = blue text
        DefaultTableCellRenderer medicineIdRenderer = new DefaultTableCellRenderer();
        medicineIdRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        medicineIdRenderer.setForeground(new Color(25, 52, 214)); // blue

        // Status column = colored depending on value
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String status = value.toString();

                switch (status) {
                    case "Good":
                        c.setForeground(new Color(0, 128, 0)); // green
                        break;
                    case "Low":
                        c.setForeground(new Color(255, 140, 0)); // orange
                        break;
                    case "Critical":
                        c.setForeground(Color.RED); // red
                        break;
                    default:
                        c.setForeground(Color.BLACK);
                }

                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };

        // Apply renderers to specific columns
        table.getColumnModel().getColumn(0).setCellRenderer(medicineIdRenderer); // Medicine ID column
        table.getColumnModel().getColumn(6).setCellRenderer(statusRenderer);     // Status column


        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(245,248,252));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(new Color(220,220,220)));

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(10,10,10,10));
        tableCard.add(scroll, BorderLayout.CENTER);

        //---------------- BODY STACK ----------------//
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(0, 0, 0, 0));

        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        tools.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        body.add(header);
        body.add(tools);
        body.add(Box.createVerticalStrut(10));
        body.add(tableCard);

        add(body, BorderLayout.CENTER);
    }
}

