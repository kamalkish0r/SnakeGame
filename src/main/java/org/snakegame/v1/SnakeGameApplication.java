package org.snakegame.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakegame.v1.game.SnakeGame;

import java.io.IOException;


public class SnakeGameApplication {
    private static final Logger logger = LoggerFactory.getLogger(SnakeGameApplication.class);

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        try {
            snakeGame.startGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}