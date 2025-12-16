import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PharmaSysSettings extends JPanel {

    // --- Design Constants (Matching Reports) ---
    private final Color pageBg = new Color(208, 217, 232);
    private final Color blue = new Color(25, 52, 214);
    private final Color hoverBlue = new Color(45, 75, 240); // Lighter blue for button hover
    private final Color grayText = new Color(100, 110, 120);
    private final Color inputBg = new Color(235, 238, 243); // Light gray background for fields

    // Tab Colors
    private final Color tabContainerColor = new Color(238, 242, 248);
    private final Color tabNormal = new Color(226, 232, 240);
    private final Color tabActive = Color.WHITE;
    private final Color tabHover = new Color(232, 238, 248);

    private CardLayout innerCards;
    private JPanel innerContainer;
    private JPanel currentTab = null;

    public PharmaSysSettings() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Wrapper to add padding (matches Reports spacing)
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(2, 5, 5, 5));

        // 1. Header (Title + Subtitle + Save Button)
        wrapper.add(buildHeader(), BorderLayout.NORTH);

        // 2. Main Content (Tabs + Cards)
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);

        // Tabs container with spacing
        JPanel tabsWithSpace = new JPanel(new BorderLayout());
        tabsWithSpace.setOpaque(false);
        tabsWithSpace.setBorder(new EmptyBorder(10, 0, 10, 0));
        tabsWithSpace.add(buildSegmentedTabs(), BorderLayout.CENTER);

        center.add(tabsWithSpace, BorderLayout.NORTH);

        // Card Container (Where the settings forms go)
        innerCards = new CardLayout();
        innerContainer = new JPanel(innerCards);
        innerContainer.setOpaque(false);

        // Add the FIVE specific Settings pages
        innerContainer.add(buildProfileSettings(), "Profile");
        innerContainer.add(buildPharmacySettings(), "Pharmacy");
        innerContainer.add(buildNotificationSettings(), "Notifications");
        innerContainer.add(buildSecuritySettings(), "Security");
        innerContainer.add(buildAppearanceSettings(), "Appearance");

        innerCards.show(innerContainer, "Profile"); // Default view

        center.add(innerContainer, BorderLayout.CENTER);
        wrapper.add(center, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);
    }

    // ---------------------------------------------------------
    //  UI BUILDERS
    // ---------------------------------------------------------

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.setBorder(new EmptyBorder(0, 0, 10, 0)); // Padding below header text

        JLabel t = new JLabel("Settings");
        t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(t);

        JLabel s = new JLabel("Manage user, pharmacy details, and application preferences");
        s.setFont(new Font("Segoe UI", Font.BOLD, 12));
        s.setForeground(Color.DARK_GRAY);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(s);

        header.add(left, BorderLayout.WEST);
        
        // Right side "Save" button (Global save)
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setOpaque(false);
        // Align button vertically with the text block
        right.setBorder(new EmptyBorder(10, 0, 0, 0)); 
        JButton saveBtn = new JButton("Save All Changes");
        stylePrimaryButton(saveBtn);
        
        // --- START CHANGE: Add ActionListener to Save Button ---
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display the success message as requested
                JOptionPane.showMessageDialog(PharmaSysSettings.this, 
                    "Settings updated successfully!", 
                    "Save Complete", 
                    JOptionPane.INFORMATION_MESSAGE);
                // System.out.println("Save button clicked - Success dialog shown.");
            }
        });
        // --- END CHANGE ---
        
        right.add(saveBtn);
        
        header.add(right, BorderLayout.EAST);

        return header;
    }

    private JPanel buildSegmentedTabs() {
        JPanel outer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(tabContainerColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        outer.setOpaque(false);
        outer.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 14)); // Increased spacing for 5 tabs
        outer.setBorder(new EmptyBorder(0, 0, 0, 0));

        // FIVE TABS
        JPanel t1 = makeTab("Profile", true);
        JPanel t2 = makeTab("Pharmacy", false);
        JPanel t3 = makeTab("Notifications", false);
        JPanel t4 = makeTab("Security", false);
        JPanel t5 = makeTab("Appearance", false);

        currentTab = t1; // Set default

        outer.add(t1);
        outer.add(t2);
        outer.add(t3);
        outer.add(t4);
        outer.add(t5);

        // Click Logic
        t1.addMouseListener(new TabClickListener(t1, "Profile"));
        t2.addMouseListener(new TabClickListener(t2, "Pharmacy"));
        t3.addMouseListener(new TabClickListener(t3, "Notifications"));
        t4.addMouseListener(new TabClickListener(t4, "Security"));
        t5.addMouseListener(new TabClickListener(t5, "Appearance"));

        return outer;
    }

    // ---------------------------------------------------------
    //  1. PROFILE SETTINGS
    // ---------------------------------------------------------

    private JPanel buildProfileSettings() {
        JPanel card = createCardedPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        addSectionTitle(card, "User Information");
        
        JPanel form = new JPanel(new GridLayout(0, 2, 20, 20));
        form.setOpaque(false);
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.setMaximumSize(new Dimension(800, 200)); 

        form.add(createFormGroup("Full Name", "Sample Name"));
        form.add(createFormGroup("Role", "Admin/Pharmacist"));
        form.add(createFormGroup("Contact Email", "sample@email.com"));
        form.add(createFormGroup("Phone Number", "+63 123 4567 890"));

        card.add(form);
        card.add(Box.createVerticalGlue()); // Push everything up

        return wrapInPadding(card);
    }
    
    // ---------------------------------------------------------
    //  2. PHARMACY SETTINGS
    // ---------------------------------------------------------

    private JPanel buildPharmacySettings() {
        JPanel card = createCardedPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        addSectionTitle(card, "Business Profile");
        
        JPanel form = new JPanel(new GridLayout(0, 2, 20, 20));
        form.setOpaque(false);
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.setMaximumSize(new Dimension(800, 200)); 

        form.add(createFormGroup("Pharmacy Name", "PharmaSys"));
        form.add(createFormGroup("Business ID / License", "PH-1234-5678-L"));
        
        // Use a single component for address to span both columns
        JPanel addressWrap = new JPanel(new BorderLayout());
        addressWrap.setOpaque(false);
        addressWrap.add(createFormGroup("Physical Address", "123 Medical Plaza, Sample Ave, Cebu City, Philippines"), BorderLayout.CENTER);
        form.add(addressWrap);
        form.add(Box.createVerticalStrut(0)); // Placeholder to keep grid layout

        card.add(form);
        card.add(Box.createVerticalStrut(20));

        addSectionTitle(card, "Financial & Tax");
        JPanel taxForm = new JPanel(new GridLayout(0, 2, 20, 20));
        taxForm.setOpaque(false);
        taxForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        taxForm.setMaximumSize(new Dimension(800, 100));
        taxForm.add(createFormGroup("Default Tax Rate (%)", "12.0"));
        taxForm.add(createFormGroup("Currency Symbol", "₱ (PHP)"));
        
        card.add(taxForm);
        card.add(Box.createVerticalGlue()); 

        return wrapInPadding(card);
    }
    
    // ---------------------------------------------------------
    //  3. NOTIFICATIONS SETTINGS
    // ---------------------------------------------------------
    
    private JPanel buildNotificationSettings() {
        JPanel card = createCardedPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        addSectionTitle(card, "Inventory Alerts");
        JCheckBox lowStock = new JCheckBox("Notify when stock is below safety threshold (10 units)");
        styleCheckBox(lowStock);
        lowStock.setSelected(true);
        card.add(lowStock);
        card.add(Box.createVerticalStrut(10));

        JCheckBox expStock = new JCheckBox("Notify 30 days before medicine expiration date");
        styleCheckBox(expStock);
        expStock.setSelected(true);
        card.add(expStock);

        card.add(Box.createVerticalStrut(30));

        addSectionTitle(card, "System and Reports");
        JCheckBox dailyEmail = new JCheckBox("Send Daily Sales Summary to Admin Email");
        styleCheckBox(dailyEmail);
        dailyEmail.setSelected(true);
        card.add(dailyEmail);
        card.add(Box.createVerticalStrut(10));

        JCheckBox backupNotify = new JCheckBox("Notify on successful data backup completion");
        styleCheckBox(backupNotify);
        card.add(backupNotify);

        card.add(Box.createVerticalGlue());
        return wrapInPadding(card);
    }

    // ---------------------------------------------------------
    //  4. SECURITY SETTINGS
    // ---------------------------------------------------------

    private JPanel buildSecuritySettings() {
        JPanel card = createCardedPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        addSectionTitle(card, "Password Management");

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 15));
        form.setOpaque(false);
        form.setMaximumSize(new Dimension(400, 200)); // Narrower for passwords
        form.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(createFormGroup("Current Password", "••••••••"));
        form.add(createFormGroup("New Password", ""));
        form.add(createFormGroup("Confirm New Password", ""));

        card.add(form);
        card.add(Box.createVerticalStrut(30));

        addSectionTitle(card, "Access Control");
        JCheckBox twoFa = new JCheckBox("Require Two-Factor Authentication (2FA) for login");
        styleCheckBox(twoFa);
        card.add(twoFa);
        card.add(Box.createVerticalStrut(10));

        JCheckBox autoLogout = new JCheckBox("Automatic logout after 30 minutes of inactivity");
        styleCheckBox(autoLogout);
        autoLogout.setSelected(true);
        card.add(autoLogout);

        card.add(Box.createVerticalGlue());
        return wrapInPadding(card);
    }

    // ---------------------------------------------------------
    //  5. APPEARANCE SETTINGS
    // ---------------------------------------------------------

    private JPanel buildAppearanceSettings() {
        JPanel card = createCardedPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        addSectionTitle(card, "Theme");
        
        // Theme selection using radio buttons for the Reports design language
        ButtonGroup themeGroup = new ButtonGroup();
        
        JRadioButton lightTheme = new JRadioButton("Light Theme (Default)");
        lightTheme.setSelected(true);
        styleRadioButton(lightTheme);
        
        JRadioButton darkTheme = new JRadioButton("Dark Theme (Requires Restart)");
        styleRadioButton(darkTheme);

        themeGroup.add(lightTheme);
        themeGroup.add(darkTheme);

        card.add(lightTheme);
        card.add(Box.createVerticalStrut(10));
        card.add(darkTheme);
        
        card.add(Box.createVerticalStrut(30));
        
        addSectionTitle(card, "Display");
        
        JPanel fontSizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        fontSizePanel.setOpaque(false);
        fontSizePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel sizeLabel = new JLabel("Application Font Size:");
        sizeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sizeLabel.setForeground(grayText);

        String[] sizes = {"Small", "Medium (Default)", "Large"};
        JComboBox<String> sizeChooser = new JComboBox<>(sizes);
        sizeChooser.setSelectedIndex(1);
        sizeChooser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        fontSizePanel.add(sizeLabel);
        fontSizePanel.add(sizeChooser);
        
        card.add(fontSizePanel);
        
        card.add(Box.createVerticalGlue());
        return wrapInPadding(card);
    }
    
    // ---------------------------------------------------------
    //  COMPONENT STYLING & HELPERS
    // ---------------------------------------------------------

    private JPanel createFormGroup(String labelText, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(grayText);

        JTextField field = roundedField(placeholder, 200);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JTextField roundedField(String text, int width) {
        JTextField f = new JTextField(text);
        f.setPreferredSize(new Dimension(width, 35));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setForeground(text.contains("•") ? Color.BLACK : Color.GRAY);
        
        // Rounded background panel for fields, similar to the date picker in Reports
        JPanel box = new JPanel(new BorderLayout());
        box.setOpaque(false);
        box.setBackground(inputBg);
        
        // Set the text field style
        f.setBorder(new EmptyBorder(5, 10, 5, 10)); // Inner padding
        f.setOpaque(false);
        
        // Custom background drawing
        return new JTextField(text) {
             { // Initialization block
                setPreferredSize(new Dimension(width, 35));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setForeground(text.contains("•") ? Color.BLACK : Color.GRAY);
                setBorder(new EmptyBorder(5, 10, 5, 10));
                setOpaque(false);
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(inputBg); // Soft background color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
    }

    private void addSectionTitle(JPanel panel, String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(blue);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 15, 0));
        panel.add(title);
    }

    private void styleCheckBox(JCheckBox box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setOpaque(false);
        box.setFocusPainted(false);
        box.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    private void styleRadioButton(JRadioButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void stylePrimaryButton(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 20, 8, 20));

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.repaint(); }
            public void mouseExited(MouseEvent e) { b.repaint(); }
        });

        b.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                JButton btn = (JButton) c;
                g2.setColor(btn.getModel().isRollover() ? hoverBlue : blue);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g, c);
            }
        });
    }

    private JPanel createCardedPanel() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(new Color(220, 220, 220));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(25, 25, 25, 25)); // Inner padding of the card
        
        return card;
    }
    
    private JPanel wrapInPadding(JPanel card) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.add(card, BorderLayout.CENTER);
        return p;
    }

    // ---------------------------------------------------------
    //  TAB LOGIC (IDENTICAL TO REPORTS)
    // ---------------------------------------------------------
    
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
        tab.setPreferredSize(new Dimension(160, 36)); 
        tab.setBorder(new EmptyBorder(6, 10, 6, 10));

        JLabel lbl = new JLabel(name);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(Color.BLACK);
        tab.add(lbl);

        tab.setBackground(active ? tabActive : tabNormal);

        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (tab != currentTab) tab.setBackground(tabHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (tab != currentTab) tab.setBackground(tabNormal);
            }
        });
        return tab;
    }

    private void setActiveTab(JPanel newTab) {
        if (currentTab != null) {
            currentTab.setBackground(tabNormal);
            currentTab.repaint();
        }
        newTab.setBackground(tabActive);
        currentTab = newTab;
        newTab.repaint();
    }

    private class TabClickListener extends MouseAdapter {
        private final JPanel tab;
        private final String cardName;

        public TabClickListener(JPanel tab, String cardName) {
            this.tab = tab;
            this.cardName = cardName;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            setActiveTab(tab);
            innerCards.show(innerContainer, cardName);
        }
    }
}