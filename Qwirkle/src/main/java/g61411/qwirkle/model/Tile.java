package g61411.qwirkle.model;

import java.io.Serializable;

/**
 * The Tile class represents a single tile in a game of Qwirkle.
 * A tile is defined by a color and a shape.
 */
public record Tile(Color color, Shape shape)implements Serializable {

}
