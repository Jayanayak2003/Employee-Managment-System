package Employee.EmployeeManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Employee.EmployeeManagment.Employee;
import util.HibernateUtil;

public class DeleteEmployeeForm extends JFrame {

    private JTextField idField;

    public DeleteEmployeeForm() {
        setTitle("Delete Employee");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(250, 250, 250));

        JLabel heading = new JLabel("Delete Employee");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(220, 53, 69)); // Bootstrap danger color
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(new EmptyBorder(20, 10, 10, 10));

        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        idField = new JTextField();
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        idField.setColumns(10);

        JButton deleteButton = new JButton("🗑️ Delete");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new Dimension(120, 35));

        deleteButton.addActionListener(e -> deleteEmployee());

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(230, 230, 230));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(10, 40, 10, 40));
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(heading, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void deleteEmployee() {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();

                Employee emp = session.get(Employee.class, id);
                if (emp != null) {
                    session.delete(emp);
                    tx.commit();

                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    idField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "No employee found with that ID.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting employee.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                session.close();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
