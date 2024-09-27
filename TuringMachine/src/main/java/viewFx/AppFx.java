package viewFx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.TurningMachineException;


import java.util.Objects;

public class AppFx extends Application {

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the primary stage and sets up the initial scene.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        HBox rootHBox = createRootHBox();
        Scene scene = new Scene(rootHBox, Color.WHITE);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        configurePrimaryStage(primaryStage, scene);
    }

    /**
     * Creates the root HBox for the application.
     *
     * @return The root HBox.
     */
    private HBox createRootHBox() {
        HBox rootHBox = new HBox();
        rootHBox.setStyle("-fx-background-color: white;");
        rootHBox.setAlignment(Pos.CENTER);

        InitialPane initialPane = createInitialPane(rootHBox);
        rootHBox.getChildren().add(initialPane);

        return rootHBox;
    }

    /**
     * Creates the initial pane with start and problem buttons.
     *
     * @param rootHBox The root HBox to which the pane will be added.
     * @return The InitialPane instance.
     */
    private InitialPane createInitialPane(HBox rootHBox) {
        InitialPane initialPane = new InitialPane();
        VBox startVBox = new VBox();
        startVBox.getChildren().add(initialPane);

        ProblemChooserPane problemPane = createProblemChooserPane(rootHBox, initialPane);
        initialPane.getBtnProblem().setOnAction(e -> changePage(rootHBox, problemPane));
        initialPane.getBtnPlay().setOnAction(e -> createAndChangeToGamePlayPane(rootHBox, initialPane));

        return initialPane;
    }

    /**
     * Creates the problem chooser pane.
     *
     * @param rootHBox    The root HBox to which the pane will be added.
     * @param initialPane The InitialPane to return to when needed.
     * @return The ProblemChooserPane instance.
     */
    private ProblemChooserPane createProblemChooserPane(HBox rootHBox, InitialPane initialPane) {
        ProblemChooserPane problemPane = new ProblemChooserPane();
        problemPane.getBtnBack().setOnAction(e -> changePage(rootHBox, initialPane));
        problemPane.getBtnLoad().setOnAction(e -> loadProblemAndCreateGamePlayPane(rootHBox, initialPane, problemPane));
        return problemPane;
    }

    /**
     * Creates and switches to the GamePlayPane.
     *
     * @param rootHBox    The root HBox to which the pane will be added.
     * @param initialPane The InitialPane to return to when needed.
     */
    private void createAndChangeToGamePlayPane(HBox rootHBox, InitialPane initialPane) {
        GamePlayPane gamePlayPane = new GamePlayPane();
        changePage(rootHBox, gamePlayPane);

        gamePlayPane.getBtnBack().setOnAction(event -> changePage(rootHBox, initialPane));
        setupAlertActions(gamePlayPane, rootHBox, initialPane);
    }

    /**
     * Loads a problem and creates the GamePlayPane.
     *
     * @param rootHBox    The root HBox to which the pane will be added.
     * @param initialPane The InitialPane to return to when needed.
     * @param problemPane The ProblemChooserPane to get the selected problem number.
     */
    private void loadProblemAndCreateGamePlayPane(HBox rootHBox, InitialPane initialPane, ProblemChooserPane problemPane) {
        try {
            int problemNumber = problemPane.getProblemNumber();
            GamePlayPane gamePlayPane = new GamePlayPane(problemNumber);
            changePage(rootHBox, gamePlayPane);

            gamePlayPane.getBtnBack().setOnAction(event -> changePage(rootHBox, initialPane));
            setupAlertActions(gamePlayPane, rootHBox, initialPane);
        } catch (TurningMachineException ignored) {
        }
    }

    /**
     * Sets up actions for the Alert in the GamePlayPane.
     *
     * @param gamePlayPane The GamePlayPane containing the Alert.
     * @param racineHBox   The root HBox.
     * @param startPane    The InitialPane.
     */
    private void setupAlertActions(GamePlayPane gamePlayPane, HBox racineHBox, InitialPane startPane) {
        gamePlayPane.getAlert().setOnCloseRequest(event -> {
            if (gamePlayPane.getAlert().getResult() == gamePlayPane.getReplayButtonType()) {
                changePage(racineHBox, startPane);
            } else if (gamePlayPane.getAlert().getResult() == gamePlayPane.getQuitButtonType()) {
                Platform.exit();
            }
        });
    }

    /**
     * Configures the primary stage with title, icon, and scene.
     *
     * @param primaryStage The primary stage.
     * @param scene         The scene to set.
     */
    private void configurePrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Turing Machine");
        primaryStage.getIcons().add(new Image("/logoTM.png"));
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Changes the content of the root HBox.
     *
     * @param root       The root HBox.
     * @param newContent The new content to set.
     */
    private void changePage(HBox root, VBox newContent) {
        root.setFillHeight(true);
        root.getChildren().setAll(newContent);
    }
}
