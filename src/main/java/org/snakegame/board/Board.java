package org.snakegame.board;

import org.snakegame.cell.Cell;
import org.snakegame.constants.CellState;

import java.util.ArrayList;

public class Board {
    private final int rows;
    private final int columns;
    private final ArrayList<ArrayList<Cell>> cells;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new ArrayList<>();
        initialiseBoard();
    }

    public Board(int size) {
        this.rows = size;
        this.columns = size;
        cells = new ArrayList<>();
        initialiseBoard();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    private void initialiseBoard() {
        for (int row = 0; row < rows; row++) {
            ArrayList<Cell> rowCells = new ArrayList<>();
            for (int col = 0; col < columns; col++) {
                rowCells.add(new Cell(row, col));
            }
            cells.add(rowCells);
        }
    }

    public boolean isOutsideBoard(int row, int col) {
        return row < 0 || col < 0 || row >= rows || col >= columns;
    }

    public boolean isOutsideBoard(Cell cell) {
        return isOutsideBoard(cell.getRow(), cell.getCol());
    }

    public Cell getCell(int row, int col) {
        if (isOutsideBoard(row, col)) {
            return null;
        }
        return cells.get(row).get(col);
    }

    @Override
    public String toString() {
        // Move cursor to top-left corner
        System.out.print("\033[H");

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                CellState currCellState = getCell(row, col).getCellState();
                switch (currCellState) {
                    case SNAKE -> sb.append("*");
                    case REGULAR_FOOD -> sb.append("#");
                    case SPECIAL_FOOD -> sb.append("$");
                    default -> sb.append(".");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
