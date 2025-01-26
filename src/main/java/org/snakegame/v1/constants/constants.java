package org.snakegame.v1.constants;

public enum constants {
    SPECIAL_FOOD_FREQUENCY(10),
    FOOD_GENERATION_RETRY(10);


    private final int value;

    constants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
