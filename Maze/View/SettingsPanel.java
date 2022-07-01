package Maze.View;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;

public class SettingsPanel extends JPanel {
    private JComboBox<String> difficulties;
    private JButton allTimeScores;
    private JButton myProfile;
    private JButton logOut;
    private JLabel difficulty;

    SettingsPanel() {
        Font f1 = new Font("SERIF", Font.PLAIN, 10);
        setSize(800, 200);
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new FlowLayout());
        myProfile = new JButton("My Profile");
        myProfile.setFont(f1);
        allTimeScores = new JButton("All time scores");
        allTimeScores.setFont(f1);
        logOut = new JButton("log out");
        difficulty = new JLabel("Difficulty: ");
        difficulties = new JComboBox<String>();
        DefaultComboBoxModel<String> cmb = new DefaultComboBoxModel<String>();
        cmb.addElement("Easy");
        cmb.addElement("Medium");
        cmb.addElement("Hard");
        difficulties.setModel(cmb);
        add(difficulty);
        add(difficulties);
        add(myProfile);
        add(allTimeScores);
    }
}
