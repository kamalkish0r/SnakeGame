package org.snakegame.snake;

public class Cell {
    private final int row;
    private final int col;
    private boolean occupied;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.occupied = false;
    }

    public Cell(int row, int col, boolean occupied) {
        this.row = row;
        this.col = col;
        this.occupied = occupied;
    }

    public void setOccupied() {
        occupied = true;
    }

    public void setUnoccupied() {
        occupied = false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
