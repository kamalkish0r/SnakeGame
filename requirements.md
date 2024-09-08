# Snake Game Requirements

## Version 1.0

The goal is to develop a functional version of the game.

### Snake :
* **Available moves** : Left, Right, Up, Down
* Should I always keep moving the snake or should it only move when it gets command to move?
  * In this version let's keep things simple and move it only when it gets command to do so.
* Size of snake :
    * Assume initial size to be one unit.
    * It grows by one unit when it consumes food.
* Where should the snake land in the beginning?
  * At any random position

### Food :
* Let's say we have multiple type of food just like in the actual game :
    * **Regular food** :
        * Increases both score and snake's length by 1 unit
        * New one appears as soon as existing one is eaten.
    * **Special food** :
        * Increases snake's length by 1 unit
        * Has random value.
        * **Future Scope** :
            * Introduce variable value which changes with time in future versions.
            * Appears for a certain interval only.
* It appears at random positions.
* Regular food appears more frequently than the special food.

### Board
* Has a fixed width & height.
* It has walls, snake can't pass through.

### Game play & Score
* Game ends if snake hits the wall or self
* Live score should be displayed
* In the end display the score to the user and ask if he/she wants to play again.  
