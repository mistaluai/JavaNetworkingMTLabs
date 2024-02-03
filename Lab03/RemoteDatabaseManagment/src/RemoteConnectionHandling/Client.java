package RemoteConnectionHandling;

import Entities.Employee;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {
    IServer server;
    public Client() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        server = (IServer) registry.lookup("Server");
    }
    public boolean add(Employee employee) throws RemoteException {
        return server.add(employee);
    }

    public boolean update(int ID, Employee newEmployee) throws RemoteException {
        return server.update(ID, newEmployee);
    }

    public boolean delete(int ID) throws RemoteException {
        return server.delete(ID);
    }

    public Employee get(int ID) throws RemoteException {
        return server.get(ID);
    }

    public String[][] getEmployees() throws RemoteException {
        List<Employee> employeesList = server.getEmployees();
        String[][] employeesArray = new String[employeesList.size()][2];
        int index = 0;
        for (Employee e : employeesList) {
            employeesArray[index][0] = String.valueOf(e.getID());
            employeesArray[index][1] = e.getName();
            index++;
        }
        return employeesArray;
    }
}
