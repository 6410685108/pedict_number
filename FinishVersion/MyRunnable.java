import java.io.*;
import java.net.*;

public class MyRunnable implements Runnable {
    private Socket socket;
    private int randomNum = RandomNumber.getNum();

    public MyRunnable(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        long TID = Thread.currentThread().getId();
        try(BufferedReader recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter send = new PrintWriter(socket.getOutputStream(), true);) {
            System.out.println("Thread " + TID + ": Random number = " + randomNum);
            String input;
            while ((input = recv.readLine()) != null) {
                int guessNum = Integer.parseInt(input);
                System.out.println("Client guess: " + guessNum);
                if (guessNum < randomNum) {
                    send.println("You should guess higher.");
                } else if (guessNum > randomNum) {
                    send.println("You should guess lower.");
                } else {
                    send.println("You guess correct.");
                    break;
                }
            }
            send.println("End.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}