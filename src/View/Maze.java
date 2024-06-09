package View;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

final class Maze extends JPanel {
	private Point entrance = null;
	private Point exit = null;
	private int rowNumber;
	private int colNumber;
	private int LatticeWidth;
	private Ball ball;
	private Lattice[][] mazeLattice;
	private boolean startTiming = false;
	private JPanel panel = new JPanel();
	private JTextField timeText = new Timers(), stepNumberText = new JTextField("0");
	private Thread thread = null;
	private int stepNumber;
	private static final char DepthFirstSearchSolveMaze = 0;
	private char solveMaze = DepthFirstSearchSolveMaze;
	private static final char DepthFirstSearchCreateMaze = 0;
	private char createMaze = DepthFirstSearchCreateMaze;
	private boolean hintSol = false;
	public String level;

	public Maze(int row, int col) {
		this.setRowNumber(row);
		this.setColNumber(col);
		this.LatticeWidth = 15;
		mazeLattice = new Lattice[getRowNumber() + 2][getColNumber() + 2];
		setLayout(new BorderLayout(0, 0));
		getTimeText().setForeground(Color.BLUE);
		getTimeText().setFont(new Font("Serif", Font.PLAIN, 14));
		getTimeText().setHorizontalAlignment(JTextField.CENTER);
		stepNumberText.setEnabled(false);
		getStepNumberText().setForeground(Color.RED);
		getStepNumberText().setFont(new Font("Serif", Font.PLAIN, 14));
		getStepNumberText().setHorizontalAlignment(JTextField.CENTER);
		Label timeLabel = new Label("Time:"), stepLabel = new Label("Steps:");
		timeLabel.setAlignment(Label.RIGHT);
		stepLabel.setAlignment(Label.RIGHT);
		panel.setLayout(new GridLayout(1, 4));
		add(panel, BorderLayout.NORTH);
		panel.add(timeLabel);
		panel.add(getTimeText());
		panel.add(stepLabel);
		panel.add(getStepNumberText());
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		setKeyListener();
		createMaze();
	}

	public void init() {
		mazeLattice = new Lattice[getRowNumber() + 2][getColNumber() + 2];
		setHintSol(false);
		setThreadStop();
		resetStepNumber();
		resetTimer();
		for (int i = 1; i < getRowNumber() + 1; ++i)
			for (int j = 1; j < getColNumber() + 1; ++j) {
				mazeLattice[i][j] = new Lattice();
			}
		for (int i = 0; i < getRowNumber() + 2; ++i) {
			mazeLattice[i][0] = new Lattice();
			mazeLattice[i][getColNumber() + 1] = new Lattice();
		}
		for (int j = 0; j < getColNumber() + 2; ++j) {
			mazeLattice[0][j] = new Lattice();
			mazeLattice[getRowNumber() + 1][j] = new Lattice();
		}
		ball = new Ball(0, 1);
		setEntrance(new Point(0, 1));
		setExit(new Point(getColNumber() + 1, getRowNumber()));
		mazeLattice[getEntrance().y][getEntrance().x].setPassable(true);
		mazeLattice[getExit().y][getExit().x].setPassable(true);
	}

	public boolean isWin() {
		if (getExit().x == ball.getX() && getExit().y == ball.getY()) {
			return true;
		}
		return false;
	}

