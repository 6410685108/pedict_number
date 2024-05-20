import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MyRunnable implements Runnable {
    private Socket socket;
    private static int randomNum = RandomNumber.getNum();
    private static int low = 1;
    private static int high = 100;
    private static ArrayList<Long> allTID = new ArrayList<Long>();
    private static ArrayList<Integer> allScore = new ArrayList<Integer>();
    private int score = 0;

    public MyRunnable(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        long TID = Thread.currentThread().getId();
        allTID.add(TID);
        allScore.add(score);
        while (true) {
            try(BufferedReader recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter send = new PrintWriter(socket.getOutputStream(), true);) {
                while (true) {
                    System.out.println("Thread " + TID + ": Random number = " + randomNum);
                    String input;
                    while ((input = recv.readLine()) != null) {
                        int guessNum = Integer.parseInt(input);
                        System.out.println("Client guess: " + guessNum);
                        send.println(allScore);
                        if (guessNum < randomNum) {
                            if (low <= guessNum && guessNum >= 1) {
                                low = guessNum+1;
                            }
                            if (low != high){
                                send.println("You should guess higher. " + "You should guess between : " + low + " - " + high);
                            } else {
                                send.println("You lost");
                                for (int i = 0; i < allTID.size(); i++) {
                                    if (allTID.get(i) != TID) {
                                        allScore.set(i, allScore.get(i) + 1);
                                    }
                                }
                                send.println(allScore);
                                break;
                            }
                        } else if (guessNum > randomNum) {
                            if (high >= guessNum && guessNum <= 100) {
                                high = guessNum-1;
                            }
                            if (low != high){
                                send.println("You should guess higher. " + "You should guess between : " + low + " - " + high);
                            } else {
                                send.println("You lost");
                                for (int i = 0; i < allTID.size(); i++) {
                                    if (allTID.get(i) != TID) {
                                        allScore.set(i, allScore.get(i) + 1);
                                    }
                                }
                                send.println(allScore);
                                break;
                            }
                        } else {
                            send.println("You guess correct, Now number is changed.");
                            for (int i = 0; i < allTID.size(); i++) {
                                if (allTID.get(i) == TID) {
                                    allScore.set(i, allScore.get(i) + 1);
                                }
                            }
                            send.println(allScore);
                            break;
                        }
                    }
                    low = 1;
                    high = 100;
                    randomNum = RandomNumber.newNum();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}