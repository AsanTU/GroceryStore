package sample.grocerystore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.grocerystore.controllers.CheckPanelController;
import sample.grocerystore.controllers.ProductSelectionController;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    private static Scene scene;

    private static CheckPanelController checkPanelController;
    private static ProductSelectionController productSelectionController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        ProductTable.createTable();

        FXMLLoader productSelectionLoader = new FXMLLoader(getClass().getResource("fxml/product-selection-panel.fxml"));
        Parent productSelectionRoot = productSelectionLoader.load();
        productSelectionController = productSelectionLoader.getController();

        FXMLLoader checkPanelLoader = new FXMLLoader(getClass().getResource("fxml/check-panel.fxml"));
        checkPanelLoader.load();
        checkPanelController = checkPanelLoader.getController();

        productSelectionController.setCheckPanelController(checkPanelController);

        scene = new Scene(productSelectionRoot);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        primaryStage.setTitle("Grocery Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        if (fxml.equals("product-selection-panel")) {
            productSelectionController = fxmlLoader.getController();
            productSelectionController.setCheckPanelController(checkPanelController);
        } else if (fxml.equals("check-panel")) {
            checkPanelController = fxmlLoader.getController();
        }
        scene.setRoot(root);
    }

    public static void main(String[] args) {
        launch();
    }
}