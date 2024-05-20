import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientMain {
    public static void main(String[] args) {
        PrintWriter send = null;
        BufferedReader recv = null;
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            send = new PrintWriter(socket.getOutputStream(), true);
            recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        DisplayGUI d = new DisplayGUI(send, recv);
        d.showDisplay(panel);

        frame.setVisible(true);

    }
}
