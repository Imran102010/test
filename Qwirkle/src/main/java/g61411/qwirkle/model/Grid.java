package g61411.qwirkle.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The Grid class represents a Qwirkle game board, which is a 91x91 grid of tiles.
 */
public class Grid implements Serializable {

    /**
     * The 2D array of tiles representing the board.
     */
    private final Tile[][] tiles;
    /**
     * A boolean indicating whether the board is empty.
     */
    private boolean isEmpty;

    /**
     * The size of the game board.
     * Represents the number of rows and columns in the game board.
     */
    public int BOARD_SIZE;

    public int PointsPourQ;

    public int Qwirkle6;

    /**
     * Constructs a new Grid with an empty board.
     */
    public Grid() {
        this.isEmpty = true;
        BOARD_SIZE = 91;
        this.tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        PointsPourQ=5;
        Qwirkle6=6;
    }

    /**
     * Returns the Tile at the specified row and column on the board.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return The Tile at the specified position.
     */
    public Tile get(int row, int col) {
        if (row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length) {
            return null;
        }
        return tiles[row][col];
    }

    /**
     * Checks if the grid is empty.
     *
     * @return true if the grid is empty, false otherwise.
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Adds the given line of tiles to the board, starting from the center position of the board.
     *
     * @param d    The direction in which the tiles should be added.
     * @param line line The line of tiles to be added.
     * @return number of point
     */
    public int firstAdd(Direction d, Tile... line) {
        if (!isEmpty) {
            throw new QwirkleException("Tiles cannot be added to a non-empty board.");
        }
        verifyTileLine(line);
        int row = BOARD_SIZE / 2;
        int col = BOARD_SIZE / 2;

        for (Tile tile : line) {
            tiles[row][col] = tile;
            row += d.getDeltaRow();
            col += d.getDeltaCol();
        }
        isEmpty = false;
        return line.length;
    }


    /**
     * Adds a tile to the board at the specified position.
     *
     * @param row  The row of the position to add the tile.
     * @param col  The column of the position to add the tile.
     * @param tile The tile to be added.
     * @return number of point earned
     * @throws QwirkleException If the tile cannot be added to the board.
     */
    public int add(int row, int col, Tile tile) {
        int point = addFake(row, col, tile);
        tiles[row][col] = tile;

        return point;
    }

    /**
     * Simulates adding a tile to the specified position on the game board without actually modifying the board.
     * Calculates and returns the score obtained by placing the tile at the given position.
     *
     * @param row  The row index of the position to add the tile to.
     * @param col  The column index of the position to add the tile to.
     * @param tile The tile to be added to the position.
     * @return The score obtained by placing the tile at the specified position.
     * @throws QwirkleException If the position is already occupied or there are no tiles adjacent to the specified position.
     */
    public int addFake(int row, int col, Tile tile) {
        int point = 0;
        isPlaceFree(row, col);
        if (!checkTileAround(row, col)) {
            throw new QwirkleException("There is no tile around.");
        }
        point += 1 + checkBoardCol(row, col, tile);
        point += 1 + checkBoardRow(row, col, tile);
        return point;
    }

    /**
     * Adds a line of tiles to the game board starting from the specified position and following the given direction.
     * Calculates and returns the score obtained by placing the tiles on the board.
     *
     * @param row  The row index of the starting position.
     * @param col  The column index of the starting position.
     * @param d    The direction in which the line of tiles should be placed.
     * @param line The line of tiles to be added to the board.
     * @return The score obtained by placing the tiles on the board.
     * @throws QwirkleException If the line of tiles cannot be placed due to invalid placement or lack of adjacent tiles.
     */
    public int add(int row, int col, Direction d, Tile... line) {
        verifyTileLine(line);
        if (!checkTileAround(row, col, d, line)) {
            throw new QwirkleException("There is no tile around this line");
        }

        int posRow = row;
        int posCol = col;
        for (Tile tile : line) {
            isPlaceFree(posRow, posCol);
            checkBoardCol(posRow, posCol, tile);
            checkBoardRow(posRow, posCol, tile);

            posRow += d.getDeltaRow();
            posCol += d.getDeltaCol();
        }
        if (d == Direction.UP || d == Direction.DOWN) {
            for (Tile tile : line) {
                checkBoardCol(row, col, tile);
            }
        }
        if (d == Direction.RIGHT || d == Direction.LEFT) {
            for (Tile tile : line) {
                checkBoardRow(row, col, tile);

            }
        }
        posRow = row;
        posCol = col;
        for (Tile tile : line) {
            tiles[posRow][posCol] = tile;
            posRow += d.getDeltaRow();
            posCol += d.getDeltaCol();
        }

        return point(row, col, d, line);
    }

