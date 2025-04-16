package com.example.vehiclerantalapp;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class ReportsView extends VBox {

    public ReportsView() {
        setPadding(new Insets(15));
        setSpacing(10);

        Button availableVehiclesReportButton = new Button("Available Vehicles Report");
        Button customerRentalHistoryButton = new Button("Customer Rental History");
        Button revenueReportButton = new Button("Revenue Report");

        getChildren().addAll(availableVehiclesReportButton, customerRentalHistoryButton, revenueReportButton);
    }
}