package Employee.EmployeeManagment;

import javax.swing.*;
import java.awt.*;
import Employee.EmployeeManagment.CreateEmployeeForm;
import Employee.EmployeeManagment.ReadEmployeeForm;
import Employee.EmployeeManagment.UpdateEmployeeForm;
import Employee.EmployeeManagment.DeleteEmployeeForm;


public class CRUDMenu extends JFrame {
    public CRUDMenu() {
        setTitle("Employee CRUD Operations");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnCreate = new JButton("Create");
        JButton btnRead = new JButton("Read");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        btnCreate.addActionListener(e -> {
            new CreateEmployeeForm().setVisible(true);
            dispose();
        });

        btnRead.addActionListener(e -> {
            new ReadEmployeeForm().setVisible(true);
            dispose();
        });

        btnUpdate.addActionListener(e -> {
            new UpdateEmployeeForm().setVisible(true);
            dispose();
        });

        btnDelete.addActionListener(e -> {
            new DeleteEmployeeForm().setVisible(true);
            dispose();
        });

        add(btnCreate);
        add(btnRead);
        add(btnUpdate);
        add(btnDelete);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CRUDMenu().setVisible(true));
    }
}
