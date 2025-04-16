package com.example.vehiclerantalapp;

import com.example.vehiclerentalapp.DatabaseUtil;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleManagementView extends GridPane {

    private TextField vehicleIdField;
    private TextField brandModelField;
    private TextField categoryField;
    private TextField priceField;
    private TextField availabilityField;

    public VehicleManagementView() {
        setPadding(new Insets(15));
        setHgap(10);
        setVgap(10);

        // Labels and Text Fields for Vehicle Details
        Label vehicleIdLabel = new Label("Vehicle ID:");
        vehicleIdField = new TextField();
        vehicleIdField.setDisable(true); // Auto-generated

        Label brandModelLabel = new Label("Brand & Model:");
        brandModelField = new TextField();

        Label categoryLabel = new Label("Category:");
        categoryField = new TextField();

        Label priceLabel = new Label("Price per Day:");
        priceField = new TextField();

        Label availabilityLabel = new Label("Availability:");
        availabilityField = new TextField();

        // Buttons for Actions
        Button addButton = new Button("Add Vehicle");
        addButton.setOnAction(e -> addVehicle());

        Button updateButton = new Button("Update Vehicle");
        updateButton.setOnAction(e -> updateVehicle());

        Button deleteButton = new Button("Delete Vehicle");
        deleteButton.setOnAction(e -> deleteVehicle());

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchVehicle(searchField.getText()));

        // Add elements to the grid
        add(vehicleIdLabel, 0, 0);
        add(vehicleIdField, 1, 0);
        add(brandModelLabel, 0, 1);
        add(brandModelField, 1, 1);
        add(categoryLabel, 0, 2);
        add(categoryField, 1, 2);
        add(priceLabel, 0, 3);
        add(priceField, 1, 3);
        add(availabilityLabel, 0, 4);
        add(availabilityField, 1, 4);

        add(addButton, 0, 5);
        add(updateButton, 1, 5);
        add(deleteButton, 2, 5);
        add(searchField, 0, 6);
        add(searchButton, 1, 6);
    }

    private void addVehicle() {
        String brandModel = brandModelField.getText();
        String category = categoryField.getText();
        String priceStr = priceField.getText();
        String availability = availabilityField.getText();

        if (brandModel.isEmpty() || category.isEmpty() || priceStr.isEmpty() || availability.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            String sql = "INSERT INTO Vehicles (BrandModel, Category, RentalPricePerDay, AvailabilityStatus) VALUES (?, ?, ?, ?)";
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, brandModel);
                pstmt.setString(2, category);
                pstmt.setDouble(3, price);
                pstmt.setString(4, availability);
                pstmt.executeUpdate();
                showAlert(AlertType.INFORMATION, "Success", "Vehicle added successfully.");
                clearFields();
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Database Error", "Could not add vehicle: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid price.");
        }
    }

    private void updateVehicle() {
        // Implement update logic here
        showAlert(AlertType.INFORMATION, "Info", "Update functionality to be implemented.");
    }

    private void deleteVehicle() {
        // Implement delete logic here
        showAlert(AlertType.INFORMATION, "Info", "Delete functionality to be implemented.");
    }

    private void searchVehicle(String searchText) {
        // Implement search logic here
        showAlert(AlertType.INFORMATION, "Info", "Search functionality to be implemented. Searching for: " + searchText);
    }

    private void clearFields() {
        vehicleIdField.clear();
        brandModelField.clear();
        categoryField.clear();
        priceField.clear();
        availabilityField.clear();
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}