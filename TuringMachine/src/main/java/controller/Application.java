package controller;

import model.SuperGame;
import model.TurningMachineException;
import viewConsole.View;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    private SuperGame superGame;
    private View view;
    private boolean gameEnd;

    private static final String CODE_REGEX = "code (\\d+)";
    private static final String VALIDATOR_REGEX = "validator (\\d+)";
    private static final String VERIFY_REGEX = "verify (\\d+)";
    private static final String NEXT_ROUND_REGEX = "next";
    private static final String REDO_REGEX = "redo";
    private static final String UNDO_REGEX = "undo";
    private static final String SHOW_VALIDATORS_REGEX = "show";

    private static final Pattern CODE_PATTERN = Pattern.compile(CODE_REGEX);
    private static final Pattern VALIDATOR_PATTERN = Pattern.compile(VALIDATOR_REGEX);
    private static final Pattern VERIFY_PATTERN = Pattern.compile(VERIFY_REGEX);


    /**
     * The main method that launches the Turing Machine game application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

    /**
     * Starts the Turing Machine game application.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        boolean chooseProblem = askForSpecifiedProblem(scanner);
        if (chooseProblem) {
            createGame(scanner);
        } else {
            superGame = new SuperGame();
        }

        gameEnd = false;
        view = new View(superGame);

        view.displayHelp();

        String command;
        do {
            System.out.print("Enter a command: ");
            command = scanner.nextLine();
            handleCommand(command);
        } while (!command.equalsIgnoreCase("exit") && !gameEnd);
    }

    /**
     * Asks the user if they would like to choose a specified problem.
     *
     * @param scanner The scanner object for user input.
     * @return True if the user chooses a specified problem, false otherwise.
     */
    private boolean askForSpecifiedProblem(Scanner scanner) {
        System.out.print("Would you like to choose a specified problem ? (yes/no): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes") || response.equals("oui");
    }

    /**
     * Creates a new game with a specified problem based on user input.
     *
     * @param scanner The scanner object for user input.
     */
    private void createGame(Scanner scanner) {
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter the number of the problem : ");
                int nb = scanner.nextInt();
                superGame = new SuperGame(nb);
                validInput = true;
            } catch (TurningMachineException e) {
                System.out.println("Invalid problem number. Please provide valid number problem.");
            }
        }
    }

    /**
     * Handles various commands entered by the user in the Turing Machine game application.
     */
    private void handleCode(String command) {
        Matcher matcherCode = CODE_PATTERN.matcher(command);
        if (matcherCode.find()) {
            int Code = Integer.parseInt(matcherCode.group(1));
            superGame.chooseCode(Code);
        }
    }

    /**
     * Handles the validator command entered by the user in the Turing Machine game application.
     */
    private void handleValidator(String command) {
        Matcher matcherValidator = VALIDATOR_PATTERN.matcher(command);
        if (matcherValidator.find()) {
            int nbValidator = Integer.parseInt(matcherValidator.group(1));
            superGame.verifyValidator(nbValidator);
        }
    }

    /**
     * Handles various commands entered by the user in the Turing Machine game application.
     */
    private void handleCommand(String command) {
        try {
            if (command.matches(CODE_REGEX)) {
                handleCode(command);
            } else if (command.matches(VALIDATOR_REGEX)) {
                handleValidator(command);
            } else if (command.matches(VERIFY_REGEX)) {
                handleVerifyCode(command);
            } else if (command.matches(NEXT_ROUND_REGEX)) {
                superGame.nextRound();
                view.displayEnterCode();
            } else if (command.matches(SHOW_VALIDATORS_REGEX)) {
                view.displayValidators(superGame.validatorList());
            } else if (command.matches(UNDO_REGEX)) {
                superGame.getManager().undo();
            } else if (command.matches(REDO_REGEX)) {
                superGame.getManager().redo();
            } else if (command.equalsIgnoreCase("help")) {
                view.displayHelp();
            } else if (!command.equalsIgnoreCase("exit")) {
                System.out.println("Invalid command.");
            }
        } catch (TurningMachineException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Handles the verify code command entered by the user in the Turing Machine game application.
     */
    private void handleVerifyCode(String command) {
        Matcher matcherValidator = VERIFY_PATTERN.matcher(command);
        if (matcherValidator.find()) {
            int code = Integer.parseInt(matcherValidator.group(1));
            if (superGame.verifyCode(code)) {
                view.displayWin(superGame.getScore());
            } else {
                view.displayLost(superGame.getScore());
            }
            gameEnd = true;
        }
    }
}



