package SAD;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;

public class PharmaSysReports extends JPanel {

    private final Color pageBg = new Color(208,217,232);
    private final Color cardBg = new Color(245,248,252);
    private final Color blue = new Color(25,52,214);
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
    private final Font smallFont = new Font("Segoe UI", Font.PLAIN, 12);

    private final Color tabContainerColor = new Color(238, 242, 248);
    private final Color tabNormal = new Color(226, 232, 240);
    private final Color tabActive = Color.WHITE;
    private final Color tabHover = new Color(232, 238, 248);

    private CardLayout innerCards;
    private JPanel innerContainer;

    public PharmaSysReports() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Outer content wrapper (mimic your Dashboard content spacing)
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(0,0,0,0));

        // --- Header: Title + Subtitle + right-side export ---
        wrapper.add(buildTopControls(), BorderLayout.NORTH);

        // --- Tabs-like segmented control and main body ---
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);

        JPanel tabsWithSpace = new JPanel(new BorderLayout());
        tabsWithSpace.setOpaque(false);
        tabsWithSpace.setBorder(new EmptyBorder(10, 0, 10, 0));  // <-- SPACE BELOW THE TABS
        tabsWithSpace.add(buildSegmentedTabs(), BorderLayout.CENTER);

        center.add(tabsWithSpace, BorderLayout.NORTH);

        // inner container where each "tab page" will be shown
        innerCards = new CardLayout();
        innerContainer = new JPanel(innerCards);
        innerContainer.setOpaque(false);

        innerContainer.add(buildDailySalesPage(), "Daily");
        innerContainer.add(buildMonthlySummaryPage(), "Monthly");
        innerContainer.add(buildTopMedicinePage(), "TopMedicine");
        innerContainer.add(buildCashierPerformancePage(), "Cashier");

        innerCards.show(innerContainer, "Daily"); // default
        center.add(innerContainer, BorderLayout.CENTER);
        wrapper.add(center, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
    }

    private void addPlaceholder(JTextField field, String placeholder) {
    field.setForeground(Color.GRAY);

    // When focus gained: remove placeholder
    field.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (field.getText().equals(placeholder)) {
                field.setText("");
                field.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (field.getText().isEmpty()) {
                field.setForeground(Color.GRAY);
                field.setText(placeholder);
            }
        }
    });
}

    private JPanel buildDateInput(String labelText) {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setBorder(new EmptyBorder(0, 0, 4, 0));

        // Rounded background panel
        JPanel box = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(235, 238, 243));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        box.setPreferredSize(new Dimension(220, 36));
        box.setOpaque(false);

        // Text field
        JTextField field = new JTextField("MM/DD/YYYY");
        field.setBorder(null);
        field.setOpaque(false);
        field.setForeground(Color.GRAY);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBounds(10, 6, 150, 24);

        addPlaceholder(field, "MM/DD/YYYY");

        // Calendar button
        JButton calBtn = new JButton(new ImageIcon("SAD/icons/calendar.png"));
        calBtn.setBounds(180, 5, 24, 24);
        calBtn.setBorder(null);
        calBtn.setContentAreaFilled(false);
        calBtn.setFocusPainted(false);
        calBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        box.add(field);
        box.add(calBtn);

        container.add(label, BorderLayout.NORTH);
        container.add(box, BorderLayout.CENTER);

        return container;
    }

    // ---------------- Top Controls (date filters + export) ----------------
    private JPanel buildTopControls() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 0, 0, 0));

        // LEFT SIDE — title + subtitle + date filters BELOW
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        JLabel t = new JLabel("Reports");
        t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(t);

        JLabel s = new JLabel("Analyze your pharmacy's performance");
        s.setFont(new Font("Segoe UI", Font.BOLD, 12));
        s.setForeground(Color.DARK_GRAY);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(s);

        // Add space
        left.add(Box.createVerticalStrut(4));

        // DATE FILTERS (Start + End)
        JPanel dateRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        dateRow.setBorder(new EmptyBorder(0, 0, 4, 0));
        dateRow.setOpaque(false);
        dateRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        dateRow.add(buildDateInput("Start Date"));
        dateRow.add(Box.createHorizontalStrut(12));  
        dateRow.add(buildDateInput("End Date"));

        left.add(dateRow);

        top.add(left, BorderLayout.WEST);

        // RIGHT SIDE — export + filter buttons
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        right.setOpaque(false);
        right.setBorder(new EmptyBorder(67, 0, 0, 0));

        JButton export = new JButton("Export All Reports");
        stylePrimaryButton(export);

        JButton apply = new JButton("Apply Filter");
        stylePrimaryButton(apply);

        right.add(export);
        right.add(apply);

        top.add(right, BorderLayout.EAST);

        return top;
    }

    private JTextField roundedField(String placeholder, int width) {
        JTextField f = new JTextField(placeholder);

        f.setPreferredSize(new Dimension(width, 30));
        f.setFont(smallFont);
        f.setForeground(Color.GRAY);

        f.setBorder(new CompoundBorder(
                new LineBorder(new Color(210,210,210), 1, true),
                new EmptyBorder(6,10,6,10)
        ));

        f.setBackground(cardBg);

        // ADD THIS: apply placeholder behavior
        addPlaceholder(f, "MM/DD/YYYY");

        return f;
    }

    private void stylePrimaryButton(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.setBorder(new EmptyBorder(10, 18, 10, 18));

        // Custom round background
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(blue);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);

                super.paint(g, c);
            }
        });
    }

    // ---------------- Segmented Tabs (Daily / Monthly / Top / Cashier) ----------------
    private JPanel buildSegmentedTabs() {

        JPanel outer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(tabContainerColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // rounded container
            }
        };

        outer.setOpaque(false);
        outer.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 14)); 
        outer.setBorder(new EmptyBorder(0, 0, 0, 0));  // More padding = bigger container

        JPanel t1 = makeTab("Daily Sales", true);
        JPanel t2 = makeTab("Monthly Summary", false);
        JPanel t3 = makeTab("Top Medicine", false);
        JPanel t4 = makeTab("Cashier Performance", false);

        currentTab = t1;

        outer.add(t1);
        outer.add(t2);
        outer.add(t3);
        outer.add(t4);

        // CLICK HANDLERS
        t1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                setActiveTab(t1);
                innerCards.show(innerContainer, "Daily");
            }
        });

        t2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                setActiveTab(t2);
                innerCards.show(innerContainer, "Monthly");
            }
        });

        t3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                setActiveTab(t3);
                innerCards.show(innerContainer, "TopMedicine");
            }
        });

        t4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                setActiveTab(t4);
                innerCards.show(innerContainer, "Cashier");
            }
        });

        return outer;
    }
    // ---------------- DAILY SALES PAGE ----------------
    private JPanel buildDailySalesPage() {

        JPanel page = new JPanel(new BorderLayout());
        page.setOpaque(false);
        page.setBorder(new EmptyBorder(0,0,0,0));

        // ------------------ METRICS ROW (standalone, NOT inside main white card) ------------------
        JPanel metrics = new JPanel(new GridLayout(1, 3, 16, 0));
        metrics.setOpaque(false);

        metrics.add(statCard("Today's Revenue", "₱7,800", "↑ 12.5% from yesterday"));
        metrics.add(statCard("Transactions", "203", "↑ 8.3% from yesterday"));
        metrics.add(statCard("Average Sale", "₱38.42", "↑ 3.8% from yesterday"));

        page.add(metrics, BorderLayout.NORTH);

        // ------------------ MAIN WHITE ROUNDED CONTAINER (chart only) ------------------
        JPanel roundedCard = createCardedPanel();   // white rounded panel
        roundedCard.setLayout(new BorderLayout());
        roundedCard.setBorder(new EmptyBorder(10,10,10,10));

        roundedCard.add(chartPlaceholder("Hourly Sales Trend (Sales $ vs Transactions)"),
                        BorderLayout.CENTER);

        // Wrapper so chart moves down properly
        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setOpaque(false);
        centerWrap.setBorder(new EmptyBorder(10, 0, 0, 0)); // <-- SPACE HERE
        centerWrap.add(roundedCard, BorderLayout.CENTER);

        page.add(centerWrap, BorderLayout.CENTER);

        return page;
    }

    // ---------------- MONTHLY SUMMARY PAGE ----------------
    private JPanel buildMonthlySummaryPage() {
        JPanel root = new JPanel();
        root.setOpaque(false);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(0,0,0,0));

        // ============================================================
        // CARD 1 — 6-Month Sales & Profit Overview (Chart Section)
        // ============================================================
        JPanel chartCard = createCardedPanel();
        chartCard.setLayout(new BorderLayout());
        chartCard.setBorder(new EmptyBorder(16,16,16,16));

        JLabel chartTitle = new JLabel("6-Month Sales & Profit Overview");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        chartTitle.setBorder(new EmptyBorder(0,0,12,0));

        chartCard.add(chartTitle, BorderLayout.NORTH);
        chartCard.add(chartPlaceholder("6-Month Sales & Profit Overview Chart Area"),
                BorderLayout.CENTER);

        root.add(chartCard);
        root.add(Box.createVerticalStrut(10)); // spacing between cards

        // ============================================================
        // CARD 2 — Monthly Summary Table
        // ============================================================
        JPanel tableCard = createCardedPanel();
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(new EmptyBorder(16,16,16,16));

        JLabel tableTitle = new JLabel("Monthly Summary");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableTitle.setBorder(new EmptyBorder(0,0,12,0));

        tableCard.add(tableTitle, BorderLayout.NORTH);


        // ========== TABLE CONTENT ==========
        String[] cols = {"Month", "Sales", "Profit", "Margin"};
        Object[][] rows = {
                {"January", "$24,500", "$4,900", "20.0%"},
                {"February", "$26,800", "$5,360", "20.0%"},
                {"March", "$29,200", "$5,840", "20.0%"},
                {"April", "$31,500", "$6,300", "20.0%"},
                {"May", "$28,900", "$5,780", "20.0%"},
                {"June", "$33,400", "$6,680", "20.0%"},
        };

        JTable table = new JTable(new DefaultTableModel(rows, cols)) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        // Table styling (same as before)
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 32));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(245, 248, 252));
        header.setForeground(Color.BLACK);

        // Profit green
        DefaultTableCellRenderer greenRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean s, boolean f, int r, int c) {

                Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);

                comp.setForeground(new Color(0, 135, 0));   
                setHorizontalAlignment(JLabel.CENTER);      
                return comp;
            }
        };
        table.getColumnModel().getColumn(2).setCellRenderer(greenRenderer);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(0,0,0,0));
        scroll.getViewport().setBackground(Color.WHITE);

        tableCard.add(scroll, BorderLayout.CENTER);


        root.add(tableCard);

        return root;
    }

    // ---------------- TOP MEDICINE PAGE ----------------
    // ---------------- TOP MEDICINE PAGE ----------------
