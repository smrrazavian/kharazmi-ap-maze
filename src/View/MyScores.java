package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MyScores extends JDialog {
    private JTextArea textarea;
    public static int scoreNumbers;
    private boolean temp = false;

    public MyScores(JFrame parent, String name, int id) {
        super(parent, "My Scores");
        setSize(300, 400);
        setLayout(new BorderLayout());
        textarea = new JTextArea();
        textarea.setBackground(Color.WHITE);
        textarea.setEditable(false);
        add(textarea, BorderLayout.CENTER);
        try {
            Path dataPath = FileSystems.getDefault().getPath("Scores.txt");
            List<String> lines = Files.readAllLines(dataPath);
            lines.remove(0);
            scoreNumbers = lines.size();
            for (String line : lines) {
                String[] arr = line.split(", ");
                if (arr[0].equals(name) && arr[1].equals(Integer.toString(id))) {
                    temp = true;
                    textarea.append("time: " + arr[2] + ", " + "steps: " + arr[3] + ", " + arr[4] + "\n");
                }

            }
            if (temp == false) {
                JOptionPane.showMessageDialog(this, "You Have No Scores");
                textarea.repaint();
            }

        } catch (IOException e1) {
        }
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public boolean isTemp() {
        return temp;
    }
}
