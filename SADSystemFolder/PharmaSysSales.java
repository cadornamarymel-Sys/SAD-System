import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PharmaSysSales extends JPanel {

    Color bgGray = new Color(208, 217, 232);
    Color cardGray = new Color(236, 243, 249);
    Color blue = new Color(25, 52, 214);

    // ================= CART SYSTEM ================= //
    private List<CartItem> cart = new ArrayList<>();

    private static class CartItem {
        String name;
        double price;
        int qty;

        CartItem(String n, double p, int q) {
            name = n;
            price = p;
            qty = q;
        }

        double getTotal() {
            return price * qty;
        }
    }

    // Rounded border for text fields
    private static class RoundedFieldBorder extends LineBorder {

        private final int radius;

        public RoundedFieldBorder(Color color, int radius) {
            super(color, 1, true);
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g,
                                int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(lineColor);
            g2.drawRoundRect(x, y,
                    width - 1,
                    height - 1,
                    radius,
                    radius);
        }
    }

    // ===== Instance fields needed across methods =====
    private JPanel cartList;
    private JLabel subtotalLabelRight;
    private JLabel vatLabelRight;
    private JLabel totalLabelRight;
    private JButton complete;
    private JPanel cartCard;

    public PharmaSysSales() {

        setLayout(new BorderLayout());
        setOpaque(false);

        //---------------- HEADER ----------------//
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel title = new JLabel("Sales");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel subtitle = new JLabel("Process customer transactions and manage sales");
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        subtitle.setForeground(Color.DARK_GRAY);

        header.add(title);
        header.add(Box.createVerticalStrut(2));
        header.add(subtitle);

        //---------------- TOP ROW ----------------//
        JPanel topRow = new JPanel(new GridLayout(1, 2, 20, 0));
        topRow.setOpaque(false);

        // SEARCH CARD
        JPanel searchCard = cardPanel("Shopping Cart");
        JTextField searchField = new JTextField(" Search medicine by name...");
        searchField.setForeground(Color.GRAY);
        searchField.setOpaque(true);
        searchField.setBackground(cardGray);
        searchField.setBorder(new CompoundBorder(
                new RoundedFieldBorder(new Color(215, 215, 215), 14),
                new EmptyBorder(8, 10, 8, 360)
        ));

        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals(" Search medicine by name...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(" Search medicine by name...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setOpaque(false);
        searchWrap.setBorder(new EmptyBorder(0, 0, 0, 14));
        searchWrap.add(searchField, BorderLayout.WEST);

        searchCard.add(searchWrap, BorderLayout.SOUTH);

        // CUSTOMER CARD
        JPanel customerCard = cardPanel("Customer Details");
        JPanel customerBox = new JPanel();
        customerBox.setLayout(new BoxLayout(customerBox, BoxLayout.Y_AXIS));
        customerBox.setOpaque(false);
        customerBox.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel lbl = new JLabel("Customer Name (Optional)");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField nameField = new JTextField(" Enter customer name");
        nameField.setForeground(Color.GRAY);
        nameField.setOpaque(true);
        nameField.setBackground(cardGray);
        nameField.setBorder(new CompoundBorder(
                new RoundedFieldBorder(new Color(215, 215, 215), 14),
                new EmptyBorder(8, 10, 8, 395)
        ));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (nameField.getText().equals(" Enter customer name")) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText(" Enter customer name");
                    nameField.setForeground(Color.GRAY);
                }
            }
        });

        customerBox.add(lbl);
        customerBox.add(Box.createVerticalStrut(4));
        customerBox.add(nameField);

        JPanel customerWrap = new JPanel(new BorderLayout());
        customerWrap.setOpaque(false);
        customerWrap.setBorder(new EmptyBorder(0, 0, 0, 14));
        customerWrap.add(customerBox, BorderLayout.WEST);

        customerCard.add(customerWrap, BorderLayout.SOUTH);

        topRow.add(searchCard);
        topRow.add(customerCard);

        //---------------- BOTTOM ROW ----------------//
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomRow.setOpaque(false);

        // CART CARD
        cartCard = cardPanel("Shopping Cart (0 items)");
        cartList = new JPanel();
        cartList.setLayout(new BoxLayout(cartList, BoxLayout.Y_AXIS));
        cartList.setOpaque(false);

        JScrollPane cartScroll = new JScrollPane(cartList);
        cartScroll.setBorder(null);
        cartScroll.setOpaque(false);
        cartScroll.getViewport().setOpaque(false);

        cartCard.add(cartScroll, BorderLayout.CENTER);

        // PAYMENT CARD
        JPanel paymentCard = cardPanel("Payment Summary");
        paymentCard.setBackground(new Color(245, 248, 255));
        paymentCard.setBorder(new EmptyBorder(12, 14, 18, 14));

        JPanel summary = new JPanel();
        summary.setLayout(new BoxLayout(summary, BoxLayout.Y_AXIS));
        summary.setOpaque(false);
        summary.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel subRow = labelRow("Subtotal:", "₱0.00");
        subtotalLabelRight = (JLabel) subRow.getComponent(1);
        summary.add(subRow);
        summary.add(Box.createVerticalStrut(6));

        JPanel vatRow = labelRow("VAT (12%):", "₱0.00");
        vatLabelRight = (JLabel) vatRow.getComponent(1);
        summary.add(vatRow);

        summary.add(Box.createVerticalStrut(25));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 208, 220));
        summary.add(sep);

        // Total row
        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);

        JLabel totalLabelLeft = new JLabel("Total:");
        totalLabelLeft.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabelLeft.setForeground(blue);

        totalLabelRight = new JLabel("₱0.00");
        totalLabelRight.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabelRight.setForeground(blue);

        totalRow.add(totalLabelLeft, BorderLayout.WEST);
        totalRow.add(totalLabelRight, BorderLayout.EAST);

        summary.add(Box.createVerticalStrut(6));
        summary.add(totalRow);
        summary.add(Box.createVerticalStrut(12));

        // Payment method
        JComboBox<String> pay = new JComboBox<>();
        pay.addItem("Cash");
        pay.addItem("GCash");
        pay.setBackground(Color.WHITE);
        pay.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));
        pay.setPreferredSize(new Dimension(0, 32));
        pay.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        JLabel payLabel = smallLabel("Payment Method");
        payLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        payLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        summary.add(payLabel);
        summary.add(pay);

        summary.add(Box.createVerticalStrut(8));

        JLabel amountLabel = smallLabel("Amount Received");
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        summary.add(amountLabel);

        JTextField received = new JTextField("0.00");
        received.setOpaque(true);
        received.setBackground(Color.WHITE);
        received.setBorder(new CompoundBorder(
                new LineBorder(new Color(210, 210, 210), 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        summary.add(received);

        summary.add(Box.createVerticalStrut(15));

        // Complete sale button
        complete = new JButton("🛒 Complete Sale - ₱0.00");
        complete.setBackground(blue);
        complete.setForeground(Color.WHITE);
        complete.setFocusPainted(false);
        complete.setPreferredSize(new Dimension(0, 44));
        complete.setFont(new Font("Segoe UI", Font.BOLD, 13));
        complete.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        complete.addActionListener(e -> {
            String customer = nameField.getText().equals(" Enter customer name") ? "Walk-in Customer" : nameField.getText();
            String amountReceived = received.getText();
            String paymentMethod = (String) pay.getSelectedItem();

            JOptionPane.showMessageDialog(
                    this,
                    "✅ Sale Completed!\n\n"
                            + "Customer: " + customer + "\n"
                            + "Payment Method: " + paymentMethod + "\n"
                            + "Amount Received: ₱" + amountReceived + "\n"
                            + "Total: " + totalLabelRight.getText() + "\n\n"
                            + "Thank you!",
                    "Sale Completed",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cart.clear();
            refreshCart();
        });

        // Print receipt button
        JButton print = new JButton("🖨️ Print Receipt");
        print.setFocusPainted(false);
        print.setPreferredSize(new Dimension(0, 44));
        print.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        print.setBorder(new LineBorder(new Color(40, 60, 160), 2, true));
        print.setBackground(new Color(245, 248, 255));
        print.setFont(new Font("Segoe UI", Font.BOLD, 13));

        print.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "🖨️ Printing receipt...\n(This is a placeholder.)",
                    "Receipt",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Wrap buttons in a left-aligned panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Pull panel 14px to the left to match card edge
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Complete sale button
        complete.setAlignmentX(Component.LEFT_ALIGNMENT);
        complete.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        buttonPanel.add(complete);
        buttonPanel.add(Box.createVerticalStrut(12));

        // Print receipt button
        print.setAlignmentX(Component.LEFT_ALIGNMENT);
        print.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        buttonPanel.add(print);

        // Add to summary
        summary.add(buttonPanel);

        paymentCard.add(summary, BorderLayout.NORTH);
        paymentCard.add(buttonPanel, BorderLayout.SOUTH);

        bottomRow.add(cartCard);
        bottomRow.add(paymentCard);

        //---------------- SIZES ----------------//
        searchCard.setPreferredSize(new Dimension(500, 135));
        customerCard.setPreferredSize(new Dimension(500, 135));

        cartCard.setPreferredSize(new Dimension(500, 320));
        paymentCard.setPreferredSize(new Dimension(500, 400));

        //---------------- BODY ----------------//
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);

        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(Box.createHorizontalStrut(0), BorderLayout.WEST);
        headerWrap.add(header, BorderLayout.CENTER);

        body.add(headerWrap);
        body.add(Box.createVerticalStrut(10));
        body.add(topRow);
        body.add(Box.createVerticalStrut(12));
        body.add(bottomRow);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(body, BorderLayout.NORTH);

        add(wrapper, BorderLayout.CENTER);

        //---------------- SEARCH FUNCTION ----------------//
        searchField.addActionListener(e -> {
            String query = searchField.getText().trim();

            Map<String, Double> db = new HashMap<>();
            db.put("Biogesic", 5.00);
            db.put("Paracetamol", 3.50);
            db.put("Vitamin C", 8.00);

            if (db.containsKey(query)) {
                cart.add(new CartItem(query, db.get(query), 1));
                refreshCart();
            } else {
                JOptionPane.showMessageDialog(this, "No result found");
            }
        });

        // Initial refresh
        refreshCart();
    }

    // ================= HELPERS ================= //

    private JPanel cardPanel(String title) {

        JPanel card = new JPanel(new BorderLayout()) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(215, 215, 215));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
            }
        };

        card.setOpaque(false);
        card.setBackground(cardGray);
        card.setBorder(new EmptyBorder(12, 14, 12, 0));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setBorder(new EmptyBorder(0, 0, 8, 0));

        card.add(t, BorderLayout.NORTH);

        return card;
    }

    private JPanel labelRow(String left, String right) {

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel l = new JLabel(left);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JLabel r = new JLabel(right);
        r.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        row.add(l, BorderLayout.WEST);
        row.add(r, BorderLayout.EAST);

        return row;
    }

    private JLabel smallLabel(String text) {

        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setBorder(new EmptyBorder(4, 0, 4, 0));
        return lbl;
    }

    private void refreshCart() {
        cartList.removeAll();

        double subtotal = 0;
        for (CartItem item : cart) {
            JLabel lbl = new JLabel(item.name + " x" + item.qty + " - ₱" + String.format("%.2f", item.getTotal()));
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            cartList.add(lbl);
            cartList.add(Box.createVerticalStrut(4));
            subtotal += item.getTotal();
        }

        double vat = subtotal * 0.12;
        double total = subtotal + vat;

        subtotalLabelRight.setText("₱" + String.format("%.2f", subtotal));
        vatLabelRight.setText("₱" + String.format("%.2f", vat));
        totalLabelRight.setText("₱" + String.format("%.2f", total));

        complete.setText("🛒 Complete Sale - ₱" + String.format("%.2f", total));

        cartList.revalidate();
        cartList.repaint();
    }
}
