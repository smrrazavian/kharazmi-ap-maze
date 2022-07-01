import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
public class SettingsPanel extends JPanel {
    private JComboBox difficulties;
    private JButton alltimescores;
    private JButton myprofile;
    private JButton logout;
    private JLabel difficulty;
    SettingsPanel(){
        Font f1 = new Font("SERIF",Font.PLAIN,10);
        setSize(800,200);
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new FlowLayout());
        myprofile = new JButton("My Profile");
        myprofile.setFont(f1);
        alltimescores = new JButton("All time scores");
        alltimescores.setFont(f1);
        logout = new JButton("log out");
        difficulty = new JLabel("Difficulty: ");
        difficulties = new JComboBox<String>();
        DefaultComboBoxModel<String> cmb = new DefaultComboBoxModel<String>();
        cmb.addElement("Easy");
        cmb.addElement("Medium");
        cmb.addElement("Hard");
        difficulties.setModel(cmb);
        add(difficulty);
        add(difficulties);
        add(myprofile);
        add(alltimescores);
    }
}
