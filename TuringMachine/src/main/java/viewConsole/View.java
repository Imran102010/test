package viewConsole;

import Util.Observer;
import model.SuperGame;

import java.util.List;
import java.util.Map;

public class View implements Observer {

    SuperGame gameFacade;

    /**
     * Constructs a new `View` with the specified `SuperGame` instance.
     *
     * @param gameFacade The `SuperGame` instance to observe.
     */
    public View(SuperGame gameFacade) {
        this.gameFacade = gameFacade;
        gameFacade.addObserver(this);
    }

    /**
     * Displays the help menu with available commands.
     */
    public void displayHelp() {
        System.out.println("T U R I N G - M A C H I N E");
        System.out.println("TuringMachine command:");
        System.out.println("- choose a code : code <code>");
        System.out.println("- check a validator : validator <nbValidator>");
        System.out.println("- verify the code : verify <code>");
        System.out.println("- move to next round : next");
        System.out.println("- undo the last command : undo");
        System.out.println("- redo the command : redo");
        System.out.println("- show validators : show");
        System.out.println("- help : help");
        System.out.println("- quit : exit");
    }

    /**
     * Displays a message when the player wins.
     *
     * @param scoreTotal The total score achieved by the player.
     */
    public void displayWin(int scoreTotal) {
        System.out.println("You won ! You found the secretCode with a score of  " + scoreTotal + " !");
    }

    /**
     * Displays a message when the player loses.
     *
     * @param scoreTotal The total score achieved by the player.
     */
    public void displayLost(int scoreTotal) {
        System.out.println("You lost ! With a score of " + scoreTotal + " !");
    }


    /**
     * Displays a message prompting the user to choose a code.
     */
    public void displayEnterCode() {
        System.out.println("Choose the code in the enter command ");
    }

    /**
     * Displays information about the available validators.
     *
     * @param validators The list of available validators.
     */
    public void displayValidators(List<Integer> validators) {
        for (Integer validator : validators) {
            switch (validator) {
                case 1 -> System.out.print("Validator numero 1 : Compare le premier chiffre du code avec 1");
                case 2 -> System.out.print("Validator numero 2 : Compare le premier chiffre avec 3");
                case 3 -> System.out.print("Validator numero 3 : Compare le deuxième chiffre avec 3");
                case 4 -> System.out.print("Validator numero 4 : Compare le deuxième chiffre avec 4");
                case 5 -> System.out.print("Validator numero 5 : Vérifie la parité du premier chiffre du code");
                case 6 -> System.out.print("Validator numero 6 : Vérifie la parité du deuxième chiffre");
                case 7 -> System.out.print("Validator numero 7 : Vérifie la parité du troisième chiffre");
                case 8 -> System.out.print("Validator numero 8 : Compte combien de fois la valeur 1 apparaît dans le code");
                case 9 -> System.out.print("Validator numero 9 : Compte combien de fois la valeur 3 apparaît");
                case 10 -> System.out.print("Validator numero 10 : Compte combien de fois la valeur 4 apparaît");
                case 11 -> System.out.print("Validator numero 11 : Compare le premier chiffre du code avec le deuxième");
                case 12 -> System.out.print("Validator numero 12 : Compare le premier chiffre du code avec le troisième");
                case 13 -> System.out.print("Validator numero 13 : Compare le deuxième chiffre du code avec le troisième");
                case 14 -> System.out.print("Validator numero 14 : Détermine quel chiffre est strictement le plus petit");
                case 15 -> System.out.print("Validator numero 15 : Détermine quel chiffre est strictement le plus grand");
                case 16 -> System.out.print("Validator numero 16 : Détermine s’il y a plus de chiffre pairs ou impairs dans le code");
                case 17 -> System.out.print("Validator numero 17 : Compte combien de chiffres dans le code sont pairs");
                case 18 -> System.out.print("Validator numero 18 : Détermine si la somme des chiffres du code est paire ou impaire");
                case 19 -> System.out.print("Validator numero 19 : Compare la somme du premier du deuxième chiffre du code avec la valeur ");
                case 20 -> System.out.print("Validator numero 20 : Détermine si un chiffre du code se répète, et si oui, combien de fois");
                case 21 -> System.out.print("Validator numero 21 :Détermine si un chiffre du code se trouve en exactement deux exemplaires dans le code (mais pas trois)");
                case 22 -> System.out.print("Validator numero 22 :Détermine si les trois chiffres du code sont en ordre croissant ou décroissant");
                default -> throw new IllegalArgumentException("Invalid validator.");
            }
            System.out.println();
        }
    }

    /**
     * Displays the states of the validators.
     *
     * @param validatorsState The map containing validator numbers and their states.
     */
    public void displayValidatorsStates(Map<Integer, String> validatorsState) {
        System.out.println("Validators State :");
        validatorsState.forEach((validatorNb, state) -> {
            String colorCode = "";

            switch (state.toLowerCase()) {
                case "red" -> colorCode = "\u001B[31m";
                case "green" -> colorCode = "\u001B[32m";
                default -> {
                }
            }

            System.out.println(colorCode + "- validator " + validatorNb + "\u001B[0m");
        });
    }

    /**
     * Updates the view based on changes in the observed `SuperGame`.
     */
    @Override
    public void update() {
        displayValidatorsStates(gameFacade.getValidators());
    }
}
