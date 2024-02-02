import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Calculator extends UnicastRemoteObject implements ICalculator {

    protected Calculator() throws RemoteException {

    }

    public double add(double a, double b) throws RemoteException {
        System.out.println("Addition Process Executed");
        return a + b;
    }

    public double subtract(double a, double b) throws RemoteException {
        System.out.println("Subtraction Process Executed");
        return a - b;
    }

    public double multiply(double a, double b) throws RemoteException {
        System.out.println("Multiplication Process Executed");
        return a * b;
    }

    public double divide(double a, double b) throws RemoteException {
        System.out.println("Division Process Executed");
        return a / b;
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Calculator server = new Calculator();
        Registry reg = LocateRegistry.createRegistry(1099);
        reg.rebind("Calc", server);
    }
}
