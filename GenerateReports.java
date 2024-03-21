package com.example.hospitalms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateReports {

    @FXML
    private TextField patientIdField;

    @FXML
    private Label patientNameLabel;

    @FXML
    private Label patientAgeLabel;

    @FXML
    private Label diagnosisLabel;

    @FXML
    private Label treatmentLabel;

    @FXML
    private Button generateReportButton;

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
    private void generateReportClicked() {
        // Get the text entered in the patient ID field
        String patientIdText = patientIdField.getText();

        if (!patientIdText.isEmpty()) {
            try {
                // Parse the patient ID as an integer
                int patientId = Integer.parseInt(patientIdText);

                // Display patient information for the given patient ID
                displayPatientInfo(patientId);
            } catch (NumberFormatException e) {
                // Handle the case where the entered text is not a valid integer
                showAlert("Invalid patient ID. Please enter a valid number.");
            }
        } else {
            // Handle the case where the patient ID field is empty
            showAlert("Please enter the patient ID.");
        }
    }

    private void displayPatientInfo(int patientId) {
        // Implement logic to retrieve patient information from the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT * FROM patient_records WHERE patient_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, patientId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve patient information
                        String patientName = getPatientName(patientId);
                        String patientAge = getPatientAge(patientId);
                        String diagnosis = resultSet.getString("diagnosis");
                        String treatment = resultSet.getString("treatment");

                        // Display patient information in the corresponding labels
                        patientNameLabel.setText("Patient Name: " + patientName);
                        patientAgeLabel.setText("Patient Age: " + patientAge);
                        diagnosisLabel.setText("Diagnosis: " + diagnosis);
                        treatmentLabel.setText("Treatment: " + treatment);
                    } else {
                        // Handle the case where no patient is found with the given ID
                        showAlert("Patient with ID " + patientId + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately in a real application
            e.printStackTrace();
        }
    }

    // ... (Existing code)

    private String getPatientName(int patientId) {
        // Implement logic to retrieve patient name from the database based on patient ID
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT name FROM patients WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, patientId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("name");
                    } else {
                        throw new SQLException("Patient not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
            return "Unknown"; // Return a default value or handle the exception
        }
    }

    private String getPatientAge(int patientId) {
        // Implement logic to retrieve patient age from the database based on patient ID
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT age FROM patients WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, patientId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("age");
                    } else {
                        throw new SQLException("Patient not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
            return "Unknown"; // Return a default value or handle the exception
        }
    }


    private void showAlert(String message) {
        // Display an information alert with the given message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
