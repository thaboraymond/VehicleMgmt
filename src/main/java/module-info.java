module com.example.vehiclerantalapp {
    requires javafx.controls;

    requires java.sql;
    requires javafx.fxml;


    opens com.example.vehiclerantalapp to javafx.fxml;
    exports com.example.vehiclerantalapp;
    exports com.example.vehiclerentalapp;
    opens com.example.vehiclerentalapp to javafx.fxml;
}