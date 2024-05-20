import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.event.*; 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DisplayGUI {
    PrintWriter send;
    BufferedReader recv;
    ArrayList<String> allGuesses = new ArrayList<String>();

    public DisplayGUI(PrintWriter send, BufferedReader recv) {
        this.send = send;
        this.recv = recv;
    }

    void showDisplay(JPanel panel) {
        panel.setLayout(null);

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setBounds(50, 15, 150, 25);
        panel.add(scoreLabel);

        JLabel scoreLabel1 = new JLabel("");
        scoreLabel1.setBounds(50, 35, 150, 25);
        panel.add(scoreLabel1);

        JLabel scoreLabel2 = new JLabel("");
        scoreLabel2.setBounds(50, 50, 150, 25);
        panel.add(scoreLabel2);

        JLabel Label = new JLabel("Enter guess number:");
        Label.setBounds(175, 60, 150, 25);
        panel.add(Label);

        JTextField guessInput = new JTextField(20);
        guessInput.setBounds(168, 90, 165, 25);
        panel.add(guessInput);

        JButton confirmInput = new JButton("Guess");
        confirmInput.setBounds(210, 130, 80, 25);
        panel.add(confirmInput);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(50, 160, 400, 25);
        panel.add(resultLabel);

        JLabel allGuess = new JLabel("");
        allGuess.setBounds(50, 190, 400, 25);
        panel.add(allGuess);

        confirmInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guess = guessInput.getText();
                try {
                    send.println(guess);
                    String response = recv.readLine() ;
                    if (response.charAt(0) == '[') {
                        response = response.substring(1, response.length() - 1);
                        String[] responseArray = response.split(", ");
                        response = recv.readLine() ;
                        scoreLabel1.setText("Player 1: " + responseArray[0]);
                        if (responseArray.length > 1) {
                            scoreLabel2.setText("Player 2: " + responseArray[1]);
                        }
                    }
                    resultLabel.setText(response);
                    allGuesses.add(guess);
                    if (response.equals("You guess correct, Now number is changed." )|| response.equals("You lost")) {
                        response = recv.readLine() ;
                        response = response.substring(1, response.length() - 1);
                        String[] responseArray = response.split(", ");
                        scoreLabel1.setText("Player 1: " + responseArray[0]);
                        if (responseArray.length > 1) {
                            scoreLabel2.setText("Player 2: " + responseArray[1]);
                        }
                        allGuesses.clear();
                        allGuess.setText("");
                    }
                    if (allGuesses.size() > 0) {
                        allGuess.setText("All guesses: " + allGuesses);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
