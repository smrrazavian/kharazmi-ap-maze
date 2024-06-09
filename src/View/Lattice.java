package View;

import java.awt.Point;

final class Lattice {
	private boolean Passable;
	private Point Father;

	public Lattice() {
		setPassable(false);
		setFather(null);
	}

	public boolean isPassable() {
		return Passable;
	}

	public void setPassable(boolean isPassable) {
		this.Passable = isPassable;
	}

	public Point getFather() {
		return Father;
	}

	public void setFather(Point father) {
		Father = father;
	}
}
