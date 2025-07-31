package Employee.EmployeeManagment;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 * Data Access Object (DAO) class for Employee entity.
 * Handles CRUD operations with Hibernate.
 */
public class EmployeeDAO {

    /**
     * Saves a new Employee record to the database.
     * @param emp Employee object to save.
     */
    public static void saveEmployee(Employee emp) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(emp); // or session.save(emp);
            tx.commit();
            System.out.println("Employee saved successfully.");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error saving employee:");
            e.printStackTrace();
        }
    }

    /**
     * Fetches all Employee records from the database.
     * @return List of Employee objects.
     */
    public static List<Employee> getAllEmployees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        } catch (Exception e) {
            System.err.println("Error fetching all employees:");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a single Employee by their ID.
     * @param id Employee ID.
     * @return Employee object or null if not found.
     */
    public static Employee getEmployeeById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        } catch (Exception e) {
            System.err.println("Error fetching employee by ID:");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates an existing Employee in the database.
     * @param emp Employee object with updated fields.
     */
    public static void updateEmployee(Employee emp) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(emp); // or session.update(emp);
            tx.commit();
            System.out.println("Employee updated successfully.");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error updating employee:");
            e.printStackTrace();
        }
    }

    /**
     * Deletes an Employee from the database by ID.
     * @param id Employee ID to delete.
     */
    public boolean deleteEmployee(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Employee emp = session.get(Employee.class, id);
            if (emp != null) {
                session.delete(emp);
                tx.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

}
