import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SignUp extends JDialog implements ActionListener {
    private String name;
    private JLabel namelabel;
    private JLabel passlabel;
    private char[] passchar;
    private JTextField namefield;
    private JPasswordField passfield;
    private JButton signup;
    public SignUp(JDialog parent){
        super(parent,"Sign Up",true);
        setSize(300,200);
        setLocationRelativeTo(parent);
        setResizable(false);

        namelabel = new JLabel("Name: ");
        passlabel = new JLabel("Password: ");
        namefield = new JTextField(10);
        passfield = new JPasswordField(10);
        signup = new JButton("Sign Up");
        signup.addActionListener(this);
        //-----------------------------------------------
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(namelabel, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        add(namefield, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        add(passlabel,gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(passfield, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(signup, gc);
        setVisible(true);
        //-----------------------------------------------

    }
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if(clicked == signup){
            JOptionPane.showMessageDialog(this, "Successfully signed up");
            setVisible(false);
        }
    } 
}
