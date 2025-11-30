package SAD;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.AbstractBorder;
import java.awt.geom.RoundRectangle2D;

public class PharmaSysLogin extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMe;
    private JButton toggleEyeBtn;

    public PharmaSysLogin() {
        setTitle("PharmaSys - Login");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- COLORS ---
        Color bgBlue = new Color(79, 107, 201);
        Color cardBg = new Color(236, 243, 249);
        Color fieldBorder = new Color(185, 205, 250);
        Color loginBtnColor = new Color(25, 52, 214);

        // --- MAIN BACKGROUND PANEL ---
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(bgBlue);
        add(background);

        // --- TOP BRAND ---
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("PharmaSys");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Pharmacy Management System");
        subtitle.setForeground(Color.BLACK);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(Box.createVerticalStrut(30));
        header.add(logo);
        header.add(subtitle);
        header.add(Box.createVerticalStrut(15));

        background.add(header, BorderLayout.NORTH);

        // --- CENTER CARD ---
        JPanel centerHolder = new JPanel(new GridBagLayout());
        centerHolder.setOpaque(false);

        JPanel card = new RoundedPanel(40);  // <- radius size (bigger = rounder edges)
        card.setBackground(cardBg);
        card.setPreferredSize(new Dimension(450, 450));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- TEXT ---
        JLabel welcome = new JLabel("Welcome");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 19));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel signText = new JLabel("Please sign in to your account");
        signText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        signText.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(welcome);
        card.add(Box.createVerticalStrut(5));
        card.add(signText);
        card.add(Box.createVerticalStrut(20));

        // --- USERNAME ---
        JPanel userLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userLabelRow.setOpaque(false);

        JLabel userLbl = new JLabel("Username");
        userLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        userLabelRow.add(userLbl);
        card.add(userLabelRow);

        usernameField = new JTextField();
        styleField(usernameField, fieldBorder);

        // placeholder behavior: only remove when user types (not on focus)
        final String placeholder = "  Enter your username";
        usernameField.setText(placeholder);
        usernameField.setForeground(Color.GRAY);

        // track whether placeholder is showing
        final boolean[] showingPlaceholder = { true };

        // Remove placeholder only when user starts typing
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (showingPlaceholder[0]) {
                    // clear placeholder so the typed key becomes the first character
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                    showingPlaceholder[0] = false;
                    // Note: do not consume the event; the typed char will still be inserted
                }
            }
        });

        // If the user leaves the field empty, restore the placeholder
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(placeholder);
                    usernameField.setForeground(Color.GRAY);
                    showingPlaceholder[0] = true;
                }
            }
        });

        usernameField.setMaximumSize(new Dimension(400, 35));
        usernameField.setPreferredSize(new Dimension(400, 35));

        card.add(usernameField);
        card.add(Box.createVerticalStrut(15));

        // --- PASSWORD ---

        JPanel passLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passLabelRow.setOpaque(false);

        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        passLabelRow.add(passLbl);
        card.add(passLabelRow);

        JPanel passRow = new JPanel(new BorderLayout());
        passRow.setOpaque(false);
        passRow.setMaximumSize(new Dimension(400, 35));
        passRow.setPreferredSize(new Dimension(400, 35));

        passRow.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(18, fieldBorder),
                new EmptyBorder(5, 8, 5, 8)
        ));

        passwordField = new JPasswordField();
        passwordField.setBorder(null);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passwordField.setOpaque(false);

        // ✅ START with NO echo char so placeholder text is visible
        passwordField.setEchoChar((char) 0);

        final String passwordPlaceholder = "  Enter your password";
        passwordField.setText(passwordPlaceholder);
        passwordField.setForeground(Color.GRAY);

        final boolean[] showingPasswordPlaceholder = { true };

        // ✅ Remove placeholder only when user types
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (showingPasswordPlaceholder[0]) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);

                    // ✅ Turn ON masking when real input starts
                    passwordField.setEchoChar('•');

                    showingPasswordPlaceholder[0] = false;
                }
            }
        });

        // ✅ Restore placeholder when empty on focus loss
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(passwordPlaceholder);
                    passwordField.setForeground(Color.GRAY);

                    // ✅ Turn OFF masking to show placeholder again
                    passwordField.setEchoChar((char) 0);

                    showingPasswordPlaceholder[0] = true;
                }
            }
        });

        toggleEyeBtn = new JButton("👁");
        toggleEyeBtn.setFocusable(false);
        toggleEyeBtn.setBorder(null);
        toggleEyeBtn.setContentAreaFilled(false);
        toggleEyeBtn.setOpaque(false);
        toggleEyeBtn.setPreferredSize(new Dimension(35, 35));

        toggleEyeBtn.addActionListener(e -> {
            if (!showingPasswordPlaceholder[0]) {
                if (passwordField.getEchoChar() == '•')
                    passwordField.setEchoChar((char) 0);
                else
                    passwordField.setEchoChar('•');
            }
        });

        passRow.add(passwordField, BorderLayout.CENTER);
        passRow.add(toggleEyeBtn, BorderLayout.EAST);

        card.add(passRow);
        card.add(Box.createVerticalStrut(12));

        // --- REMEMBER & FORGOT ---
        JPanel optionsRow = new JPanel(new BorderLayout());
        optionsRow.setOpaque(false);

        rememberMe = new JCheckBox("Remember me");
        rememberMe.setOpaque(false);
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel forgot = new JLabel("Forgot password?");
        forgot.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgot.setForeground(Color.BLUE);
        setHoverUnderline(forgot);

        optionsRow.add(rememberMe, BorderLayout.WEST);
        optionsRow.add(forgot, BorderLayout.EAST);

        card.add(optionsRow);
        card.add(Box.createVerticalStrut(25));

        // --- LOGIN BUTTON ---
        final boolean[] pressed = { false };

        JButton loginBtn = new JButton("Log in") {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);

                // Move DOWN when pressed
                int offset = pressed[0] ? 2 : 0;

                // --- background ---
                g2.setColor(getBackground());
                g2.fillRoundRect(0, offset, getWidth(),
                        getHeight() - offset,
                        22, 22);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Focus glow border
                if (isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

                    g2.setColor(new Color(255, 255, 255, 180));
                    g2.drawRoundRect(1, 1,
                                    getWidth() - 3,
                                    getHeight() - 3,
                                    22, 22);
                    g2.dispose();
                }
            }
        };

        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginBtn.setBackground(loginBtnColor);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setOpaque(false);

        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginBtn.setPreferredSize(new Dimension(380, 35));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Hover highlight ---
        loginBtn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                loginBtn.setBackground(loginBtn.getBackground().brighter());
                loginBtn.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginBtn.setBackground(loginBtnColor);
                loginBtn.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ((JButton) e.getSource()).getModel().setArmed(true);
                pressed[0] = true;
                loginBtn.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed[0] = false;
                loginBtn.repaint();
            }
        });

        // --- Keyboard press animation (ENTER / SPACE) ---
        loginBtn.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER ||
                    e.getKeyCode() == KeyEvent.VK_SPACE) {
                    pressed[0] = true;
                    loginBtn.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressed[0] = false;
                loginBtn.repaint();
            }
        });

        card.add(loginBtn);
        card.add(Box.createVerticalStrut(20));

        // --- LOGIN ACTION (Open Dashboard) ---
        loginBtn.addActionListener(e -> {
            new PharmaSysDashboard().setVisible(true);
            dispose();
        });

        // --- CREATE ACCOUNT TEXT (SEPARATE COLORS) ---
        JPanel createRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        createRow.setOpaque(false);

        JLabel noAcc = new JLabel("Don’t have an account?");
        noAcc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        noAcc.setForeground(Color.BLACK);

        JLabel createAcc = new JLabel("Create account");
        createAcc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        createAcc.setForeground(Color.BLUE);
        setHoverUnderline(createAcc);


        createRow.add(noAcc);
        createRow.add(createAcc);

        card.add(createRow);

        centerHolder.add(card);
        background.add(centerHolder, BorderLayout.CENTER);
    }
    class RoundedBorder extends AbstractBorder {

        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g,
                                int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(
                    x + 0.5, y + 0.5,
                    width - 1, height - 1,
                    radius, radius));

            g2.dispose();
        }
    }
    private void setHoverUnderline(JLabel label) {
    Font originalFont = label.getFont();

    label.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            Map<TextAttribute, Object> underline = new HashMap<>();
            underline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            label.setFont(originalFont.deriveFont(underline));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Remove underline
            label.setFont(originalFont);
        }
    });
}

    // --- FIELD STYLE METHOD ---
    private void styleField(JTextField field, Color borderColor) {

    field.setMaximumSize(new Dimension(340, 35));
    field.setPreferredSize(new Dimension(340, 35));
    field.setFont(new Font("Segoe UI", Font.PLAIN, 12));

    field.setOpaque(false);
    field.setBorder(new RoundedBorder(18, borderColor));
}

class RoundedPanel extends JPanel {

    private final int cornerRadius;

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
        setOpaque(false); // VERY important for rounded edges
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(),
                         cornerRadius, cornerRadius);

        g2.dispose();
    }
}
    // --- RUN ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PharmaSysLogin().setVisible(true);
        });
    }
}
