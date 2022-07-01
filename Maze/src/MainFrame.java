import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
public class MainFrame extends JFrame{
    private SignIn signin;
    private SettingsPanel settingpanel;
    private GamePanel gamepanel;
    public MainFrame(){
        super("Maze");
        setSize(800,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        settingpanel = new SettingsPanel();
        gamepanel = new GamePanel();
        add(settingpanel,BorderLayout.NORTH);
        add(gamepanel,BorderLayout.CENTER);
        signin = new SignIn(this);
    }
}
