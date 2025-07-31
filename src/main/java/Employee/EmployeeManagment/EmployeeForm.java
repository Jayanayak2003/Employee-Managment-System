package Employee.EmployeeManagment;

import javax.swing.*;

public class EmployeeForm extends JFrame {
    public EmployeeForm() {
        setTitle("Employee Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("Welcome to Employee Management System", SwingConstants.CENTER);
        title.setBounds(20, 50, 350, 30);
        add(title);

        JButton enterButton = new JButton("Enter");
        enterButton.setBounds(150, 150, 100, 30);
        enterButton.addActionListener(e -> {
            new CRUDMenu().setVisible(true);
            dispose();
        });
        add(enterButton);
    }
}
