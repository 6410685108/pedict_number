import java.io.*;
import java.net.*;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started...");
            while (true) {
                Socket socket = serverSocket.accept();
                MyRunnable myRunnable = new MyRunnable(socket);
                Thread thread = new Thread(myRunnable);
                thread.start();
            }
        }
    }
}