	private void GameOverMessage() {
		if (!SignIn.isCasual && !MainFrame.isSolved) {
			((Timers) getTimeText()).stop();
			JOptionPane.showMessageDialog(null,
					"You got out of the maze\n" + "Your Time: "
							+ timeText.getText() + "\nYour moves: "
							+ stepNumber,
					"GameOver", JOptionPane.INFORMATION_MESSAGE);
			try {
				String Score = "\n" + SignIn.name + ", " + SignIn.id + ", " + timeText.getText() + ", " + stepNumber
						+ ", " + level;
				Path dataPath = FileSystems.getDefault().getPath("Scores.txt");
				Files.write(dataPath, Score.getBytes("UTF-8"), StandardOpenOption.APPEND);
			} catch (IOException e1) {
			}
		} else {
			((Timers) getTimeText()).stop();
			JOptionPane.showMessageDialog(null,
					"You got out of the maze\n" + "Your Time: "
							+ timeText.getText() + "\nYour moves: "
							+ stepNumber,
					"GameOver", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private boolean isOutOfBorder(int x, int y) {
		if ((x == 0 && y == 1) || (x == getColNumber() + 1 && y == getRowNumber()))
			return false;
		else
			return (x > getColNumber() || y > getRowNumber() || x < 1 || y < 1);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < getRowNumber() + 2; ++i)
			for (int j = 0; j < getColNumber() + 2; ++j) {
				g.drawRect((j + 1) * LatticeWidth, (i + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
				if (mazeLattice[i][j].isPassable())
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.BLACK);
				g.fillRect((j + 1) * LatticeWidth, (i + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
			}
		g.setColor(Color.RED);
		g.fillRect((getColNumber() + 2) * LatticeWidth, (getRowNumber() + 1) * LatticeWidth + 30, LatticeWidth,
				LatticeWidth);
		g.setColor(ball.getColor());
		g.drawOval((ball.getX() + 1) * LatticeWidth, (ball.getY() + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
		g.fillOval((ball.getX() + 1) * LatticeWidth, (ball.getY() + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
		if (isHintSol()) {
			Stack<Point> pathStack = HintSolve();
			g.setColor(Color.BLUE);
			Point start = pathStack.pop();
			while (!pathStack.isEmpty()) {
				Point end = pathStack.pop();
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(3.0f));
				g2.drawLine((int) (start.getX() + 1) * LatticeWidth + LatticeWidth / 2,
						(int) (start.getY() + 1) * LatticeWidth + 30 + LatticeWidth / 2,
						(int) (end.getX() + 1) * LatticeWidth + LatticeWidth / 2,
						(int) (end.getY() + 1) * LatticeWidth + 30 + LatticeWidth / 2);
				start = end;
			}
		}
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
				tx = 0;
				ty = 0;
				break;
		}
		if (!isOutOfBorder(tx, ty) && mazeLattice[ty][tx].isPassable()) {
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
			public void keyPressed(KeyEvent e) {
				if (!isWin()) {
					int c = e.getKeyCode();
					move(c);
					repaint();
					if (isWin())
						GameOverMessage();
				}
			}
		});
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

	public void createMaze() {
		init();
		AbstractCreateMaze c = null;
		c = new DepthFirstSearchCreateMaze();
		c.createMaze(mazeLattice, getColNumber(), getRowNumber());
		repaint();
	}

	private Stack<Point> HintSolve() {
		AbstractSolveMaze a = null;
		a = new DepthFirstSearchSolveMaze();
		return a.solveMaze(mazeLattice, new Point(ball.getX(), ball.getY()), getExit(), getColNumber(), getRowNumber());
	}

	private void hint_time(int time) {
		if (getThread() == null)
			setThread(new Thread() {
				@Override
				public void run() {
					while (!isInterrupted())
						try {
							setHintSol(true);
							repaint();
							Thread.sleep(time);
							setHintSol(false);
							repaint();
							setThreadStop();
						} catch (InterruptedException e) {
							break;
						}
				}
			});
		getThread().start();
	}

	public boolean solution() {
		setThreadStop();
		((Timers) getTimeText()).stop();
		hint_time(2000000000);
		((Timers) getTimeText()).proceed();
		return true;
	}

	public boolean hint() {
		setThreadStop();
		((Timers) getTimeText()).stop();
		hint_time(3000);
		((Timers) getTimeText()).proceed();
		return true;
	}

	public int getLatticeWidth() {
		return LatticeWidth;
	}

	public void setLatticeWidth(int latticeWidth) {
		LatticeWidth = latticeWidth;
	}

	public JTextField getTimeText() {
		return timeText;
	}

	public boolean isStartTiming() {
		return startTiming;
	}

	public void setStartTiming(boolean startTiming) {
		this.startTiming = startTiming;
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

	private void setExit(Point exit) {
		this.exit = exit;
	}

	public Thread getThread() {
		return thread;
	}

	private void setThread(Thread thread) {
		this.thread = thread;
	}

	public void setThreadStop() {
		if (getThread() != null) {
			if (isHintSol())
				setHintSol(false);
			thread.interrupt();
			setThread(null);
		}
	}

	public JTextField getStepNumberText() {
		return stepNumberText;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColNumber() {
		return colNumber;
	}

	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
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

	public boolean isHintSol() {
		return hintSol;
	}

	public void setHintSol(boolean hintSol) {
		this.hintSol = hintSol;
	}
}
