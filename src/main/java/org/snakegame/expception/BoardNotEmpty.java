package org.snakegame.expception;

public class BoardNotEmpty extends RuntimeException {
    public BoardNotEmpty(String message) {
        super(message);
    }
}
