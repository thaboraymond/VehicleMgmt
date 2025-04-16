package com.example.vehiclerantalapp;


import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class UserManagementView extends GridPane {

    public UserManagementView() {
        setPadding(new Insets(15));
        setHgap(10);
        setVgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Label roleLabel = new Label("Role:");
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Admin", "Employee");
        roleCombo.setValue("Employee"); // Default role

        Button addUserButton = new Button("Add User");
        Button updateUserButton = new Button("Update User");
        Button deleteUserButton = new Button("Delete User");

        add(usernameLabel, 0, 0);
        add(usernameField, 1, 0);
        add(passwordLabel, 0, 1);
        add(passwordField, 1, 1);
        add(roleLabel, 0, 2);
        add(roleCombo, 1, 2);

        add(addUserButton, 0, 3);
        add(updateUserButton, 1, 3);
        add(deleteUserButton, 2, 3);
    }
}