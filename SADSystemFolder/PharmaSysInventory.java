import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter; // Required for searching
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PharmaSysInventory extends JPanel {

    // Define colors to match your theme
    Color blue = new Color(79,107,201);
    Color bgGray = new Color(208,217,232);

    public PharmaSysInventory(){

        setLayout(new BorderLayout());
        setBackground(bgGray);
        setBorder(new EmptyBorder(15,20,15,20));

        //---------------- HEADER ----------------//
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

        //---------------- CENTER PANEL (Tools + Table) ----------------//
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        //---------------- SEARCH + FILTER BAR ----------------//
        JPanel tools = new JPanel();
        tools.setLayout(new BoxLayout(tools, BoxLayout.X_AXIS));
        tools.setOpaque(false);
        tools.setBorder(new EmptyBorder(0,0,10,5));

        // LEFT SIDE: Search
        String placeholder = "Search medicine inventory...";
        JTextField search = new JTextField(placeholder);
        search.setMaximumSize(new Dimension(438, 35));
        search.setPreferredSize(new Dimension(438, 35));
        search.setForeground(Color.GRAY);
        search.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));

        // RIGHT SIDE: Category + Add Button
        JComboBox<String> category = new JComboBox<>();
        category.setPreferredSize(new Dimension(200, 35));  
        category.setMaximumSize(new Dimension(200, 35));     
        category.addItem("All Categories");
        category.addItem("Antibiotics");
        category.addItem("Analgesics");
        category.addItem("Antidiabetic");
        category.addItem("Antihypertensive");
        category.addItem("Antihistamine");
        category.addItem("Antacids");

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

        //---------------- INVENTORY TABLE ----------------//
        String[] cols = {
                "Medicine ID", "Name", "Generic Name", "Category", 
                "Stock", "Price", "Status", "Expiry Date", "Action"
        };

        Object[][] rows = {
                {"MED001","Amoxicillin 500mg","Amoxicillin","Antibiotics",150,"₱8.50","Good","2026-03-15","✏ 🗑"},
                {"MED002","Paracetamol 500mg","Acetaminophen","Analgesics",300,"₱2.50","Good","2026-06-20","✏ 🗑"},
                {"MED003","Metformin 500mg","Metformin HCl","Antidiabetic",15,"₱5.00","Critical","2026-01-10","✏ 🗑"},
                {"MED004","Losartan 50mg","Losartan Potassium","Antihypertensive",28,"₱12.00","Low","2026-04-25","✏ 🗑"},
                {"MED005","Cetirizine 10mg","Cetirizine","Antihistamine",180,"₱3.50","Good","2025-12-10","✏ 🗑"},
                {"MED006","Omeprazole 20mg","Omeprazole","Antacids",12,"₱6.00","Critical","2026-02-15","✏ 🗑"},
                {"MED007","Ibuprofen 200mg","Ibuprofen","Analgesics",210,"₱6.00","Good","2026-08-15","✏ 🗑"}
        };

        // 1. Create Model explicitly so we can pass it to the sorter
        DefaultTableModel model = new DefaultTableModel(rows, cols);
        JTable table = new JTable(model);

        // 2. Setup Sorter (This makes the search work)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // 3. Search Logic implementation
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = search.getText();
                
                // If text is empty or equals the placeholder, show all rows
                if (text.trim().length() == 0 || text.equals(placeholder)) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) makes it case-insensitive
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Search Placeholder Logic (Visual only)
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
                    sorter.setRowFilter(null); // Reset filter on empty
                }
            }
        });


        // Table Styling
        table.setRowHeight(45);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245,248,252));
        table.getTableHeader().setBorder(new EmptyBorder(10,5,10,5));

        // Center Alignment Renderer
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i=0; i<table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // Custom Renderer for Medicine ID and Status (Finishing your cut-off code)
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                c.setForeground(new Color(25, 52, 214)); // Blue ID
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        // Status Column Renderer (Colors based on text)
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = value.toString();
                
                c.setFont(new Font("Segoe UI", Font.BOLD, 12));
                
                if (status.equalsIgnoreCase("Good")) {
                    c.setForeground(new Color(0, 150, 0)); // Green
                } else if (status.equalsIgnoreCase("Low")) {
                    c.setForeground(new Color(255, 140, 0)); // Orange
                } else if (status.equalsIgnoreCase("Critical")) {
                    c.setForeground(Color.RED); // Red
                } else {
                    c.setForeground(Color.BLACK);
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(new Color(230,230,230)));
        scroll.getViewport().setBackground(Color.WHITE);

        centerPanel.add(scroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }
}
