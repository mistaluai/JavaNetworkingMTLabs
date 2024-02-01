import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL(args[0]);
            URLConnection urlConnection = url.openConnection();
            Scanner downloader = new Scanner(urlConnection.getInputStream(),
                    urlConnection.getContentEncoding() == null ? "UTF-8": urlConnection.getContentEncoding());
            File index = new File("index.html");
            if (!index.exists()) index.createNewFile();
            else {
                index.delete();
                index.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(index, true);
            while (downloader.hasNext()) {
                fos.write(downloader.nextLine().getBytes(StandardCharsets.UTF_8));
                fos.write("\n".getBytes(StandardCharsets.UTF_8));
            }
            new Webview(index);

        } catch (MalformedURLException mURL) {
mURL.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
