import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class PharmaSysLogin extends JFrame {

    private enum Mode { LOGIN, REGISTER }

    private final JPanel card;
    private final JLabel topTitle;
    private final JLabel topSub;

    private final JPanel userLabelRow;
    private final JTextField usernameField;
    private final Component vStrutBeforeUser;

    private final JPanel passLabelRow;
    private final JPasswordField passwordField;
    private final JPanel passRow;
    private final JButton toggleEyeBtn;
    private final Component vStrutBeforePass;

    private final JPanel conLabelRow;
    private final JPasswordField confirmField;
    private final JPanel conRow;
    private final JButton toggleConfirmEyeBtn;
    private final Component vStrutBeforeConfirm;

    private final JPanel optionsRow;
    private final JCheckBox rememberMe;

    private final JButton loginBtn;
    private final JButton createBtn;
    private final JPanel createRowLink;

    private final JLabel backToLoginLink;

    private final Color bgBlue = new Color(79, 107, 201);
    private final Color cardBg = new Color(236, 243, 249);
    private final Color fieldBorder = new Color(185, 205, 250);
    private final Color actionBtnColor = new Color(25, 52, 214);

    private Mode mode = Mode.LOGIN;
    private final Map<String, String> accounts = new HashMap<>();

    public PharmaSysLogin() {

        accounts.put("admin", "1234");
        setTitle("PharmaSys - Login");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(bgBlue);
        add(background);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        ImageIcon rawLogo = new ImageIcon(getClass().getResource("/img/Login (1).jpg"));
        Image scaledLogoImg = rawLogo.getImage().getScaledInstance(90, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(scaledLogoImg);

        JLabel logoImage = new JLabel(scaledLogo);
        logoImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logo = new JLabel("PharmaSys");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Pharmacy Management System");
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(Box.createVerticalStrut(30));  
        header.add(logoImage);
        header.add(Box.createVerticalStrut(-5));   
        header.add(logo);
        header.add(Box.createVerticalStrut(0));   
        header.add(subtitleLabel);
        header.add(Box.createVerticalStrut(0));  


        background.add(header, BorderLayout.NORTH);

        JPanel centerHolder = new JPanel(new GridBagLayout());
        centerHolder.setOpaque(false);

        card = new RoundedPanel(40);
        card.setBackground(cardBg);
        card.setPreferredSize(new Dimension(450, 450));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        topTitle = new JLabel("Welcome");
        topTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));
        topTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        topSub = new JLabel("Please sign in to your account");
        topSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        topSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(topTitle);
        card.add(Box.createVerticalStrut(5));
        card.add(topSub);
        card.add(Box.createVerticalStrut(20));

        vStrutBeforeUser = Box.createVerticalStrut(30);
        vStrutBeforeUser.setVisible(false);
        card.add(vStrutBeforeUser);

        userLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        userLabelRow.setOpaque(false);
        JLabel userLbl = new JLabel("Username");
        userLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabelRow.add(userLbl);
        card.add(userLabelRow);

        usernameField = new JTextField();
        styleField(usernameField, fieldBorder);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(18, fieldBorder),
                new EmptyBorder(5, 0, 5, 8)
        ));

        usernameField.setMaximumSize(new Dimension(400, 40));
        usernameField.setPreferredSize(new Dimension(400, 40));
        card.add(usernameField);
        addPlaceholder(usernameField, "Enter your username");
        card.add(Box.createVerticalStrut(15));

        vStrutBeforePass = Box.createVerticalStrut(5);
        vStrutBeforePass.setVisible(false);
        card.add(vStrutBeforePass);

        passLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        passLabelRow.setOpaque(false);
        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabelRow.add(passLbl);
        card.add(passLabelRow);

        passRow = new JPanel(new BorderLayout());
        passRow.setOpaque(false);
        passRow.setMaximumSize(new Dimension(400, 40));
        passRow.setPreferredSize(new Dimension(400, 40));
    
        passRow.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(18, fieldBorder),
                new EmptyBorder(5, 0, 5, 8)
        ));

        passwordField = new JPasswordField();
        passwordField.setBorder(null);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passwordField.setOpaque(false);
        passwordField.setEchoChar((char) 0);


        toggleEyeBtn = new JButton("       👁");
        toggleEyeBtn.setFocusable(false);
        toggleEyeBtn.setBorder(null);
        toggleEyeBtn.setContentAreaFilled(false);
        toggleEyeBtn.setOpaque(false);
        toggleEyeBtn.setPreferredSize(new Dimension(35, 35));
        toggleEyeBtn.addActionListener(e -> {
            if (passwordField.getEchoChar() == '•') {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });


        passRow.add(passwordField, BorderLayout.CENTER);
        addPlaceholder(passwordField, "Enter your password");
        passRow.add(toggleEyeBtn, BorderLayout.EAST);
        card.add(passRow);
        card.add(Box.createVerticalStrut(5));

        optionsRow = new JPanel(new BorderLayout());
        optionsRow.setOpaque(false);

        rememberMe = new JCheckBox("Remember me");
        rememberMe.setOpaque(false);
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        optionsRow.add(rememberMe, BorderLayout.WEST);
        card.add(optionsRow);
        card.add(Box.createVerticalStrut(5));

        final boolean[] pressed = { false };

        loginBtn = new JButton("Log in") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                int offset = pressed[0] ? 2 : 0;

                g2.setColor(getBackground());
                g2.fillRoundRect(0, offset, getWidth(), getHeight() - offset, 22, 22);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                if (isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

                    g2.setColor(new Color(255, 255, 255, 180));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 22, 22);
                    g2.dispose();
                }
            }
        };

        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginBtn.setBackground(actionBtnColor);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setOpaque(false);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginBtn.setPreferredSize(new Dimension(380, 35));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginBtn.setBackground(loginBtn.getBackground().brighter());
                loginBtn.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginBtn.setBackground(actionBtnColor);
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

        loginBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
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

        loginBtn.addActionListener(e -> {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.equals("Enter your username") || username.isEmpty()) username = "";
        if (password.equals("Enter your password") || password.isEmpty()) password = "";

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter username and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (accounts.containsKey(username) &&
            accounts.get(username).equals(password)) {

            new PharmaSysDashboard().setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    });

        card.add(loginBtn);
        card.add(Box.createVerticalStrut(20));

        vStrutBeforeConfirm = Box.createVerticalStrut(-18);
        vStrutBeforeConfirm.setVisible(false);
        card.add(vStrutBeforeConfirm);

        conLabelRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 15));
        conLabelRow.setOpaque(false);
        JLabel conLbl = new JLabel("Confirm Password");
        conLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        conLabelRow.add(conLbl);
        conLabelRow.setVisible(false);
        card.add(conLabelRow);

        confirmField = new JPasswordField();

        addPlaceholder(confirmField, "Re-enter password...");
        conRow = createInputField(confirmField, fieldBorder, 18);
        conRow.setMaximumSize(new Dimension(400, 20));
        conRow.setOpaque(false);
        toggleConfirmEyeBtn = createEyeButton(confirmField);
        conRow.add(toggleConfirmEyeBtn, BorderLayout.EAST);
        conRow.setVisible(false);
        card.add(Box.createVerticalStrut(-10));
        card.add(conRow);

        createBtn = new JButton("Sign Up") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);

                super.paintComponent(g2);
                g2.dispose();
            }
        };
        createBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        createBtn.setBackground(actionBtnColor);
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.setContentAreaFilled(false);
        createBtn.setBorderPainted(false);
        createBtn.setOpaque(false);
        createBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        createBtn.setVisible(false);

        createBtn.addActionListener(e -> handleCreateAccount());
        card.add(Box.createVerticalStrut(25));
        card.add(createBtn);

        createRowLink = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        createRowLink.setOpaque(false);
        JLabel noAcc = new JLabel("Don’t have an account?");
        noAcc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        noAcc.setForeground(Color.BLACK);

        JLabel createAcc = new JLabel("Create account");
        createAcc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        createAcc.setForeground(Color.BLUE);
        setHoverUnderline(createAcc);

        createRowLink.add(noAcc);
        createRowLink.add(createAcc);

        createAcc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchToRegister();
            }
        });

        card.add(createRowLink);
        card.add(Box.createVerticalStrut(10));

        backToLoginLink = new JLabel("Already have an account?");
        backToLoginLink.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        backToLoginLink.setForeground(Color.BLUE);
        setHoverUnderline(backToLoginLink);
        backToLoginLink.setVisible(false);
        backToLoginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToLoginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchToLogin();
            }
        });
        card.add(backToLoginLink);

        centerHolder.add(card);
        background.add(centerHolder, BorderLayout.CENTER);

        setVisible(true);
    }

    private void switchToRegister() {
        mode = Mode.REGISTER;

        topTitle.setText("Create Account");
        topSub.setText("Enter your details to register");

        vStrutBeforeUser.setVisible(true);
        vStrutBeforePass.setVisible(true);
        vStrutBeforeConfirm.setVisible(true);

        userLabelRow.setVisible(true);
        usernameField.setVisible(true);

        passLabelRow.setVisible(true);
        passRow.setVisible(true);

        conLabelRow.setVisible(true);
        conRow.setVisible(true);
        createBtn.setVisible(true);

        loginBtn.setVisible(false);
        optionsRow.setVisible(false);
        createRowLink.setVisible(false);

        backToLoginLink.setVisible(true);

        addPlaceholder(confirmField, "Re-enter password...");

        revalidate();
        repaint();
    }

    private void switchToLogin() {
        mode = Mode.LOGIN;

        topTitle.setText("Welcome");
        topSub.setText("Please sign in to your account");

        userLabelRow.setVisible(true);
        usernameField.setVisible(true);
        passLabelRow.setVisible(true);
        passRow.setVisible(true);
        loginBtn.setVisible(true);
        optionsRow.setVisible(true);
        createRowLink.setVisible(true);

        vStrutBeforeUser.setVisible(false);
        vStrutBeforePass.setVisible(false);
        vStrutBeforeConfirm.setVisible(false);
        conLabelRow.setVisible(false);
        conRow.setVisible(false);
        createBtn.setVisible(false);

        backToLoginLink.setVisible(false);

        addPlaceholder(usernameField, "Enter your username");
        addPlaceholder(passwordField, "Enter your password");

        revalidate();
        repaint();
    }

        private void handleCreateAccount() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword());
        String c = new String(confirmField.getPassword());

        if (u.equals("Enter your username")) u = "";
        if (p.equals("Enter your password")) p = "";
        if (c.equals("Re-enter password...")) c = "";

        if (u.isEmpty() || p.isEmpty() || c.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill out all fields!",
                    "Incomplete Form",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!p.equals(c)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (accounts.containsKey(u)) {
            JOptionPane.showMessageDialog(this,
                    "Username already exists!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        accounts.put(u, p);

        JOptionPane.showMessageDialog(this,
                "Account successfully created!\nPlease log in.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        switchToLogin();
    }
    private JPanel createInputField(JComponent input, Color borderColor, int radius) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        row.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(radius, borderColor),
                new EmptyBorder(5, 0, 5, 8)
        ));

        input.setBorder(null);
        input.setOpaque(false);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        row.add(input, BorderLayout.CENTER);
        return row;
    }

    private JButton createEyeButton(JPasswordField field) {
        JButton eye = new JButton("👁");
        eye.setContentAreaFilled(false);
        eye.setBorder(null);
        eye.setFocusPainted(false);

        eye.addActionListener(e -> {
            if (field.getEchoChar() == '•') field.setEchoChar((char) 0);
            else field.setEchoChar('•');
        });

        return eye;
    }

    private void addPlaceholder(JTextComponent field, String placeholder) {
    field.setText(placeholder);
    field.setForeground(Color.GRAY);

    field.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (field.getText().equals(placeholder)) {
                field.setText("");
                field.setForeground(Color.BLACK);
                if (field instanceof JPasswordField) {
                    ((JPasswordField) field).setEchoChar('•');
                }
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (field.getText().isEmpty()) {
                field.setText(placeholder);
                field.setForeground(Color.GRAY);
                if (field instanceof JPasswordField) {
                    ((JPasswordField) field).setEchoChar((char) 0);
                }
            }
        }
    });
}
    private void styleField(JTextField field, Color borderColor) {
        field.setMaximumSize(new Dimension(340, 35));
        field.setPreferredSize(new Dimension(340, 35));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setOpaque(false);
        field.setBorder(new RoundedBorder(18, borderColor));
    }

    private void setHoverUnderline(JLabel label) {
        Font originalFont = label.getFont();
        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Map<TextAttribute, Object> underline = new HashMap<>();
                underline.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                label.setFont(originalFont.deriveFont(underline));
            }

            public void mouseExited(MouseEvent e) {
                label.setFont(originalFont);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PharmaSysLogin().setVisible(true));
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
