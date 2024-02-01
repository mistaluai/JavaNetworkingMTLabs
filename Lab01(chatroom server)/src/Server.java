import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6060);
        List<ServerThread> serverThreads = new ArrayList<>();
        Scanner serverScanner = new Scanner(System.in, "UTF-8");
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (serverScanner.hasNext()) {
                        System.out.println("message received: " + serverScanner.nextLine());
                    }
                }
            }
        }).start();
        while (true) {
            Socket clientSocket = ss.accept();
            System.out.println("New Connection Established");
            Runnable client = new ServerThread(serverThreads.size(),clientSocket, serverThreads);
            serverThreads.add((ServerThread)client);
            Thread t = new Thread(client);
            t.start();
            System.out.println("Number of clients: " + serverThreads.size());
        }
    }
}
