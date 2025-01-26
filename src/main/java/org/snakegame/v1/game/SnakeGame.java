package org.snakegame.v1.game;

import org.snakegame.v1.board.Board;
import org.snakegame.v1.constants.CellState;
import org.snakegame.v1.constants.Direction;
import org.snakegame.v1.cell.Cell;
import org.snakegame.v1.food.FoodGenerator;
import org.snakegame.v1.snake.Snake;

import java.io.IOException;
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
        FoodGenerator.generateFood(board);
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

    public void startGame() throws IOException {
        while (true) {
            clearConsole();
            printGameState();

            Direction direction = getUserDirection();

            Cell nextCell = getCell(direction);
            if (nextCell == null) {
                System.out.println("Your score : " + score);
                System.out.println("...Game Over...");
                break;
            }

            update(nextCell);
        }
    }

    private void update(Cell nextCell) {
        // if snake ate food increase score and it's length
        if (nextCell.getCellState() == CellState.REGULAR_FOOD) {
            System.out.println("Adding " + nextCell.getValue() + " to score");
            score += nextCell.getValue();
            snake.move(nextCell);
            FoodGenerator.generateFood(board);
        } else {
            snake.move(nextCell);
        }
    }

    private Cell getCell(Direction direction) {
        Cell nextCell = getNextCell(snake.getHead(), direction);
        if (nextCell == null || nextCell.getCellState().equals(CellState.SNAKE)) {
            return null;
        }
        return nextCell;
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printGameState() {
        System.out.println(board);
    }

    private Direction getUserDirection() {
        while (true) {
            System.out.print("Which direction do you want to move (A, D, W, S): ");
            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "A":
                    return Direction.LEFT;
                case "D":
                    return Direction.RIGHT;
                case "W":
                    return Direction.UP;
                case "S":
                    return Direction.DOWN;
                default:
                    System.out.println("Invalid input. Please enter A, D, W, or S.");
            }
        }
    }
}
