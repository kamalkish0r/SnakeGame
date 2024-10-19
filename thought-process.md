# Snake Game Development : Thought Process
Documenting my thought process, how I solve the problem & the reasoning behind the decisions I make along the way.

## Version 1.0

The building block for both snake & the board is a cell.

Properties of cell :
1. A cell is 1 sq. unit in area.
2. It has fixed coordinates, i.e. the location of cell can be determined by (i, j);

This is what my `Cell` class looks like
```java
public class Cell {
    private final int row;
    private final int col;
    private boolean occupied;
}
```
**Note**: this just to visualisation purpose and actual implementation will have constructor, getter, setter methods etc.

A board is just a grid of cells. Let's define the coordinate & indexing convention for the board.
We will follow row, column convention for simplicity. Let's assume that the board has dimensions `R` rows & `C` columns.
The top left corner of the board can be defined as `(0, 0)` and the bottom right corner is defined as `(R - 1, C - 1)`. We are following 0 based indexing.
```java
public class Board {
    private final int rows;
    private final int columns;
}
```

Now let's focus on the snake, 

how to maintain the state of snake at any moment? 

A snake can be visualised as multiple cells linked to one another.
This reminds of a data structure called linked list. A linked list fits perfectly to our use case, at it also consists of nodes linked to one another. 
It allows addition and removal at both ends in constant time, which would be helpful to simulate the movement of snake.
To simulate the movement, we add a node at head and remove one at tail, this makes the snake progress forward by one unit.

Now how do we know that snake bit itself?

One way is to mark as cell as occupied when snake enters that cell and unoccupied when it leaves. 
We can put a check when the snake enters a cell, if the cell which snake is about to enter is already occupied then the snake is biting itself, 
so after this operation we can declare the game as over as snake bit itself.
Similarly, we can also put another check to evaluate if snake is slipping out of the board. 

So we have to check the following when snake moves
1. Will it bite itself?
2. Will it hit the wall of board?

Now the question comes where should we put these checks on?

* Should we put these checks inside `move` method of `Snake` class? 
  * This will create coupling between `Board` & `Snake` which doesn't seem right to me.

I think the snake class should be free of checks on where snake can go, instead we should have these checks in game controller class, one which would be responsible for bringing all the components together.
This will also help when we introduce any change in logic like `Wall Pass-Through Mode`, we would only need to change the controller class and if we put checks in snake class then we will need to update both snake & the controller class.
So we can directly pass the cell to which snake would go next. 

Not this means that we will be passing the cell to snake's `move` method from the `SnakeGame` class. We can decide the next cell based on direction from user input and coordinates of snake's head.

Now we there are two options : 
1. Create new `Cell` object based on new coordinates.
2. Create all the cells of board at the beginning of game.

Both the approaches have their pros & cons. 
In the first approach we have less dependency on the board while in second approach,
we are not creating objects again & again.

I'll stick with the second approach because of the following pros : 
1. A cell is created only once
2. Since controller already has the board instance, it makes more sense to abstract the logic around cell in the `Board` itself, because we mentioned in the beginning that board consists of cells.

After these changes here's how the updated classes look like
```java
public class Board {
    private final int rows;
    private final int columns;
    private final ArrayList<ArrayList<Cell>> cells;
}
```
```java
public class Snake {
    private final LinkedList<Cell> body;
}
```

Now it's time to focus on `Food`. We'll reflect back & update the above classes if necessary.

Let's list down things we need to take care of :
1. FoodType : Regular, Special
2. FoodValue : 1 unit for regular & random value for special.
3. Coordinate : appears on random coordinates
   * Edge case : shouldn't overlap with snake
4. Frequency : regular food appears more frequently than the special one.
5. Both increase the snake's length by 1 unit.
6. A new one should appear as soon as previous one is consumed.
   * Edge case : if there's no space left for food to appear the game ends and the player won!
7. Both food may be present at the same time on board.

The tricky one's here are `Frequency` & `Coordinate`(where food is to be placed).

I am thinking of having an abstract class `Food` which is inherited by `RegularFood` & `SpecialFood`.

Now let's deal with the coordinates part first : 
* we generate random coordinates and check if it's occupied or not
* How to deal with coordinates for special food as it spans to more than a cell?
  * For simplicity let's assume that special food has 4 unit size
  * Like regular food we first generate coordinate for a cell and then try different combinations with its neighbors to find unoccupied space. 
* How can it be done differently?


Now let's take care of frequency : 
* One option which comes to mind is to establish direct relation between frequencies of both food types.
  * something like after every `k` appearances of regular food they both appear together
* Another way is to make it probabilistic, i.e. there's some `p` % chance that special food will appear

For food generation, we can have separate class responsible for food generation. This class will also take of frequency management 
for food generation. I'm thinking of keeping this as static and stateless. It'll have a `generateFood` method which returns a list of 
Food when called. Its responsibility is to generate food when called, which means finding empty coordinates, decide whether it should generate special
food or not.

Maintaining a separate class for `Food` is creating unnecessary complexity, and I'm thinking of moving the value to `Cell`, it already has got `CellState`
which can be set as `RegularFood`, `SpecialFood`.

ToDo : make snake head distinguishable so that it's easy for the player

This is how the `FoodGenerator` class looks like
```java
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

```

I went with this approach, although it creates coupling between board and FoodGenerator. Maybe I'll refactor it and remove this someday.
