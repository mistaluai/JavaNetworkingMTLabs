package DatabaseHandling;

import Entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private Connection dbConnection;
    DatabaseOperations(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * inserts a new employee to the database
     * @param employee the employee to be inserted in the database
     * @return returns true if the employee was added successfully.
     */
    public boolean add(Employee employee) {
        try {
            Statement addQuery = dbConnection.createStatement();
            String sql = "INSERT INTO employees(ID, employeeName) \n VALUES(" + employee.getID() + ", \'" + employee.getName() +"\')";
            addQuery.executeUpdate(sql);
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Failed to add employee");
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * updates the details of a specific employee
     * @param ID the id of the employee to be updates
     * @param newEmployee the new data of the employee
     * @return true if the employee was updated successfully
     */
    public boolean update(int ID, Employee newEmployee) {
        try {
            Statement updateQuery = dbConnection.createStatement();
            String sql = "UPDATE employees \n SET ID = " + newEmployee.getID() + ", employeeName= \'" + newEmployee.getName() +"\' \n WHERE ID= " + ID + ";";
            updateQuery.executeUpdate(sql);
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Failed to update employee["+ID+"]");
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * deletes a specific employee from the database
     * @param ID the id of the employee to be deleted
     * @return true if the employee was deleted successfully
     */
    public boolean delete(int ID) {
        try {
            Statement deleteQuery = dbConnection.createStatement();
            String sql = "DELETE FROM employees WHERE ID= " + ID;
            deleteQuery.executeUpdate(sql);
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Failed to delete employee["+ID+"]");
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * returns a specific employee by his id
     * @param ID the id of the employee we are looking for
     * @return an object on data type employee
     */
    public Employee get(int ID) {
        try {
            Statement getQuery = dbConnection.createStatement();
            String sql = "SELECT * FROM employees\nWHERE ID= " + ID + ";";
            ResultSet employeeData = getQuery.executeQuery(sql);
            Employee employee = null;
            if (employeeData.next()) {
                System.out.println("Fetching data from database");
                String employeeName = employeeData.getString(2);
                int employeeID = employeeData.getInt(1);
                employee = new Employee(employeeID, employeeName);
            }
            employeeData.close();
            return employee;
        } catch (SQLException sqlException) {
            System.out.println("Unable to retrieve employee");
            sqlException.printStackTrace();
            return null;
        }
    }

    /**
     * gets all available employees from database
     * @return a list of all the employees
     */
    public List<Employee> getEmployees() {
        try {
            Statement getQuery = dbConnection.createStatement();
            String sql = "SELECT * FROM employees";
            ResultSet employeeData = getQuery.executeQuery(sql);
            List<Employee> employees = new ArrayList<>();
            while(employeeData.next()) {
                System.out.println("Fetching data from database");
                String employeeName = employeeData.getString(2);
                int employeeID = employeeData.getInt(1);
                Employee employee = new Employee(employeeID, employeeName);
                employees.add(employee);
            }
            employeeData.close();
            return employees;
        } catch (SQLException sqlException) {
            System.out.println("Unable to retrieve employees");
            sqlException.printStackTrace();
            return null;
        }
    }

}
