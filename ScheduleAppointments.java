package com.example.hospitalms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleAppointments {
    @FXML
    private String loggedInUserEmail;

    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private Button scheduleButton;

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
    private void initialize() {
        // Populate the doctorComboBox and timeComboBox with data from the database
        populateDoctorComboBox();
        populateTimeComboBox();
    }

    private void populateDoctorComboBox() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT name FROM doctors";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<String> doctorNames = new ArrayList<>();
                    while (resultSet.next()) {
                        doctorNames.add(resultSet.getString("name"));
                    }
                    doctorComboBox.getItems().addAll(doctorNames);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }

    private void populateTimeComboBox() {
        // You can fetch available time slots from the database here
        // For demonstration, let's add some sample time slots
        timeComboBox.getItems().addAll("9:00 AM", "10:00 AM", "11:00 AM", "2:00 PM", "3:00 PM");
    }

    @FXML
    private void scheduleAppointments() {
        String selectedDoctor = doctorComboBox.getValue();
        String selectedTime = timeComboBox.getValue();


        if (selectedDoctor != null && selectedTime != null) {
            // Get the doctor_id from the database based on the selected doctor's name
            int doctorId = getDoctorId(selectedDoctor);

            // Assuming patientId is available based on the logged-in user or other logic
            int patientId = getPatientId(); // You need to implement this method

            if (doctorId != -1 && patientId != -1) {
                // Add logic to save appointment details to the database
                saveAppointmentToDatabase(selectedTime, doctorId, patientId);

                System.out.println("Appointment Scheduled with " + selectedDoctor + " at " + selectedTime);
            } else {
                System.out.println("Error getting doctorId or patientId.");
            }
        } else {
            System.out.println("Please select a doctor and a time.");
        }
    }

    private int getDoctorId(String doctorName) {
        // Implement logic to get the doctor_id from the database based on the doctor's name
        int doctorId = -1;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT id FROM doctors WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, doctorName);

                // Execute the query
                var resultSet = preparedStatement.executeQuery();

                // Check if there is a result
                if (resultSet.next()) {
                    doctorId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }

        return doctorId;
    }

    private int getPatientId() {
        // Implement logic to get the patient_id based on the logged-in user or other criteria
        // For demonstration, let's assume a patientId is available

        // Replace the following code with your actual authentication logic
        String loggedInUserEmail = getLoggedInUserEmail(); // Assume this method returns the email of the logged-in user

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "SELECT id FROM users WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, loggedInUserEmail);

                // Execute the query
                var resultSet = preparedStatement.executeQuery();

                // Check if there is a result
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }

        return -1; // Return -1 if the patient ID cannot be determined
    }

    private void saveAppointmentToDatabase(String selectedTime, int doctorId, int patientId) {
        // Implement logic to save the appointment details to the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "")) {
            String query = "INSERT INTO appointments (date, time, doctor_id, patient_id) VALUES (CURDATE(), ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, selectedTime);
                preparedStatement.setInt(2, doctorId);
                preparedStatement.setInt(3, patientId);

                // Execute the query
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }

    public void setLoggedInUserEmail(String email) {

            this.loggedInUserEmail = email;
    }

    public String getLoggedInUserEmail() {
        return this.loggedInUserEmail;
    }

    /*private Connection getConnection() throws SQLException {
        // Replace with your actual database connection details
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/integratedhospitalms", "root", "");
    }*/
}
