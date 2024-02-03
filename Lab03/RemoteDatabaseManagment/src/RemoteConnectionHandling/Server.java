package RemoteConnectionHandling;

import DatabaseHandling.DatabaseManager;
import DatabaseHandling.DatabaseOperations;
import Entities.Employee;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server extends UnicastRemoteObject implements IServer {
    private DatabaseManager dmb;
    private DatabaseOperations dbo;
    Server(String hostname, int portNumber, String dbName,
           String username, String password, String properties) throws RemoteException {
        super();
        dmb = new DatabaseManager(hostname, portNumber,
                        dbName, username, password, properties);
        if (dmb.establishConnection()) {
            dbo = dmb.getDatabaseOperations();
        } else {
            System.out.println("Invalid arguments, failed to launch server!");
            System.exit(1);
        }
    }
    public boolean add(Employee employee) throws RemoteException {
        return dbo.add(employee);
    }

    public boolean update(int ID, Employee newEmployee) throws RemoteException {
        return dbo.update(ID, newEmployee);
    }

    public boolean delete(int ID) throws RemoteException {
        return dbo.delete(ID);
    }

    public Employee get(int ID) throws RemoteException {
        return dbo.get(ID);
    }

    public List<Employee> getEmployees() throws RemoteException {
        return dbo.getEmployees();
    }

    public static void main(String[] args) throws RemoteException {
        Server server = new Server(args[0], Integer.parseInt(args[1]), args[2],
                args[3], args[4], args[5]);
        Registry reg = LocateRegistry.createRegistry(1099);
        reg.rebind("Server", server);
        System.out.println("Server Launch Success!");
    }
}