private JPanel buildTopMedicinePage() {
    JPanel p = createCardedPanel();
    p.setLayout(new BorderLayout());
    p.setBorder(new EmptyBorder(12,12,12,12));

    // TITLE
    JLabel title = new JLabel("Top Selling Medicines (This Month)");
    title.setFont(new Font("Segoe UI", Font.BOLD, 20));
    p.add(title, BorderLayout.NORTH);

    // TABLE DATA
    String[] cols = {"Rank", "Medicine Name", "Units Sold", "Revenue", "Profit Margin"};
    Object[][] rows = {
            {"1", "Paracetamol 500mg", "1,245", "$3,112.50", "45%"},
            {"2", "Ibuprofen 400mg", "856", "$7,276.00", "52%"},
            {"3", "Amoxicillin 250mg", "742", "$2,374.40", "48%"},
            {"4", "Metformin 500mg", "634", "$3,170.00", "50%"},
            {"5", "Vitamin C 1000mg", "589", "$3,828.50", "55%"},
    };

    JTable table = new JTable(new DefaultTableModel(rows, cols)) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };

    // BASIC STYLING
    table.setRowHeight(40);
    table.setFont(new Font("Segoe UI", Font.BOLD, 13));
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));
    table.setFillsViewportHeight(true);

    JTableHeader header = table.getTableHeader();
    header.setPreferredSize(new Dimension(header.getWidth(), 32));
    header.setFont(new Font("Segoe UI", Font.BOLD, 18));
    header.setBackground(new Color(245, 248, 252));
    header.setForeground(Color.BLACK);

    // ------------------- RANK COLUMN -------------------
    DefaultTableCellRenderer rankRenderer = new DefaultTableCellRenderer();
    rankRenderer.setHorizontalAlignment(JLabel.CENTER); // center the numbers
    table.getColumnModel().getColumn(0).setCellRenderer(rankRenderer);

    // CENTER ALL OTHER COLUMNS
    DefaultTableCellRenderer center = new DefaultTableCellRenderer();
    center.setHorizontalAlignment(JLabel.CENTER);
    table.getColumnModel().getColumn(1).setCellRenderer(center); // Medicine Name
    table.getColumnModel().getColumn(2).setCellRenderer(center); // Units Sold
    table.getColumnModel().getColumn(3).setCellRenderer(center); // Revenue

    // PROFIT MARGIN GREEN + CENTER
    DefaultTableCellRenderer marginRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean s, boolean f, int r, int c) {
            JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
            lbl.setForeground(new Color(0, 135, 0));
            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            return lbl;
        }
    };
    table.getColumnModel().getColumn(4).setCellRenderer(marginRenderer);

    // SCROLLPANE
    JScrollPane scroll = new JScrollPane(table);
    scroll.setBorder(new EmptyBorder(12,0,0,0));
    scroll.getViewport().setBackground(Color.WHITE);

    p.add(scroll, BorderLayout.CENTER);
    return p;
}