    /**
     * Retrieves a list of tiles in a specific direction on the grid, starting from a specified position.
     *
     * @param startRow     the row of the starting position
     * @param startCol     the column of the starting position
     * @param rowIncrement the increment value for the row
     * @param colIncrement the increment value for the column
     * @return a list of tiles in the specified direction, starting from the specified position
     */
    private List<Tile> getTilesInDirection(int startRow, int startCol, int rowIncrement, int colIncrement) {
        List<Tile> tilesInDirection = new ArrayList<>();
        int row = startRow + rowIncrement;
        int col = startCol + colIncrement;
        while (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length && tiles[row][col] != null) {
            tilesInDirection.add(tiles[row][col]);
            row += rowIncrement;
            col += colIncrement;
        }
        return tilesInDirection;
    }

    /**
     * Checks if the tile can be added to the board row.
     *
     * @param row  the row where the tile should be added
     * @param col  the column where the tile should be added
     * @param tile the tile to be added
     * @return the number of point
     * @throws QwirkleException if the length of the tile sequence is greater than 6 or the tile sequence is not valid
     */

    private int checkBoardRow(int row, int col, Tile tile) {
        List<Tile> boardRow = new ArrayList<>();
        boardRow.addAll(getTilesInDirection(row, col, 0, -1));
        boardRow.addAll(getTilesInDirection(row, col, 0, 1));
        checkQwirkle(boardRow, tile);
        int points = boardRow.size();
        if (points == PointsPourQ) {
            points += Qwirkle6;
        }
        return points;
    }

    /**
     * Checks the number of consecutive tiles in the same column on the game board, including the given tile position.
     * It also checks for the presence of a Qwirkle (a column with  tiles of the same color or shape).
     *
     * @param row  The row index of the tile position.
     * @param col  The column index of the tile position.
     * @param tile The tile to be checked.
     * @return The number of consecutive tiles in the same column, including the given tile position.
     */
    private int checkBoardCol(int row, int col, Tile tile) {
        List<Tile> boardCol = new ArrayList<>();
        boardCol.addAll(getTilesInDirection(row, col, -1, 0));
        boardCol.addAll(getTilesInDirection(row, col, 1, 0));
        checkQwirkle(boardCol, tile);
        int points = boardCol.size();
        if (points == PointsPourQ) {
            points += Qwirkle6;
        }
        return points;
    }

    /**
     * Checks if a Qwirkle is formed with the given tileList and tile.
     *
     * @param tileList the list of tiles to check
     * @param tile     the tile to add to the list and check for Qwirkle formation
     * @throws QwirkleException if the maximum allowed length for a Qwirkle line is exceeded or if the tile sequence is not valid
     */

    private void checkQwirkle(List<Tile> tileList, Tile tile) {
        if (tileList.size() > Qwirkle6) {
            throw new QwirkleException("The maximum allowed length for a Qwirkle line is six.");
        }
        for (Tile contain : tileList) {
            if (compare(tile, contain)) {
                throw new QwirkleException("The tile sequence is not valid");
            }
        }
        for (int i = 0; i < tileList.size() - 1; i++) {
            for (int j = i + 1; j < tileList.size(); j++) {
                if (compare(tileList.get(i), tileList.get(j))) {
                    throw new QwirkleException("The tile sequence is not valid");
                }
            }
        }
    }


