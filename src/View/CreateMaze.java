package View;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;

abstract class AbstractCreateMaze {
	protected boolean isOutOfBorder(int x, int y, int colNumber, int rowNumber) {
		if ((x == 0 && y == 1) || (x == colNumber + 1 && y == rowNumber))
			return false;
		else
			return (x > colNumber || y > rowNumber || x < 1 || y < 1);
	}

	abstract void createMaze(Lattice[][] mazeLattice, int colNumber, int rowNumber);
}

class DepthFirstSearchCreateMaze extends AbstractCreateMaze {
	protected Point AroundPoint(Lattice[][] mazeLattice, Point p, Stack<Point> s, Random rand, int colNumber,
			int rowNumber) {
		final int[] aroundPoint = { -2, 0, 2, 0, -2 };
		int r = rand.nextInt(4);
		for (int i = 0; i < 4; ++i) {
			int j = r % 4;
			int x = p.x + aroundPoint[j];
			int y = p.y + aroundPoint[j + 1];
			++r;
			if (!isOutOfBorder(x, y, colNumber, rowNumber) && !mazeLattice[y][x].isPassable()) {
				mazeLattice[y][x].setPassable(true);
				mazeLattice[p.y + aroundPoint[j + 1] / 2][p.x + aroundPoint[j] / 2].setPassable(true);
				return new Point(x, y);
			}
		}
		return null;
	}

	@Override
	public void createMaze(Lattice[][] mazeLattice, int colNumber, int rowNumber) {
		Random rand = new Random();
		Point currentPoint = new Point(2 * rand.nextInt(colNumber / 2) + 1, 2 * rand.nextInt(rowNumber / 2) + 1);
		mazeLattice[currentPoint.y][currentPoint.x].setPassable(true);
		Stack<Point> pathStack = new Stack<>();
		pathStack.push(currentPoint);
		currentPoint = AroundPoint(mazeLattice, currentPoint, pathStack, rand, colNumber, rowNumber);
		while (true) {
			Point p = AroundPoint(mazeLattice, currentPoint, pathStack, rand, colNumber, rowNumber);
			if (p != null) {
				pathStack.push(currentPoint);
				currentPoint = p;
			} else if (!pathStack.isEmpty())
				currentPoint = pathStack.pop();
			else
				break;
		}
	}
}
