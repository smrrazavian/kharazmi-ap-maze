package Maze.View;

import javax.swing.JFrame;

import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    private SignIn signIn;
    private SettingsPanel settingPanel;
    private GamePanel gamePanel;

    public MainFrame() {
        super("Hezar Too | هزار تو");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        settingPanel = new SettingsPanel();
        add(settingPanel, BorderLayout.NORTH);

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        signIn = new SignIn(this);
    }
}
