package View;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AllTimeScores extends JDialog {
    private JTextArea textarea;
    public static int scoreNumber;

    public AllTimeScores(JFrame parent) {
        super(parent, "All Time Scores");
        setSize(300, 400);
        textarea = new JTextArea();
        add(textarea);
        try {
            Path dataPath = FileSystems.getDefault().getPath("Scores.txt");
            List<String> lines = Files.readAllLines(dataPath);
            lines.remove(0);
            scoreNumber = lines.size();
            for (String line : lines) {
                textarea.append(line + "\n");
            }
        } catch (IOException e1) {
        }
    }
}
