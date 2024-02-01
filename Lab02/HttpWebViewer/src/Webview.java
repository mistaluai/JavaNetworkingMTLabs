import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Webview extends JFrame {
    File index;
    Webview (File index) throws IOException {
        this.index = index;

        JEditorPane web = new JEditorPane();
        web.setPage(index.getAbsoluteFile().toURL());
        web.setContentType("text/html");
        this.add(web);
        this.setSize(new Dimension(1080,720));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