    /**
     * Checks if there is a tile adjacent to the given position.
     *
     * @param row the row of the tile to check
     * @param col the column of the tile to check
     * @return true if there is at least one tile adjacent to the given position, false otherwise
     * @throws QwirkleException if the given position is out of the grid
     */

    private boolean checkTileAround(int row, int col) {
        if (row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length) {
            throw new QwirkleException("Tile out of the grid");
        }

        int numRows = tiles.length;
        int numCols = tiles[0].length;
        int startRow = Math.max(0, row - 1);
        int endRow = Math.min(numRows - 1, row + 1);
        int startCol = Math.max(0, col - 1);
        int endCol = Math.min(numCols - 1, col + 1);
        boolean ok = false;

        for (int i = startRow; i <= endRow; i++) {
            if (i == row) {
                continue; // Skip the current position
            }
            if (tiles[i][col] != null) {
                ok = true; // A non-null value is found in vertical direction
            }
        }

        for (int j = startCol; j <= endCol; j++) {
            if (j == col) {
                continue; // Skip the current position
            }
            if (tiles[row][j] != null) {
                ok = true; // A non-null value is found in horizontal direction
            }
        }

        return ok;
    }

    /**
     * Checks if there is at least one non-null tile around a given row and column in a specific direction, based on an array of
     * tiles. The direction can be up, down, left, or right.
     *
     * @param row   the row of the starting tile
     * @param col   the column of the starting tile
     * @param d     the direction to check (up, down, left, or right)
     * @param tiles the array of tiles to use for the check
     * @return {@code true} if there is at least one non-null tile around the starting tile in the given direction, {@code false} otherwise
     */
    private boolean checkTileAround(int row, int col, Direction d, Tile[] tiles) {
        int deltaRow = 0;
        int deltaCol = 0;
        boolean result = false;

        switch (d) {
            case RIGHT -> deltaCol = 1;
            case LEFT -> deltaCol = -1;
            case UP -> deltaRow = -1;
            case DOWN -> deltaRow = 1;
        }

        int currentRow = row;
        int currentCol = col;

        for (Tile tile : tiles) {
            if (currentRow >= 0 && currentRow < this.tiles.length &&
                    currentCol >= 0 && currentCol < this.tiles[0].length) {
                result = checkTileAround(currentRow, currentCol);
            }

            if (result) {
                return true;
            }

            currentRow += deltaRow;
            currentCol += deltaCol;
        }

        return false;
    }

    /**
     * Returns a list of lists of adjacent tiles, where each inner list contains a group of adjacent tiles.
     *
     * @param tiles an array of TileAtPosition objects representing the tiles to check for adjacency
     * @return a list of lists of TileAtPosition objects representing adjacent tiles
     */
    private List<List<TileAtPosition>> getAdjacentTiles(TileAtPosition[] tiles) {
        List<List<TileAtPosition>> result = new ArrayList<>();

        // On initialise la liste temporaire avec le premier point
        List<TileAtPosition> tempList = new ArrayList<>();
        tempList.add(tiles[0]);

        // On parcourt les points et on ajoute ceux qui sont adjacents dans la liste temporaire
        for (int i = 1; i < tiles.length; i++) {
            TileAtPosition currentPoint = tiles[i];

            // Si le point est adjacent au dernier point de la liste temporaire
            if (isAdjacent(currentPoint, tempList.get(tempList.size() - 1))) {
                tempList.add(currentPoint);
            } else {
                // Sinon, on ajoute la liste temporaire à la liste de résultats
                result.add(tempList);
                // On crée une nouvelle liste temporaire avec le point courant
                tempList = new ArrayList<>();
                tempList.add(currentPoint);
            }
        }

        // On ajoute la dernière liste temporaire à la liste de résultats
        result.add(tempList);

        return result;
    }

