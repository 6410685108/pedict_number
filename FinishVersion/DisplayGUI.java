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

        JLabel scoreLabel = new JLabel("Score: ");
        scoreLabel.setBounds(50, 20, 150, 25);
        panel.add(scoreLabel);

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
                    resultLabel.setText(response);
                    allGuesses.add(guess);
                    if (response.equals("You guess correct, Now number is changed.")) {
                        response = recv.readLine() ;
                        scoreLabel.setText("Score: " + response);
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
