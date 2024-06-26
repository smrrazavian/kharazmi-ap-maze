package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

public class MainFrame extends JFrame {
    private SignIn signIn;
    private char solveMaze = 0;
    private char createMaze = 0;
    private Maze maze;
    private MyScores myScores;
    private AllTimeScores allTimeScore;
    private JPanel settingPanel;
    private JComboBox<String> difficulties;
    private JButton allTimeScores;
    private JButton myProfile;
    private JLabel zoomLabel;
    private JSpinner zoom;
    private JButton apply;
    private JLabel difficulty;
    private JPanel gamePanel;
    private JPanel gamePanel1;
    private JButton restart;
    private JButton hint;
    private JButton solution;
    private boolean isStopped;
    public static boolean isSolved = false;

    public MainFrame() {
        super("Maze");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        signIn = new SignIn(this);
        initializeComponents();
        setVisible(true);
        revalidate();
        repaint();
    }

    private void initializeComponents() {
        maze = new Maze(15, 15);
        maze.setBackground(Color.white);

        // Initialize settingPanel
        settingPanel = new JPanel(new FlowLayout());
        settingPanel.setBorder(new TitledBorder("Settings"));
        myProfile = new JButton("My Profile");
        allTimeScores = new JButton("All Time Scores");
        apply = new JButton("Start");
        difficulty = new JLabel("Difficulties :");
        solution = new JButton("Solution");
        zoomLabel = new JLabel("Zoom: ");
        zoom = new JSpinner();
        difficulties = new JComboBox<>();
        DefaultComboBoxModel<String> cmb = new DefaultComboBoxModel<>();
        cmb.addElement("Easy");
        cmb.addElement("Medium");
        cmb.addElement("Hard");
        difficulties.setModel(cmb);

        settingPanel.add(difficulty);
        settingPanel.add(difficulties);
        settingPanel.add(myProfile);
        settingPanel.add(allTimeScores);
        settingPanel.add(zoomLabel);
        settingPanel.add(zoom);
        zoom.setModel(new SpinnerNumberModel(15, 5, 30, 1));
        settingPanel.add(zoom);
        settingPanel.add(apply);
        add(settingPanel, BorderLayout.NORTH);
        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createEtchedBorder());
        gamePanel.setLayout(new BorderLayout(0, 0));
        gamePanel1 = new JPanel();
        gamePanel1.setBorder(BorderFactory.createEtchedBorder());
        restart = new JButton("Restart");
        hint = new JButton("Hint");
        gamePanel1.add(restart);
        gamePanel1.add(hint);
        gamePanel1.add(solution);
        gamePanel.add(gamePanel1, BorderLayout.NORTH);
        gamePanel.add(maze);
        add(gamePanel, BorderLayout.CENTER);

        apply.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSolved = false;
                if (getSolveMaze() != maze.getSolveMaze())
                    maze.setSolveMaze(getSolveMaze());
                int row = 0, col = 0;
                String level = (String) difficulties.getSelectedItem();
                maze.level = level;
                switch (level) {
                    case "Easy":
                        row = 15;
                        col = 15;
                        break;
                    case "Medium":
                        row = 25;
                        col = 25;
                        break;
                    case "Hard":
                        row = 41;
                        col = 41;
                        break;
                }

                maze.setHintSol(false);
                hint.setEnabled(true);
                if (maze.getColNumber() == col && maze.getRowNumber() == row) {
                    if (getCreateMaze() != maze.getCreateMaze()) {
                        maze.setCreateMaze(getCreateMaze());
                        maze.createMaze();
                    } else {
                        maze.resetStepNumber();
                        maze.resetTimer();
                        maze.setThreadStop();
                        maze.setBallPosition(maze.getEntrance());
                    }
                    maze.requestFocus();
                    maze.repaint();
                } else {
                    maze.setColNumber(col);
                    maze.setRowNumber(row);
                    if (getCreateMaze() != maze.getCreateMaze()) {
                        maze.setCreateMaze(getCreateMaze());
                    }
                    maze.createMaze();
                    maze.requestFocus();
                }
            }
        });

        zoom.addChangeListener((ChangeEvent e) -> {
            maze.setLatticeWidth(Integer.parseInt(zoom.getValue().toString()));
            maze.repaint();
        });
        add(settingPanel, BorderLayout.NORTH);
        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createEtchedBorder());
        gamePanel.setLayout(new BorderLayout(0, 0));
        gamePanel1 = new JPanel();
        gamePanel1.setBorder(BorderFactory.createEtchedBorder());
        restart = new JButton("Restart");
        hint = new JButton("Hint");

        gamePanel1.add(restart);
        gamePanel1.add(hint);
        gamePanel1.add(solution);
        gamePanel.add(gamePanel1, BorderLayout.NORTH);
        gamePanel.add(maze);
        add(gamePanel, BorderLayout.CENTER);

        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSolved = false;
                maze.init();
                maze.createMaze();
                hint.setEnabled(true);
                solution.setEnabled(true);
                maze.requestFocus();
            }
        });
        hint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!maze.isWin()) {
                    maze.hint();
                    maze.requestFocus();
                }
            }
        });
        solution.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!maze.isWin()) {
                    isSolved = true;
                    maze.solution();
                    maze.requestFocus();
                    setStopped(true);
                    ((Timers) maze.getTimeText()).stop();
                }
            }
        });
        myProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myScores = new MyScores(new JFrame(), SignIn.name, SignIn.id);
                if (!SignIn.isCasual && myScores.isTemp())
                    myScores.setVisible(true);
            }
        });
        allTimeScores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                allTimeScore = new AllTimeScores(new JFrame());
                if (AllTimeScores.scoreNumber == 0) {
                    JOptionPane.showMessageDialog(gamePanel, "There Are No Scores Yet!");
                } else {
                    allTimeScore.setVisible(true);
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    public char getSolveMaze() {
        return solveMaze;
    }

    public void setSolveMaze(char solveMaze) {
        this.solveMaze = solveMaze;
    }

    public char getCreateMaze() {
        return createMaze;
    }

    public void setCreateMaze(char createMaze) {
        this.createMaze = createMaze;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }
}
