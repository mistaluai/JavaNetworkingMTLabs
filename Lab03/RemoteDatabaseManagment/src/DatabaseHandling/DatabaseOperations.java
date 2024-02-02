package DatabaseHandling;

import Entities.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
            Statement addQuery = dbConnection.createStatement();
            String sql = "UPDATE employees \n SET ID = " + newEmployee.getID() + ", employeeName= \'" + newEmployee.getName() +"\' \n WHERE ID= " + ID + ";";
            addQuery.executeUpdate(sql);
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Failed to update employee["+ID+"]");
            sqlException.printStackTrace();
            return false;
        }
    }

}
