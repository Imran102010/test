package g61411.qwirkle.model;


/**
 * The TileAtPosition record represents a Tile object at a specific position in a grid.
 * It is an immutable data object that stores the row and column position of a Tile object and the Tile object itself.
 * @param row  the row position of the Tile object in the grid
 * @param col  the column position of the Tile object in the grid
 * @param tile the Tile object at the specified position in the grid
 */
public record TileAtPosition(int row, int col, Tile tile) {

}
