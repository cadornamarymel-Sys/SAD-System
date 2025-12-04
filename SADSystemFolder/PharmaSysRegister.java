import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

// ----------------------------------------------------
// MAIN CLASS
// ----------------------------------------------------
public class PharmaSysRegister extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passField;
    private final JPasswordField confirmField;

    public PharmaSysRegister() {

        setTitle("PharmaSys - Create Account");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // COLORS
        Color bgBlue = new Color(79, 107, 201);
        Color cardBg = new Color(236, 243, 249);
        Color fieldBorder = new Color(185, 205, 250);
        Color createBtnColor = new Color(25, 52, 214);

        // MAIN BACKGROUND
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(bgBlue);
        add(background);

        // HEADER
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

        // CENTER HOLDER
        JPanel centerHolder = new JPanel(new GridBagLayout());
        centerHolder.setOpaque(false);

        JPanel card = new RoundedPanel(40);
        card.setBackground(cardBg);
        card.setPreferredSize(new Dimension(450, 450));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        // TITLE
        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 19));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle2 = new JLabel("Enter your details to register");
        subtitle2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle2.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitle2);
        card.add(Box.createVerticalStrut(20));

        // ----------------------------------------------------
        // USERNAME FIELD
        // ----------------------------------------------------
        JPanel userLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userLabelRow.setOpaque(false);
        JLabel userLbl = new JLabel("Username");
        userLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userLabelRow.add(userLbl);
        card.add(Box.createVerticalStrut(25));
        card.add(userLabelRow);

        usernameField = new JTextField();
        addPlaceholder(usernameField, "Enter username...");
        JPanel userRow = createInputField(usernameField, fieldBorder, 18);
        userRow.setOpaque(false);
        userRow.setMaximumSize(new Dimension(400, 35));
        
        card.add(userRow);

        // ----------------------------------------------------
        // PASSWORD FIELD
        // ----------------------------------------------------
        JPanel passLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passLabelRow.setOpaque(false);
        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passLabelRow.add(passLbl);
        card.add(Box.createVerticalStrut(25));
        card.add(passLabelRow);

        passField = new JPasswordField();
        addPlaceholder(passField, "Enter password...");

        
        JPanel passRow = createInputField(passField, fieldBorder, 1);
        passRow.setOpaque(false);
        passRow.setMaximumSize(new Dimension(400, 35));
        JButton eye1 = createEyeButton(passField);
        passRow.add(eye1, BorderLayout.EAST);

        card.add(passRow);

        // ----------------------------------------------------
        // CONFIRM PASSWORD
        // ----------------------------------------------------
        JPanel conLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        conLabelRow.setOpaque(false);
        JLabel conLbl = new JLabel("Confirm Password");
        conLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        conLabelRow.add(conLbl);
        card.add(Box.createVerticalStrut(25));
        card.add(conLabelRow);

        confirmField = new JPasswordField();
        addPlaceholder(confirmField, "Re-enter password...");

        
        JPanel conRow = createInputField(confirmField, fieldBorder, 1);
        conRow.setOpaque(false);
        conRow.setMaximumSize(new Dimension(400, 35));
        JButton eye2 = createEyeButton(confirmField);
        conRow.add(eye2, BorderLayout.EAST);
        
        card.add(conRow);

        // ----------------------------------------------------
        // CREATE BUTTON
        // ----------------------------------------------------
        JButton createBtn = new JButton("Create Account");
        createBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        createBtn.setBackground(createBtnColor);
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        createBtn.addActionListener(e -> handleCreate());
        card.add(Box.createVerticalStrut(15));
        card.add(createBtn);

        centerHolder.add(card);
        background.add(centerHolder, BorderLayout.CENTER);
    }

    // ----------------------------------------------------
    // CREATE INPUT FIELD (CONSISTENT LAYOUT)
    // ----------------------------------------------------
    private JPanel createInputField(JComponent input, Color borderColor, int radius) {

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        row.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(radius, borderColor),
            new EmptyBorder(5, 8, 5, 8)
        ));

        input.setBorder(null);
        input.setOpaque(false);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        row.add(input, BorderLayout.CENTER);
        return row;
    }

    // ----------------------------------------------------
    // EYE BUTTON (SHOW/HIDE PASSWORD)
    // ----------------------------------------------------
    private JButton createEyeButton(JPasswordField field) {

        JButton eye = new JButton("👁");
        eye.setContentAreaFilled(false);
        eye.setBorder(null);
        eye.setFocusPainted(false);

        eye.addActionListener(e -> {
            if (field.getEchoChar() == '•')
                field.setEchoChar((char) 0);
            else
                field.setEchoChar('•');
        });

        return eye;
    }

    // ----------------------------------------------------
    // PLACEHOLDER TEXT FOR FIELDS
    // ----------------------------------------------------
    private void addPlaceholder(JTextComponent field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField)
                        ((JPasswordField) field).setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField)
                        ((JPasswordField) field).setEchoChar((char) 0);
                }
            }
        });
    }

    // ----------------------------------------------------
    // CREATE ACCOUNT LOGIC
    // ----------------------------------------------------
    private void handleCreate() {

        String u = usernameField.getText().trim();
        String p = new String(passField.getPassword());
        String c = new String(confirmField.getPassword());

        if (u.isEmpty() || u.equals("Enter username...") ||
            p.isEmpty() || c.isEmpty() ||
            p.equals("Enter password...") ||
            c.equals("Re-enter password...")) {

            JOptionPane.showMessageDialog(this, "Please fill out all fields!");
            return;
        }

        if (!p.equals(c)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Account Created!\nUsername: " + u);
        dispose();
    }

    // For testing directly
    public static void main(String[] args) {
        new PharmaSysRegister().setVisible(true);
    }
}

// ----------------------------------------------------
// ROUNDED PANEL (FIXED FULLY WORKING)
// ----------------------------------------------------
class RoundedPanel extends JPanel {
    private int cornerRadius;

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.dispose();
    }
}

// ----------------------------------------------------
// ROUNDED BORDER
// ----------------------------------------------------
class RoundedBorder implements Border {
    private final int radius;
    private final Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 10, 5, 10);
    }

    @Override
    public boolean isBorderOpaque() { return false; }

    @Override
    public void paintBorder(Component c, Graphics g,
            int x, int y, int w, int h) {
        g.setColor(color);
        g.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
    }
}
