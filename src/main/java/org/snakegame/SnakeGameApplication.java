package org.snakegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakegame.game.SnakeGame;


public class SnakeGameApplication {
    private static final Logger logger = LoggerFactory.getLogger(SnakeGameApplication.class);

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.startGame();
    }
}