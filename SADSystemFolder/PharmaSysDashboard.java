package SAD;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PharmaSysDashboard extends JFrame {

    Color blue = new Color(79,107,201);
    Color bgGray = new Color(208,217,232);
    Color cardGray = new Color(236,243,249);
    Font titleFont = new Font("Segoe UI", Font.BOLD, 15);

    private JPanel activeTab = null;
    private final Color activeColor = new Color(92,120,224);
    private final Color normalColor = blue;

    private JPanel pageContainer;
    private CardLayout cardLayout;

    private JLabel dashboardLbl;   // to change page title


    public PharmaSysDashboard() {

        setTitle("PharmaSys - Dashboard");
        setSize(1350,760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgGray);

        //------------------------------ SIDEBAR ------------------------------//
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250,0));
        sidebar.setBackground(blue);
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20,5,15,15));

        JLabel logo = new JLabel("Admin");
        logo.setFont(new Font("Segoe UI",Font.BOLD,16));
        logo.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Admin/Pharmasist");
        subtitle.setFont(new Font("Segoe UI",Font.PLAIN,11));
        subtitle.setForeground(Color.BLACK);

        sidebar.add(logo);
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.setPreferredSize(new Dimension(200,30));

        sidebar.add(sideBtn("Dashboard",true));
        sidebar.add(sideBtn("Inventory",false));
        sidebar.add(sideBtn("Sales",false));
        sidebar.add(sideBtn("Reports",false));
        sidebar.add(sideBtn("Settings",false));

        // ✅ REAL LOG OUT BUTTON
        JPanel logoutPanel = sideBtn("Log out", false);
        sidebar.add(logoutPanel);

        logoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            new PharmaSysDashboard().setVisible(true);
            dispose();
        }
    });
        add(sidebar,BorderLayout.WEST);

        //------------------------------ TOP BAR ------------------------------//
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(blue);
        topBar.setBorder(new EmptyBorder(10,15,10,15));


        // ✅ LEFT SIDE: Logo + Search
        JPanel leftBar = new JPanel(new FlowLayout(FlowLayout.LEFT,17,0));
        leftBar.setOpaque(false);

        // Logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));

        JLabel topLogo = new JLabel("    PharmaSys");
        topLogo.setFont(new Font("Segoe UI", Font.BOLD, 23));
        topLogo.setForeground(Color.WHITE);

        JLabel topSub = new JLabel("        Pharmacy Management");
        topSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        topSub.setForeground(Color.BLACK);

        logoPanel.add(topLogo);
        logoPanel.add(topSub);

        // Search bar — GUARANTEED WORKING PLACEHOLDER ✅
        JTextField search = new JTextField();
        search.setBorder(new EmptyBorder(5,20,5,10));
        search.setPreferredSize(new Dimension(450,30));

        String placeholder = "Search for anything here...";
        search.setText(placeholder);
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

        // Add both to left of top bar
        leftBar.add(logoPanel);
        leftBar.add(search);


        // ✅ RIGHT SIDE
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        rightPanel.add(new JLabel("English (US)"));

        JLabel greeting = new JLabel("  Good Morning      03 November 2025 | 11:45:04   ");
        greeting.setForeground(Color.WHITE);

        rightPanel.add(greeting);


        // Apply to topBar
        topBar.add(leftBar, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);



        //------------------------------ CONTENT ------------------------------//
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(bgGray);
        content.setBorder(new EmptyBorder(15,15,15,15));
        add(content,BorderLayout.CENTER);

        JLabel dashboardLbl = new JLabel("Dashboard");
        dashboardLbl.setFont(new Font("Segoe UI",Font.BOLD,20));

        JLabel subtitle2 = new JLabel(
                "Welcome back! Here's what's happening with your pharmacy today.");

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header,BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.add(dashboardLbl);
        header.add(subtitle2);

        content.add(header,BorderLayout.NORTH);


        //------------------------------ STAT CARDS ------------------------------//
        JPanel statRow = new JPanel(new GridLayout(1,4,15,15));
        statRow.setOpaque(false);
        statRow.setBorder(new EmptyBorder(15,0,15,0));

        statRow.add(statCard("Total Sales","₱245,680","+12.5%"));
        statRow.add(statCard("Total Medicine","1,234","+5.2%"));
        statRow.add(statCard("Transactions Today","156","+8.3%"));
        statRow.add(statCard("Low Stock Items","23","-3 from yesterday"));


        //------------------------------ MID PANELS ------------------------------//
        JPanel midRow = new JPanel(new GridLayout(1,2,20,20));
        midRow.setOpaque(false);

        JPanel expiring = listPanel("Expiring Soon",
                new String[][]{
                        {"Amoxicillin 500mg","Stock: 150","17 days left"},
                        {"Paracetamol 500mg","Stock: 300","17 days left"},
                        {"Ibuprofen 200mg","Stock: 200","33 days left"},
                        {"Cetirizine 10mg","Stock: 200","42 days left"}
                });

        JPanel lowStock = listPanel("Low Stock Alert",
                new String[][]{
                        {"Metformin 500mg","Critical","30%"},
                        {"Losartan 50mg","Low","56%"},
                        {"Atorvastatin 20mg","Low","70%"},
                        {"Omeprazole 20mg","Critical","24%"}
                });

        midRow.add(expiring);
        midRow.add(lowStock);


        //------------------------------ TABLE ------------------------------//
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);

        // card spacing like screenshot
        tableCard.setBorder(new EmptyBorder(15,15,15,15));

        // title
        JLabel tTitle = new JLabel("Recent Transactions");
        tTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tTitle.setBorder(new EmptyBorder(0,0,10,0));
        tableCard.add(tTitle, BorderLayout.NORTH);

        // table setup
        String[] cols = {"Transaction ID","Customer","Items","Amount","Time"};

        Object[][] rows = {
                {"TXN-1234","Juan Dela Cruz",3,"₱1,250","10:30 AM"},
                {"TXN-1235","Maria Santos",2,"₱850","10:15 AM"},
                {"TXN-1236","Pedro Garcia",5,"₱2,100","09:45 AM"},
                {"TXN-1237","Ana Reyes",1,"₱450","09:30 AM"},
        };

        JTable table = new JTable(new DefaultTableModel(rows, cols));

        // table styling like design
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Transaction ID
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Customer
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Items
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Amount
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Time

        // header styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245,248,252));
        table.getTableHeader().setBorder(new EmptyBorder(5,5,5,5));

        // scroll container
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(new Color(220,220,220)));

        tableCard.add(scroll, BorderLayout.CENTER);



        //------------------------------ BODY STACK ------------------------------//
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));

        body.add(statRow);
        body.add(midRow);
        body.add(Box.createVerticalStrut(20));
        body.add(tableCard);

        content.add(body,BorderLayout.CENTER);
    }

        private JPanel sideBtn(String name, boolean active){

        final boolean[] pressed = { false };

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT)){
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);

                // ✅ pressed movement effect
                int offset = pressed[0] ? 2 : 0;

                g2.setColor(getBackground());
                g2.fillRoundRect(
                        0,
                        offset,
                        getWidth(),
                        getHeight() - offset,
                        10,
                        10
                );

                super.paintComponent(g2);
                g2.dispose();
            }
        };

        p.setMaximumSize(new Dimension(320,30));
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.setOpaque(false);
        p.setBackground(active ? bgGray : normalColor);

        JLabel lbl = new JLabel(name);
        lbl.setFont(new Font("Segoe UI",Font.BOLD,13));
        lbl.setForeground(Color.BLACK);

        p.add(lbl);

        // Default active button
        if(active){
            activeTab = p;
        }

        // Cursor
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ✅ Mouse interactions like LOGIN button
        p.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Temporary highlight on hover
                if (p != activeTab) {
                    p.setBackground(activeColor);
                    p.repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Remove highlight if not the selected tab
                if (p != activeTab) {
                    p.setBackground(normalColor);
                    p.repaint();
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                pressed[0] = true;
                p.repaint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                pressed[0] = false;
                p.repaint();
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                // ✅ Clear old active tab highlight
                if (activeTab != null) {
                    activeTab.setBackground(normalColor);
                    activeTab.repaint();
                }

                // ✅ Set clicked tab as permanent active
                p.setBackground(bgGray);
                activeTab = p;

                p.repaint();
            }

        });

        return p;
    }

    private JPanel statCard(String title,String value,String perc){

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(cardGray);
        p.setBorder(new EmptyBorder(12,15,12,15));

        // ------- TOP ROW (ICON + TREND) -------
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JLabel icon = new JLabel("▢");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        icon.setForeground(blue);

        JLabel trend = new JLabel("↗");
        trend.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        trend.setForeground(blue);

        topRow.add(icon);
        topRow.add(trend, BorderLayout.EAST);

        // ---------- TEXT ----------
        JLabel t  = new JLabel(title, SwingConstants.CENTER);
        JLabel v  = new JLabel(value, SwingConstants.CENTER);
        JLabel pc = new JLabel(perc, SwingConstants.RIGHT);

        t.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        t.setForeground(Color.DARK_GRAY);

        // BIG centered value like screenshot
        v.setFont(new Font("Segoe UI", Font.BOLD, 12));
        v.setForeground(Color.BLACK);

        // smaller right-aligned percentage
        pc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        pc.setForeground(Color.GRAY);

        // ---------- VALUE + PERCENT ROW ----------
        JPanel valueRow = new JPanel(new GridBagLayout());
        valueRow.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CENTER VALUE
        gbc.gridx = 0;
        gbc.weightx = 1;
        v.setHorizontalAlignment(SwingConstants.CENTER);
        valueRow.add(v, gbc);

        // RIGHT PERCENT
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        valueRow.add(pc, gbc);

        // FORCE FULL WIDTH ALIGNMENT
        valueRow.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, v.getPreferredSize().height)
        );
        valueRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---------- CENTER STACK ----------
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        valueRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(t);
        center.add(Box.createVerticalStrut(5));
        center.add(Box.createHorizontalStrut(50));
        center.add(valueRow);

        // ---------- APPLY ----------
        p.add(topRow, BorderLayout.NORTH);
        p.add(center, BorderLayout.CENTER);

        return p;
    }



    private JPanel listPanel(String title, String[][] rows) {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new LineBorder(new Color(200,200,200)));

        JLabel t = new JLabel(title);
        t.setFont(titleFont);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setBorder(
            BorderFactory.createCompoundBorder(
                new EmptyBorder(10,10,15,10),   // increase bottom spacing → underline moves DOWN (farther)
                BorderFactory.createMatteBorder(0,0,1,0,new Color(120,120,120))
            )
        );

        p.add(t,BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list,BoxLayout.Y_AXIS));
        list.setOpaque(false);
        list.setBorder(new EmptyBorder(5,10,10,10));

        for(String[] r : rows){

            // outer 2-column row
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);

            // LEFT STACK (name + stock/batch info)
            JPanel leftCol = new JPanel();
            leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
            leftCol.setOpaque(false);

            JLabel name = new JLabel(r[0]);
            name.setFont(new Font("Segoe UI", Font.BOLD, 13));

            JLabel detail = new JLabel(r[1]);
            detail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            detail.setForeground(Color.DARK_GRAY);

            leftCol.add(name);
            leftCol.add(detail);

            // RIGHT STACK (status/percent or days)
            JPanel rightCol = new JPanel();
            rightCol.setLayout(new BoxLayout(rightCol, BoxLayout.Y_AXIS));
            rightCol.setOpaque(false);

            JLabel stat = new JLabel(r[2], SwingConstants.RIGHT);
            stat.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            stat.setAlignmentX(Component.RIGHT_ALIGNMENT);

            rightCol.add(stat);

            // position columns
            row.add(leftCol, BorderLayout.WEST);
            row.add(rightCol, BorderLayout.EAST);

            list.add(row);
            list.add(Box.createVerticalStrut(10));
        }


        p.add(list,BorderLayout.CENTER);
        return p;
    }

    //-----------------------------------------------------------//

    public static void main(String[] args) {
        new PharmaSysDashboard().setVisible(true);
    }
}
