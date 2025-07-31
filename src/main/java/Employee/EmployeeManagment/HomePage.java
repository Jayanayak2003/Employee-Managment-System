package Employee.EmployeeManagment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Employee Management System");
        setSize(600, 500);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title panel
        JLabel title = new JLabel("Employee Management System", JLabel.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(30, 60, 120));
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        mainPanel.add(title);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 248, 255));
        buttonPanel.setLayout(new GridLayout(5, 1, 20, 20));
        buttonPanel.setMaximumSize(new Dimension(300, 300));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        String[] buttonLabels = {
            "Create Employee",
            "Read Employee",
            "Update Employee",
            "Delete Employee",
            "Exit"
        };

        JButton[] buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton btn = new JButton(buttonLabels[i]);
            btn.setBackground(new Color(76, 145, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Rounded edges
            btn.setUI(new RoundedButtonUI());

            // Hover effect
            int finalI = i;
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setBackground(new Color(50, 115, 220));
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setBackground(new Color(76, 145, 255));
                }
            });

            buttons[i] = btn;
            buttonPanel.add(btn);
        }

        // Actions
        buttons[0].addActionListener(e -> {
            dispose();
            new CreateEmployeeForm();
        });
        buttons[1].addActionListener(e -> {
            dispose();
            new ReadEmployeeForm();
        });
        buttons[2].addActionListener(e -> {
            dispose();
            new UpdateEmployeeForm();
        });
        buttons[3].addActionListener(e -> {
            dispose();
            new DeleteEmployeeForm();
        });
        buttons[4].addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        mainPanel.add(buttonPanel);
        add(mainPanel);
        setVisible(true);
    }

    // Inner class for rounded buttons
    class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            AbstractButton button = (AbstractButton) c;
            button.setOpaque(false);
            button.setBorderPainted(false);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
            super.paint(g, c);
        }

        private void paintBackground(Graphics g, JComponent c, int yOffset) {
            Dimension size = c.getSize();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(c.getBackground().darker());
            g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 30, 30);
        }
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
