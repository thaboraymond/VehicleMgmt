package com.example.vehiclerantalapp;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class CustomerManagementView extends GridPane {

    public CustomerManagementView() {
        setPadding(new Insets(15));
        setHgap(10);
        setVgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label contactLabel = new Label("Contact Info:");
        TextField contactField = new TextField();

        Label licenseLabel = new Label("License No:");
        TextField licenseField = new TextField();

        Button registerButton = new Button("Register Customer");
        Button updateButton = new Button("Update Customer");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");

        add(nameLabel, 0, 0);
        add(nameField, 1, 0);
        add(contactLabel, 0, 1);
        add(contactField, 1, 1);
        add(licenseLabel, 0, 2);
        add(licenseField, 1, 2);

        add(registerButton, 0, 3);
        add(updateButton, 1, 3);
        add(searchField, 0, 4);
        add(searchButton, 1, 4);
    }
}