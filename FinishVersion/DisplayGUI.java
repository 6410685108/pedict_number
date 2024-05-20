import java.io.*;
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
    ArrayList<JLabel> allScorePlayer = new ArrayList<JLabel>();

    public DisplayGUI(PrintWriter send, BufferedReader recv) {
        this.send = send;
        this.recv = recv;
    }

    void showDisplay(JPanel panel) {
        panel.setLayout(null);

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setBounds(50, 15, 150, 25);
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
                    if (response.charAt(0) == '[') {
                        getScore(response);
                        response = recv.readLine() ;
                    }
                    resultLabel.setText(response);
                    allGuesses.add(guess);
                    if (response.equals("You guess correct, Now number is changed." )|| response.equals("You lost, Now number is changed.")) {
                        response = recv.readLine() ;
                        getScore(response);
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

            public void getScore(String response) {
                try {
                    for (int i = 0; i < allScorePlayer.size(); i++) {
                        panel.remove(allScorePlayer.get(i));
                    }
                    allScorePlayer.clear();
                    response = response.substring(1, response.length() - 1);
                    String[] responseArray = response.split(", ");
                    for (int i = 0; i < responseArray.length; i++) {
                        JLabel scorePlayer = new JLabel("");
                        panel.add(scorePlayer);
                        scorePlayer.setBounds(50, 35+i*15, 150, 25);
                        scorePlayer.setText("Player "+ (i+1) +": " + responseArray[i]);
                        allScorePlayer.add(scorePlayer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
