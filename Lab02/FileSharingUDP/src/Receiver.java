import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Receiver {
    static class ReceivingThread implements Runnable {
        private DatagramSocket datagramSocket;
        private String file;
        ReceivingThread(DatagramSocket datagramSocket, String file) {
            this.datagramSocket = datagramSocket;
            this.file = file;
        }
        public void run() {
            File output = new File(file);
            byte[] data = new byte[1000];
            DatagramPacket linePacket = new DatagramPacket(data, data.length);
            try {
                output.createNewFile();
                FileOutputStream fos = new FileOutputStream(output, true);
                while (true) {
                    datagramSocket.receive(linePacket);
                    fos.write(linePacket.getData());
                    fos.write("\n".getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                System.out.println("Error writing to the file.");
            }
        }
    }
    public static void main(String[] args) throws IOException {
        try {

            DatagramSocket datagramSocket = new DatagramSocket(Integer.valueOf(args[0]));
            Thread receivingThread = new Thread(new Receiver.ReceivingThread(datagramSocket, args[1]));
            receivingThread.start();
        } catch (SocketException e) {
            System.out.println("Socket exception occurred.");
        }
    }
}
