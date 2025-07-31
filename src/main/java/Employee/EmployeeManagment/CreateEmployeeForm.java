package Employee.EmployeeManagment;

import javax.swing.*;
import java.awt.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class CreateEmployeeForm extends JFrame {
    private JTextField nameField, ageField, positionField;

    public CreateEmployeeForm() {
        setTitle("Create Employee");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 247, 255));

        JLabel titleLabel = new JLabel("Create New Employee", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 153));

        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel positionLabel = new JLabel("Position:");

        nameField = new JTextField(20);
        ageField = new JTextField(20);
        positionField = new JTextField(20);

        JButton createButton = new JButton("Create");
        JButton backButton = new JButton("Back");

        createButton.setBackground(new Color(0, 153, 76));
        createButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(204, 0, 0));
        backButton.setForeground(Color.WHITE);

        createButton.addActionListener(e -> createEmployee());
        backButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(230, 247, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(nameLabel); formPanel.add(nameField);
        formPanel.add(ageLabel); formPanel.add(ageField);
        formPanel.add(positionLabel); formPanel.add(positionField);
        formPanel.add(createButton); formPanel.add(backButton);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createEmployee() {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String position = positionField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty() || position.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age.");
            return;
        }

        Employee emp = new Employee();
        emp.setName(name);
        emp.setAge(age);
        emp.setPosition(position);

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(emp);
            transaction.commit();

            JOptionPane.showMessageDialog(this, "Employee created successfully.");
            nameField.setText("");
            ageField.setText("");
            positionField.setText("");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating employee: " + e.getMessage());
        }
    }
}
