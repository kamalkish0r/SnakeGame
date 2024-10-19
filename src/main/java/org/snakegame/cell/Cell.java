package org.snakegame.cell;

import org.snakegame.constants.CellState;

public class Cell {
    private final int row;
    private final int col;
    private int value;
    private CellState cellState;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0;
        this.cellState = CellState.EMPTY;
    }

    public Cell(int row, int col, boolean occupied) {
        this.row = row;
        this.col = col;
    }

    public Cell(int row, int col, CellState cellState) {
        this.row = row;
        this.col = col;
        this.cellState = cellState;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState newState) {
        cellState = newState;
    }

    public void setUnoccupied() {
        cellState = CellState.EMPTY;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
