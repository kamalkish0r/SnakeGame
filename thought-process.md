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

