module sample.grocerystore {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires jdk.compiler;
    requires java.sql;


    opens sample.grocerystore to javafx.fxml;
    exports sample.grocerystore;
    exports sample.grocerystore.controllers;
    opens sample.grocerystore.controllers to javafx.fxml;
    exports sample.grocerystore.models;
    opens sample.grocerystore.models to javafx.fxml;
}