    /**
     * Returns true if two tiles are adjacent (i.e., they are next to each other horizontally or vertically),
     * false otherwise.
     *
     * @param tile1 the first tile to check
     * @param tile2 the second tile to check
     * @return true if the two tiles are adjacent, false otherwise
     */
    private boolean isAdjacent(TileAtPosition tile1, TileAtPosition tile2) {

        int distance = Math.abs(tile1.row() - tile2.row()) + Math.abs(tile1.col() - tile2.col());

        return distance == 1;
    }

    /**
     * Returns true if there is at least one tile adjacent to any of the given tiles, false otherwise.
     *
     * @param line an array of TileAtPosition objects representing the tiles to check
     * @return true if there is at least one tile adjacent to any of the given tiles, false otherwise
     */
    private boolean checkTileAround(TileAtPosition... line) {
        List<List<TileAtPosition>> adjacentTiles = getAdjacentTiles(line);

        for (List<TileAtPosition> list : adjacentTiles) {

            if (list.size() == 1) {
                return checkTileAround(list.get(0).row(), list.get(0).col());
            } else {
                boolean result = false;

                for (TileAtPosition tile : list) {
                    if (tile.row() >= 0 && tile.row() < this.tiles.length &&
                            tile.col() >= 0 && tile.col() < this.tiles[0].length) {
                        result = checkTileAround(tile.row(), tile.col());
                    }

                    if (result) {
                        return true;
                    }
                }
                return false;
            }
        }

        return false;
    }

    /**
     * Adds a line of tiles to the game board and calculates the number of points earned.
     *
     * @param line The line of TileAtPosition objects representing the tiles to be added.
     * @return The number of points earned by adding the tiles to the board.
     * @throws QwirkleException if the line is empty, exceeds the maximum allowed length, the tiles do not belong to the same row or column,
     *                          there are other tiles present in the same row or column, the placement of tiles violates the game rules,
     *                          or there are no adjacent tiles on the board.
     */
    public int add(TileAtPosition... line) {
        int point = 0;

        if (line == null || line.length == 0) {
            throw new QwirkleException("The line is empty");
        }

        if (line.length > Qwirkle6) {
            throw new QwirkleException("The maximum allowed length for a Qwirkle line is six.");
        }
        if (line.length == 1) {
            point = add(line[0].row(), line[0].col(), line[0].tile());
        }
        if (line.length >= 2) {
            Tile[] lineOfTile = new Tile[line.length];
            for (int i = 0; i < line.length; i++) {
                lineOfTile[i] = line[i].tile();
            }
            verifyTileLine(lineOfTile);
            boolean sameCol = line[0].col() == line[1].col();
            boolean sameRow = line[0].row() == line[1].row();

            if (sameCol == sameRow) {
                throw new QwirkleException("Les tuiles doivent appartenir à la meme ligne ou colonne");
            }

            if (sameCol) {
                for (TileAtPosition tile : line) {
                    if (tile.col() != line[0].col()) {
                        throw new QwirkleException("");
                    }
                }
            }
            if (sameRow) {
                for (TileAtPosition tile : line) {
                    if (tile.row() != line[0].row()) {
                        throw new QwirkleException("");
                    }
                }
            }

            for (TileAtPosition tilePos : line) {
                isPlaceFree(tilePos.row(), tilePos.col());
                checkBoardRow(tilePos.row(), tilePos.col(), tilePos.tile());
                checkBoardCol(tilePos.row(), tilePos.col(), tilePos.tile());
            }

            if (!checkTileAround(line)) {
                throw new QwirkleException("There is no tile around.");
            }

            for (TileAtPosition tilePos : line) {
                tiles[tilePos.row()][tilePos.col()] = tilePos.tile();
            }
            point = pointTileAtPosition(line);
        }
        return point;
    }

    /**
     * Checks if a position on the grid is free, i.e., if it does not contain a tile.
     *
     * @param row the row of the position to check
     * @param col the column of the position to check
     * @throws QwirkleException if the position is not free
     */
    private void isPlaceFree(int row, int col) {
        if (row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length) {
            throw new QwirkleException("Tile out of the grid");
        }
        if (tiles[row][col] != null) {
            throw new QwirkleException("The position isn't free");
        }
    }

