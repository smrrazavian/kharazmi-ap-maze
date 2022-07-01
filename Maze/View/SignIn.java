package Maze.View;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignIn extends JDialog implements ActionListener {
    private JLabel nameLabel;
    private JLabel idLabel;
    private JTextField nameField;
    private JPasswordField idField;
    private JButton login;
    private JButton signUp;
    private JButton casual;

    public SignIn(JFrame parent) {
        super(parent, "Sign in", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        // ------------------------------------
        nameLabel = new JLabel("Username: ");
        idLabel = new JLabel("Password: ");
        nameField = new JTextField(10);
        idField = new JPasswordField(10);
        login = new JButton("Log In");
        signUp = new JButton("Sign Up");
        casual = new JButton("Casual");
        // ------------------------------------------
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
        add(signUp, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        add(casual, gc);
        // ------------------------------------------
        login.addActionListener(this);
        casual.addActionListener(this);
        signUp.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == login) {
            // TODO : database shit
            setVisible(false);
        } else if (clicked == casual) {
            setVisible(false);
        } else if (clicked == signUp) {
            new SignUp(this);
        }
    }
}
