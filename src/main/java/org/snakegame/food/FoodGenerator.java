package org.snakegame.food;

import org.snakegame.board.Board;
import org.snakegame.cell.Cell;
import org.snakegame.constants.CellState;
import org.snakegame.constants.FoodValue;
import org.snakegame.constants.constants;
import org.snakegame.expception.BoardNotEmpty;

import java.util.Random;

public abstract class FoodGenerator {
    public static Random random = new Random();

    private static boolean shouldGenerateSpecialFood() {
        Random random = new Random();
        return random.nextInt(constants.SPECIAL_FOOD_FREQUENCY.getValue()) == 0;
    }

    private static void generateFood(
            Board board,
            CellState cellState,
            FoodValue foodValue
    ) throws BoardNotEmpty {
        Cell cell = null;
        int retries = 0;
        do {
            int row = random.nextInt(board.getRows());
            int col = random.nextInt(board.getColumns());

            cell = board.getCell(row, col);
            retries += 1;
        } while (
                !cell.getCellState().equals(CellState.EMPTY) &&
                retries < constants.FOOD_GENERATION_RETRY.getValue()
        );

        if (cell.getCellState().equals(CellState.EMPTY)) {
            cell.setCellState(cellState);
            cell.setValue(foodValue.getValue());
        } else {
            throw new BoardNotEmpty("No empty cells on board!");
        }
    }

    public static void generateFood(Board board) throws BoardNotEmpty {
        generateFood(board, CellState.REGULAR_FOOD, FoodValue.REGULAR_FOOD_VALUE);
        if (shouldGenerateSpecialFood()) {
            generateFood(board, CellState.SPECIAL_FOOD, FoodValue.SPECIAL_FOOD_VALUE);
        }
    }
}
