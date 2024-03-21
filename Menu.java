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

public class Menu {

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

    @FXML
    private Button FeedbackButton;

    @FXML
    private Button AppointmentButton;

    @FXML
    private Button ManageRecordsButton;

    @FXML
    private Button BillingButton;

    @FXML
    private Button InventoryButton;

    @FXML
    private Button ReportsButton;


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
    private void ScheduleAppointment() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScheduleAppointments.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) AppointmentButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private void ManagePatientRecords() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagePatientRecord.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) ManageRecordsButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    // Default constructor
    public Menu() {
        // Initialization code, if needed
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

    @FXML
    private void OpenFeedback() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) LoginButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private void OpenBilling() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BillingAndPayment.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) BillingButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private void OpenInventory() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryManagement.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) InventoryButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private void OpenReports() throws IOException {
        // Logic to open the registration file goes here
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GenerateReports.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the stage
        Stage stage = (Stage) ReportsButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}

