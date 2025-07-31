package Employee.EmployeeManagment;

import javax.swing.*;
import java.awt.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class UpdateEmployeeForm extends JFrame {

    private JTextField idField, nameField, ageField, positionField;
    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public UpdateEmployeeForm() {
        setTitle("Update Employee");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Update Employee Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Employee ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Position:"));
        positionField = new JTextField();
        formPanel.add(positionField);

        JButton fetchButton = new JButton("Fetch");
        fetchButton.addActionListener(e -> fetchEmployeeDetails());
        formPanel.add(fetchButton);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Update");
        JButton backButton = new JButton("Back");

        updateButton.addActionListener(e -> updateEmployee());
        backButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchEmployeeDetails() {
        String idStr = idField.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter employee ID.");
            return;
        }

        try (Session session = factory.openSession()) {
            int id = Integer.parseInt(idStr);
            Employee emp = session.get(Employee.class, id);

            if (emp != null) {
                nameField.setText(emp.getName());
                ageField.setText(String.valueOf(emp.getAge()));
                positionField.setText(emp.getPosition());
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee.");
        }
    }

    private void updateEmployee() {
        String idStr = idField.getText();
        String name = nameField.getText();
        String ageStr = ageField.getText();
        String position = positionField.getText();

        if (idStr.isEmpty() || name.isEmpty() || ageStr.isEmpty() || position.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            int id = Integer.parseInt(idStr);
            Employee emp = session.get(Employee.class, id);

            if (emp != null) {
                emp.setName(name);
                emp.setAge(Integer.parseInt(ageStr));
                emp.setPosition(position);

                session.update(emp);
                tx.commit();

                JOptionPane.showMessageDialog(this, "Employee updated successfully.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating employee.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");
    }
}
