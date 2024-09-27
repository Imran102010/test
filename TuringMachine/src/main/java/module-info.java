module com.example.atlg3turingmachine {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.opencsv;

    opens viewFx to javafx.fxml;
    exports viewFx;


    /*
    opens com.example.atlg3turingmachine61411 to javafx.fxml;
    exports com.example.atlg3turingmachine61411;

     */
}