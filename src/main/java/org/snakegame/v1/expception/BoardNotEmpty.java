package org.snakegame.v1.expception;

public class BoardNotEmpty extends RuntimeException {
    public BoardNotEmpty(String message) {
        super(message);
    }
}
