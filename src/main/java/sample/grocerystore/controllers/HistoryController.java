package sample.grocerystore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import sample.grocerystore.models.Product;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    private BorderPane historyPane;

    @FXML
    private Text IDtext;

    @FXML
    private ListView<String> listForHistory;

    @FXML
    private Text nameText;

    @FXML
    private Text priceText;

    @FXML
    private Text quantityText;

    @FXML
    private Text timeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
