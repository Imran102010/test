package viewFx;

import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Code;
import model.SuperGame;
import Util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.TurningMachineException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class GamePlayPane extends VBox implements Observer {

    private final ToggleGroup[] toggleGroups = new ToggleGroup[3];
    private ToggleButton[][] keyboardButtons = new ToggleButton[3][5];
    private Map<Integer, ImageView> robotButtonMap = new HashMap<>();
    private Map<Integer, Character> robotLetterMap = new HashMap<>();
    private SuperGame superGame;
    private final HBox choix = new HBox();
    private TextField tfdCode;
    private final HBox valid = new HBox();
    private Label rounds;
    private Label score;
    private Label errorLabel;
    private Button btnBack;
    private ButtonType replayButton;
    private ButtonType quitButton;
    private Alert alert;

    /**
     * Constructs a new GamePlayPane, initializing the graphical elements
     * and components for the gameplay interface. This pane includes
     * validators, a virtual keyboard, control buttons, and a back button.
     * The styles are applied, and the alignment is set to the center.
     * An Alert of type NONE is also created for displaying messages during gameplay.
     */
    public GamePlayPane() {
        initialize(new SuperGame());
    }

    /**
     * Constructs a new GamePlayPane for a specific problem number, initializing
     * the graphical elements and components for the gameplay interface. This pane includes
     * validators, a virtual keyboard, control buttons, and a back button.
     * The styles are applied, and the alignment is set to the center.
     * An Alert of type NONE is also created for displaying messages during gameplay.
     *
     * @param numberProblem The specific problem number for which the gameplay is initialized.
     */
    public GamePlayPane(int numberProblem) {
        initialize(new SuperGame(numberProblem));
    }

    /**
     * Initializes the GamePlayPane with a new SuperGame, observer registration, and layout configuration.
     */
    private void initialize(SuperGame game) {
        superGame = game;
        superGame.addObserver(this);

        initializeValidators();
        initializeKeyboard();
        createButtons();
        applyStyles();
        addBackButton();

        setAlignment(Pos.CENTER);
        alert = new Alert(Alert.AlertType.NONE);
    }

    /**
     * Creates and configures the buttons and layouts for the GamePlayPane.
     * Includes rows of buttons, code confirmation section, and error label.
     */
    private void createButtons() {
        VBox buttonsRow = createButtonsRow();
        VBox confirmeCode = createConfirmeCode();

        VBox.setMargin(confirmeCode, new Insets(0, 10, 20, 10));

        HBox choixGlobal = createChoixGlobal();
        HBox choix2 = createChoix2();
        errorLabel = new Label();
        errorLabel.getStyleClass().add("alert-label");

        choix.setAlignment(Pos.CENTER);
        choix.getChildren().addAll(buttonsRow, confirmeCode);

        choixGlobal.getChildren().addAll(choix, choix2);

        getChildren().addAll(valid, errorLabel, choixGlobal);
    }

    /**
     * Creates and configures a VBox for a row of buttons in the GamePlayPane.
     * Includes "Undo," "Redo," and "Next Round" buttons with corresponding actions.
     *
     * @return The configured VBox containing the buttons.
     */
    private VBox createButtonsRow() {
        VBox buttonsRow = new VBox();
        buttonsRow.setAlignment(Pos.CENTER);
        buttonsRow.setSpacing(10);

        Button undoButton = createButton("Undo", this::handleUndoButtonClick);
        undoButton.setId("undo-button");

        Button redoButton = createButton("Redo", this::handleRedoButtonClick);
        redoButton.setId("redo-button");

        Button nextRoundButton = createButton("Next Round", this::handleNextRoundButtonClick);
        nextRoundButton.setId("next-round-button");

        buttonsRow.getChildren().addAll(undoButton, redoButton, nextRoundButton);
        return buttonsRow;
    }

    /**
     * Creates and configures a VBox for the code confirmation section in the GamePlayPane.
     * Includes a "Check Code" button with the corresponding action and a TextField for entering the code.
     *
     * @return The configured VBox for code confirmation.
     */
    private VBox createConfirmeCode() {
        VBox confirmeCode = new VBox();
        confirmeCode.setAlignment(Pos.CENTER);
        confirmeCode.setSpacing(15);
        Button boutonCheckCode = createButton("Check Code", this::handleCheckCodeButtonClick);
        boutonCheckCode.setId("check-code-button");
        tfdCode = new TextField();
        tfdCode.setPromptText("Entrer votre dernier Code");
        setupCodeTextField();
        confirmeCode.getChildren().addAll(tfdCode, boutonCheckCode);
        confirmeCode.setAlignment(Pos.CENTER);
        confirmeCode.setSpacing(20);
        return confirmeCode;
    }

    /**
     * Creates and configures an HBox for the global choice section in the GamePlayPane.
     * @return The configured HBox for global choices.
     */
    private HBox createChoixGlobal() {
        HBox choixGlobal = new HBox();
        choixGlobal.setAlignment(Pos.CENTER);
        return choixGlobal;
    }

    /**
     * Creates and configures an HBox for the second choice section in the GamePlayPane.
     * Includes game information and ensures horizontal growth for the first choice section.
     *
     * @return The configured HBox for the second choice.
     */
    private HBox createChoix2() {
        HBox choix2 = new HBox();
        choix2.getChildren().add(gameInfo());
        HBox.setHgrow(choix, Priority.ALWAYS);
        choix2.setAlignment(Pos.CENTER_RIGHT);
        choix2.setPadding(new Insets(80, 80, 0, 0));
        return choix2;
    }

    /**
     * Configures the TextField for entering the code in the GamePlayPane.
     * Sets up a TextFormatter with an Integer converter and a validator
     * to allow only valid input matching the pattern [1-5]{0,3}.
     */
    private void setupCodeTextField() {
        StringConverter<Integer> converter = new IntegerStringConverter();
        TextFormatter<Integer> textFormatter = new TextFormatter<>(converter, null,
                change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-5]{0,3}")) {
                        return change;
                    } else {
                        return null;
                    }
                });
        tfdCode.setTextFormatter(textFormatter);
    }

    /**
     * Creates and configures a VBox for displaying game information in the GamePlayPane.
     * @return The configured VBox for game information.
     */
    private VBox gameInfo() {
        VBox gameInfo = new VBox();
        score = new Label();
        rounds = new Label();
        Label numberPoblem = new Label();

        score.setText("score : " + superGame.getScore());
        rounds.setText("round : " + superGame.getRound());
        numberPoblem.setText(" number problem : " + superGame.numberProblem());
        gameInfo.getChildren().addAll(score, rounds, numberPoblem);
        gameInfo.setAlignment(Pos.TOP_RIGHT);

        return gameInfo;
    }

    /**
     * Creates a button with the specified text and action handler.
     *
     * @param text    The text to be displayed on the button.
     * @param handler The action handler for the button.
     * @return The configured Button.
     */
    private Button createButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setAlignment(Pos.BOTTOM_CENTER);
        setMargin(button, new Insets(10, 10, 10, 10));
        button.setId("btn-round");
        button.setOnAction(handler);
        return button;
    }

    /**
     * Applies styles to the GamePlayPane, including CSS stylesheets and background color.
     */
    private void applyStyles() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        setStyle("-fx-background-color: white;");
        setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
    }

    /**
     * Initializes the validators for the GamePlayPane, creating validator columns and configuring their layout.
     * Also, sets up the keyboard layout and buttons for the code input.
     */
    private void initializeValidators() {
        char[] robotLetter = {'A', 'B', 'C', 'D', 'E', 'F'};
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        int indexCol = 0;
        int numberOfValidators = superGame.validatorList().size();
        double imageSizePercentage = 0.15;
        double espacementHorizontal = screenWidth / (numberOfValidators + 1);
        double espacementEntreValidateurs = espacementHorizontal * 0.125;

        HBox.setHgrow(valid, Priority.ALWAYS);

        Region espacementInitial = new Region();
        HBox.setHgrow(espacementInitial, Priority.ALWAYS);
        espacementInitial.setMinWidth(espacementEntreValidateurs);
        valid.getChildren().add(espacementInitial);

        for (Integer v : superGame.validatorList()) {
            createValidatorColumn(robotLetter, screenWidth, indexCol, imageSizePercentage, v, espacementEntreValidateurs);
            indexCol++;
        }
        valid.setPrefWidth(screenWidth);
        valid.setAlignment(Pos.CENTER);
        valid.setStyle("-fx-background-color: white;");
    }

    /**
     * Creates and configures a validator column in the GamePlayPane.
     *
     * @param robotLetter         An array of robot letters.
     * @param screenWidth         The width of the screen.
     * @param indexCol            The index of the column.
     * @param imageSizePercentage The percentage of the screen width for the image size.
     * @param v                   The validator associated with the column.
     * @param espacement          The horizontal spacing between validator columns.
     */
    private void createValidatorColumn(char[] robotLetter, double screenWidth, int indexCol, double imageSizePercentage, Integer v, double espacement) {
        String face = "/robotPicture/robot" + robotLetter[indexCol] + "white.png";
        String card = "/ValidatorCards/card" + v + ".png";
        Image robot = new Image(Objects.requireNonNull(getClass().getResourceAsStream(face)));

        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(20, 0, 20, 0));
        vBox.setAlignment(Pos.CENTER);

        double imageSize = screenWidth * imageSizePercentage;
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card)));
        ImageView imageView1 = createImageView(image1, imageSize);

        ImageView robotButton = createImageView(robot, (50));
        robotButtonMap.put(v, robotButton);
        robotLetterMap.put(v, robotLetter[indexCol]);

        vBox.getChildren().addAll(imageView1, robotButton);
        robotButton.setId("robot-button");


        robotButton.setOnMouseClicked(e -> {
            handleRobotButtonClick(v);
        });

        valid.getChildren().add(vBox);

        Region espacementRegion = new Region();
        HBox.setHgrow(espacementRegion, Priority.ALWAYS);
        espacementRegion.setMinWidth(espacement);
        valid.getChildren().add(espacementRegion);
    }

    /**
     * Handles the click event when a robot button is clicked.
     *
     * @param v The validator associated with the clicked robot button.
     */
    private void handleRobotButtonClick(Integer v) {
        try {
            superGame.verifyValidator(v);
            errorLabel.setText("");
        } catch (TurningMachineException exception) {
            errorLabel.setText("Error: " + exception.getMessage());
        }
    }

    /**
     * Handles the click event when the "Validate" button is clicked.
     *
     * @param event The ActionEvent associated with the button click.
     */
    private void handleValidateButtonClick(ActionEvent event) {
        try {
            superGame.chooseCode(validateCode());
            onValidateButtonClick(superGame.hasEnteredCode());
            errorLabel.setText("");
        } catch (TurningMachineException exception) {
            errorLabel.setText("Error: " + exception.getMessage());
        }
    }

    /**
     * Handles the click event when the "Next Round" button is clicked.
     *
     * @param event The ActionEvent associated with the button click.
     */
    private void handleNextRoundButtonClick(ActionEvent event) {
        try {
            superGame.nextRound();
            clearButtonStyles();
            onValidateButtonClick(superGame.hasEnteredCode());
        } catch (TurningMachineException exception) {
            errorLabel.setText("Error: " + exception.getMessage());
        }
    }

    /**
     * Handles the click event when the "Undo" button is clicked.
     *
     * @param event The ActionEvent associated with the button click.
     */
    private void handleUndoButtonClick(ActionEvent event) {
        try {
            superGame.getManager().undo();
            onValidateButtonClick(superGame.hasEnteredCode());
            if (superGame.getProposedCode() != null) {
                resetButtonStyles();
            }
        } catch (TurningMachineException exception) {
            errorLabel.setText("Error: " + exception.getMessage());
        }
    }

    /**
     * Handles the click event when the "Redo" button is clicked.
     *
     * @param event The ActionEvent associated with the button click.
     */
    private void handleRedoButtonClick(ActionEvent event) {
        try {
            superGame.getManager().redo();
            onValidateButtonClick(superGame.hasEnteredCode());
        } catch (TurningMachineException exception) {
            errorLabel.setText("Error: " + exception.getMessage());
        }
    }

    /**
     * Handles the click event when the "Check Code" button is clicked.
     *
     * @param actionEvent The ActionEvent associated with the button click.
     */
    private void handleCheckCodeButtonClick(ActionEvent actionEvent) {
        try {
            String enteredCode = tfdCode.getText();
            int codeAsInt = Integer.parseInt(enteredCode);
            if (superGame.verifyCode(codeAsInt)) {
                showPopup("You Win!", "Congratulations! You guessed the correct code.");
            } else {
                showPopup("You Lose", "Sorry, the code is incorrect.");
            }
            errorLabel.setText("");
        } catch (TurningMachineException exception) {
            errorLabel.setText("Error: " + exception.getMessage());
        }
    }

    /**
     * Displays a popup with the specified title and content.
     *
     * @param title   The title of the popup.
     * @param content The content of the popup.
     */
    private void showPopup(String title, String content) {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        replayButton = new ButtonType("Rejouer", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().add(replayButton);

        quitButton = new ButtonType("Quitter", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().add(quitButton);

        alert.showAndWait();
    }

    /**
     * Creates an ImageView with the specified image and size.
     *
     * @param image The image for the ImageView.
     * @return An ImageView with the specified image and size.
     */
    private ImageView createImageView(Image image, double taille) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(taille);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Adds a centered ImageView to the specified GridPane at the specified column index.
     *
     * @param grid       The GridPane to which the ImageView is added.
     * @param image      The image for the ImageView.
     * @param columnIndex The column index in the GridPane where the ImageView is added.
     */
    private void addCenteredImageViewToGrid(GridPane grid, Image image, int columnIndex) {
        ImageView imageView = createImageView(image, 25);
        grid.add(imageView, columnIndex, 0);
        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);
    }

    /**
     * Initializes the keyboard UI component, including shapes, buttons, and validation logic.
     */
    private void initializeKeyboard() {
        VBox keyBoard = new VBox();
        keyBoard.setPadding(new Insets(50));
        GridPane gridPane = new GridPane();
        gridPane.setId("keyboard-grid");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(30, 10, 10, 10));

        addCenteredImageViewToGrid(gridPane, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/shapes/triangle.png"))), 0);
        addCenteredImageViewToGrid(gridPane, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/shapes/square.png"))), 1);
        addCenteredImageViewToGrid(gridPane, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/shapes/circle.png"))), 2);

        for (int col = 0; col < 3; col++) {
            ToggleGroup toggleGroup = new ToggleGroup();

            int value = 5;
            for (int row = 1; row <= 5; row++) {
                ToggleButton button = new ToggleButton(Integer.toString(value));
                button.setMinSize(25, 15);
                button.setToggleGroup(toggleGroup);
                button.getStyleClass().add("column" + col);
                button.setStyle("-fx-content-display: center;");
                keyboardButtons[col][row - 1] = button;
                gridPane.add(button, col, row);

                int finalCol = col;
                button.setOnMousePressed(e -> handleButtonClick(gridPane, finalCol, button));

                value--;

            }
            toggleGroups[col] = toggleGroup;
        }
        Button validateButton = createButton("Validate", this::handleValidateButtonClick);
        validateButton.setId("validate-button");
        validateButton.setAlignment(Pos.CENTER);
        validateButton.setPadding(new Insets(30));
        validateButton.setMaxWidth(Double.MAX_VALUE);

        keyBoard.getChildren().addAll(gridPane, validateButton);
        choix.getChildren().add(keyBoard);
    }

    /**
     * Handles the click event when a button in the keyboard is pressed.
     *
     * @param keyboard The GridPane representing the keyboard.
     * @param column   The column index of the clicked button.
     * @param button   The clicked ToggleButton.
     */
    private void handleButtonClick(GridPane keyboard, int column, ToggleButton button) {
        keyboard.getChildren().stream().filter(node -> node instanceof ToggleButton).map(node -> (ToggleButton) node).filter(btn -> keyboard.getColumnIndex(btn) == column).forEach(btn -> btn.getStyleClass().remove("active"));

        button.getStyleClass().add("active");
    }

    /**
     * Validates the code selected by the user in the keyboard.
     *
     * @return The validated code as an integer.
     */
    private int validateCode() {
        StringBuilder codeBuilder = new StringBuilder();

        for (int col = 0; col < 3; col++) {
            ToggleButton selectedButton = (ToggleButton) toggleGroups[col].getSelectedToggle();
            if (selectedButton != null) {
                int value = Integer.parseInt(selectedButton.getText());
                codeBuilder.append(value);
            }
        }
        String code = codeBuilder.toString();

        return Integer.parseInt(code);
    }

    /**
     * Changes the color of the specified validator's robot image based on the provided color and robot letter.
     *
     * @param nbValidator The validator number.
     * @param color       The color to set for the robot image.
     * @param robotLetter The letter corresponding to the robot image.
     */
    public void colorValidator(int nbValidator, String color, char robotLetter) {
        ImageView robotButton = robotButtonMap.get(nbValidator);
        if (robotButton != null) {
            String face = "/robotPicture/robot" + robotLetter + color + ".png";
            Image newRobotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(face)));
            robotButton.setImage(newRobotImage);
        }
    }

    /**
     * Disables or enables the buttons in the keyboard based on the specified condition.
     *
     * @param bloquer If true, the buttons are disabled; otherwise, they are enabled.
     */
    private void onValidateButtonClick(boolean bloquer) {
        for (ToggleButton[] buttons : keyboardButtons) {
            for (ToggleButton button : buttons) {
                button.setDisable(bloquer);
            }
        }
    }

    /**
     * Adds a "Back" button to the UI.
     */
    private void addBackButton() {
        btnBack = new Button("\u21E6 Back to homepage");
        btnBack.setMaxWidth(Double.MAX_VALUE);
        setMargin(btnBack, new Insets(10, 10, 10, 10));
        btnBack.setId("back-button");

        getChildren().add(btnBack);
    }

    /**
     * Clears the styles of all buttons in the keyboard.
     */
    private void clearButtonStyles() {
        for (ToggleButton[] buttons : keyboardButtons) {
            for (ToggleButton button : buttons) {
                button.getStyleClass().remove("active");
                button.setSelected(false);
            }
        }
    }

    private void resetButtonStyles() {
        Code code = superGame.getProposedCode();

        int first = code.getFirst();
        int second = code.getSecond();
        int third = code.getThird();

        clearButtonStyles();

        for (int row = 0; row < 5; row++) {
            keyboardButtons[0][row].getStyleClass().remove("active");
            if (row == 5 - first) {
                keyboardButtons[0][row].getStyleClass().add("active");
                keyboardButtons[0][row].setSelected(true);
            }
        }

        for (int row = 0; row < 5; row++) {
            keyboardButtons[1][row].getStyleClass().remove("active");
            if (row == 5 - second) {
                keyboardButtons[1][row].getStyleClass().add("active");
                keyboardButtons[1][row].setSelected(true);
            }
        }

        for (int row = 0; row < 5; row++) {
            keyboardButtons[2][row].getStyleClass().remove("active");
            if (row == 5 - third) {
                keyboardButtons[2][row].getStyleClass().add("active");
                keyboardButtons[2][row].setSelected(true);
            }
        }
    }

    /**
     * Updates the GamePlayPane to reflect changes in the game state.
     */
    @Override
    public void update() {
        for (Map.Entry<Integer, String> entry : superGame.getValidators().entrySet()) {
            Integer validator = entry.getKey();
            String color = entry.getValue();
            char robotLetter = robotLetterMap.get(validator);
            colorValidator(validator, color, robotLetter);
        }

        score.setText("score : " + superGame.getScore());
        rounds.setText("round : " + superGame.getRound());
    }

    /**
     * Gets the Alert associated with the GamePlayPane.
     *
     * @return The Alert for displaying messages.
     */
    public Alert getAlert() {
        return alert;
    }

    /**
     * Gets the Button associated with going back to the homepage.
     * @return The back button.
     */
    public ButtonType getReplayButtonType() {
        return replayButton;
    }

    /**
     * Gets the ButtonType associated with replaying the game.
     * @return The replay button type.
     */
    public Button getBtnBack() {
        return btnBack;
    }

    /**
     * Gets the ButtonType associated with quitting the game.
     * @return The quit button type.
     */
    public ButtonType getQuitButtonType() {
        return quitButton;
    }
}