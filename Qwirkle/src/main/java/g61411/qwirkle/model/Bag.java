package g61411.qwirkle.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Bag represent the bag of tiles.
 */
public class Bag implements Serializable {


    /**
     * The tiles field represents a list of Tile objects.
     * It is a final field, which means it cannot be modified once it is initialized.
     * The list of Tile objects is used to represent a player's hand in a game.
     */
    private List<Tile> tiles;


    /**
     * The instance field represents the only instance of the Bag class.
     * It is a private, static, and final field, which means it cannot be modified once it is initialized and is accessible only within the Bag class.
     * The Bag object is used to manage a set of tiles in a game of Qwirkle.
     */
    private static Bag instance = new Bag();

    /**
     * Constructs a Bag object with 108 tiles.
     */

    private Bag() { //private permet de ne pas creer un nouveau bag
        tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (Shape s : Shape.values()) {
                for (Color c : Color.values()) {
                    tiles.add(new Tile(c, s));
                }
            }
        }
    }

    /**
     * @return Returns the singleton instance of the Bag class.
     */
    public static Bag getInstance() {

        return instance;
    }

    /**
     * Empties the bag of tiles by replacing the current tile collection with a new empty list.
     * This operation effectively removes all tiles from the bag.
     */
    public void cleanBag(){
        tiles = new ArrayList<>();
    }
    /**
     * @param n this is the number of tiles you want drawn
     * @return it returns null if the list is empty otherwise it returns a list containing tiles
     */
    public Tile[] getRandomTiles(int n) {
        List<Tile> pickedTiles = new ArrayList<>();

        if (n <= 0 || n > 6) {
            throw new QwirkleException("The number of tiles is smaller than 0 or bigger than 6");
        }

        if (tiles.isEmpty()) {
            return null;
        }

        if (n >= size()) { //si pas assez de tuile prend tout ce qu'il reste
            n = size();
            for (int i = 0; i < n; i++) {
                pickedTiles.add(tiles.get(n));
                tiles.remove(n);
            }
        } else {// sinon prend des tuiles au hasard
            for (int i = 0; i < n; i++) {
                int indice = (int) (Math.random() * size());
                pickedTiles.add(tiles.get(indice));
                tiles.remove(indice);
            }
        }

        return pickedTiles.toArray(new Tile[0]);
    }

    /**
     * @return la taille de la liste tuile
     */
    public int size() {
        return tiles.size();
    }

    /**
     * Désérialise un objet Game à partir d'un fichier.
     *
     * @param fileName le nom du fichier à partir duquel désérialiser l'objet Game
     * @return l'objet Game désérialisé, ou null si une erreur s'est produite
     */
    public void getFromFileBag(String fileName) {
        fileName += "Bag.ser";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            instance = (Bag) in.readObject();
            in.close();
            System.out.println("Le Bag est restauré");
        } catch (IOException | ClassNotFoundException e) {
            throw new QwirkleException("La restauration de la sauvegarde du Bag a échoué");
        }
    }
    /**
     * Saves the Game object to a file using serialization.
     *
     * @param fileName The name of the file to save the Bag object to.
     * @throws QwirkleException If the saving process fails.
     */
    public void writeBag(String fileName) {
        fileName += "Bag.ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            out.close();
            System.out.println("Le Bag a été sauvegardé avec succès");
        } catch (IOException e) {
            throw new QwirkleException("La sauvegarde du Bag a échoué");
        }
    }
}
