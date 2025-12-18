import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
    private JLabel dashboardLbl;
    
    private final String[] PAGE_NAMES = {"Dashboard", "Inventory", "Sales", "Reports", "Settings"};

    public PharmaSysDashboard() {

        setTitle("PharmaSys - Dashboard");
        setSize(1350,760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgGray);

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(205,0));
        sidebar.setBackground(blue);
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20,15,15,15));

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
        adminPanel.setOpaque(false);

        adminPanel.setBorder(new EmptyBorder(0, 0, 0, 20)); 

        ImageIcon adminRaw = new ImageIcon(getClass().getResource("/img/admin.png"));
        Image adminScaled = adminRaw.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        ImageIcon adminIcon = new ImageIcon(adminScaled);

        JLabel adminIconLbl = new JLabel(adminIcon);
        adminIconLbl.setBorder(new EmptyBorder(0, 0, 0, 10)); 

        JPanel adminTextBlock = new JPanel();
        adminTextBlock.setLayout(new BoxLayout(adminTextBlock, BoxLayout.Y_AXIS));
        adminTextBlock.setOpaque(false);

        JLabel logo = new JLabel("Admin");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Admin/Pharmacist");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitle.setForeground(Color.BLACK);

        adminTextBlock.add(logo);
        adminTextBlock.add(subtitle);

        JPanel adminRow = new JPanel();
        adminRow.setLayout(new BoxLayout(adminRow, BoxLayout.X_AXIS));
        adminRow.setOpaque(false);

        adminRow.add(Box.createRigidArea(new Dimension(0, 0)));

        adminRow.add(adminIconLbl);

        adminRow.add(Box.createRigidArea(new Dimension(5, 0)));

        adminRow.add(adminTextBlock);

        adminPanel.add(adminRow);

        sidebar.add(adminPanel);
        sidebar.add(Box.createVerticalStrut(50));
        
        JPanel dashboardButton = sideBtn(" Dashboard", false, "/img/Icon (1).png");
        JPanel inventoryButton = sideBtn(" Inventory", false, "/img/Icon (2).png");
        JPanel salesButton = sideBtn(" Sales", false, "/img/Icon (3).png");
        JPanel reportsButton = sideBtn(" Reports", false, "/img/Icon (4).png");
        JPanel settingsButton = sideBtn(" Settings", false, "/img/Icon (5).png");
        JPanel logoutPanel = sideBtn(" Log out", false, "/img/Icon (6).png");

        
        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(inventoryButton);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(salesButton);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(reportsButton);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(settingsButton);
        sidebar.add(Box.createVerticalStrut(15));

        sidebar.add(logoutPanel);

        logoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new PharmaSysLogin().setVisible(true); 
                dispose(); 
            }
        });

        add(sidebar,BorderLayout.WEST);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(blue);
        topBar.setBorder(new EmptyBorder(10,15,10,15));

        JPanel leftBar = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        leftBar.setOpaque(false);
        leftBar.setBorder(new EmptyBorder(0,-5,0,0));

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.X_AXIS));

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/img/Dashboard (1).jpg"));
        Image scaledImg = rawIcon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImg);

        JLabel logoImg = new JLabel(logoIcon);

        JPanel textBlock = new JPanel();
        textBlock.setOpaque(false);
        textBlock.setLayout(new BoxLayout(textBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(" PharmaSys");
        title.setFont(new Font("Segoe UI", Font.BOLD, 23));
        title.setForeground(Color.WHITE);

        JLabel subtitleTxt = new JLabel("  Pharmacy Management");
        subtitleTxt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleTxt.setForeground(Color.BLACK);

        textBlock.add(title);
        textBlock.add(subtitleTxt);

        logoPanel.add(logoImg);
        logoPanel.add(Box.createHorizontalStrut(0)); 
        logoPanel.add(textBlock);
        
        JPanel searchContainer = new RoundedPanel(20);
        searchContainer.setLayout(new BorderLayout());
        searchContainer.setBackground(Color.WHITE);
        searchContainer.setPreferredSize(new Dimension(550, 40)); 

        JTextField search = new JTextField();
        search.setBorder(new EmptyBorder(5,15,5,10));
        search.setPreferredSize(new Dimension(480,40)); 

        String placeholder = "Search for anything here...";
        search.setText(placeholder);
        search.setForeground(Color.GRAY);
        
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(92,120,224)); 
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.setBorder(new EmptyBorder(0, 10, 0, 10)); 

        searchContainer.add(search, BorderLayout.CENTER);
        searchContainer.add(searchBtn, BorderLayout.EAST);
        
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (search.getText().equals(placeholder)) {
                    search.setText("");
                    search.setForeground(Color.BLACK);
                }
            }
        });

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {

            if (!(event instanceof java.awt.event.MouseEvent)) return;

            java.awt.event.MouseEvent me = (java.awt.event.MouseEvent) event;

            if (me.getID() != java.awt.event.MouseEvent.MOUSE_PRESSED) return;

            if (SwingUtilities.isDescendingFrom(me.getComponent(), searchContainer)) return;

            if (search.getText().trim().isEmpty()) {
                search.setText(placeholder);
                search.setForeground(Color.GRAY);
            }

        }, AWTEvent.MOUSE_EVENT_MASK);

        searchBtn.addActionListener(e -> {
            String searchTerm = search.getText().trim();
            if (searchTerm.equalsIgnoreCase(placeholder) || searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Search Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean pageFound = false;
            for (String page : PAGE_NAMES) {
                if (page.equalsIgnoreCase(searchTerm)) {
                    cardLayout.show(pageContainer, page);
                    dashboardLbl.setText(page);
                    
                    resetSideButtonHighlight(dashboardButton, inventoryButton, salesButton, reportsButton, settingsButton, logoutPanel);
                    
                    JPanel targetPanel = getPanelByName(page, dashboardButton, inventoryButton, salesButton, reportsButton, settingsButton, logoutPanel);
                    if (targetPanel != null) {
                        if (activeTab != null) {
                            activeTab.setBackground(normalColor);
                            activeTab.repaint();
                        }
                        targetPanel.setBackground(bgGray);
                        activeTab = targetPanel;
                        targetPanel.repaint();
                    }
                    
                    pageFound = true;
                    break; 
                }
            }

            if (!pageFound) {
                 JOptionPane.showMessageDialog(this, "No page found for: " + searchTerm, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        leftBar.add(logoPanel);
        leftBar.add(Box.createHorizontalStrut(13)); 
        leftBar.add(searchContainer);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        rightPanel.add(new JLabel("English (US)"));

        JLabel greeting = new JLabel("  Good Morning     03 November 2025 | 11:45:04  ");
        greeting.setForeground(Color.WHITE);

        rightPanel.add(greeting);

        topBar.add(leftBar, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        cardLayout = new CardLayout();
        pageContainer = new JPanel(cardLayout);
        pageContainer.setOpaque(false);

        content.setBackground(bgGray);
        content.setBorder(new EmptyBorder(15,15,15,15));
        add(content,BorderLayout.CENTER);

        dashboardLbl = new JLabel("Dashboard");
        dashboardLbl.setFont(new Font("Segoe UI",Font.BOLD,20));

        JLabel subtitle2 = new JLabel(
                "Welcome back! Here's what's happening with your pharmacy today."
        );

        JPanel leftHeader = new JPanel();
        leftHeader.setLayout(new BoxLayout(leftHeader, BoxLayout.Y_AXIS));
        leftHeader.setOpaque(false);

        dashboardLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle2.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftHeader.add(dashboardLbl);
        leftHeader.add(subtitle2);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        dashboardLbl.getPreferredSize().height
                                + subtitle2.getPreferredSize().height + 5
                )
        );

        header.add(leftHeader, BorderLayout.WEST);

        JPanel statRow = new JPanel(new GridLayout(1,4,10,15));
        statRow.setOpaque(false);
        statRow.setBorder(new EmptyBorder(10,0,5,0));

        statRow.add(statCard("Total Sales","₱245,680","+12.5%"));
        statRow.add(statCard("Total Medicine","1,234","+5.2%"));
        statRow.add(statCard("Transactions Today","156","+8.3%"));
        statRow.add(statCard("Low Stock Items","23","-3 from yesterday"));

        JPanel midRow = new JPanel();
        midRow.setOpaque(false);
        midRow.setLayout(new BoxLayout(midRow, BoxLayout.X_AXIS));

        JPanel expiringSoonPanel = listPanelExpiring(
                "Expiring Soon",
                new String[][]{
                        {"Amoxicillin 500mg", "Batch: BT001", "17 days left", "Stock: 150"},
                        {"Paracetamol 500mg", "Batch: BT045", "17 days left", "Stock: 300"},
                        {"Ibuprofen 200mg", "Batch: BT078", "33 days left", "Stock: 200"},
                        {"Cetirizine 10mg", "Batch: BT092", "42 days left", "Stock: 200"}
                }
        );

        expiringSoonPanel.setPreferredSize(new Dimension(500, 310));

        JPanel lowStockPanel = listPanelLowStock(
                "Low Stock Alert",
                new String[][]{
                        {"Metformin 500mg", "Batch: BT015", "Critical", "30%"},
                        {"Losartan 50mg", "Batch: BT028", "Low", "56%"},
                        {"Atorvastatin 20mg", "Batch: BT035", "Low", "70%"},
                        {"Omeprazole 20mg", "Batch: BT012", "Critical", "24%"}
                }
        );

        lowStockPanel.setPreferredSize(new Dimension(500, 310));


        JPanel leftWrap = new JPanel(new BorderLayout());
        leftWrap.setOpaque(false);
        leftWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 310));
        leftWrap.add(expiringSoonPanel);

        JPanel rightWrap = new JPanel(new BorderLayout());
        rightWrap.setOpaque(false);
        rightWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 310));
        rightWrap.add(lowStockPanel);

        midRow.add(leftWrap);
        midRow.add(Box.createHorizontalStrut(20));
        midRow.add(rightWrap);

        JPanel tableCard = new RoundedPanel(20);
        tableCard.setBackground(Color.WHITE);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(new EmptyBorder(8,15,15,15));

        tableCard.setBorder(new EmptyBorder(8,15,15,15));
        tableCard.setPreferredSize(new Dimension(1000, 350));
        tableCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));

        JLabel tTitle = new JLabel("Recent Transactions");
        tTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tTitle.setBorder(new EmptyBorder(0,0,10,0));
        tableCard.add(tTitle, BorderLayout.NORTH);

        String[] cols = {"Transaction ID","Customer","Items","Amount","Time"};

        Object[][] rows = {
                {"TXN-1234","Juan Dela Cruz",3,"₱1,250","10:30 AM"},
                {"TXN-1235","Maria Santos",2,"₱850","10:15 AM"},
                {"TXN-1236","Pedro Garcia",5,"₱2,100","09:45 AM"},
                {"TXN-1237","Ana Reyes",1,"₱450","09:30 AM"},
        };

        JTable table = new JTable(new DefaultTableModel(rows, cols));

        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); 

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245,248,252));
        table.getTableHeader().setBorder(new EmptyBorder(5,5,5,5));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(new Color(220,220,220)));

        tableCard.add(scroll, BorderLayout.CENTER);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
        body.add(header);

        body.add(statRow);
        body.add(midRow);
        body.add(Box.createVerticalStrut(6));
        tableCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        body.add(tableCard);


        pageContainer.add(body, "Dashboard");
        content.add(pageContainer, BorderLayout.CENTER);

        pageContainer.add(new PharmaSysInventory(), "Inventory");
        pageContainer.add(new PharmaSysSales(), "Sales");
        pageContainer.add(new PharmaSysReports(), "Reports");
        pageContainer.add(new PharmaSysSettings(), "Settings");
        cardLayout.show(pageContainer, "Dashboard");
    }
    
    
    private void resetSideButtonHighlight(JPanel... buttons) {
        if (activeTab != null) {
            activeTab.setBackground(normalColor);
            activeTab.repaint();
            activeTab = null;
        }
    }
    
    private JPanel getPanelByName(String name, JPanel... buttons) {
        for (JPanel panel : buttons) {
            Component[] components = panel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel) {
                    JLabel lbl = (JLabel) comp;
                    if (lbl.getText().equalsIgnoreCase(name)) {
                        return panel;
                    }
                }
            }
        }
        return null;
    }

    private JPanel listPanelLowStock(String title, String[][] rows) {

    JPanel p = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 15);

            g2.setColor(new Color(210,210,210));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 15);
        }
    };

    p.setLayout(new BorderLayout());
    p.setOpaque(false);
    p.setBorder(new EmptyBorder(10, 15, 15, 15));

    JLabel t = new JLabel(title);
    t.setFont(new Font("Segoe UI", Font.BOLD, 12));
    t.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220,220,220)));
    t.setBorder(new CompoundBorder(
                t.getBorder(),
                new EmptyBorder(0,0,8,0)
    ));

    p.add(t, BorderLayout.NORTH);

    JPanel list = new JPanel();
    list.setOpaque(false);
    list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
    list.setBorder(new EmptyBorder(10, 0, 0, 0));

    for (String[] r : rows) {

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(r[0]);
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel batch = new JLabel(r[1]); 
        batch.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        batch.setForeground(new Color(70,70,70));

        left.add(name);
        left.add(batch);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JLabel status = new JLabel(r[2]);
        status.setFont(new Font("Segoe UI", Font.BOLD, 11));

        if (r[2].equalsIgnoreCase("Critical")) {
            status.setForeground(new Color(200, 0, 0)); 
        } else if (r[2].equalsIgnoreCase("Low")) {
            status.setForeground(new Color(255, 140, 0)); 
        } else {
            status.setForeground(Color.DARK_GRAY);
        }

        status.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel percent = new JLabel(r[3]);
        percent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        percent.setForeground(Color.DARK_GRAY);
        percent.setAlignmentX(Component.RIGHT_ALIGNMENT);

        right.add(status);
        right.add(percent);

        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);

        list.add(row);
        list.add(Box.createVerticalStrut(10));
    }

    p.add(list, BorderLayout.CENTER);

    return p;

}

