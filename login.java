package com.example.hospitalms;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    @FXML
    private TextField userEmailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button LoginButton;

    @FXML
    private Button AppointmentButton;

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
    private void setRegisterButton() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) RegisterButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private String handleLogin() throws IOException {
        String email = userEmailField.getText();
        String password = passwordField.getText();

        if (authenticate(email, password)) {
            showAlert("Login Successful!");


        } else {
            showAlert("Invalid Email or Password!");
        }
        return email;

    }

    @FXML
    private void ScheduleAppointment() throws IOException {
        String loggedInUserEmail = handleLogin(); // Assuming you have a method to get the logged-in user's email

        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScheduleAppointments.fxml"));
        Parent root = fxmlLoader.load();

        // Access the controller and set the email
        ScheduleAppointments scheduleController = fxmlLoader.getController();
        scheduleController.setLoggedInUserEmail(loggedInUserEmail);

        // Set up the stage
        Stage stage = (Stage) AppointmentButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    private boolean authenticate(String Email, String password1) {


        // JDBC connection parameters
        String driver= "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/integratedhospitalms";
        String Username = "root";
        String password = "";


        try (Connection connection = DriverManager.getConnection(jdbcUrl, Username, password)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, Email);
                preparedStatement.setString(2, password1);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If there is a matching user, authentication is successful
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
            return false;
        }
    }
    private void showAlert(String message) {
        System.out.println(message);
    }


}