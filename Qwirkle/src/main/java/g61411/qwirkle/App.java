package g61411.qwirkle;
import java.io.*;

import g61411.qwirkle.model.Bag;
import g61411.qwirkle.model.Direction;
import g61411.qwirkle.model.Game;
import g61411.qwirkle.model.QwirkleException;
import g61411.qwirkle.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {


    /**
     * The main method to start the game. It creates a new game object with the names of the players,
     * displays the help instructions, and prompts the user for commands until the game is over.
     * The user can enter different commands to play the game, such as "o" to place a word horizontally,
     * "l" to place a word vertically, "m" to swap tiles, "f" to start the game with the first player,
     * "p" to pass the turn, "q" to quit the game, and "h" to display the help instructions.
     * After each command, the game grid and player's hand are displayed.
     */
    public static void main(String[] args) {

        Game game = initialiser();

        System.out.println(Bag.getInstance().size() + " taille du sac");
        Scanner clavier = new Scanner(System.in);
        gameloop:
        while (!game.isOver()) {
            View.display(game.getCurrentPlayerName(), game.getCurrentPlayerHand(), game.getCurrentplayerScore());
            System.out.print("Entrez la commande : ");


            String command = clavier.next().toLowerCase(Locale.ROOT);
            switch (command) {
                case "o" -> {
                    if (game.getGrid().isEmpty()) {
                        View.displayError("Veuillez jouer first d'abord");
                        break;
                    }
                    command_O(game);
                }
                case "l" -> {
                    if (game.getGrid().isEmpty()) {
                        View.displayError("Veuillez jouer first d'abord");
                        break;
                    }
                    command_L(game);
                }
                case "m" -> {
                    if (game.getGrid().isEmpty()) {
                        View.displayError("Veuillez jouer first d'abord");
                        break;
                    }
                    command_M(game);
                }
                case "f" -> {
                    if (!game.getGrid().isEmpty()) {
                        View.displayError("Vous avez deja joué first");
                        break;
                    }
                    command_F(game);
                }
                case "p" -> game.pass();
                case "q" -> {
                    System.out.println("Merci d'avoir joué a Qwirkle!");
                    break gameloop;
                }
                case "h" -> View.displayHelp();
                case "s" -> {
                    System.out.println("Entrez un nom de fichier pour la sauvegarde : ");
                    String fileName = clavier.next();
                    game.write(fileName);
                    Bag.getInstance().writeBag(fileName);
                    break gameloop;
                }

                default -> View.displayError("Veuillez entrez une commmande correcte");

            }
            View.display(game.getGrid());


        }

        System.out.println("Le gagnant est : " + game.winner());
    }
    /**
     * Initializes and returns a Game object based on user input.
     *
     * @return The initialized Game object.
     */
    private static Game initialiser() {
        Game game;
        Scanner clavier = new Scanner(System.in);
        System.out.print("Voulez-vous charger une partie précédente ? (o/n) : ");
        String response = clavier.next().toLowerCase(Locale.ROOT);

        if (response.equals("o")) {
            // Demande à l'utilisateur le nom du fichier contenant les données sérialisées
            System.out.print("Entrez le nom du fichier de sauvegarde : ");
            String fileName = clavier.next();

            game = new Game();

            // Désérialisation de l'objet Game à partir du fichier
            Game savedGame = game.getFromFile(fileName);
            Bag.getInstance().getFromFileBag(fileName);

            if (savedGame != null) {
                // Utilisez l'objet Game désérialisé pour continuer la partie
                game = savedGame;
                View.display(game.getGrid());
            } else {
                System.out.println("Erreur lors de la désérialisation du jeu. Une nouvelle partie sera créée.");
            }
        } else {
            List<String> names = playerListNames();
            game = new Game(names);

            View.displayHelp();
        }

        return game;
    }


    /**
     * Executes the "O" command, which allows the player to play a tile from their hand on the grid.
     * Prompts the user for the row and column position on the grid, as well as the index of the tile in the player's hand.
     * Calls the Game's play() method to attempt to play the tile.
     * If successful, the player passes their turn.
     * If an exception is thrown by the Game's play() method, displays an error message to the user.
     *
     * @param game the Game instance being played
     */
    private static void command_O(Game game) {
        int row = lectureEntierEntreAetB("Entrez la position de la ligne : ", 0, 90);
        int col = lectureEntierEntreAetB("Entrez la position de la colonne : ", 0, 90);
        int i = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        try {
            game.play(row, col, i);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }

    /**
     * Executes the 'L' command, which allows the player to play a line of tiles on the board, in a given direction.
     * Prompts the user for the row and column of the first tile, the direction of the line, the number of tiles to play,
     * and the indices of the tiles to play from the player's hand. Then attempts to play the line on the board and pass the turn to the next player.
     * If the move is not valid, displays an error message.
     *
     * @param game the current game instance
     */
    private static void command_L(Game game) {
        int row = lectureEntierEntreAetB("Entrez la position de la ligne : ", 0, 90);
        int col = lectureEntierEntreAetB("Entrez la position de la colonne : ", 0, 90);
        int isSize = lectureEntierEntreAetB("Combien de tuiles souhaitez vous poser : ", 1, game.getCurrentPlayerHand().size());
        Direction d = getDirection();
        int[] is = new int[isSize];
        for (int i = 0; i < is.length; i++) {
            is[i] = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        }
        try {
            game.play(row, col, d, is);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }

    /**
     * Allows the player to place multiple tiles on the board at once.
     * Asks the player to enter the position and index of each tile to place, then tries to play them on the board.
     * Displays an error message if the move is not valid.
     * Ends the player's turn by passing their turn in the game.
     *
     * @param game The game instance to play the move on.
     */
    private static void command_M(Game game) {
        int isSize = lectureEntierEntreAetB("Combien de tuiles souhaitez vous poser : ", 1, game.getCurrentPlayerHand().size());
        int[] is = new int[isSize * 3];
        for (int i = 0; i < is.length - 2; i += 3) {
            is[i] = lectureEntierEntreAetB("Entrez la position de la ligne : ", 0, 90);
            is[i + 1] = lectureEntierEntreAetB("Entrez la position de la colonne : ", 0, 90);
            is[i + 2] = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        }
        try {
            game.play(is);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }


    /**
     * Executes the command to play the first turn of the game.
     * Prompts the user to enter a direction, number of tiles to play, and the indices of the tiles to play.
     * Then calls the corresponding method in the game object to execute the command.
     * If an exception is thrown, displays an error message to the user.
     *
     * @param game the game object to execute the command on
     */
    private static void command_F(Game game) {
        Direction d = getDirection();
        int isSize = lectureEntierEntreAetB("Combien de tuiles souhaitez vous poser : ", 1, game.getCurrentPlayerHand().size());
        int[] is = new int[isSize];
        for (int i = 0; i < is.length; i++) {
            is[i] = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        }
        try {
            game.first(d, is);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }


    /**
     * Asks the user to input the number of players and their names, then returns a list of player names.
     *
     * @return a list of player names
     */
    private static List<String> playerListNames() {
        List<String> playerList = new ArrayList<>();

        Scanner clavier = new Scanner(System.in);

        int n = lectureEntierEntreAetB("Entrez le nombre de joueurs : ", 2, 4);

        for (int i = 0; i < n; i++) {
            System.out.println("Entrez votre nom : ");
            String nom = clavier.next();
            playerList.add(nom);
        }
        return playerList;
    }

    /**
     * Lecture robuste d'un entier.
     * Tant que l’entrée de l’utilisateur n’est pas
     * un entier la méthode demande une nouvelle entrée.
     *
     * @param message message à afficher.
     * @return l'entier saisi par l'utilisateur.
     */
    private static int lectureEntier(String message) {
        Scanner clavier = new Scanner(System.in);
        System.out.println(message);
        while (!clavier.hasNextInt()) {
            System.out.println("Ce n'est pas ce qui est demandé. " + message);
            clavier.next();
        }
        return clavier.nextInt();
    }

    private static int lectureEntierEntreAetB(String message, int a, int b) {
        int nb = lectureEntier(message);

        while (nb < a || nb > b) {
            System.out.println("Ce n'est pas ce qui est demandé. ");
            nb = lectureEntier(message);
        }

        return nb;
    }

    /**
     * This method prompts the user to enter a direction and returns the corresponding Direction enum value.
     *
     * @return the Direction enum value entered by the user
     */
    private static Direction getDirection() {
        Scanner clavier = new Scanner(System.in);
        System.out.print("Entrez une direction (u, d, r, l) : ");
        String command = clavier.next().toLowerCase(Locale.ROOT);
        Direction direction;
        switch (command) {
            case "u" -> direction = Direction.UP;
            case "d" -> direction = Direction.DOWN;
            case "r" -> direction = Direction.RIGHT;
            case "l" -> direction = Direction.LEFT;
            default -> {
                View.displayError("Veuillez entrer une direction correcte");
                direction = getDirection();
            }
        }
        return direction;
    }

}