private JPanel sideBtn(String name, boolean active, String iconPath) {

        final boolean[] pressed = { false };

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT)){
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                                RenderingHints.VALUE_ANTIALIAS_ON);

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

        p.setMaximumSize(new Dimension(320,35));
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.setOpaque(false);
        p.setBackground(active ? bgGray : normalColor);

        JLabel lbl = new JLabel(name);
        lbl.setFont(new Font("Segoe UI",Font.BOLD,15));
        lbl.setForeground(Color.BLACK);

        ImageIcon rawIcon = new ImageIcon(getClass().getResource(iconPath));

        Image scaledImg = rawIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImg);

        JLabel iconLbl = new JLabel(finalIcon);
        iconLbl.setBorder(new EmptyBorder(0, 5, 0, 10)); 

        p.add(iconLbl);
        p.add(lbl);

        if(active){
            activeTab = p;
        }

        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (p != activeTab) {
                    p.setBackground(activeColor);
                    p.repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
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
                
                if (name.equals("Log out")) {
                    return; 
                }

                if (activeTab != null) {
                    activeTab.setBackground(normalColor);
                    activeTab.repaint();
                }

                p.setBackground(bgGray);
                activeTab = p;

                p.repaint();

                cardLayout.show(pageContainer, name.trim()); 
                dashboardLbl.setText(name.trim());
            }

        });

        return p;
    }

    private JPanel statCard(String title,String value,String perc){

        JPanel p = new RoundedPanel(20);
        p.setLayout(new BorderLayout());
        p.setBackground(cardGray);
        p.setBorder(new EmptyBorder(12,15,12,15));

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

        JLabel t  = new JLabel(title, SwingConstants.CENTER);
        JLabel v  = new JLabel(value, SwingConstants.CENTER);
        JLabel pc = new JLabel(perc, SwingConstants.RIGHT);

        t.setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setForeground(Color.DARK_GRAY);

        v.setFont(new Font("Segoe UI", Font.BOLD, 12));
        v.setForeground(Color.BLACK);

        pc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        pc.setForeground(Color.GRAY);

        JPanel percentRow = new JPanel(new BorderLayout());
        percentRow.setOpaque(false);
        pc.setHorizontalAlignment(SwingConstants.RIGHT);
        percentRow.add(pc, BorderLayout.EAST);

        v.setHorizontalAlignment(SwingConstants.CENTER);
        v.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        t.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(t);
        center.add(Box.createVerticalStrut(6));
        center.add(v);          
        center.add(Box.createVerticalStrut(4));
        center.add(percentRow);     

        p.add(topRow, BorderLayout.NORTH);
        p.add(center, BorderLayout.CENTER);

        return p;
    }

    private JPanel listPanelExpiring(String title, String[][] rows) {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 15);

                g2.setColor(new Color(210,210,210));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 15);
            }
        };

        p.setLayout(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(10, 15, 15, 15));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220,220,220)));
        t.setBorder(new CompoundBorder(
                    t.getBorder(),
                    new EmptyBorder(0,0,8,0)
        ));

        p.add(t, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBorder(new EmptyBorder(10, 0, 0, 0));

        for (String[] r : rows) {

            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);

            JPanel left = new JPanel();
            left.setOpaque(false);
            left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

            JLabel name = new JLabel(r[0]);
            name.setFont(new Font("Segoe UI", Font.BOLD, 13));

            JLabel batch = new JLabel(r[1]);  
            batch.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            batch.setForeground(new Color(70,70,70));

            JLabel days = new JLabel(r[2]);  
            days.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            days.setForeground(new Color(70,70,70));

            left.add(name);
            left.add(batch);
            left.add(days);

            JPanel right = new JPanel();
            right.setOpaque(false);
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

            JLabel stock = new JLabel(r[3]);
            stock.setFont(new Font("Segoe UI", Font.BOLD, 12));
            stock.setForeground(Color.BLACK);
            stock.setAlignmentX(Component.RIGHT_ALIGNMENT);

            right.add(stock);

            row.add(left, BorderLayout.WEST);
            row.add(right, BorderLayout.EAST);

            list.add(row);
            list.add(Box.createVerticalStrut(18));
        }

        p.add(list, BorderLayout.CENTER);

        return p;

        }
        private JPanel pageHeader(String title, String subtitle) {
            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
            header.setOpaque(false);
            header.setBorder(new EmptyBorder(0,0,15,0));

            JLabel t = new JLabel(title);
            t.setFont(new Font("Segoe UI", Font.BOLD, 20));

            JLabel s = new JLabel(subtitle);
            s.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            header.add(t);
            header.add(s);

            return header;
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PharmaSysDashboard().setVisible(true));
    }
}
