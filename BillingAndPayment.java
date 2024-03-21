package com.example.hospitalms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillingAndPayment {

    @FXML
    private TextField patientIdField;

    @FXML
    private TextArea invoiceDetailsArea;

    @FXML
    private Button generateInvoiceButton;

    @FXML
    private Button recordPaymentButton;

    @FXML
    private Button goBackButton;


    @FXML
    private void initialize() {
        // Initialize the controller
        // You can add additional initialization logic here
    }

    @FXML
    private void goBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private void generateInvoice() {
        String patientId = patientIdField.getText();

        if (!patientId.isEmpty()) {
            String invoiceDetails = generateInvoiceDetails(patientId);
            invoiceDetailsArea.setText(invoiceDetails);
        } else {
            showAlert("Please enter a valid Patient ID.");
        }
    }

    @FXML
    private void recordPayment() {
        String patientId = patientIdField.getText();

        if (!patientId.isEmpty()) {
            recordPayment(patientId);
            showAlert("Payment recorded successfully!");
        } else {
            showAlert("Please enter a valid Patient ID.");
        }
    }

    private String generateInvoiceDetails(String patientId) {
        // Implement logic to generate invoice details from the database
        StringBuilder invoiceDetails = new StringBuilder();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT invoice_details FROM billings WHERE patient_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(patientId));

                // Execute the query
                var resultSet = preparedStatement.executeQuery();

                // Check if there is a result
                if (resultSet.next()) {
                    invoiceDetails.append(resultSet.getString("invoice_details"));
                } else {
                    showAlert("No invoice details found for the given Patient ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }

        return invoiceDetails.toString();
    }

    private void recordPayment(String patientId) {
        // Implement logic to record payment in the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "UPDATE billings SET payment_status = 'Paid' WHERE patient_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(patientId));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
