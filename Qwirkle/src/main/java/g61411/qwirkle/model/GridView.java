package g61411.qwirkle.model;

/**
 * The GridView class is responsible for providing a view of the Grid object.
 * It allows clients to get a Tile from a specific position in the Grid and check if the Grid is empty.
 * The GridView class is immutable, which means it cannot be modified once it is initialized with a Grid object.
 */
public class GridView {

    /**
     * The grid field represents the Grid object that this GridView provides a view of.
     * It is a final field, which means it cannot be modified once it is initialized.
     * The Grid object is used to get a Tile at a specific position in the grid and check if the grid is empty.
     */
    private final Grid grid;

    /**
     * The size of the game board.
     * Represents the number of rows or columns in the square game board.
     */
    public int BOARD_SIZE;

    /**
     * Constructs a new GridView object with the specified Grid object.
     *
     * @param grid the Grid object to provide a view of
     */
    public GridView(Grid grid) {
        this.grid = grid;
        BOARD_SIZE = grid.BOARD_SIZE;
    }

    /**
     * Returns the Tile object at the specified row and column position in the Grid.
     *
     * @param row the row position of the Tile object to retrieve
     * @param col the column position of the Tile object to retrieve
     * @return the Tile object at the specified row and column position
     */
    public Tile get(int row, int col) {
        return grid.get(row, col);
    }

    /**
     * Returns whether the Grid is empty or not.
     *
     * @return true if the Grid is empty, false otherwise
     */
    public boolean isEmpty() {
        return grid.isEmpty();
    }
}
