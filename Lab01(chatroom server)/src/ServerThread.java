import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Socket connection;
    private List<ServerThread> serverThreads;
    private PrintWriter DOS;
    private Scanner DIS;
    private int ID;
    ServerThread(int ID, Socket connection, List<ServerThread> serverThreads) {
        this.connection = connection;
        this.serverThreads = serverThreads;
        this.ID = ID;
    }
    public void run() {
        try {
            DOS = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"), true);
            DIS = new Scanner(connection.getInputStream(), "UTF-8");
            boolean disconnect = false;
            while (!disconnect && DIS.hasNext()) {
                String line = DIS.nextLine();
                for (ServerThread c : serverThreads) {
                    c.DOS.println("["+ID+"]: "+ line);
                }
                disconnect = line.contains("\\leave");
            }
            serverThreads.remove(this);
            connection.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Some IO Exception happened");
        }
    }
}
