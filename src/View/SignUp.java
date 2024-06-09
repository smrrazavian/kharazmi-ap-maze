package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SignUp extends JDialog implements ActionListener {
    private JLabel nameLabel;
    private JTextField nameField;
    private JButton signup;

    public SignUp(JDialog parent) {
        super(parent, "Sign Up", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
        initializeComponents();
        setVisible(true);
    }
    
    private void initializeComponents() {
        nameLabel = new JLabel("Name: ");
        nameField = new JTextField(10);
        signup = new JButton("Sign Up");
        signup.addActionListener(this);
    
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;
    
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(nameLabel, gc);
    
        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        add(nameField, gc);
    
        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(signup, gc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int n = 0;
        JButton clicked = (JButton) e.getSource();
        if (clicked == signup) {
            try {
                Path dataPath = FileSystems.getDefault().getPath("Users.txt");
                List<String> lines = Files.readAllLines(dataPath);
                String s = "\n" + nameField.getText() + ": " + lines.size();
                n = lines.size();
                Files.write(dataPath, s.getBytes("UTF-8"), StandardOpenOption.APPEND);

            } catch (IOException e1) {
            }
            JOptionPane.showMessageDialog(this, "Successfully Signed Up\n Your ID: " + n);
            setVisible(false);
        }
    }
}