    /**
     * Compares two tiles to check if they have either the same color or the same shape.
     *
     * @param tile1 the first tile to compare
     * @param tile2 the second tile to compare
     * @return true if the two tiles have different colors and shapes, false otherwise
     */
    private boolean compare(Tile tile1, Tile tile2) {
        boolean sameColor = tile1.color().equals(tile2.color());
        boolean sameShape = tile1.shape().equals(tile2.shape());
        boolean isSameColorOrShape = sameColor || sameShape;
        boolean isSameColorAndShape = tile1.equals(tile2);
        if (isSameColorAndShape) {
            return true;
        }
        return !isSameColorOrShape;
    }

    /**
     * Verifies that the given line of tiles is valid.
     *
     * @param line The line of tiles to be verified.
     * @throws QwirkleException If the line of tiles is not valid.
     */
    private void verifyTileLine(Tile... line) {
        if (line == null || line.length == 0) {
            throw new QwirkleException("The line is empty");
        }

        if (line.length > Qwirkle6) {
            throw new QwirkleException("The maximum allowed length for a Qwirkle line is six.");
        }

        for (Tile tile : line) {
            if (tile == null) {
                throw new QwirkleException("Null Tile in the line");
            }
        }

        for (int i = 0; i < line.length - 1; i++) {
            for (int j = i + 1; j < line.length; j++) {
                if (compare(line[i], line[j])) {
                    throw new QwirkleException("The tile sequence is not valid");
                }
            }
        }
    }

    /**
     * Calculates the number of points for a line of tiles based on the given row, column, direction, and tile objects.
     *
     * @param row  The starting row index.
     * @param col  The starting column index.
     * @param d    The direction of the line.
     * @param line The array of tiles representing the line.
     * @return The number of points for the line.
     */
    private int point(int row, int col, Direction d, Tile... line) {
        int point = 0;
        int posRow = row;
        int posCol = col;

        if (d == Direction.UP || d == Direction.DOWN) {
            point += 1 + checkBoardCol(row, col, line[0]);
            int points = 0;
            for (Tile tile : line) {
                points = checkBoardRow(posRow, posCol, tile);
                if (points != 0) {
                    point += 1;
                }
                point += checkBoardRow(posRow, posCol, tile);
                posRow += d.getDeltaRow();
                posCol += d.getDeltaCol();
            }
        }
        if (d == Direction.RIGHT || d == Direction.LEFT) {
            point += 1 + checkBoardRow(row, col, line[0]);
            int points = 0;
            for (Tile tile : line) {
                points = checkBoardCol(posRow, posCol, tile);
                if (points != 0) {
                    point += 1;
                }
                point += checkBoardCol(posRow, posCol, tile);
                posRow += d.getDeltaRow();
                posCol += d.getDeltaCol();
            }
        }
        return point;
    }

    /**
     * Calculates the number of points for a line of TileAtPosition objects.
     *
     * @param line The line of TileAtPosition objects.
     * @return The number of points for the line.
     */
    private int pointTileAtPosition(TileAtPosition... line) {
        int point = 0;

        boolean sameCol = line[0].col() == line[1].col();
        if (sameCol) {
            point += 1 + checkBoardCol(line[0].row(), line[0].col(), line[0].tile());
            int points = 0;
            for (TileAtPosition tile : line) {
                points = checkBoardRow(tile.row(), tile.col(), tile.tile());
                if (points != 0) {
                    point += 1;
                }
                point += checkBoardRow(tile.row(), tile.col(), tile.tile());
            }
        }
        boolean sameRow = line[0].row() == line[1].row();
        if (sameRow) {
            point += 1 + checkBoardRow(line[0].row(), line[0].col(), line[0].tile());
            int points = 0;
            for (TileAtPosition tile : line) {
                points = checkBoardCol(tile.row(), tile.col(), tile.tile());
                if (points != 0) {
                    point += 1;
                }
                point += checkBoardCol(tile.row(), tile.col(), tile.tile());
            }
        }
        return point;
    }
}
