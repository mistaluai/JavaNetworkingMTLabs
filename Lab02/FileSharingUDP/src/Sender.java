import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sender {

    private static File file;
    static class SendingThread implements Runnable {

        private Scanner fileReader;
        private DatagramSocket datagramSocket;
        private InetAddress receiverAdress;
        private int portNumber;
        SendingThread(DatagramSocket datagramSocket, Scanner fileReader, String[] args)
                throws UnknownHostException {
            this.datagramSocket = datagramSocket;
            this.fileReader = fileReader;
            this.receiverAdress = InetAddress.getByName(args[1]);
            this.portNumber = Integer.parseInt(args[2]);
        }

        public void run() {
            int n = 0;
            while(fileReader.hasNext())
            {
                try {
                    byte[] line = fileReader.nextLine().getBytes(StandardCharsets.UTF_8);
                    DatagramPacket linePacket = new DatagramPacket(line, line.length, receiverAdress, portNumber);
                    datagramSocket.send(linePacket);
                    System.out.println("Line " + n + " sent");
                    n++;
                    Thread.sleep(200);
                }
                catch (IOException e) {} catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    public static void main(String[] args) {
        try {
            file = new File(args[0]);
            Scanner fileReader = new Scanner(file);
            DatagramSocket datagramSocket = new DatagramSocket();
            Thread sendingThread =
                    new Thread(new Sender.SendingThread(datagramSocket, fileReader, args));
            sendingThread.start();
        } catch (FileNotFoundException fnf) {
            System.out.println("No such file found.");
        } catch (SocketException s) {
            System.out.println("Socket exception occurred.");
        } catch (UnknownHostException eh) {
            System.out.println("Unknown host.");
        }
    }
}
