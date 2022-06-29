package Maze;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;

abstract class AbstractMazeGenerator {
    protected boolean isOutOfBorder(int x, int y, int colNumber, int rowNumber) {
        if ((x == 0 && y == 1) || (x == colNumber && y == rowNumber))
            return false;
        else
            return (x > colNumber || y > rowNumber || x < 1 || y < 1) ? true : false;
    }

    abstract void GenerateMaze(MazeBoard[][] mazeBoard, int colNumber, int rowNumber);
}

class DFSMazeGenerator extends AbstractMazeGenerator {
    protected Point AroundPoint(MazeBoard[][] mazeBoard, Point point, Stack<Point> stack, Random rand, int rowNumber,
            int colNumber) {
        final int[] aroundPoints = { -2, 0, 2, 0, -2 };
        int random = rand.nextInt(4);
        for (int i = 0; i < 4; i++) {
            int j = random % 4;
            int x = point.x + aroundPoints[j];
            int y = point.y + aroundPoints[j + 1];
            ++random;
            if (!isOutOfBorder(x, y, colNumber, rowNumber) && !mazeBoard[y][x].isPassable()) {
                mazeBoard[y][x].setPassable(true);
                mazeBoard[point.y + aroundPoints[j + 1] / 2][point.y + aroundPoints[j] / 2].setPassable(true);
                return new Point(x, y);
            }
        }
        return null;
    }

    @Override
    public void GenerateMaze(MazeBoard[][] mazeBoard, int colNumber, int rowNumber) {
        Random random = new Random();
        Point currentPoint = new Point(2 * random.nextInt(colNumber / 2) + 1, 2 * random.nextInt(rowNumber / 2) + 1);
        mazeBoard[currentPoint.y][currentPoint.x].setPassable(true);
        Stack<Point> pathStack = new Stack<Point>();
        pathStack.push(currentPoint);
        currentPoint = AroundPoint(mazeBoard, currentPoint, pathStack, random, rowNumber, colNumber);
        while (true) {
            Point p = AroundPoint(mazeBoard, currentPoint, pathStack, random, rowNumber, colNumber);
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
