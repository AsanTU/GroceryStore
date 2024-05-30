package sample.grocerystore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.grocerystore.Database;
import sample.grocerystore.models.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    private TableView<Sale> historyTableView;

    @FXML
    private TableColumn<Sale, Timestamp> dateColumn;

    @FXML
    private TableColumn<Sale, String> productNameColumn;

    @FXML
    private TableColumn<Sale, Integer> quantityColumn;

    @FXML
    private TableColumn<Sale, Double> pricePerPieceColumn;

    @FXML
    private TableColumn<Sale, Double> totalPriceColumn;

    @FXML
    private TableView<Sale> todaySalesTableView;

    @FXML
    private TableColumn<Sale, Timestamp> todayDateColumn;

    @FXML
    private TableColumn<Sale, String> todayProductNameColumn;

    @FXML
    private TableColumn<Sale, Integer> todayQuantityColumn;

    @FXML
    private TableColumn<Sale, Double> todayPricePerPieceColumn;

    @FXML
    private TableColumn<Sale, Double> todayTotalPriceColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        loadTodaySales();
    }

    private void initializeTable() {
        // Установка соответствия между столбцами и полями объекта Sale
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        pricePerPieceColumn.setCellValueFactory(cellData -> cellData.getValue().pricePerPieceProperty().asObject());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM history")) {
            while (rs.next()) {
                Sale sale = new Sale(
                        rs.getTimestamp("sale_date"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price_per_peace"),
                        rs.getDouble("total_price")
                );
                historyTableView.getItems().add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTodaySales() {
        todayDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        todayProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        todayQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        todayPricePerPieceColumn.setCellValueFactory(cellData -> cellData.getValue().pricePerPieceProperty().asObject());
        todayTotalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        ObservableList<Sale> todaySales = FXCollections.observableArrayList();

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM history WHERE DATE(sale_date) = DATE('now', 'localtime')")) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale(
                            rs.getTimestamp("sale_date"),
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price_per_peace"),
                            rs.getDouble("total_price")
                    );
                    todaySales.add(sale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        todaySalesTableView.setItems(todaySales);

        // Debug output
        System.out.println("Today's Sales Count: " + todaySales.size());
        for (Sale sale : todaySales) {
            System.out.println("Sale Date: " + sale.getDate() + ", Product: " + sale.getProductName());
        }
    }
}
