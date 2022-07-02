package Maze;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

abstract class AbstractSolveMaze {
    protected Stack<Point> pathStack = null;

    protected boolean isOutOfBorder(int x, int y, int colNumber, int rowNumber) {
        if ((x == 0 && y == 1) || (x == colNumber + 1 && y == rowNumber))
            return false;
        else
            return (x > colNumber || y > rowNumber || x < 1 || y < 1) ? true : false;
    }

    abstract Stack<Point> solveMaze(MazeBoard[][] mazeLattice, Point entrance, Point exit, int colNumber,
            int rowNumber);
}

class DepthFirstSearchSolveMaze extends AbstractSolveMaze {
    protected Point AroundPointDepthFirst(MazeBoard[][] mazeBoard, Point p, int colNumber, int rowNumber) {
        final int[] aroundPoint = { -1, 0, 1, 0, -1 };
        for (int i = 0; i < 4;) {
            int x = p.x + aroundPoint[i];
            int y = p.y + aroundPoint[++i];
            if (!isOutOfBorder(x, y, colNumber, rowNumber) && mazeBoard[y][x].isPassable()
                    && mazeBoard[y][x].getFather() == null) {
                p = new Point(x, y);
                mazeBoard[y][x].setFather(p);
                return p;
            }
        }
        return null;
    }

    @Override
    public Stack<Point> solveMaze(MazeBoard[][] mazeBoard, Point entrance, Point exit, int colNumber, int rowNumber) {
        pathStack = new Stack<Point>();
        Deque<Point> pathDeque = new ArrayDeque<Point>();
        Point judge = new Point(0, 0);
        Point end = new Point(exit.x - 1, exit.y);
        for (int i = 0; i < rowNumber + 2; ++i)
            for (int j = 0; j < colNumber + 2; ++j)
                mazeBoard[i][j].setFather(null);
        mazeBoard[entrance.y][entrance.x].setFather(judge);
        pathDeque.addLast(entrance);
        Point currentPoint = entrance;
        while (!currentPoint.equals(end)) {
            currentPoint = AroundPointDepthFirst(mazeBoard, currentPoint, colNumber, rowNumber);
            if (currentPoint == null) {
                pathDeque.removeLast();
                if (pathDeque.isEmpty())
                    break;
                currentPoint = pathDeque.getLast();
            } else {
                pathDeque.addLast(currentPoint);
            }
        }
        mazeBoard[exit.y][exit.x].setFather(end);
        pathDeque.addLast(exit);
        while (!pathDeque.isEmpty())
            pathStack.push(pathDeque.removeLast());
        return pathStack;
    }
}