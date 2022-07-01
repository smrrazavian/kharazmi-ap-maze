package Maze;

public class Maze {
    // ----------------------- Variables -----------------------
    private int rowNumber;
    private int colNumber;

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

    public Maze(int row, int col) {
        this.setColNumber(col);
        this.setRowNumber(row);
    }

    public static void main(String[] args) {
        AbstractMazeGenerator a = null;
        a = new DFSMazeGenerator();
        MazeBoard[][] mazeBoard = new MazeBoard[22][22];
        int x = 20, y = 20;
        a.GenerateMaze(mazeBoard, x, y);
    }
}
