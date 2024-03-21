package com.example.hospitalms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Feedback {
    @FXML
    private TextArea feedbackTextArea;

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

    // Method to handle the submit button action
    @FXML
    void handleSubmitButton(ActionEvent event) {
        String feedback = feedbackTextArea.getText();

        if (!feedback.isEmpty()) {
            // Store feedback in the database
            storeFeedbackInDatabase(feedback);
            System.out.println("Feedback submitted: " + feedback);
            // Optionally, display a confirmation message or perform other actions
        } else {
            System.out.println("Feedback is empty. Please enter feedback before submitting.");
            // Optionally, display an error message or perform other actions
        }
    }

    private void storeFeedbackInDatabase(String feedback) {
        // Replace these details with your actual database connection information
        String url = "jdbc:mysql://localhost:3306/integratedhospitalms";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO feedbacks (message) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, feedback);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }
}
