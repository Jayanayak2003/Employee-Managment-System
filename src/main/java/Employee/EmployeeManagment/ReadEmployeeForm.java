package Employee.EmployeeManagment;

import Employee.EmployeeManagment.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReadEmployeeForm extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ReadEmployeeForm() {
        setTitle("View Employees");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("All Employee Records", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Age", "Position"});
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(220, 53, 69));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backButton);

        fetchEmployees();

        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void fetchEmployees() {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();

            for (Employee emp : employees) {
                model.addRow(new Object[]{
                        emp.getId(),
                        emp.getName(),
                        emp.getAge(),
                        emp.getPosition()
                });
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            JOptionPane.showMessageDialog(this, "Error loading employee data.", "Hibernate Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReadEmployeeForm::new);
    }
}
