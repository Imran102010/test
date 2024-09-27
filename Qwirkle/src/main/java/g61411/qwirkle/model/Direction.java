package g61411.qwirkle.model;

/**
 * An enumeration representing the directions in a 2D grid.
 */
public enum Direction {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    /**
     * The deltaRow field represents the change in row position of a cell in a grid.
     */
    private final int deltaRow;


    /**
     * The deltaCol field represents the change in column position of a cell in a grid.
     */
    private final int deltaCol;

    /**
     * Creates a new Position with the given row and column deltas.
     *
     * @param row the change in the row coordinate
     * @param col the change in the column coordinate
     */
     Direction(int row, int col) {
        deltaRow = row;
        deltaCol = col;
    }

    /**
     * returns the in row for this direction
     *
     * @return the change in row
     */
    public int getDeltaRow() {
        return deltaRow;
    }

    /**
     * returns the change colomn in  for this direction
     *
     * @return the change in column
     */
    public int getDeltaCol() {
        return deltaCol;
    }

    /**
     * Returns the opposite direction of this direction.
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        return switch (this) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP -> DOWN;
            case DOWN -> UP;
        };

    }

}