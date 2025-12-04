import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class PharmaSysForgotPassword extends JFrame {

    private final JTextField userField;

    public PharmaSysForgotPassword() {

        setTitle("PharmaSys - Forgot Password");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // COLORS
        Color bgBlue = new Color(79, 107, 201);
        Color cardBg = new Color(236, 243, 249);
        Color fieldBorder = new Color(185, 205, 250);
        Color btnColor = new Color(25, 52, 214);

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

        // CENTER HOLDE
        JPanel centerHolder = new JPanel(new GridBagLayout());
        centerHolder.setOpaque(false);

        // CARD PANEL
        JPanel card = new RoundedPanel(40);
        card.setBackground(cardBg);
        card.setPreferredSize(new Dimension(450, 330));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        // TITLE
        JLabel title = new JLabel("Forgot Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 19));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle2 = new JLabel("Enter your email or username to recover your account");
        subtitle2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle2.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitle2);
        card.add(Box.createVerticalStrut(20));

        // FIELD LABEL
        JPanel lblRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        lblRow.setOpaque(false);

        JLabel lblUser = new JLabel("Email / Username");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblRow.add(lblUser);
        card.add(lblRow);

        // INPUT FIELD
        userField = new JTextField();
        addPlaceholder(userField, "Enter email or username...");

        JPanel userRow = createInputField(userField, fieldBorder, 18);
        userRow.setOpaque(false);
        userRow.setMaximumSize(new Dimension(400, 35));
        card.add(userRow);
        card.add(Box.createVerticalStrut(80));

        // SUBMIT BUTTON
        JButton submitBtn = new JButton("Submit Request");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        submitBtn.setBackground(btnColor);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        submitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitBtn.addActionListener(e -> handleSubmit());
        card.add(Box.createVerticalStrut(15));
        card.add(submitBtn);

        centerHolder.add(card);
        background.add(centerHolder, BorderLayout.CENTER);
    }

    // ----------------------------------------------------
    // SUBMIT LOGIC
    // ----------------------------------------------------
    private void handleSubmit() {
        String input = userField.getText().trim();

        if (input.isEmpty() || input.equals("Enter email or username...")) {
            JOptionPane.showMessageDialog(this, "Please enter an email or username!");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "If this account exists, reset instructions have been sent.");
        dispose();
    }

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

    private void addPlaceholder(JTextComponent field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    public static void main(String[] args) {
        new PharmaSysForgotPassword().setVisible(true);
    }
}

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

class RoundedBorder implements javax.swing.border.Border {
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
