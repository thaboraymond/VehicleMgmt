package com.example.vehiclerantalapp;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class PaymentView extends GridPane {

    public PaymentView() {
        setPadding(new Insets(15));
        setHgap(10);
        setVgap(10);

        Label bookingIdLabel = new Label("Booking ID:");
        TextField bookingIdField = new TextField();

        Label paymentMethodLabel = new Label("Payment Method:");
        ComboBox<String> paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("Cash", "Credit Card", "Online");
        paymentMethodCombo.setValue("Cash"); // Default value

        Button processPaymentButton = new Button("Process Payment");
        Button generateInvoiceButton = new Button("Generate Invoice");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search Invoice");

        add(bookingIdLabel, 0, 0);
        add(bookingIdField, 1, 0);
        add(paymentMethodLabel, 0, 1);
        add(paymentMethodCombo, 1, 1);

        add(processPaymentButton, 0, 2);
        add(generateInvoiceButton, 1, 2);
        add(searchField, 0, 3);
        add(searchButton, 1, 3);
    }
}