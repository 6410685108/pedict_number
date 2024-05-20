import java.io.*;
import java.awt.event.*; 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DisplayGUI {
    PrintWriter send;
    BufferedReader recv;

    public DisplayGUI(PrintWriter send, BufferedReader recv) {
        this.send = send;
        this.recv = recv;
    }

    void showDisplay(JPanel panel) {
        panel.setLayout(null);

        JLabel Label = new JLabel("Enter guess number:");
        Label.setBounds(200, 60, 100, 25);
        panel.add(Label);

        JTextField guessInput = new JTextField(20);
        guessInput.setBounds(200, 90, 165, 25);
        panel.add(guessInput);

        JButton confirmInput = new JButton("Guess");
        confirmInput.setBounds(200, 130, 80, 25);
        panel.add(confirmInput);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(200, 160, 300, 25);
        panel.add(resultLabel);

        confirmInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guess = guessInput.getText();
                try {
                    send.println(guess);
                    String response = recv.readLine();
                    resultLabel.setText(response);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
