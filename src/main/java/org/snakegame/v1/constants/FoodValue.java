package org.snakegame.v1.constants;

public enum FoodValue {
    REGULAR_FOOD_VALUE(1),
    SPECIAL_FOOD_VALUE(5);

    private final int value;

    FoodValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
