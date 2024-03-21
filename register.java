package com.example.hospitalms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class register {

    @FXML
    private TextField usernameField1;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private TextField userEmailField;
    @FXML
    private Button LoginButton;
    @FXML
    private Button SignInButton;
    @FXML
    private Button RegisterButton;

    // Default constructor
    public register() {
        // Initialization code, if needed
    }

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
    private void OpenLoginPage() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) LoginButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    public void registerButtonClicked() {
        String username = usernameField1.getText();
        String password = passwordField.getText();
        String confirmPassword = passwordField1.getText();
        String email = userEmailField.getText();

        //    Validate registration input
        if (validateRegistration(username, password, confirmPassword, email)) {
            // Attempt to register the user in the database
            if (registerUserInDatabase(username, password, email)) {
                System.out.println("Registration successful");
            } else {
                System.out.println("Registration failed");
            }
        } else {
            System.out.println("Invalid registration input");
        }
    }

    // Replace this method with your actual registration validation logic
    private boolean validateRegistration(String username, String password, String confirmPassword, String email) {
        return !username.isEmpty() && password.equals(confirmPassword) && isValidEmail(email);
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private boolean registerUserInDatabase(String username, String password, String email) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //JDBC connection parameters
            String driver = "com.mysql.cj.jdbc.Driver";
            String jdbcUrl = "jdbc:mysql://localhost:3306/integratedhospitalms";
            String DB_USER = "root";
            String DB_PASSWORD = "";
            // Establish a connection to the database
            try (Connection connection = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) {
                // Create a prepared statement to insert the user into the 'users' table
                String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, email);

                    // Execute the statement
                    preparedStatement.executeUpdate();
                }
            }
            return true; // Registration successful
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false; // Registration failed
        }
    }
}
