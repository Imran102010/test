package viewFx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class InitialPane extends VBox{

    private Button btnPlay;
    private Button btnProblem;

    /**
     * Constructs a new `InitialPane` and initializes the user interface.
     */
    public InitialPane() {
        initializeUI();
    }

    /**
     * Initializes the user interface components, including an image and buttons.
     */
    private void initializeUI() {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/turingmachine.png")));
        ImageView imageView = createImageView(image1);
        getChildren().add(imageView);

        btnPlay = createButton("Play", "play-button");
        btnProblem = createButton("Problem search", "problem-button");

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        getChildren().addAll(btnPlay, btnProblem);
        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color: white;");
    }

    /**
     * Creates a button with the specified text and ID.
     *
     * @param text The text of the button.
     * @param id   The ID of the button.
     * @return The created button.
     */
    private Button createButton(String text, String id) {
        Button button = new Button(text);
        button.setAlignment(Pos.CENTER);
        button.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));
        button.setId(id);
        setMargin(button, new Insets(10, 10, 10, 10));
        return button;
    }

    /**
     * Creates an ImageView with the specified image.
     *
     * @param image The image for the ImageView.
     * @return The created ImageView.
     */
    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Gets the "Play" button.
     *
     * @return The "Play" button.
     */
    public Button getBtnPlay() {
        return btnPlay;
    }

    /**
     * Gets the "Problem search" button.
     *
     * @return The "Problem search" button.
     */
    public Button getBtnProblem() {
        return btnProblem;
    }
}
