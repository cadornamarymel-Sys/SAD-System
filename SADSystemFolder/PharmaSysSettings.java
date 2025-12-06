import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PharmaSysSettings extends JPanel {

    // ---- Styling constants to match your Dashboard ----
    private final Color blue = new Color(79, 107, 201);
    private final Color bgGray = new Color(208, 217, 232);
    private final Color cardGray = new Color(236, 243, 249);
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 15);
    private final Font normalFont = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font boldFont = new Font("Segoe UI", Font.BOLD, 12);

    // Tab components
    private final JPanel tabBar = new JPanel();
    private final CardLayout innerCardLayout = new CardLayout();
    private final JPanel innerCardPanel = new JPanel(innerCardLayout);

    public PharmaSysSettings() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBackground(bgGray);
        setBorder(new EmptyBorder(0, 0, 0, 0));

        // Header (Title + subtitle)
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel subtitle = new JLabel("Manage your pharmacy system preferences and configurations");
        subtitle.setFont(normalFont);

        header.add(title);
        header.add(subtitle);

        // Tabs
        tabBar.setOpaque(false);
        tabBar.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 6));
        tabBar.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Create tab buttons
        TabButton profileTab = new TabButton("Profile", true);
        TabButton pharmacyTab = new TabButton("Pharmacy", false);
        TabButton notificationsTab = new TabButton("Notifications", false);
        TabButton securityTab = new TabButton("Security", false);
        TabButton appearanceTab = new TabButton("Appearance", false);

        // Tab click handlers
        profileTab.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { switchTab("Profile", profileTab); }
        });
        pharmacyTab.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { switchTab("Pharmacy", pharmacyTab); }
        });
        notificationsTab.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { switchTab("Notifications", notificationsTab); }
        });
        securityTab.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { switchTab("Security", securityTab); }
        });
        appearanceTab.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { switchTab("Appearance", appearanceTab); }
        });

        tabBar.add(profileTab);
        tabBar.add(pharmacyTab);
        tabBar.add(notificationsTab);
        tabBar.add(securityTab);
        tabBar.add(appearanceTab);

        // Build inner cards for each tab
        innerCardPanel.setOpaque(false);
        innerCardPanel.add(buildProfilePanel(), "Profile");
        innerCardPanel.add(buildPharmacyPanel(), "Pharmacy");
        innerCardPanel.add(buildNotificationsPanel(), "Notifications");
        innerCardPanel.add(buildSecurityPanel(), "Security");
        innerCardPanel.add(buildAppearancePanel(), "Appearance");

        // Default to Profile
        innerCardLayout.show(innerCardPanel, "Profile");

        // Save/Cancel bar at bottom (shared)
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 12));
        bottomBar.setOpaque(false);
        bottomBar.setAlignmentX(RIGHT_ALIGNMENT);
        bottomBar.setBorder(new EmptyBorder(0, 0, 0, -20));
        bottomBar.setPreferredSize(new Dimension(65, 50));

        RoundedButton cancelBtn = new RoundedButton("Cancel", false);
        RoundedButton saveBtn = new RoundedButton("Save Changes", true);
        saveBtn.setBackground(new Color(37, 99, 235));   
        saveBtn.setForeground(Color.WHITE);             
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setOpaque(true);

        bottomBar.add(cancelBtn);
        bottomBar.add(saveBtn);

        // Compose everything
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(header, BorderLayout.NORTH);
        top.add(tabBar, BorderLayout.CENTER);

        // central card area with rounded white background like screenshot
        JPanel centerWrap = new RoundedCardPanel();
        centerWrap.setLayout(new BorderLayout());
        centerWrap.setOpaque(false);
        centerWrap.setBorder(new EmptyBorder(18, 18, 18, 18));

        centerWrap.add(innerCardPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(centerWrap, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);
    }

    private void switchTab(String name, TabButton clicked) {
        // Update visual selection
        for (Component c : tabBar.getComponents()) {
            if (c instanceof TabButton) {
                ((TabButton) c).setSelected(c == clicked);
            }
        }
        // Switch shown card
        innerCardLayout.show(innerCardPanel, name);
    }

    // ------------------- BUILD TABS -------------------
    private JPanel buildProfilePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JPanel form = new RoundedCardPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(18, 18, 18, 18));
        form.setOpaque(false);

        // Profile header
        JLabel heading = new JLabel("Profile Information");
        heading.setFont(boldFont);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        form.add(heading);
        form.add(Box.createVerticalStrut(12));

        // Top row: avatar + change photo
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        topRow.setOpaque(false);

        // Avatar circle (simple)
        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int size = Math.min(getWidth(), getHeight());
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(blue);
                g2.fillOval(0, 0, size, size);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
                FontMetrics fm = g2.getFontMetrics();
                String letter = "A";
                int w = fm.stringWidth(letter);
                int h = fm.getAscent();
                g2.drawString(letter, (size - w) / 2, (size + h) / 2 - 4);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(96, 96));
        avatar.setOpaque(false);

        JPanel avatarRight = new JPanel();
        avatarRight.setLayout(new BoxLayout(avatarRight, BoxLayout.Y_AXIS));
        avatarRight.setOpaque(false);

        RoundedButton changePhoto = new RoundedButton("Change Photo", false);
        changePhoto.setMaximumSize(new Dimension(140, 35));
        JLabel help = new JLabel("JPG, GIF or PNG. Max size 2MB");
        help.setFont(new Font("Segoe UI", Font.BOLD, 12));
        help.setForeground(Color.DARK_GRAY);

        avatarRight.add(changePhoto);
        avatarRight.add(Box.createVerticalStrut(6));
        avatarRight.add(help);

        topRow.add(avatar);
        topRow.add(avatarRight);

        form.add(topRow);
        form.add(Box.createVerticalStrut(18));

        // Grid like inputs: two columns
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Row 1: First name | Last name
        gbc.gridx = 0; gbc.gridy = 0;
        grid.add(labeledField("First Name", "Enter your first name"), gbc);
        gbc.gridx = 1;
        grid.add(labeledField("Last Name", "Enter your last name"), gbc);

        // Row 2: Email full width
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        grid.add(labeledField("Email Address", "Enter your email address"), gbc);
        gbc.gridwidth = 1;

        // Row 3: Phone | Role
        gbc.gridx = 0; gbc.gridy = 2;
        grid.add(labeledField("Phone Number", "Enter your phone number"), gbc);
        gbc.gridx = 1;
        grid.add(labeledField("Role", "Enter your role"), gbc);

        form.add(grid);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildPharmacyPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JPanel card = new RoundedCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.setOpaque(false);

        JLabel heading = new JLabel("Pharmacy Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(heading);
        card.add(Box.createVerticalStrut(-5));

        // Form using GridBag for flexible fields
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Pharmacy name full width
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        grid.add(labeledField("Pharmacy Name", "Enter your pharmacy name"), gbc);
        gbc.gridwidth = 1;

        // License number full width
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        grid.add(labeledField("License Number", "Enter your license number"), gbc);
        gbc.gridwidth = 1;

        // Address full width
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        grid.add(labeledField("Address", "Enter your address"), gbc);
        gbc.gridwidth = 1;

        // City | Zip
        gbc.gridx = 0; gbc.gridy = 3;
        grid.add(labeledField("City", "Enter your city"), gbc);
        gbc.gridx = 1;
        grid.add(labeledField("Zip Code", "Enter your zip code"), gbc);

        // Phone | Email
        gbc.gridx = 0; gbc.gridy = 4;
        grid.add(labeledField("Phone Number", "Enter your phone number"), gbc);
        gbc.gridx = 1;
        grid.add(labeledField("Email", "Enter your email"), gbc);

        card.add(grid);

        // Business hours small footer right-aligned (simulates screenshot text)
        JPanel hours = new JPanel(new BorderLayout());
        hours.setOpaque(false);
        JLabel left = new JLabel("Business Hours");
        left.setFont(boldFont);
        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(createSmallLabel("Monday - Friday    8:00 AM - 8:00 PM"));
        right.add(createSmallLabel("Saturday            9:00 AM - 6:00 PM"));
        right.add(createSmallLabel("Sunday              10:00 AM - 4:00 PM"));

        hours.add(left, BorderLayout.WEST);
        hours.add(right, BorderLayout.EAST);

        card.add(Box.createVerticalStrut(18));
        card.add(hours);
        card.add(Box.createVerticalGlue());

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildNotificationsPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JPanel card = new RoundedCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.setOpaque(false);

        JLabel heading = new JLabel("Notification Settings");
        heading.setFont(boldFont);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(heading);
        card.add(Box.createVerticalStrut(12));

        // Each row with description and a toggle aligned to right
        card.add(notificationRow("Low Stock Alerts", "Get notified when medicines are running low", true));
        card.add(notificationRow("Expiry Warnings", "Alert when medicines are close to expiration", true));
        card.add(notificationRow("Daily Sales Report", "Receive daily sales summary via email", true));
        card.add(notificationRow("System Updates", "Get notified about system updates and maintenance", false));
        card.add(notificationRow("Email Notifications", "Receive notifications via email", true));
        card.add(notificationRow("SMS Notifications", "Receive critical alerts via SMS", false));

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSecurityPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JPanel card = new RoundedCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(0, 18, 18, 18));
        card.setOpaque(false);

        JLabel heading = new JLabel("Security Settings");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(heading);
        card.add(Box.createVerticalStrut(5));

        // ---------------------------
        // PASSWORD FIELDS (2-column grid)
        // ---------------------------
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Current Password full width
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        grid.add(labeledField("Current Password", "Enter current password"), gbc);

        // New Password | Confirm Password
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        grid.add(labeledField("New Password", "Enter new password"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        grid.add(labeledField("Confirm Password", "Re-enter password"), gbc);

        card.add(grid);
        card.add(Box.createVerticalStrut(18));

        // ---------------------------
        // SECURITY SWITCHES
        // ---------------------------
        card.add(notificationRow(
                "Two-Factor Authentication",
                "Add an extra layer of security to your account",
                false
        ));

        card.add(notificationRow(
                "Login Alerts",
                "Get notified when someone logs into your account",
                true
        ));

        card.add(Box.createVerticalStrut(20));

        // ---------------------------
        // ACTIVE SESSIONS BLOCK (Styled card)
        // ---------------------------
        JLabel sessions = new JLabel("Active Sessions");
        sessions.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sessions.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Wrap inside a left-aligned panel
        JPanel sessionTitleWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sessionTitleWrap.setOpaque(false);
        sessionTitleWrap.add(sessions);

        // small positive spacing ABOVE the session box
        card.add(Box.createVerticalStrut(-10));  // adjust to taste
        card.add(sessionTitleWrap);
        card.add(Box.createVerticalStrut(6));  // small space before the container

        JPanel sessionBox = new RoundedPanel();
        sessionBox.setLayout(new BorderLayout());
        sessionBox.setBorder(new EmptyBorder(0, 10, 10, 10));
        sessionBox.setOpaque(false);

        JLabel sessionLabel = new JLabel(
                "<html><b>Current Device</b><br/>" +
                "<span style='font-size:11px;color:gray'>" +
                "Chrome on Windows • Manila, Philippines<br/>" +
                "Last active: Now" +
                "</span></html>"
        );

        sessionBox.add(sessionLabel, BorderLayout.WEST);

        JLabel status = new JLabel("Active");
        status.setFont(new Font("Segoe UI", Font.BOLD, 13));
        status.setForeground(new Color(18, 150, 80));
        sessionBox.add(status, BorderLayout.EAST);

        card.add(sessionBox);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAppearancePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JPanel card = new RoundedCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.setOpaque(false);

        JLabel heading = new JLabel("Appearance Settings");
        heading.setFont(boldFont);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(heading);
        card.add(Box.createVerticalStrut(12));

        // Simple static fields to match screenshot layout
        card.add(simpleKeyValue("Language", "English (US)"));
        card.add(simpleKeyValue("Timezone", "PH-2024-12345"));
        card.add(simpleKeyValue("Date Format", "MM/DD/YYYY"));
        card.add(simpleKeyValue("Currency", "Philippine Peso (₱)"));

        // Toggles
        card.add(Box.createVerticalStrut(8));
        card.add(notificationRow("Compact Mode", "Reduce spacing for more content density", true));
        card.add(notificationRow("Show Animations", "Enable smooth transitions and animations", false));

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    // ------------------- Utility UI Builders -------------------
    private JLabel createSmallLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(Color.DARK_GRAY);
        return l;
    }

    private JPanel labeledField(String label, String placeholder) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l = new JLabel(label);
        l.setFont(boldFont);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField tf = new JTextField(placeholder);
        tf.setFont(normalFont);
        tf.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        tf.setForeground(Color.DARK_GRAY);

        // Rounded look wrapper (no external libs)
        RoundedPanel wrapper = new RoundedPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setBorder(new EmptyBorder(2, 2, 2, 2));
        wrapper.add(tf, BorderLayout.CENTER);
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(wrapper);
        return p;
    }

    private JPanel notificationRow(String title, String description, boolean initialState) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(10, 6, 10, 6));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JLabel t = new JLabel(title);
        t.setFont(boldFont);
        JLabel d = new JLabel(description);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        d.setForeground(Color.DARK_GRAY);

        left.add(t);
        left.add(d);

        // Toggle control
        SimpleToggle toggle = new SimpleToggle(initialState);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setOpaque(false);
        right.add(toggle);

        row.add(left, BorderLayout.CENTER);
        row.add(right, BorderLayout.EAST);

        // optional separator line
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220, 220, 220));
        row.add(sep, BorderLayout.SOUTH);

        return row;
    }

    private JPanel simpleKeyValue(String key, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(8, 6, 8, 6));
        JLabel k = new JLabel(key);
        k.setFont(boldFont);
        JLabel v = new JLabel(value);
        v.setFont(normalFont);
        v.setForeground(Color.DARK_GRAY);
        p.add(k, BorderLayout.WEST);
        p.add(v, BorderLayout.EAST);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.add(p, BorderLayout.CENTER);
        outer.add(sep, BorderLayout.SOUTH);
        return outer;
    }

    // ------------------- Custom Components -------------------
    private class RoundedCardPanel extends JPanel {
        public RoundedCardPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 18;
            // Background
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            // Border
            g2.setColor(new Color(220, 220, 220));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

            g2.dispose();
            super.paintComponent(g);
        }

        // ensure children are painted on transparent background
        @Override
        public boolean isOpaque() {
            return false;
        }
    }

    private class RoundedPanel extends JPanel {
        private final int arc = 12;

        public RoundedPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // subtle background slightly off-white to emulate input container
            g2.setColor(new Color(245, 248, 252));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.setColor(new Color(220, 220, 220));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override public boolean isOpaque() { return false; }
    }
    private class SimpleToggle extends JComponent {
        private boolean on;
        private final int width = 42;
        private final int height = 22;

        public SimpleToggle(boolean initial) {
            on = initial;
            setPreferredSize(new Dimension(width, height));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    on = !on;
                    repaint();
                }
            });
        }

        public boolean isOn() { return on; }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = height;
            // background
            if (on) g2.setColor(new Color(20, 20, 20)); // dark as in screenshot when enabled
            else g2.setColor(new Color(230, 230, 230));

            g2.fillRoundRect(0, 0, width, height, arc, arc);

            // knob
            int knobSize = height - 6;
            int knobX = on ? width - knobSize - 3 : 3;
            int knobY = 3;
            g2.setColor(Color.WHITE);
            g2.fillOval(knobX, knobY, knobSize, knobSize);

            g2.dispose();
        }
    }

    private class TabButton extends JPanel {
        private final JLabel label;
        private boolean selected = false;

        public TabButton(String text, boolean sel) {
            setOpaque(false);
            setLayout(new BorderLayout());
            label = new JLabel(text);
            label.setFont(boldFont);
            label.setForeground(sel ? Color.BLACK : new Color(90, 90, 90));
            add(label, BorderLayout.CENTER);
            setSelected(sel);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            setBorder(new EmptyBorder(6, 10, 6, 10));
        }

        public void setSelected(boolean s) {
            selected = s;
            if (selected) {
                label.setForeground(Color.BLACK);
                // show underline by painting
            } else {
                label.setForeground(new Color(90, 90, 90));
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (selected) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                // Draw a subtle rounded underline under label (blue)
                g2.setColor(new Color(92, 120, 224)); // activeColor similar to Dashboard
                g2.fillRoundRect(8, h - 6, w - 16, 6, 6, 6);
                g2.dispose();
            }
        }
    }
    private class RoundedButton extends JButton {
        private final boolean primary;

        public RoundedButton(String text, boolean primary) {
            super(text);
            this.primary = primary;
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setForeground(primary ? Color.WHITE : Color.BLACK);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(120, 36));
            setBorder(new EmptyBorder(8, 16, 8, 16));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { repaint(); }
                @Override public void mouseExited(MouseEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            int arc = 14;
            if (primary) {
            g2.setColor(getBackground());    // use actual background
            g2.fillRoundRect(0, 0, w, h, arc, arc);
            } else {
                g2.setColor(new Color(235, 238, 244));
                g2.fillRoundRect(0, 0, w, h, arc, arc);
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            }

            // Text
            FontMetrics fm = g2.getFontMetrics();
            int sw = fm.stringWidth(getText());
            int sh = fm.getAscent();
            g2.setColor(getForeground());
            g2.setFont(getFont());
            g2.drawString(getText(), (w - sw) / 2, (h + sh) / 2 - 3);

            g2.dispose();
        }
    }

}
