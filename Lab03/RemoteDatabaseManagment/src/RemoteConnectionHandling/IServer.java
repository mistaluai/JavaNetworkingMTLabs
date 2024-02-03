package RemoteConnectionHandling;

import Entities.Employee;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServer extends Remote {
    boolean add(Employee employee) throws RemoteException;
    boolean update(int ID, Employee newEmployee) throws RemoteException;
    boolean delete(int ID) throws RemoteException;
    Employee get(int ID) throws RemoteException;
    List<Employee> getEmployees() throws RemoteException;
}
