import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class NumberGuessClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessClient::new);
    }

    public NumberGuessClient() {
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Enter a number:");
        userLabel.setBounds(10, 20, 100, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(120, 20, 165, 25);
        panel.add(userText);

        JButton guessButton = new JButton("Guess");
        guessButton.setBounds(10, 80, 80, 25);
        panel.add(guessButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(10, 110, 300, 25);
        panel.add(resultLabel);

        guessButton.addActionListener(e -> {
            String guess = userText.getText();
            try {
                out.println(guess);
                String response = in.readLine();
                resultLabel.setText(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