// Helper for rank circular badge
class RoundedBorder implements Border {

    private int radius;
    RoundedBorder(int r) { radius = r; }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius+1, radius+1, radius+1, radius+1);
    }

    @Override
    public boolean isBorderOpaque() { return false; }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        g.setColor(c.getBackground());
        g.fillOval(x, y, w-1, h-1);
    }
}

    // ---------------- CASHIER PERFORMANCE PAGE ----------------
    private JPanel buildCashierPerformancePage() {

    JPanel p = createCardedPanel();
    p.setLayout(new BorderLayout());
    p.setBorder(new EmptyBorder(12,12,12,12));

    // TITLE — same style as screenshot
    JLabel title = new JLabel("Cashier Performance (This Month)");
    title.setFont(new Font("Segoe UI", Font.BOLD, 20));
    title.setBorder(new EmptyBorder(4, 2, 12, 0));
    p.add(title, BorderLayout.NORTH);

    // TABLE DATA
    String[] cols = {"Cashier Name", "Total Sales", "Transactions", "Avg Sale Value"};
    Object[][] rows = {
            {"Sarah Johnson", "$45,600", "218", "$194.87"},
            {"Michael Chen", "$42,300", "234", "$194.04"},
            {"Emily Davis", "$38,900", "201", "$193.53"},
            {"James Wilson", "$35,200", "189", "$186.24"}
    };

    JTable table = new JTable(new DefaultTableModel(rows, cols)) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };

    // ------------------------------------------------------
    // TABLE STYLING — (matches the screenshot exactly)
    // ------------------------------------------------------
    table.setRowHeight(36);
    table.setFont(new Font("Segoe UI", Font.BOLD, 13));
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));
    table.setFillsViewportHeight(true);
    table.setBackground(Color.WHITE);

    // HEADER STYLING
    JTableHeader header = table.getTableHeader();
    header.setPreferredSize(new Dimension(header.getWidth(), 34));
    header.setFont(new Font("Segoe UI", Font.BOLD, 18));
    header.setBackground(new Color(245, 248, 252));   // soft gray-blue
    header.setForeground(Color.BLACK);
    header.setBorder(new EmptyBorder(6, 10, 6, 10));

    // CENTER align numeric columns
    DefaultTableCellRenderer center = new DefaultTableCellRenderer();
    center.setHorizontalAlignment(JLabel.CENTER);

    table.getColumnModel().getColumn(0).setCellRenderer(center); // Cashier Name
    table.getColumnModel().getColumn(1).setCellRenderer(center); // Total Sales
    table.getColumnModel().getColumn(2).setCellRenderer(center); // Transactions
    table.getColumnModel().getColumn(3).setCellRenderer(center); // Avg Sale Value

    // SCROLLPANE — clean, rounded interior
    JScrollPane scroll = new JScrollPane(table);
    scroll.setBorder(new EmptyBorder(0,0,0,0));
    scroll.getViewport().setBackground(Color.WHITE);
    scroll.setBackground(Color.WHITE);

    p.add(scroll, BorderLayout.CENTER);

    return p;
}

    // ---------------- Utility: stat card, chart placeholder, base card ----------------
    private JPanel statCard(String title, String value, String foot) {

        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(235,241,252));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

                g2.setColor(new Color(210,220,240));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
            }
        };

        card.setOpaque(false);
        card.setBorder(new EmptyBorder(12,12,12,12));

        // -----------------------------------------------------
        // TOP ROW: Title (left) + ↗ icon (right)
        // -----------------------------------------------------
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel icon = new JLabel("↗");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        icon.setForeground(new Color(40, 120, 40)); // green-ish increase color
        icon.setBorder(new EmptyBorder(0,0,0,0));

        topRow.add(lblTitle, BorderLayout.WEST);
        topRow.add(icon, BorderLayout.EAST);

        // -----------------------------------------------------
        // CENTERED VALUE AND FOOTER
        // -----------------------------------------------------
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblFoot = new JLabel(foot, SwingConstants.CENTER);
        lblFoot.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFoot.setForeground(Color.GRAY);
        lblFoot.setBorder(new EmptyBorder(6,0,0,0));
        lblFoot.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(6));
        centerPanel.add(lblValue);
        centerPanel.add(lblFoot);

        // assemble
        card.add(topRow, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel chartPlaceholder(String title) {
        JPanel ch = new JPanel(new BorderLayout());
        ch.setOpaque(true);
        ch.setBackground(Color.WHITE);
        ch.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220), 1, true),
                new EmptyBorder(12,12,12,12)
        ));

        JLabel l = new JLabel(title);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ch.add(l, BorderLayout.NORTH);

        JLabel ph = new JLabel("[ Chart Placeholder ]", SwingConstants.CENTER);
        ph.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        ph.setForeground(Color.GRAY);
        ch.add(ph, BorderLayout.CENTER);

        return ch;
    }

    private JPanel createCardedPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        // create an inner white rounded panel to mimic screenshot rounded card
        JPanel inner = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(new Color(220,220,220));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
            }
        };
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(12,12,12,12));

        p.add(inner, BorderLayout.CENTER);

        // return the white rounded panel for direct content insertion
        return inner;
    }
        private JPanel currentTab = null;

        private void setActiveTab(JPanel newTab) {
            if (currentTab != null) {
                currentTab.setBackground(tabNormal);
                currentTab.repaint();
            }

            newTab.setBackground(tabActive);
            currentTab = newTab;
            newTab.repaint();
        }

    private JPanel makeTab(String name, boolean active) {

    JPanel tab = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
        }
    };

    tab.setLayout(new GridBagLayout());
    tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    tab.setOpaque(false);
    tab.setPreferredSize(new Dimension(200, 36));
    tab.setBorder(new EmptyBorder(6, 10, 6, 10));

    JLabel lbl = new JLabel(name);
    lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
    lbl.setForeground(Color.BLACK);
    tab.add(lbl);

    // Initial background
    tab.setBackground(active ? tabActive : tabNormal);

    // ✔ FIXED HOVER LOGIC
    tab.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            if (tab != currentTab) tab.setBackground(tabHover);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            if (tab != currentTab) tab.setBackground(tabNormal);
        }
    });

    return tab;
}


}
