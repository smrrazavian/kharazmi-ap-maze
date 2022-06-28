package Maze;

import java.awt.Point;

class MazeBoard {
    private boolean Passable;
    private Point Father;

    public MazeBoard() {
        setPassable(false);
    }

    public boolean isPassable() {
        return Passable;
    }

    public void setPassable(boolean passable) {
        Passable = passable;
    }

    public Point getFather() {
        return Father;
    }

    public void setFather(Point father) {
        Father = father;
    }
}
