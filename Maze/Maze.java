package Maze;

import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Maze extends JPanel {
    // ----------------------- Variables -----------------------
    private Point entrance = null;
    private Point exit = null;
    private int rowNumber;
    private int colNumber;
    private int BoardWidth;
    private int stepNumber;
    static MazeBoard[][] mazeBoard;
    private Ball ball;
    private boolean promptSolveMaze = false;
    private boolean startTiming = false;
    private boolean computerDo = false;
    private JPanel panel;
    private JTextField timeText = new Timers(), stepNumberText = new JTextField("0");

    // ---------------------------------------------------------
    /**
     * @return the rowNumber
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * @param rowNumber the rowNumber to set
     */
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * @return the colNumber
     */
    public int getColNumber() {
        return colNumber;
    }

    /**
     * @param colNumber the colNumber to set
     */
    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public Maze(int row, int col) {
        this.setColNumber(col);
        this.setRowNumber(row);
        this.BoardWidth = 15;
        mazeBoard = new MazeBoard[getRowNumber() + 2][getColNumber() + 2];
        setLayout(new BorderLayout(0, 0));
		getTimeText().setForeground(Color.BLUE);
		getTimeText().setFont(new Font("", Font.PLAIN, 14)); // TODO Set font
		getTimeText().setHorizontalAlignment(JTextField.CENTER);
		stepNumberText.setEnabled(false);
        getStepNumberText().setForeground(Color.BLUE);
		getStepNumberText().setFont(new Font("", Font.PLAIN, 14)); // TODO Set font
		getStepNumberText().setHorizontalAlignment(JTextField.CENTER);
		Label timeLabel = new Label("Time:"), stepLabel = new Label("StepNumber:");
		timeLabel.setAlignment(Label.RIGHT);
		stepLabel.setAlignment(Label.RIGHT);
        panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4));
		add(panel, BorderLayout.NORTH);
		panel.add(timeLabel);Graphics2D
		panel.add(getTimeText());
		panel.add(stepLabel);
		panel.add(getStepNumberText());
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isComputerDo()) {
					requestFocus();
				}
			}
		});
        setKeyListener();
        GenerateMaze();
    }

    public void init() {
        mazeBoard = new MazeBoard[getRowNumber() + 2][getColNumber() + 2];
        setPromptSolveMaze(false);
        resetStepNumber();
		resetTimer();
        for (int i = 1; i < getRowNumber() + 1; i++) {
            for (int j = 1; j < getColNumber() + 1; j++) {
                mazeBoard[i][j] = new MazeBoard();
            }
        }
        for (int i = 0; i < getRowNumber() + 2; i++) {
            mazeBoard[i][0] = new MazeBoard();
            mazeBoard[i][getColNumber() + 1] = new MazeBoard();
        }
        for (int j = 0; j < getColNumber() + 2; j++) {
            mazeBoard[0][j] = new MazeBoard();
            mazeBoard[0][getColNumber() + 1] = new MazeBoard();
        }
        ball = new Ball(0, 1);
        setEntrance(new Point(0, 1));
        setExit(new Point(getColNumber() + 1, getRowNumber()));
        mazeBoard[getEntrance().y][getEntrance().x].setPassable(true);
        mazeBoard[getExit().y][getExit().x].setPassable(true);
    }

    public boolean isWinner() {
        if (getExit().x == ball.getX() && getExit().y == ball.getY())
            return true;
        return false;
    }

    private void GameOverMessage() {
        // TODO: Complete this shit.
        JOptionPane.showMessageDialog(null,
                "Congratulations on getting out of the maze!\n" + "Time you have used to go out of the maze is: "
                        + timeText.getText() + "\nThe number of the steps you have used to go out of the maze is: "
                        + stepNumber,
                "GameOver", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isOutOfBorder(int x, int y) {
        if ((x == 0 && y == 1) || (x == getColNumber() + 1 && y == getRowNumber()))
            return false;
        else
            return (x > getColNumber() || y > getRowNumber() || x < 1 || y < 1) ? true : false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < getRowNumber() + 2; ++i) {
            for (int j = 0; j < getColNumber() + 2; ++j) {
                g.drawRect((j + 1) * BoardWidth, (i + 1) * BoardWidth + 30, BoardWidth, BoardWidth);
                if (mazeBoard[i][j].isPassable())
                    g.setColor(Color.WHITE);
                else
                    g.setColor(Color.BLACK);
                g.fillRect((j + 1) * BoardWidth, (i + 1) * BoardWidth + 30, BoardWidth, BoardWidth);
            }
        }
        g.setColor(Color.RED);
        g.fillRect((getColNumber() + 2) * BoardWidth, (getRowNumber() + 1) * BoardWidth + 30, BoardWidth,
                BoardWidth);
        g.setColor(ball.getColor());
        g.drawOval((ball.getX() + 1) * BoardWidth, (ball.getY() + 1) * BoardWidth + 30, BoardWidth, BoardWidth);
        g.fillOval((ball.getX() + 1) * BoardWidth, (ball.getY() + 1) * BoardWidth + 30, BoardWidth, BoardWidth);
        if (isPromptSolveMaze()) {
            Stack<Point> pathStack = promptSolveMaze();
            g.setColor(Color.GREEN);
            Point start = pathStack.pop();
            while (!pathStack.isEmpty()) {
                Point end = pathStack.pop();
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3.0f));
                g2.drawLine((int) (start.getX() + 1) * BoardWidth + BoardWidth / 2,
                        (int) (start.getY() + 1) * BoardWidth + 30 + BoardWidth / 2,
                        (int) (end.getX() + 1) * BoardWidth + BoardWidth / 2,
                        (int) (end.getY() + 1) * BoardWidth + 30 + BoardWidth / 2);
                start = end;
            }
        }
    }

    public void GenerateMaze() {
        init();
        AbstractMazeGenerator c = null;
        c = new DFSMazeGenerator();
        c.GenerateMaze(mazeBoard, getColNumber(), getRowNumber());
        repaint();
    }

    public void resetTimer() {
        setStartTiming(false);
        getTimeText().setText("00:00:00");
        ((Timers) timeText).restart();
    }

    public void resetStepNumber() {
        setStepNumber(0);
        stepNumberText.setText(Integer.toString(stepNumber));
    }

    public void setBallPosition(Point p) {
        ball.setX(p.x);
        ball.setY(p.y);
        repaint();
    }

    synchronized private void move(int c) {
        int tx = ball.getX(), ty = ball.getY();
        switch (c) {
            case KeyEvent.VK_LEFT:
                --tx;
                break;
            case KeyEvent.VK_RIGHT:
                ++tx;
                break;
            case KeyEvent.VK_UP:
                --ty;
                break;
            case KeyEvent.VK_DOWN:
                ++ty;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            default:
                // 防止按键盘上其他键仍然产生键盘音效、使计步器递增
                tx = 0;
                ty = 0;
                break;
        }
        if (!isOutOfBorder(tx, ty) && mazeBoard[ty][tx].isPassable()) {
            ball.setX(tx);
            ball.setY(ty);
            ++stepNumber;
            stepNumberText.setText(Integer.toString(stepNumber));
            if (!isStartTiming()) {
                setStartTiming(!isStartTiming());
                ((Timers) getTimeText()).start();
            }
        }

    }

    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (!isWinner()) {
                    int temp = event.getKeyCode();
                    move(temp);
                    repaint();
                    if (isWinner() && !isComputerDo())
                        GameOverMessage();
                }
            }
        });
    }

    private Stack<Point> solveMaze(Point p) {
        AbstractSolveMaze a = null;
        a = new DFSSolveMaze();
        return a.solveMaze(mazeBoard, p, getExit(), getColNumber(), getRowNumber());
    }

    private Stack<Point> promptSolveMaze() {
        AbstractSolveMaze a = null;
        a = new DFSSolveMaze();
        return a.solveMaze(mazeBoard, new Point(ball.getX(), ball.getY()), getExit(), getColNumber(), getRowNumber());
    }

    public Point getEntrance() {
        return entrance;
    }

    public void setEntrance(Point entrance) {
        this.entrance = entrance;
    }

    public Point getExit() {
        return exit;
    }

    public void setExit(Point exit) {
        this.exit = exit;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public JTextField getStepNumberText() {
        return stepNumberText;
    }

    public void setStepNumberText(JTextField stepNumberText) {
        this.stepNumberText = stepNumberText;
    }

    public boolean isPromptSolveMaze() {
        return promptSolveMaze;
    }

    public void setPromptSolveMaze(boolean promptSolveMaze) {
        this.promptSolveMaze = promptSolveMaze;
    }

    public void setStartTiming(boolean startTiming) {
        this.startTiming = startTiming;
    }

    public boolean isStartTiming() {
        return startTiming;
    }

    public boolean isComputerDo() {
        return computerDo;
    }

    public void setComputerDo(boolean computerDo) {
        this.computerDo = computerDo;
    }

    public void setTimeText(JTextField timeText) {
        this.timeText = timeText;
    }

    public JTextField getTimeText() {
        return timeText;
    }

    public static void main(String[] args) {

    }
}
