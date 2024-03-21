package com.example.hospitalms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryManagement {

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button trackInventoryButton;

    @FXML
    private Button restockButton;

    @FXML
    private Button addItemButton;

    @FXML
    private Button deleteItemButton;

    @FXML
    private Button goBackButton;

    @FXML
    private void goBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private void trackInventoryClicked() {
        String itemName = itemNameField.getText();

        if (!itemName.isEmpty()) {
            // Implement logic to track inventory
            trackInventory(itemName);
        } else {
            showAlert("Please enter the item name.");
        }
    }

    @FXML
    private void restockClicked() {
        String itemName = itemNameField.getText();
        String quantityText = quantityField.getText();

        if (!itemName.isEmpty() && !quantityText.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityText);
                // Implement logic to restock inventory
                restockInventory(itemName, quantity);
            } catch (NumberFormatException e) {
                showAlert("Invalid quantity. Please enter a valid number.");
            }
        } else {
            showAlert("Please enter the item name and quantity to restock.");
        }
    }

    @FXML
    private void addItemClicked() {
        String itemName = itemNameField.getText();
        String quantityText = quantityField.getText();

        if (!itemName.isEmpty() && !quantityText.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityText);
                // Implement logic to add item to inventory
                addItemToInventory(itemName, quantity);
            } catch (NumberFormatException e) {
                showAlert("Invalid quantity. Please enter a valid number.");
            }
        } else {
            showAlert("Please enter the item name and quantity to add.");
        }
    }

    @FXML
    private void deleteItemClicked() {
        String itemName = itemNameField.getText();

        if (!itemName.isEmpty()) {
            // Implement logic to delete item from inventory
            deleteItemFromInventory(itemName);
        } else {
            showAlert("Please enter the item name to delete.");
        }
    }

    private void trackInventory(String itemName) {

         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
             String query = "SELECT quantity FROM inventory_items WHERE name = ?";
             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                 preparedStatement.setString(1, itemName);
                 try (var resultSet = preparedStatement.executeQuery()) {
                     if (resultSet.next()) {
                         int currentQuantity = resultSet.getInt("quantity");
                         System.out.println("Current quantity of " + itemName + ": " + currentQuantity);
                     } else {
                         System.out.println("Item not found in inventory.");
                     }
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

        showAlert("Inventory tracked successfully!");
    }

    private void restockInventory(String itemName, int quantity) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
             String query = "INSERT INTO inventory_items (name, quantity) VALUES (?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";
             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                 preparedStatement.setString(1, itemName);
                 preparedStatement.setInt(2, quantity);
                 preparedStatement.setInt(3, quantity);
                 preparedStatement.executeUpdate();
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

        showAlert("Inventory restocked successfully!");
    }

    private void addItemToInventory(String itemName, int quantity) {
        // Implement logic to add item to inventory
        // This could involve inserting a new item into the database, for example:
         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
             String query = "INSERT INTO inventory_items (name, quantity) VALUES (?, ?)";
             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                 preparedStatement.setString(1, itemName);
                 preparedStatement.setInt(2, quantity);
                 preparedStatement.executeUpdate();
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

        showAlert("Item added to inventory successfully!");
    }

    private void deleteItemFromInventory(String itemName) {
        // Implement logic to delete item from inventory
        // This could involve deleting an item from the database, for example:
         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
             String query = "DELETE FROM inventory_items WHERE name = ?";
             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                 preparedStatement.setString(1, itemName);
                 preparedStatement.executeUpdate();
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

        showAlert("Item deleted from inventory successfully!");
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
