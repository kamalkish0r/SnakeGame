package org.snakegame.snake;

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
        initCell.setOccupied();
    }

    public void move(Cell nextCell) {
        nextCell.setOccupied();
        body.addFirst(nextCell);
        body.getLast().setUnoccupied();
        body.removeLast();
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
