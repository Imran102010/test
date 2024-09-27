package g61411.qwirkle.view;

import g61411.qwirkle.model.*;

import java.util.List;


public class View {

    /**
     * Displays the current state of the grid in the console.
     *
     * @param grid the grid to display
     */
    public static void display(GridView grid) {
        int minRow = grid.BOARD_SIZE/2;
        int maxRow = grid.BOARD_SIZE/2;
        int minCol = grid.BOARD_SIZE/2;
        int maxCol = grid.BOARD_SIZE/2;

        for (int i = 0; i < grid.BOARD_SIZE; i++) {
            for (int j = 0; j < grid.BOARD_SIZE; j++) {
                if (grid.get(i, j) != null) {
                    if (i < minRow) {
                        minRow = i;
                    }
                    if (i > maxRow) {
                        maxRow = i;
                    }
                    if (j < minCol) {
                        minCol = j;
                    }
                    if (j > maxCol) {
                        maxCol = j;
                    }
                }
            }
        }

        for (int i = minRow; i <= maxRow; i++) {
            System.out.print(i + " |");
            for (int j = minCol; j <= maxCol; j++) {
                Tile tile = grid.get(i, j);
                if (tile != null) {
                    displayTile(tile.color(), tile.shape());
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }

        System.out.print("    ");
        for (int i = minCol; i <= maxCol; i++) {
            System.out.print(i +" ");
        }
        System.out.println();

        System.out.println("taille du sac de Tuiles: "+ Bag.getInstance().size());

    }


    /**
     * Displays the name and hand of a player in the console.
     *
     * @param name the name of the player
     * @param hand the hand of the player
     */
    public static void display(String name, List<Tile> hand,int score) {
        System.out.print("Player : " + name + "  ");

        for (Tile tile : hand) {
            displayTile(tile.color(), tile.shape());
            System.out.print(" ");
        }
        System.out.print(", Score " + score);

        System.out.println();
    }


    /**
     * Displays a tile with a specific color and shape using ANSI escape codes to add color to the console output.
     * @param color The color of the tile to be displayed.
     * @param shape The shape of the tile to be displayed.
     */
    private static void displayTile(Color color, Shape shape) {
        String colorCode = switch (color) {
            case RED -> "\u001B[31m";
            case BLUE -> "\u001B[34m";
            case YELLOW -> "\u001B[33m";
            case GREEN -> "\u001B[32m";
            case ORANGE -> "\u001B[38;5;208m";
            case PURPLE -> "\u001B[38;5;165m";
        };

        String shapeDrawn = switch (shape) {
            case ROUND -> " O ";
            case PLUS -> " + ";
            case CROSS -> " X ";
            case STAR -> " * ";
            case SQUARE -> "[ ]";
            case DIAMOND -> "< >";
        };

        System.out.print(colorCode + shapeDrawn + "\u001B[0m");
    }


    /**
     * Displays the Qwirkle game command help in the console.
     */
    public static void displayHelp() {
        System.out.println("Q W I R K L E");
        System.out.println("Qwirkle command :");
        System.out.println("- play 1 tile : o <row> <col> <i>");
        System.out.println("- play line : l <row> <col> <direction> <i1> [<i2>]");
        System.out.println("- play plic-ploc : m <row1> <col1> <i1> [<row2> <col2> <i2>]");
        System.out.println("- play first : f [<direction>] <f1> [<f2> …]");
        System.out.println("- pass : p");
        System.out.println("- quit : q");
        System.out.println("- save : s");
        System.out.println("   i : index in list of tiles");
        System.out.println("   d : direction in l (left), r (right), u (up), d(down)");
    }


    /**
     * Displays an error message in the console with red color.
     *
     * @param message the error message to display
     */
    public static void displayError(String message) {
        System.out.println();
        System.out.println("\u001B[31m" + message + "\u001B[0m");
        System.out.println();

    }
}
