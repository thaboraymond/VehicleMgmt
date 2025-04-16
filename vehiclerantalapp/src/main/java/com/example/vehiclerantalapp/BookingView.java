package com.example.vehiclerantalapp;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;

public class BookingView extends GridPane {

    public BookingView() {
        setPadding(new Insets(15));
        setHgap(10);
        setVgap(10);

        Label customerIdLabel = new Label("Customer ID:");
        TextField customerIdField = new TextField();

        Label vehicleIdLabel = new Label("Vehicle ID:");
        TextField vehicleIdField = new TextField();

        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker();

        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker();

        Button rentButton = new Button("Rent Vehicle");
        Button modifyButton = new Button("Modify Booking");
        Button cancelButton = new Button("Cancel Booking");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search Booking");

        add(customerIdLabel, 0, 0);
        add(customerIdField, 1, 0);
        add(vehicleIdLabel, 0, 1);
        add(vehicleIdField, 1, 1);
        add(startDateLabel, 0, 2);
        add(startDatePicker, 1, 2);
        add(endDateLabel, 0, 3);
        add(endDatePicker, 1, 3);

        add(rentButton, 0, 4);
        add(modifyButton, 1, 4);
        add(cancelButton, 2, 4);
        add(searchField, 0, 5);
        add(searchButton, 1, 5);
    }
}