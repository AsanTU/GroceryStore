module sample.grocerystore {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires jdk.compiler;


    opens sample.grocerystore to javafx.fxml;
    exports sample.grocerystore;
}