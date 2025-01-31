package org.snakegame.v1.snake;

import org.snakegame.v1.cell.Cell;
import org.snakegame.v1.constants.CellState;

import java.util.LinkedList;

public class Snake {
    private final LinkedList<Cell> body;

    public Snake(int row, int col) {
        // initial length of snake is 1
        this.body = new LinkedList<>();
        body.addFirst(new Cell(row, col));
    }

    public Snake(Cell initCell) {
        this.body = new LinkedList<>();
        this.body.addFirst(initCell);
        initCell.setCellState(CellState.SNAKE);
    }

    public void move(Cell nextCell) {
        boolean removeLast = nextCell.getCellState() == CellState.EMPTY;
        nextCell.setCellState(CellState.SNAKE);
        body.addFirst(nextCell);
        if (removeLast) {
            body.getLast().setUnoccupied();
            body.removeLast();
        }
    }

    public Cell getHead() {
        return body.getFirst();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Snake [");

        // Loop through the body and append each cell's position
        for (Cell cell : body) {
            sb.append(String.format("(%d, %d) ", cell.getRow(), cell.getCol()));
        }
        return sb.toString();
    }
}
