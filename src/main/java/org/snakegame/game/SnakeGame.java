package org.snakegame.game;

import org.snakegame.board.Board;
import org.snakegame.constants.Direction;
import org.snakegame.snake.Cell;
import org.snakegame.snake.Snake;

import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private final Snake snake;
    private final Board board;
    private int score;
    private final Scanner scanner = new Scanner(System.in);

    public SnakeGame() {
        int boardSize = 6;
        board = new Board(boardSize);
        Random random = new Random();
        int initialSnakeRow = random.nextInt(boardSize);
        int initialSnakeCol = random.nextInt(boardSize);
        snake = new Snake(board.getCell(initialSnakeRow, initialSnakeCol));
    }

    private Cell getNextCell(Cell currCell, Direction direction) {
        int newRow = currCell.getRow();
        int newCol = currCell.getCol();
        switch (direction) {
            case LEFT -> newCol -= 1;
            case RIGHT -> newCol += 1;
            case UP -> newRow -= 1;
            case DOWN -> newRow += 1;
        }

        return board.getCell(newRow, newCol);
    }

    public void startGame() {
        while (true) {
            printGameState();

            Direction direction = getUserDirection();
            Cell nextCell = getNextCell(snake.getHead(), direction);
            if (nextCell == null || nextCell.isOccupied()) {
                System.out.println("Your score : " + score);
                System.out.println("...Game Over...");
                break;
            }

            snake.move(nextCell);
        }
    }

    private void printGameState() {
        System.out.println(board);
    }

    private Direction getUserDirection() {
        while (true) {
            System.out.print("Which direction you want to move (L, R, U, D): ");
            String input = scanner.nextLine().trim().toUpperCase();
            switch (input) {
                case "L":
                    return Direction.LEFT;
                case "R":
                    return Direction.RIGHT;
                case "U":
                    return Direction.UP;
                case "D":
                    return Direction.DOWN;
                default:
                    System.out.println("Invalid input. Please enter L, R, U, or D.");
            }
        }
    }
}
