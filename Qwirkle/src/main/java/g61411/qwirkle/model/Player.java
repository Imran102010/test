package g61411.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


/**
 * Represents a player in a game of Scrabble.
 */
public class Player implements Serializable {


    /**
     * The name of the player.
     */
    private final String name;

    /**
     * The tiles in the player's hand.
     */
    private final List<Tile> tiles;
    /**
     * The score of the player.
     */
    private int score;

    /**
     * Constructs a new player with the given name and a hand of six random tiles.
     *
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
        tiles = new ArrayList<>();
        tiles.addAll(List.of(Bag.getInstance().getRandomTiles(6)));
        score=5;
        //
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an unmodifiable view of the tiles in the player's hand.
     *
     * @return an unmodifiable view of the tiles in the player's hand.
     */
    public List<Tile> getHand() {
        return Collections.unmodifiableList(tiles);
    }

    /**
     * Returns the score of the player.
     *
     * @return the score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Refills the player's hand with new tiles up to a maximum of six tiles.
     */
    public void refill() {
        if(Bag.getInstance().size()!=0){
            tiles.addAll(List.of(Bag.getInstance().getRandomTiles(6 - tiles.size())));
        }
    }



    /**
     * Removes the specified tiles from the player's hand.
     *
     * @param ts the tiles to remove.
     */
    public void remove(Tile... ts) {
        //tiles.removeAll(List.of(ts));
        for (Tile tile : ts) {
            tiles.remove(tile);
        }
    }
    /**
     * Increases the current score by the specified value.
     *
     * @param value The value to be added to the score.
     */
    public void addScore(int value) {
        score += value;
    }
}
