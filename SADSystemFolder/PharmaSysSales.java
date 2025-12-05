import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PharmaSysSales extends JPanel {

    Color bgGray   = new Color(208,217,232);
    Color cardGray = new Color(236,243,249);
    Color blue     = new Color(25,52,214);

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
        JPanel topRow = new JPanel(new GridLayout(1,2,20,0));
        topRow.setOpaque(false);

        // SEARCH CARD
        JPanel searchCard = cardPanel("Shopping Cart");
        JTextField searchField = new JTextField(" Search medicine by name...");
        searchField.setForeground(Color.GRAY);
        searchField.setOpaque(true);                     
        searchField.setBackground(cardGray);
        searchField.setBorder(new CompoundBorder(
                new RoundedFieldBorder(new Color(215,215,215), 14),
                new EmptyBorder(8,10,8,360)
        ));
        // Add focus listener for placeholder behavior
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

       // SEARCH WRAP (inside Shopping Cart)
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
        lbl.setBorder(new EmptyBorder(0, 0, 0, 0));

        JTextField nameField = new JTextField(" Enter customer name");
        nameField.setForeground(Color.GRAY);
        nameField.setOpaque(true);                       
        nameField.setBackground(cardGray);               
        nameField.setBorder(new CompoundBorder(
                new RoundedFieldBorder(new Color(215,215,215), 14),
                new EmptyBorder(8,10,8,395)
        ));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add focus listener for placeholder behavior
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

        // CUSTOMER WRAP (inside Customer Details)
        JPanel customerWrap = new JPanel(new BorderLayout());
        customerWrap.setOpaque(false);
        customerWrap.setBorder(new EmptyBorder(0, 0, 0, 14)); 
        customerWrap.add(customerBox, BorderLayout.WEST);

        customerCard.add(customerWrap, BorderLayout.SOUTH);

        topRow.add(searchCard);
        topRow.add(customerCard);

        //---------------- BOTTOM ROW ----------------//
        JPanel bottomRow = new JPanel(new GridLayout(1,2,20,0));
        bottomRow.setOpaque(false);

        // CART CARD
        JPanel cartCard = cardPanel("Shopping Cart (0 items)");
        JPanel cartEmpty = new JPanel();
        cartEmpty.setOpaque(false);
        cartEmpty.setLayout(new BoxLayout(cartEmpty, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("🛒", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel empty = new JLabel("No items in cart");
        empty.setFont(new Font("Segoe UI", Font.BOLD, 12));
        empty.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hint = new JLabel("Search and add medicines above");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hint.setForeground(Color.GRAY);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        cartEmpty.add(Box.createVerticalGlue());
        cartEmpty.add(icon);
        cartEmpty.add(Box.createVerticalStrut(8));
        cartEmpty.add(empty);
        cartEmpty.add(Box.createVerticalStrut(4));
        cartEmpty.add(hint);
        cartEmpty.add(Box.createVerticalGlue());

        cartCard.add(cartEmpty, BorderLayout.CENTER);

        // PAYMENT CARD
        JPanel paymentCard = cardPanel("Payment Summary");
        paymentCard.setBackground(new Color(245, 248, 255));     
        paymentCard.setBorder(new EmptyBorder(12, 14, 18, 14));   
        JPanel summary = new JPanel();
        summary.setLayout(new BoxLayout(summary, BoxLayout.Y_AXIS));
        summary.setOpaque(false);
        summary.setAlignmentX(Component.LEFT_ALIGNMENT);

        summary.add(labelRow("Subtotal:", "₱0.00"));
        summary.add(Box.createVerticalStrut(6));
        summary.add(labelRow("VAT (12%):", "₱0.00"));

        summary.add(Box.createVerticalStrut(12));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 208, 220));   
        summary.add(sep);


        JLabel total = new JLabel("Total: ₱0.00");
        total.setFont(new Font("Segoe UI", Font.BOLD, 14));
        total.setForeground(blue);
        total.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- TOTAL ROW (aligned like Subtotal & VAT) ---
        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);

        JLabel totalLabelLeft = new JLabel("Total:");
        totalLabelLeft.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabelLeft.setForeground(blue);

        JLabel totalLabelRight = new JLabel("₱0.00");
        totalLabelRight.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabelRight.setForeground(blue);

        totalRow.add(totalLabelLeft, BorderLayout.WEST);
        totalRow.add(totalLabelRight, BorderLayout.EAST);

        summary.add(Box.createVerticalStrut(6));
        summary.add(totalRow);
        summary.add(Box.createVerticalStrut(12));

        JComboBox<String> pay = new JComboBox<>();
        JLabel payLabel = smallLabel("Payment Method");
        payLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.add(payLabel);  

        // WRAPPER so Payment Method aligns with ₱0.00
        JPanel payWrap = new JPanel();
        payWrap.setLayout(new BoxLayout(payWrap, BoxLayout.X_AXIS));
        payWrap.setOpaque(true);

        pay.addItem("Cash");
        pay.setBackground(Color.WHITE);
        pay.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));
        pay.setPreferredSize(new Dimension(0, 32));
        pay.setAlignmentX(Component.LEFT_ALIGNMENT);
        pay.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        pay.setAlignmentX(Component.LEFT_ALIGNMENT);
        payWrap.add(pay);
        summary.add(payWrap);

        summary.add(Box.createVerticalStrut(8));
        
        JLabel amountLabel = smallLabel("Amount Receive");
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.add(amountLabel);

        // WRAPPER so Amount Receive aligns with ₱0.00
        JPanel receiveWrap = new JPanel();
        receiveWrap.setLayout(new BoxLayout(receiveWrap, BoxLayout.X_AXIS));
        receiveWrap.setOpaque(true);

        JTextField received = new JTextField("0.00");
        received.setOpaque(true);
        received.setBackground(Color.WHITE);
        received.setBorder(new CompoundBorder(
                new LineBorder(new Color(210, 210, 210), 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        received.setAlignmentX(Component.LEFT_ALIGNMENT);
        receiveWrap.add(received);
        summary.add(receiveWrap);

        summary.add(Box.createVerticalStrut(15));

        JButton complete = new JButton("   🛒   Complete Sale  -  ₱0.00");
        complete.setBackground(blue);
        complete.setForeground(Color.WHITE);
        complete.setFocusPainted(false);
        complete.setPreferredSize(new Dimension(0, 44));
        complete.setBorder(new EmptyBorder(10, 20, 10, 20));
        complete.setFont(new Font("Segoe UI", Font.BOLD, 13));
        complete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        complete.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        complete.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton print = new JButton("   🖨️   Print Receipt");
        print.setFocusPainted(false);
        print.setPreferredSize(new Dimension(0, 44));
        print.setBorder(new LineBorder(new Color(40, 60, 160), 2, true));
        print.setBackground(new Color(245, 248, 255));
        print.setFont(new Font("Segoe UI", Font.BOLD, 13));
        print.setCursor(new Cursor(Cursor.HAND_CURSOR));
        print.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        print.setAlignmentX(Component.CENTER_ALIGNMENT);

        summary.add(complete);
        summary.add(Box.createVerticalStrut(8));
        summary.add(print);

        paymentCard.add(summary, BorderLayout.CENTER);

        bottomRow.add(cartCard);
        bottomRow.add(paymentCard);

        //---------------- SIZES ----------------//
        searchCard.setPreferredSize(new Dimension(500,135));
        customerCard.setPreferredSize(new Dimension(500,135));

        cartCard.setPreferredSize(new Dimension(500,320));
        paymentCard.setPreferredSize(new Dimension(500,400));

        //---------------- BODY ----------------//
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);

        // ✅ WRAP HEADER TO MATCH CARD LEFT ALIGNMENT
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

    }

    //---------------- HELPERS ----------------//

    private JPanel cardPanel(String title) {

    JPanel card = new JPanel(new BorderLayout()) {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            // Rounded background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            // Rounded border
            g2.setColor(new Color(215,215,215));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
        }
    };

    card.setOpaque(false);
    card.setBackground(cardGray);
    card.setBorder(new EmptyBorder(12,14,12,0));

    JLabel t = new JLabel(title);
    t.setFont(new Font("Segoe UI", Font.BOLD, 12));
    t.setBorder(new EmptyBorder(0,0,8,0));

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
        lbl.setBorder(new EmptyBorder(4,0,4,0));
        return lbl;
    }

}
