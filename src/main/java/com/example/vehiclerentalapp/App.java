package com.example.vehiclerentalapp;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class App extends Application {

    // Data storage using collections
    private ObservableList<Map<String, String>> customers = FXCollections.observableArrayList();
    private ObservableList<Map<String, String>> vehicles = FXCollections.observableArrayList();
    private ObservableList<Map<String, String>> bookings = FXCollections.observableArrayList();
    private ObservableList<Map<String, String>> users = FXCollections.observableArrayList();
    private ObservableList<Map<String, String>> payments = FXCollections.observableArrayList();

    private Stage primaryStage;
    private TabPane mainTabPane;

    // A map to store user credentials for login
    private final Map<String, String> userCredentials = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeUserCredentials();
        showLoginDialog();
    }

    private void initializeUserCredentials() {
        // Add user credentials for logging in
        userCredentials.put("admin", "admin123");
        userCredentials.put("staff", "staff123");
        userCredentials.put("customer1", "customer123");
        userCredentials.put("customer2", "customer456");
    }

    private void showLoginDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please enter your credentials");

        // Create fields for username and password
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate credentials
            if (userCredentials.containsKey(username) &&
                    userCredentials.get(username).equals(password)) {
                dialog.close();
                String role = username.startsWith("customer") ? "CUSTOMER" : username.toUpperCase();
                initializeSampleData();
                setupMainScene(role); // Pass role to setupMainScene
            } else {
                showAlert("Invalid Credentials", "Please check your username and password.");
            }
        });

        VBox dialogPane = new VBox(10, usernameField, passwordField, loginButton);
        dialogPane.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(dialogPane);
        dialog.showAndWait();
    }

    private void setupMainScene(String role) {
        mainTabPane = new TabPane();

        // Set up common tabs
        Tab vehicleTab = new Tab("Vehicles");
        vehicleTab.setContent(createVehicleManagementView());

        // Add user-specific tabs
        if ("ADMIN".equals(role)) {
            // Admin specific tabs
            mainTabPane.getTabs().addAll(vehicleTab, createCustomerManagementTab(), createBookingTab(), createPaymentTab(), createReportsTab(), createUserManagementTab());
        } else if ("STAFF".equals(role)) {
            // Staff specific tabs
            mainTabPane.getTabs().addAll(vehicleTab, createBookingTab(), createPaymentTab());
        } else {
            // Customer specific view
            mainTabPane.getTabs().addAll(vehicleTab, createBookingTab());
        }

        BorderPane root = new BorderPane();
        root.setCenter(mainTabPane);

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("Vehicle Rental System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab createCustomerManagementTab() {
        Tab customerTab = new Tab("Customers");
        customerTab.setContent(createCustomerManagementView());
        return customerTab;
    }

    private Tab createBookingTab() {
        Tab bookingTab = new Tab("Bookings");
        bookingTab.setContent(createBookingView());
        return bookingTab;
    }

    private Tab createPaymentTab() {
        Tab paymentTab = new Tab("Payments");
        paymentTab.setContent(createPaymentView());
        return paymentTab;
    }

    private Tab createReportsTab() {
        Tab reportsTab = new Tab("Reports");
        reportsTab.setContent(createReportsView());
        return reportsTab;
    }

    private Tab createUserManagementTab() {
        Tab usersTab = new Tab("Users");
        usersTab.setContent(createUserManagementView());
        return usersTab;
    }

    private void initializeSampleData() {
        // Sample users
        Map<String, String> user1 = new HashMap<>();
        user1.put("id", "1");
        user1.put("username", "admin");
        user1.put("password", "admin123");
        user1.put("role", "ADMIN");
        users.add(user1);

        Map<String, String> user2 = new HashMap<>();
        user2.put("id", "2");
        user2.put("username", "staff");
        user2.put("password", "staff123");
        user2.put("role", "STAFF");
        users.add(user2);

        // Sample customers
        Map<String, String> customer1 = new HashMap<>();
        customer1.put("id", "1");
        customer1.put("name", "John Doe");
        customer1.put("contact", "123456789");
        customer1.put("license", "ABC123");
        customers.add(customer1);

        Map<String, String> customer2 = new HashMap<>();
        customer2.put("id", "2");
        customer2.put("name", "Jane Smith");
        customer2.put("contact", "987654321");
        customer2.put("license", "XYZ789");
        customers.add(customer2);

        // Sample vehicles
        Map<String, String> vehicle1 = new HashMap<>();
        vehicle1.put("id", "1");
        vehicle1.put("brand", "Toyota");
        vehicle1.put("model", "Camry");
        vehicle1.put("category", "Sedan");
        vehicle1.put("price", "50.0");
        vehicles.add(vehicle1);

        Map<String, String> vehicle2 = new HashMap<>();
        vehicle2.put("id", "2");
        vehicle2.put("brand", "Honda");
        vehicle2.put("model", "Civic");
        vehicle2.put("category", "Sedan");
        vehicle2.put("price", "45.0");
        vehicles.add(vehicle2);

        // Sample booking
        Map<String, String> booking = new HashMap<>();
        booking.put("id", "1");
        booking.put("customerId", "1");
        booking.put("vehicleId", "1");
        booking.put("startDate", "2023-01-01");
        booking.put("endDate", "2023-01-05");
        booking.put("price", "200.0");
        bookings.add(booking);
    }

    private Node createVehicleManagementView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        Label titleLabel = new Label("Vehicle Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Vehicle Table
        TableView<Map<String, String>> vehicleTable = new TableView<>();
        vehicleTable.setItems(vehicles);

        TableColumn<Map<String, String>, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("id")));

        TableColumn<Map<String, String>, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("brand")));

        TableColumn<Map<String, String>, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("model")));

        TableColumn<Map<String, String>, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("category")));

        TableColumn<Map<String, String>, String> priceCol = new TableColumn<>("Daily Price");
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("price")));

        vehicleTable.getColumns().addAll(idCol, brandCol, modelCol, categoryCol, priceCol);

        // Management Controls
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField brandField = new TextField();
        brandField.setPromptText("Brand");
        TextField modelField = new TextField();
        modelField.setPromptText("Model");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");
        TextField priceField = new TextField();
        priceField.setPromptText("Daily Price");

        Button addButton = new Button("Add Vehicle");
        addButton.setOnAction(e -> {
            Map<String, String> vehicle = new HashMap<>();
            vehicle.put("id", String.valueOf(vehicles.size() + 1));
            vehicle.put("brand", brandField.getText());
            vehicle.put("model", modelField.getText());
            vehicle.put("category", categoryField.getText());
            vehicle.put("price", priceField.getText());
            vehicles.add(vehicle);
            clearVehicleFields(brandField, modelField, categoryField, priceField);
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            Map<String, String> selected = vehicleTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                vehicles.remove(selected);
            }
        });

        grid.addRow(0, new Label("Brand:"), brandField, new Label("Model:"), modelField);
        grid.addRow(1, new Label("Category:"), categoryField, new Label("Price:"), priceField);
        grid.addRow(2, addButton, deleteButton);

        vbox.getChildren().addAll(titleLabel, grid, vehicleTable);
        return vbox;
    }

    private Node createCustomerManagementView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        Label titleLabel = new Label("Customer Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Customer Table
        TableView<Map<String, String>> customerTable = new TableView<>();
        customerTable.setItems(customers);

        TableColumn<Map<String, String>, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("id")));

        TableColumn<Map<String, String>, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("name")));

        TableColumn<Map<String, String>, String> contactCol = new TableColumn<>("Contact");
        contactCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("contact")));

        TableColumn<Map<String, String>, String> licenseCol = new TableColumn<>("License");
        licenseCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("license")));

        customerTable.getColumns().addAll(idCol, nameCol, contactCol, licenseCol);

        // Register New Customer
        TextField registerNameField = new TextField();
        registerNameField.setPromptText("Name");
        TextField registerContactField = new TextField();
        registerContactField.setPromptText("Contact");
        TextField registerLicenseField = new TextField();
        registerLicenseField.setPromptText("License #");
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            Map<String, String> newCustomer = new HashMap<>();
            newCustomer.put("id", String.valueOf(customers.size() + 1));
            newCustomer.put("name", registerNameField.getText());
            newCustomer.put("contact", registerContactField.getText());
            newCustomer.put("license", registerLicenseField.getText());
            customers.add(newCustomer);

            registerNameField.clear();
            registerContactField.clear();
            registerLicenseField.clear();
        });

        // Assemble the customer management layout
        VBox customerLayout = new VBox(10, titleLabel, customerTable, registerNameField, registerContactField, registerLicenseField, registerButton);
        vbox.getChildren().addAll(customerLayout);
        return vbox;
    }

    private Node createBookingView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        Label titleLabel = new Label("Booking Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Booking Table
        TableView<Map<String, String>> bookingTable = new TableView<>();
        bookingTable.setItems(bookings);

        TableColumn<Map<String, String>, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("id")));

        TableColumn<Map<String, String>, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(data -> new SimpleStringProperty(
                customers.stream()
                        .filter(c -> c.get("id").equals(data.getValue().get("customerId")))
                        .findFirst()
                        .map(c -> c.get("name"))
                        .orElse("Unknown")
        ));

        TableColumn<Map<String, String>, String> vehicleCol = new TableColumn<>("Vehicle");
        vehicleCol.setCellValueFactory(data -> new SimpleStringProperty(
                vehicles.stream()
                        .filter(v -> v.get("id").equals(data.getValue().get("vehicleId")))
                        .findFirst()
                        .map(v -> v.get("brand") + " " + v.get("model"))
                        .orElse("Unknown")
        ));

        TableColumn<Map<String, String>, String> datesCol = new TableColumn<>("Dates");
        datesCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().get("startDate") + " to " + data.getValue().get("endDate")
        ));

        TableColumn<Map<String, String>, String> priceCol = new TableColumn<>("Total Price");
        priceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("price")));

        bookingTable.getColumns().addAll(idCol, customerCol, vehicleCol, datesCol, priceCol);

        // Booking Management Controls
        ComboBox<String> customerCombo = new ComboBox<>(
                FXCollections.observableArrayList(
                        customers.stream()
                                .map(c -> c.get("id") + ": " + c.get("name"))
                                .collect(Collectors.toList())
                )
        );

        ComboBox<String> vehicleCombo = new ComboBox<>(
                FXCollections.observableArrayList(
                        vehicles.stream()
                                .map(v -> v.get("id") + ": " + v.get("brand") + " " + v.get("model"))
                                .collect(Collectors.toList())
                )
        );

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        Button addBookingButton = new Button("Create Booking");
        addBookingButton.setOnAction(e -> {
            Map<String, String> booking = new HashMap<>();
            booking.put("id", String.valueOf(bookings.size() + 1));
            booking.put("customerId", customerCombo.getValue().split(":")[0]);
            booking.put("vehicleId", vehicleCombo.getValue().split(":")[0]);
            booking.put("startDate", startDatePicker.getValue().toString());
            booking.put("endDate", endDatePicker.getValue().toString());
            // Calculate price
            int days = (int) ChronoUnit.DAYS.between(startDatePicker.getValue(), endDatePicker.getValue());
            double price = days * Double.parseDouble(vehicles.stream()
                    .filter(v -> v.get("id").equals(booking.get("vehicleId")))
                    .findFirst().get().get("price"));
            booking.put("price", String.valueOf(price));
            bookings.add(booking);
            // Clear fields (optional)
        });

        VBox bookingLayout = new VBox(10, titleLabel, bookingTable, customerCombo, vehicleCombo, startDatePicker, endDatePicker, addBookingButton);
        vbox.getChildren().addAll(bookingLayout);
        return vbox;
    }

    private Node createPaymentView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        Label titleLabel = new Label("Payment Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Payment Table
        TableView<Map<String, String>> paymentTable = new TableView<>();
        paymentTable.setItems(payments);

        TableColumn<Map<String, String>, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("id")));

        TableColumn<Map<String, String>, String> bookingCol = new TableColumn<>("Booking ID");
        bookingCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("bookingId")));

        TableColumn<Map<String, String>, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("amount")));

        TableColumn<Map<String, String>, String> dateCol = new TableColumn<>("Payment Date");
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("paymentDate")));

        paymentTable.getColumns().addAll(idCol, bookingCol, amountCol, dateCol);

        // Controls for adding payments
        ComboBox<String> bookingCombo = new ComboBox<>(
                FXCollections.observableArrayList(
                        bookings.stream()
                                .map(b -> b.get("id") + ": " + b.get("customerId")) // Simplified representation
                                .collect(Collectors.toList())
                )
        );

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        Button addPaymentButton = new Button("Add Payment");
        addPaymentButton.setOnAction(e -> {
            Map<String, String> newPayment = new HashMap<>();
            newPayment.put("id", String.valueOf(payments.size() + 1));
            newPayment.put("bookingId", bookingCombo.getValue().split(":")[0]); // Extract booking ID
            newPayment.put("amount", amountField.getText());
            newPayment.put("paymentDate", java.time.LocalDate.now().toString()); // Payment date set to today
            payments.add(newPayment);

            // Clear fields after adding
            amountField.clear();
        });

        vbox.getChildren().addAll(titleLabel, paymentTable, bookingCombo, amountField, addPaymentButton);
        return vbox;
    }

    private Node createReportsView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        Label titleLabel = new Label("Reports Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // PieChart for vehicle categories
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Vehicle Categories Distribution");
        // Example to add data
        pieChart.getData().add(new PieChart.Data("Sedans", 20));
        pieChart.getData().add(new PieChart.Data("SUVs", 15));
        pieChart.getData().add(new PieChart.Data("Trucks", 10));

        vbox.getChildren().addAll(titleLabel, pieChart);
        return vbox;
    }

    private Node createUserManagementView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        Label titleLabel = new Label("User Management Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TableView<Map<String, String>> userTable = new TableView<>();
        userTable.setItems(users);

        TableColumn<Map<String, String>, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("id")));

        TableColumn<Map<String, String>, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("username")));

        TableColumn<Map<String, String>, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("role")));

        userTable.getColumns().addAll(idCol, usernameCol, roleCol);

        // Controls for Adding, Updating, and Deleting Users
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField roleField = new TextField();
        roleField.setPromptText("Role");

        Button addButton = new Button("Add User");
        addButton.setOnAction(e -> {
            Map<String, String> newUser = new HashMap<>();
            newUser.put("id", String.valueOf(users.size() + 1));
            newUser.put("username", usernameField.getText());
            newUser.put("password", passwordField.getText());
            newUser.put("role", roleField.getText());
            users.add(newUser);

            // Clear fields
            usernameField.clear();
            passwordField.clear();
            roleField.clear();
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            Map<String, String> selected = userTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                users.remove(selected);
            }
        });

        userTable.setOnMouseClicked(e -> {
            Map<String, String> selected = userTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                usernameField.setText(selected.get("username"));
                passwordField.setText(selected.get("password"));
                roleField.setText(selected.get("role"));
            }
        });

        Button updateButton = new Button("Update User");
        updateButton.setOnAction(e -> {
            Map<String, String> selected = userTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.put("username", usernameField.getText());
                selected.put("password", passwordField.getText());
                selected.put("role", roleField.getText());
                userTable.refresh(); // Refresh the table view
            }
        });

        VBox userControls = new VBox(10, usernameField, passwordField, roleField, addButton, updateButton, deleteButton);
        vbox.getChildren().addAll(titleLabel, userTable, userControls);
        return vbox;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearVehicleFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}