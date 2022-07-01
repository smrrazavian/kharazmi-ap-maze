package Maze.View;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    GamePanel() {
        setSize(800, 600);
        setBorder(BorderFactory.createRaisedSoftBevelBorder());
        setBackground(Color.WHITE);

    }
}
