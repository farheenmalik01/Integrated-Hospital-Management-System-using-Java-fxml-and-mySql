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

public class ManagePatientRecord {

    @FXML
    private TextField patientNameField;

    @FXML
    private TextField patientAgeField;

    @FXML
    private TextField diagnosisField;

    @FXML
    private TextField treatmentField;

    @FXML
    private Button addPatientButton;

    @FXML
    private Button updatePatientButton;

    @FXML
    private Button deletePatientButton;

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
    private void addPatientClicked() {
        String patientName = patientNameField.getText();
        String patientAge = patientAgeField.getText();
        String diagnosis = diagnosisField.getText();
        String treatment = treatmentField.getText();

        if (validateInput(patientName, patientAge, diagnosis, treatment)) {
            int patientId = insertPatient(patientName, patientAge);
            insertPatientRecord(patientId, diagnosis, treatment);
            showAlert("Patient added successfully!");
        } else {
            showAlert("Invalid input. Please check your entries.");
        }
    }

    @FXML
    private void updatePatientClicked() {
        String patientName = patientNameField.getText();
        String patientAge = patientAgeField.getText();
        String diagnosis = diagnosisField.getText();
        String treatment = treatmentField.getText();

        if (validateInput(patientName, patientAge, diagnosis, treatment)) {
            int patientId = getPatientId(patientName, patientAge);
            updatePatientRecord(patientId, diagnosis, treatment);
            showAlert("Patient updated successfully!");
        } else {
            showAlert("Invalid input. Please check your entries.");
        }
    }

    private int insertPatient(String name, String age) {
        // Implement logic to insert patient into the database and return the patient ID
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "INSERT INTO patients (name, age) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, age);
                preparedStatement.executeUpdate();

                // Retrieve the generated patient ID
                try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get the patient ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
            return -1; // Return a special value to indicate failure
        }
    }

    private void insertPatientRecord(int patientId, String diagnosis, String treatment) {
        // Implement logic to insert patient record into the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "INSERT INTO patient_records (patient_id, diagnosis, treatment) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, patientId);
                preparedStatement.setString(2, diagnosis);
                preparedStatement.setString(3, treatment);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }

    private void updatePatientRecord(int patientId, String diagnosis, String treatment) {
        // Implement logic to update patient record in the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "UPDATE patient_records SET diagnosis = ?, treatment = ? WHERE patient_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, diagnosis);
                preparedStatement.setString(2, treatment);
                preparedStatement.setInt(3, patientId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }

    @FXML
    private void deletePatientClicked() {
        String patientName = patientNameField.getText();
        if (!patientName.isEmpty()) {
                deletePatient(patientName);
                showAlert("Patient deleted successfully!");
            }
         else {
            showAlert("Please enter the patient's name to delete.");
        }
    }

    private void deletePatient(String name) {
        // Implement logic to delete patient from the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "DELETE FROM patients WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }


    private int getPatientId(String name, String age) {
        // Implement logic to get the patient ID based on name and age
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT id FROM patients WHERE name = ? AND age = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, age);

                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        throw new SQLException("Patient not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
            return -1; // Return a special value to indicate failure
        }
    }

    private boolean validateInput(String name, String age, String diagnosis, String treatment) {
        return !name.isEmpty() && !age.isEmpty() && !diagnosis.isEmpty() && !treatment.isEmpty();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
