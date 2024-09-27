package g61411.qwirkle.model;

import g61411.qwirkle.view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

    private final Grid grid;

    private final Player[] players;

    private int currentplayer;

    /**
     * Constructs a new Game object with the specified list of player names.
     *
     * @param names a list of player names
     * @throws QwirkleException if the number of players is not between 2 and 4 (inclusive)
     * @see Player
     */
    public Game(List<String> names) {
        if (names.size() < 2 || names.size() > 4) {
            throw new QwirkleException("The number of players is incorrect");
        }
        players = new Player[names.size()];
        for (int i = 0; i < names.size(); i++) {
            players[i] = new Player(names.get(i));
        }
        grid = new Grid();
        currentplayer = 0;
    }

    public Game() {
        grid = new Grid();
        players = new Player[0];
    }

    /**
     * Adds the first line of tiles to the game grid.
     *
     * @param d  the direction of the line to be added (either Direction.HORIZONTAL or Direction.VERTICAL)
     * @param is the indices of the tiles to be played from the current player's hand
     * @throws QwirkleException if the current player is not the first player
     * @see Direction
     * @see Player
     */

    public void first(Direction d, int... is) {
        if (!grid.isEmpty()) {
            throw new QwirkleException("This is not the first play");
        }
        Tile[] line = new Tile[is.length];
        for (int i = 0; i < is.length; i++) {
            line[i] = players[currentplayer].getHand().get(is[i]);
        }
        int points = grid.firstAdd(d, line);
        players[currentplayer].addScore(points);
        players[currentplayer].remove(line);
        players[currentplayer].refill();
    }


    /**
     * Plays a single tile on the game grid.
     *
     * @param row   the row position to add the tile
     * @param col   the column position to add the tile
     * @param index the index of the tile to be played from the current player's hand
     * @see Player
     * @see Tile
     */
    public void play(int row, int col, int index) {
        int points = grid.add(row, col, players[currentplayer].getHand().get(index));
        players[currentplayer].addScore(points);
        players[currentplayer].remove(players[currentplayer].getHand().get(index));
        players[currentplayer].refill();

    }

    /**
     * Plays a line of tiles on the game grid.
     *
     * @param row     the row position to add the line
     * @param col     the column position to add the line
     * @param d       the direction of the line to be added (either D.HORIZONTAL or D.VERTICAL)
     * @param indexes the indices of the tiles to be played from the current player's hand
     */
    public void play(int row, int col, Direction d, int... indexes) {
        Tile[] line = new Tile[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            line[i] = players[currentplayer].getHand().get(indexes[i]);
        }
        int points = grid.add(row, col, d, line);
        players[currentplayer].addScore(points);
        players[currentplayer].remove(line);
        players[currentplayer].refill();
    }

    /**
     * Plays multiple tiles at once on the grid by specifying their position and index in the player's hand.
     *
     * @param is an array of integers representing the position (row and column) and index of each tile in the player's hand.
     *           The array must have a length that is a multiple of 3.
     * @throws QwirkleException if the position or index is invalid, or if the tiles cannot be placed on the grid
     */

    public void play(int... is) {
        TileAtPosition[] tiles = new TileAtPosition[is.length / 3];
        int j = 0;
        for (int i = 0; i < is.length - 2; i = i + 3) {
            tiles[j] = new TileAtPosition(is[i], is[i + 1], players[currentplayer].getHand().get(is[i + 2]));
            j++;
        }
        int points = grid.add(tiles);

        /*for (int i = is.length - 3; i >= 0; i = i - 3) {
            players[currentplayer].remove(players[currentplayer].getHand().get(is[i + 2]));
        }**/

        Tile[] removeTiles = new Tile[is.length / 3];
        int k = 0;
        for (int i = is.length - 3; i >= 0; i = i - 3) {
            removeTiles[k] = players[currentplayer].getHand().get(is[i + 2]);
            k++;
        }
        players[currentplayer].remove(removeTiles);

        players[currentplayer].addScore(points);
        players[currentplayer].refill();
    }


    /**
     * Returns the name of the current player.
     *
     * @return the name of the current player
     */
    public String getCurrentPlayerName() {
        return players[currentplayer].getName();
    }


    /**
     * Returns a list of tiles in the hand of the current player.
     *
     * @return a list of tiles in the hand of the current player
     */
    public List<Tile> getCurrentPlayerHand() {
        return players[currentplayer].getHand();
    }


    /**
     * Returns a new GridView object representing the current state of the grid.
     *
     * @return a new GridView object representing the current state of the grid
     */
    public GridView getGrid() {
        return new GridView(grid);
    }

    /**
     * Returns the current player's score.
     *
     * @return The current player's score.
     */
    public int getCurrentplayerScore() {
        return players[currentplayer].getScore();
    }

    /**
     * Advances the game to the next player in turn.
     */
    public void pass() {
        if (currentplayer == players.length - 1) {
            players[currentplayer].addScore(-1);
            currentplayer = 0;
        } else {
            players[currentplayer].addScore(-1);
            currentplayer++;
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isOver() {
        if (players[currentplayer].getHand().size() == 0) {
            players[currentplayer].addScore(6);
            return true;
        }

        return Bag.getInstance().size() == 0 && canNotPlay();
    }

    /**
     * Checks if the current player cannot make any valid moves.
     *
     * @return true if the current player cannot play, false otherwise
     */

    public boolean canNotPlay() {

        if (grid.isEmpty()) {
            return false;
        }

        for (int i = 0; i < 91; i++) {
            for (int j = 0; j < 91; j++) {
                for (Player player : players) {
                    for (Tile tile : player.getHand()) {
                        try {
                            grid.addFake(i, j, tile);
                            return false;
                        }
                        catch (QwirkleException ignored) {
                        }
                    }
                }
            }
        }

        return true;
    }

    public String winner() {
        int max = players[0].getScore();
        String NomMax = "";
        for (Player player : players) {
            if (player.getScore() > max) {
                max = player.getScore();
                NomMax = player.getName();
            }
        }
        return NomMax;
    }

    /**
     * Désérialise un objet Game à partir d'un fichier.
     *
     * @param fileName le nom du fichier à partir duquel désérialiser l'objet Game
     * @return l'objet Game désérialisé, ou null si une erreur s'est produite
     */
    public Game getFromFile(String fileName) {
        Game game;
        fileName += ".ser";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            game = (Game) in.readObject();
            in.close();
            System.out.println("la partie a été restaurée avec succès");
        } catch (IOException | ClassNotFoundException e) {
            throw new QwirkleException("la restauration de la sauvegarde a échoué");
        }
        return game;
    }

    /**
     * Sauvegarde un objet Game dans un fichier utilisant la sérialisation.
     *
     * @param fileName le nom du fichier qui va etre serialiser
     */
    public void write(String fileName) {
        fileName += ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            out.close();
            System.out.println("La partie a été sauvegardée avec succès");
        } catch (IOException e) {
            throw new QwirkleException("la sauvegarde a échoué");
        }
    }
}

