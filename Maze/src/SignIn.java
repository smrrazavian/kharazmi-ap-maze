import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SignIn extends JDialog implements ActionListener{
    private JLabel namelabel;
    private JLabel idlabel;
    private JTextField namefield;
    private JPasswordField idfield;
    private JButton login;
    private JButton signup;
    private JButton casual;
    public SignIn(JFrame parent){
        super(parent,"Sign in",true);
        setSize(300,200);
        setLocationRelativeTo(parent);
        setResizable(false);

        //------------------------------------
        namelabel = new JLabel("Username: ");
        idlabel = new JLabel("Password: ");
        namefield = new JTextField(10);
        idfield = new JPasswordField(10);
        login = new JButton("Log In");
        signup = new JButton("Sign Up");
        casual = new JButton("Casual");
        //------------------------------------------
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
        add(idlabel,gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(idfield, gc);

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
        //------------------------------------------
        login.addActionListener(this);
        casual.addActionListener(this);
        signup.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if(clicked == login){
            //TODO database shit
            setVisible(false);
        }
        else if(clicked == casual){setVisible(false);}
        else if(clicked == signup){
            new SignUp(this);
        }
    }
}
