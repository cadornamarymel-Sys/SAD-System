import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PharmaSysSettings extends JPanel {

    private final Color blue = new Color(79, 107, 201);
    private final Color bgGray = new Color(208, 217, 232);
    private final Color cardGray = new Color(236, 243, 249);
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 15);
    private final Font normalFont = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font boldFont = new Font("Segoe UI", Font.BOLD, 12);

    private String savedLanguage;
    private String savedTimezone;
    private String savedDateFormat;
    private String savedCurrency;

    private JComboBox<String> languageCombo;
    private JComboBox<String> timezoneCombo;
    private JComboBox<String> dateFormatCombo;
    private JComboBox<String> currencyCombo;

    private final JPanel tabBar = new JPanel();
    private final CardLayout innerCardLayout = new CardLayout();
    private final JPanel innerCardPanel = new JPanel(innerCardLayout);
    private JPanel avatarPreview;
    private java.util.List<JTextField> allFields = new java.util.ArrayList<>();
    private java.util.List<SimpleToggle> allToggles = new java.util.ArrayList<>();

    private JLabel profileHeading;
    private JLabel pharmacyHeading;
    private JLabel notificationsHeading;
    private JLabel securityHeading;
    private JLabel appearanceHeading;

    private JLabel profileTabLabel;
    private JLabel pharmacyTabLabel;
    private JLabel notificationsTabLabel;
    private JLabel securityTabLabel;
    private JLabel appearanceTabLabel;

    private final Map<String, Map<String, String>> translations = new HashMap<>();

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;           
    private JTextField phoneField;         
    private JTextField roleField;

    private JTextField pharmacyNameField;
    private JTextField licenseNumberField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField zipField;
    private JTextField pharmacyPhoneField;    
    private JTextField pharmacyEmailField;   

    private JTextField currentPassField;
    private JTextField newPassField;
    private JTextField confirmPassField;

    private void initTranslations() {
        Map<String, String> en = new HashMap<>();
        en.put("Dashboard", "Dashboard");
        en.put("Inventory", "Inventory");
        en.put("Sales", "Sales");
        en.put("Reports", "Reports");
        en.put("Settings", "Settings");
        en.put("Profile Information", "Profile Information");
        en.put("Pharmacy Details", "Pharmacy Details");
        translations.put("English", en);

        Map<String, String> fil = new HashMap<>();
        fil.put("Dashboard", "Dashboard"); 
        fil.put("Inventory", "Imbentaryo");
        fil.put("Sales", "Benta");
        fil.put("Reports", "Ulat");
        fil.put("Settings", "Mga Setting");
        fil.put("Profile Information", "Impormasyon ng Profile");
        fil.put("Pharmacy Details", "Detalye ng Parmasya");
        translations.put("Filipino", fil);

        Map<String, String> sp = new HashMap<>();
        sp.put("Dashboard", "Tablero");
        sp.put("Inventory", "Inventario");
        sp.put("Sales", "Ventas");
        sp.put("Reports", "Informes");
        sp.put("Settings", "Configuración");
        sp.put("Profile Information", "Información de Perfil");
        sp.put("Pharmacy Details", "Detalles de la Farmacia");
        translations.put("Spanish", sp);
    }

    private void applyLanguage(String language) {
        if (!translations.containsKey(language)) return;
        Map<String, String> t = translations.get(language);

        profileHeading.setText(t.getOrDefault("Profile Information", "Profile Information"));
        pharmacyHeading.setText(t.getOrDefault("Pharmacy Details", "Pharmacy Details"));
        notificationsHeading.setText(t.getOrDefault("Notification Settings", "Notification Settings"));
        securityHeading.setText(t.getOrDefault("Security Settings", "Security Settings"));
        appearanceHeading.setText(t.getOrDefault("Appearance Settings", "Appearance Settings"));

        profileTabLabel.setText(t.getOrDefault("Profile", "Profile"));
        pharmacyTabLabel.setText(t.getOrDefault("Pharmacy", "Pharmacy"));
        notificationsTabLabel.setText(t.getOrDefault("Notifications", "Notifications"));
        securityTabLabel.setText(t.getOrDefault("Security", "Security"));
        appearanceTabLabel.setText(t.getOrDefault("Appearance", "Appearance"));

    }

    public PharmaSysSettings() {
        initTranslations();
        setOpaque(false);
        setLayout(new BorderLayout());
        setBackground(bgGray);
        setBorder(new EmptyBorder(0, 0, 0, 0));

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

        tabBar.setOpaque(false);
        tabBar.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 6));
        tabBar.setBorder(new EmptyBorder(10, 0, 10, 0));

        TabButton profileTab = new TabButton("Profile", true);
        TabButton pharmacyTab = new TabButton("Pharmacy", false);
        TabButton notificationsTab = new TabButton("Notifications", false);
        TabButton securityTab = new TabButton("Security", false);
        TabButton appearanceTab = new TabButton("Appearance", false);

        profileTabLabel = new JLabel("Profile");
        pharmacyTabLabel = new JLabel("Pharmacy");
        notificationsTabLabel = new JLabel("Notifications");
        securityTabLabel = new JLabel("Security");
        appearanceTabLabel = new JLabel("Appearance");

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

        innerCardPanel.setOpaque(false);
        innerCardPanel.add(buildProfilePanel(), "Profile");
        innerCardPanel.add(buildPharmacyPanel(), "Pharmacy");
        innerCardPanel.add(buildNotificationsPanel(), "Notifications");
        innerCardPanel.add(buildSecurityPanel(), "Security");
        innerCardPanel.add(buildAppearancePanel(), "Appearance");

        innerCardLayout.show(innerCardPanel, "Profile");

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 12));
        bottomBar.setOpaque(false);
        bottomBar.setAlignmentX(RIGHT_ALIGNMENT);
        bottomBar.setBorder(new EmptyBorder(0, 0, 0, -20));
        bottomBar.setPreferredSize(new Dimension(65, 50));

        RoundedButton cancelBtn = new RoundedButton("Cancel", false);
        cancelBtn.addActionListener(e -> {
            for (JTextField tf : allFields) {
                tf.setText("");
            }

            for (SimpleToggle t : allToggles) {
                try {
                    java.lang.reflect.Field f = t.getClass().getDeclaredField("on");
                    f.setAccessible(true);
                    f.setBoolean(t, false);
                    t.repaint();
                } catch (Exception ignored) {}
            }

            JOptionPane.showMessageDialog(this, "All changes were discarded.");
        });
        RoundedButton saveBtn = new RoundedButton("Save Changes", true);
        saveBtn.addActionListener(e -> {

        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String role = roleField.getText().trim();

        String pharmName = pharmacyNameField.getText().trim();
        String lic = licenseNumberField.getText().trim();
        String addr = addressField.getText().trim();
        String city = cityField.getText().trim();
        String zip = zipField.getText().trim();
        String pharmPhone = pharmacyPhoneField.getText().trim();
        String pharmEmail = pharmacyEmailField.getText().trim();

        String curPass = currentPassField.getText().trim();
        String newPass = newPassField.getText().trim();
        String confPass = confirmPassField.getText().trim();

            for (JTextField tf : allFields) {
                String text = tf.getText() == null ? "" : tf.getText().trim();
                Object ph = tf.getClientProperty("placeholder");
                String placeholder = ph == null ? "" : ph.toString();
                if (text.isEmpty() || text.equals(placeholder)) {
                    JOptionPane.showMessageDialog(this,
                            "Please complete all fields before saving.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (languageCombo.getSelectedItem() == null ||
                timezoneCombo.getSelectedItem() == null ||
                dateFormatCombo.getSelectedItem() == null ||
                currencyCombo.getSelectedItem() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all appearance settings.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            savedLanguage = languageCombo.getSelectedItem().toString();
            savedTimezone = timezoneCombo.getSelectedItem().toString();
            savedDateFormat = dateFormatCombo.getSelectedItem().toString();
            savedCurrency = currencyCombo.getSelectedItem().toString();

            applyLanguage(savedLanguage);

            JOptionPane.showMessageDialog(this,
                    "Settings saved successfully!\n" +
                    "Language: " + savedLanguage + "\n" +
                    "Timezone: " + savedTimezone + "\n" +
                    "Date Format: " + savedDateFormat + "\n" +
                    "Currency: " + savedCurrency,
                    "Saved",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        saveBtn.setBackground(new Color(37, 99, 235));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setOpaque(true);

        bottomBar.add(cancelBtn);
        bottomBar.add(saveBtn);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(header, BorderLayout.NORTH);
        top.add(tabBar, BorderLayout.CENTER);

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
        for (Component c : tabBar.getComponents()) {
            if (c instanceof TabButton) {
                ((TabButton) c).setSelected(c == clicked);
            }
        }
        innerCardLayout.show(innerCardPanel, name);
    }

    private JPanel buildProfilePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JPanel form = new RoundedCardPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(18, 18, 18, 18));
        form.setOpaque(false);

        profileHeading = new JLabel("Profile Information");
        profileHeading.setFont(boldFont);
        profileHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileHeading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        form.add(profileHeading);
        form.add(Box.createVerticalStrut(12));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        topRow.setOpaque(false);

        avatarPreview = new JPanel() {
            BufferedImage image;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int size = Math.min(getWidth(), getHeight());
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (image != null) {
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, size, size));
                    g2.drawImage(image, 0, 0, size, size, null);
                } else {
                    g2.setColor(blue);
                    g2.fillOval(0, 0, size, size);

                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
                    FontMetrics fm = g2.getFontMetrics();
                    String letter = "A";
                    int w = fm.stringWidth(letter);
                    int h = fm.getAscent();

                    g2.drawString(letter, (size - w) / 2, (size + h) / 2 - 4);
                }
                g2.dispose();
            }

            public void setImage(File file) {
                try {
                    image = ImageIO.read(file);
                    repaint();
                } catch (Exception ignored) {}
            }
        };
        avatarPreview.setPreferredSize(new Dimension(96, 96));

        JPanel avatarRight = new JPanel();
        avatarRight.setLayout(new BoxLayout(avatarRight, BoxLayout.Y_AXIS));
        avatarRight.setOpaque(false);

        RoundedButton changePhoto = new RoundedButton("Change Photo", false);
        changePhoto.setMaximumSize(new Dimension(140, 35));
        changePhoto.addActionListener(ev -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                try {
                    avatarPreview.getClass().getMethod("setImage", File.class).invoke(avatarPreview, f);
                } catch (Exception ignored) {}
            }
        });

        JLabel help = new JLabel("JPG, GIF or PNG. Max size 2MB");
        help.setFont(new Font("Segoe UI", Font.BOLD, 12));
        help.setForeground(Color.DARK_GRAY);

        avatarRight.add(changePhoto);
        avatarRight.add(Box.createVerticalStrut(6));
        avatarRight.add(help);

        topRow.add(avatarPreview);
        topRow.add(avatarRight);

        form.add(topRow);
        form.add(Box.createVerticalStrut(18));

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0;
        grid.add(labeledField("First Name", "Enter your first name"), gbc);
        gbc.gridx = 1;
        grid.add(labeledField("Last Name", "Enter your last name"), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        grid.add(labeledField("Email Address", "Enter your email address"), gbc);
        gbc.gridwidth = 1;

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

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        grid.add(labeledField("Pharmacy Name", "Enter your pharmacy name"), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        grid.add(labeledField("License Number", "Enter your license number"), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        grid.add(labeledField("Address", "Enter your address"), gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3;
        grid.add(labeledField("City", "Enter your city"), gbc);
        gbc.gridx = 1;
        grid.add(labeledField("Zip Code", "Enter your zip code"), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        grid.add(labeledField("Phone Number", "Enter your phone number"), gbc);
        gbc.gridx = 1;
        grid.add(labeledField("Email", "Enter your email"), gbc);

        card.add(grid);

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

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        grid.add(labeledField("Current Password", "Enter current password"), gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        grid.add(labeledField("New Password", "Enter new password"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        grid.add(labeledField("Confirm Password", "Re-enter password"), gbc);

        card.add(grid);
        card.add(Box.createVerticalStrut(18));

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

        JLabel sessions = new JLabel("Active Sessions");
        sessions.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sessions.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel sessionTitleWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sessionTitleWrap.setOpaque(false);
        sessionTitleWrap.add(sessions);

        card.add(Box.createVerticalStrut(-10));  
        card.add(sessionTitleWrap);
        card.add(Box.createVerticalStrut(6));  

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

        languageCombo = new JComboBox<>(new String[]{
                "English (US)",
                "Filipino",
                "Spanish"
        });

        timezoneCombo = new JComboBox<>(new String[]{
                "Asia/Manila",
                "UTC",
                "America/New_York",
                "Europe/London"
        });

        dateFormatCombo = new JComboBox<>(new String[]{
                "MM/DD/YYYY",
                "DD/MM/YYYY",
                "YYYY-MM-DD"
        });

        currencyCombo = new JComboBox<>(new String[]{
                "Philippine Peso (₱)",
                "US Dollar ($)",
                "Euro (€)"
        });

        card.add(keyValueDropdown("Language", languageCombo));
        card.add(keyValueDropdown("Timezone", timezoneCombo));
        card.add(keyValueDropdown("Date Format", dateFormatCombo));
        card.add(keyValueDropdown("Currency", currencyCombo));

        card.add(Box.createVerticalStrut(8));
        card.add(notificationRow("Compact Mode", "Reduce spacing for more content density", true));
        card.add(notificationRow("Show Animations", "Enable smooth transitions and animations", false));

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

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
        tf.putClientProperty("placeholder", placeholder);
        allFields.add(tf);
        tf.setFont(normalFont);
        tf.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        tf.setForeground(Color.DARK_GRAY);

        if ("First Name".equals(label)) {
            firstNameField = tf;
        } else if ("Last Name".equals(label)) {
            lastNameField = tf;
        } else if ("Email Address".equals(label)) {
            emailField = tf;
        } else if ("Phone Number".equals(label)) {
            if (phoneField == null) phoneField = tf;
            else pharmacyPhoneField = tf;
        } else if ("Role".equals(label)) {
            roleField = tf;
        } else if ("Pharmacy Name".equals(label)) {
            pharmacyNameField = tf;
        } else if ("License Number".equals(label)) {
            licenseNumberField = tf;
        } else if ("Address".equals(label)) {
            addressField = tf;
        } else if ("City".equals(label)) {
            cityField = tf;
        } else if ("Zip Code".equals(label)) {
            zipField = tf;
        } else if ("Email".equals(label)) {
            pharmacyEmailField = tf;
        } else if ("Current Password".equals(label)) {
            currentPassField = tf;
        } else if ("New Password".equals(label)) {
            newPassField = tf;
        } else if ("Confirm Password".equals(label)) {
            confirmPassField = tf;
        }

        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (tf.getText().equals(placeholder)) {
                    tf.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (tf.getText().trim().isEmpty()) {
                    tf.setText(placeholder);
                }
            }
        });

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

        SimpleToggle toggle = new SimpleToggle(initialState);
        allToggles.add(toggle);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setOpaque(false);
        right.add(toggle);

        row.add(left, BorderLayout.CENTER);
        row.add(right, BorderLayout.EAST);

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

    private JPanel keyValueDropdown(String key, JComboBox<String> combo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(8, 6, 8, 6));

        JLabel k = new JLabel(key);
        k.setFont(boldFont);

        combo.setFont(normalFont);
        combo.setPreferredSize(new Dimension(180, 28));

        p.add(k, BorderLayout.WEST);
        p.add(combo, BorderLayout.EAST);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));

        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.add(p, BorderLayout.CENTER);
        outer.add(sep, BorderLayout.SOUTH);

        return outer;
    }

    private class RoundedCardPanel extends JPanel {
        public RoundedCardPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 18;
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            g2.setColor(new Color(220, 220, 220));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

            g2.dispose();
            super.paintComponent(g);
        }

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
            if (on) g2.setColor(new Color(20, 20, 20)); 
            else g2.setColor(new Color(230, 230, 230));

            g2.fillRoundRect(0, 0, width, height, arc, arc);

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
                g2.setColor(new Color(92, 120, 224)); 
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
                g2.setColor(getBackground());    
                g2.fillRoundRect(0, 0, w, h, arc, arc);
            } else {
                g2.setColor(new Color(235, 238, 244));
                g2.fillRoundRect(0, 0, w, h, arc, arc);
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
            }

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
