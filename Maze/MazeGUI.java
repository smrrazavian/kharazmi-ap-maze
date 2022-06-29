package Maze;

import javax.swing.JFrame;

public class MazeGUI extends JFrame {
    
    public MazeGUI() {
        this.setTitle("Hezar Too | هزار تو");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setResizable(false);
        this.setVisible(true);

    }
    public static void main(String[] args) {
        new MazeGUI();
    }
}
