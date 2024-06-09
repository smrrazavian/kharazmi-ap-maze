package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SignIn extends JDialog implements ActionListener {
    public static String name;
    public static int id;
    public static boolean isCasual = false;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JTextField nameField;
    private JTextField idField;
    private JButton login;
    private JButton signup;
    private JButton casual;

    public SignIn(JFrame parent) {
        super(parent, "Sign in", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
        initializeComponents();
        setVisible(true);
    }
    
    private void initializeComponents() {
        nameLabel = new JLabel("Username: ");
        idLabel = new JLabel("ID: ");
        nameField = new JTextField(10);
        idField = new JTextField(10);
        login = new JButton("Log In");
        signup = new JButton("Sign Up");
        casual = new JButton("Casual");
    
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
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        add(idLabel, gc);
    
        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(idField, gc);
    
        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(login, gc);
    
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(signup, gc);
    
        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        add(casual, gc);
    
        login.addActionListener(this);
        casual.addActionListener(this);
        signup.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == login) {
            try {
                Path dataPath = FileSystems.getDefault().getPath("Users.txt");
                List<String> lines = Files.readAllLines(dataPath);
                if (lines.contains(nameField.getText() + ": " + idField.getText())) {
                    name = nameField.getText();
                    id = Integer.parseInt(idField.getText());
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "You Are Not Signed Up");
                }
            } catch (IOException e1) {
            }

        } else if (clicked == casual) {
            setVisible(false);
            isCasual = true;
        } else if (clicked == signup) {
            SignUp signUpDialog = new SignUp(this);
            signUpDialog.setVisible(true);
        }
    }
}
