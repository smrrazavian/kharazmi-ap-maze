package View;

import java.awt.Color;

final class Ball {
	private int x;
	private int y;
	private Color color;

	public Ball(int x, int y) {
		setX(x);
		setY(y);
		setColor(Color.BLUE);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int setY(int y) {
		this.y = y;
		return y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}