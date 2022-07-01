package Maze.View;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUp extends JDialog implements ActionListener {
    private String name;
    private JLabel nameLabel;
    private JLabel passLabel;
    private char[] passChar;
    private JTextField nameField;
    private JPasswordField passField;
    private JButton signUp;

    public SignUp(JDialog parent) {
        super(parent, "Sign Up", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        nameLabel = new JLabel("Name: ");
        passLabel = new JLabel("Password: ");
        nameField = new JTextField(10);
        passField = new JPasswordField(10);
        signUp = new JButton("Sign Up");
        signUp.addActionListener(this);
        // -----------------------------------------------
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
        add(passLabel, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(passField, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(signUp, gc);
        setVisible(true);
        // -----------------------------------------------

    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == signUp) {
            JOptionPane.showMessageDialog(clicked, this, "Successfully signed up", 0);
            setVisible(false);
        }
    }
}
