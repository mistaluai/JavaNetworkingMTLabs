import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class User {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(args[0]);
        ICalculator calculator = (ICalculator)reg.lookup(args[1]);
        double a = Double.parseDouble(args[2]);
        char op = args[3].toCharArray()[0];
        double b = Double.parseDouble(args[4]);
        switch (op) {
            case '+': print(calculator.add(a, b));
            break;
            case '-': print(calculator.subtract(a, b));
            break;
            case '*': print(calculator.multiply(a, b));
            break;
            case '/': print(calculator.divide(a, b));
            break;
        }
    }
    private static void print(double o) {
        System.out.println(o);
    }
}
