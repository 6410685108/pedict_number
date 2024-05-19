import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.security.SecureRandom;

public class NumberGuessServer {
    private static final int PORT = 12345;
    private static final int MAX_CLIENTS = 10;
    private static ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
    private static int targetNumber = new SecureRandom().nextInt(100) + 1;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    int guess = Integer.parseInt(inputLine);
                    if (guess < targetNumber) {
                        out.println("Higher");
                    } else if (guess > targetNumber) {
                        out.println("Lower");
                    } else {
                        out.println("Correct");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
