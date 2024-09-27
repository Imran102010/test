package viewFx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.TurningMachineException;

import java.util.Objects;

public class ProblemChooserPane extends VBox {

    private Button btnLoad;
    private Button btnBack;
    private ComboBox<Integer> problemComboBox;

    /**
     * Constructs a new `ProblemChooserPane` and initializes the user interface.
     */
    public ProblemChooserPane() {
        initializeUI();
    }

    /**
     * Initializes the user interface components, including labels, ComboBox, and buttons.
     */
    private void initializeUI() {
        Label prblmSearch = createLabel("Problem Search", "labelProblem");
        Label choose = createLabel("Choose Game ID", "labelChoose");

        problemComboBox = createComboBox();
        btnLoad = createButton("LOAD", "load-button");
        btnBack = createButton("Back to homepage", "back-button");

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        setStyle("-fx-background-color: white;");

        getChildren().addAll(prblmSearch, choose, problemComboBox, btnLoad, btnBack);
        setAlignment(Pos.CENTER);
    }

    /**
     * Creates a label with the specified text and ID.
     *
     * @param text The text of the label.
     * @param id   The ID of the label.
     * @return The created label.
     */
    private Label createLabel(String text, String id) {
        Label label = new Label(text);
        label.setId(id);
        return label;
    }

    /**
     * Creates a ComboBox with problem numbers.
     *
     * @return The created ComboBox.
     */
    private ComboBox<Integer> createComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>(
                FXCollections.observableArrayList(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
                )
        );
        comboBox.setMaxWidth(Double.MAX_VALUE);
        setMargin(comboBox, new Insets(10, 10, 10, 10));
        comboBox.setId("choiceComboBox");
        return comboBox;
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
        button.setMaxWidth(Double.MAX_VALUE);
        setMargin(button, new Insets(10, 10, 10, 10));
        button.setId(id);
        return button;
    }

    /**
     * Gets the "Back" button.
     *
     * @return The "Back" button.
     */
    public Button getBtnBack() {
        return btnBack;
    }

    /**
     * Gets the "LOAD" button.
     *
     * @return The "LOAD" button.
     */
    public Button getBtnLoad() {
        return btnLoad;
    }

    /**
     * Gets the selected problem number from the ComboBox.
     *
     * @return The selected problem number.
     * @throws TurningMachineException if no problem number is selected.
     */
    public int getProblemNumber() {
        if (problemComboBox.getValue() == null) {
            throw new TurningMachineException("You have to choose a problem number");
        }
        return problemComboBox.getValue();
    }
}
