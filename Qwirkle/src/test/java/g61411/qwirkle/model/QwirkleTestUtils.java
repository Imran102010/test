package g61411.qwirkle.model;


import static g61411.qwirkle.model.Color.*;
import static g61411.qwirkle.model.Shape.*;

public class QwirkleTestUtils {
    /**
     * The initial row is mandated to be 45.
     */
    static final int INITIAL_ROW = 45;
    /**
     * The initial column is mandated to be 45.
     */
    static final int INITIAL_COLUMN = 45;

    /**
     * Get a tile at a given 0-based position.
     * <p>
     * A 0-based position means a position where (0, 0) denotes the center ;
     * whereas the model accepts a position where (INITIAL_ROW, INITIAL_COLUMN) is the actual center.
     *
     * @param grid The grid to get the tile from.
     * @param row  a row number, with 0 being the center row.
     * @param col  a column number, with 0 being the center column
     * @return the tile at the given position
     */
    static Tile get(Grid grid, int row, int col) {
        return grid.get(INITIAL_ROW + row, INITIAL_COLUMN + col);
    }

    /**
     * Adds a tile to the specified position in the grid and returns the result of the addition operation.
     *
     * @param grid The grid object representing the game board.
     * @param row  The row index of the position where the tile is to be added.
     * @param col  The column index of the position where the tile is to be added.
     * @param tile The tile to be added.
     * @return The result of the addition operation on the grid.
     */
    static int add(Grid grid, int row, int col, Tile tile) {

        return grid.add(INITIAL_ROW + row, INITIAL_COLUMN + col, tile);
    }


    /**
     * Adds a line of tiles to the specified position in the grid, following the given direction, and returns the result of the addition operation.
     *
     * @param grid The grid object representing the game board.
     * @param row  The row index of the starting position where the line of tiles is to be added.
     * @param col  The column index of the starting position where the line of tiles is to be added.
     * @param d    The direction in which the line of tiles will be added.
     * @param line The array of tiles representing the line to be added.
     * @return The result of the addition operation on the grid.
     */
    static int add(Grid grid, int row, int col, Direction d, Tile... line) {
        return grid.add(INITIAL_ROW + row, INITIAL_COLUMN + col, d, line);
    }
    /**
     * Create a TileAtPosition object using a 0-based position.
     * <p>
     * A 0-based position means a position where (0, 0) denotes the center ;
     * whereas the model accepts a position where (INITIAL_ROW, INITIAL_COLUMN) is the actual center.
     *
     * @param row  a row number, with 0 being the center row.
     * @param col  a column number, with 0 being the center column.
     * @param tile a tile to insert into the object.
     */
    static TileAtPosition createTileAtpos(int row, int col, Tile tile) {
        return new TileAtPosition(INITIAL_ROW + row, INITIAL_COLUMN + col, tile);}


    public static final Tile TILE_RED_CROSS = new Tile(RED, CROSS);
    public static final Tile TILE_RED_DIAMOND = new Tile(RED, DIAMOND);
    public static final Tile TILE_RED_DIAMOND_2 = new Tile(RED, DIAMOND);
    public static final Tile TILE_RED_PLUS = new Tile(RED, PLUS);
    public static final Tile TILE_RED_PLUS_2 = new Tile(RED, PLUS);
    public static final Tile TILE_RED_ROUND = new Tile(RED, ROUND);
    public static final Tile TILE_RED_SQUARE = new Tile(RED, SQUARE);
    public static final Tile TILE_RED_STAR = new Tile(RED, STAR);

    public static final Tile TILE_YELLOW_CROSS = new Tile(YELLOW, CROSS);
    public static final Tile TILE_YELLOW_DIAMOND = new Tile(YELLOW, DIAMOND);
    public static final Tile TILE_YELLOW_PLUS = new Tile(YELLOW, PLUS);
    public static final Tile TILE_YELLOW_ROUND = new Tile(YELLOW, ROUND);
    public static final Tile TILE_YELLOW_STAR = new Tile(YELLOW, STAR);

    public static final Tile TILE_GREEN_CROSS = new Tile(GREEN, CROSS);
    public static final Tile TILE_GREEN_DIAMOND = new Tile(GREEN, DIAMOND);
    public static final Tile TILE_GREEN_PLUS = new Tile(GREEN, PLUS);
}

