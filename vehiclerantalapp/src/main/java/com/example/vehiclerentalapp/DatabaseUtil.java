package com.example.vehiclerentalapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/rental_system"; // Replace with your URL
    private static final String DATABASE_USER = "root"; // Replace with your username
    private static final String DATABASE_PASSWORD = "123456"; // Replace with your password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Corrected MySQL driver class name
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found!", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Log the error
            }
        }
    }

    // Method to initialize the database tables (if they don't exist)
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {

            // Create Vehicles table
            String sqlVehicles = "CREATE TABLE IF NOT EXISTS Vehicles (" +
                    "VehicleID INT AUTO_INCREMENT PRIMARY KEY," +
                    "BrandModel VARCHAR(255) NOT NULL," +
                    "Category VARCHAR(50) NOT NULL," +
                    "RentalPricePerDay DECIMAL(10, 2) NOT NULL," +
                    "AvailabilityStatus VARCHAR(50) NOT NULL" +
                    ");";
            stmt.execute(sqlVehicles);

            // Create Customers table
            String sqlCustomers = "CREATE TABLE IF NOT EXISTS Customers (" +
                    "CustomerID INT AUTO_INCREMENT PRIMARY KEY," +
                    "Name VARCHAR(255) NOT NULL," +
                    "ContactInfo VARCHAR(255)," +
                    "DrivingLicenseNumber VARCHAR(100) UNIQUE" +
                    ");";
            stmt.execute(sqlCustomers);

            // Create Bookings table
            String sqlBookings = "CREATE TABLE IF NOT EXISTS Bookings (" +
                    "BookingID INT AUTO_INCREMENT PRIMARY KEY," +
                    "CustomerID INT NOT NULL," +
                    "VehicleID INT NOT NULL," +
                    "StartDate DATE NOT NULL," +
                    "EndDate DATE NOT NULL," +
                    "FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)," +
                    "FOREIGN KEY (VehicleID) REFERENCES Vehicles(VehicleID)" +
                    ");";
            stmt.execute(sqlBookings);

            // Create Payments table
            String sqlPayments = "CREATE TABLE IF NOT EXISTS Payments (" +
                    "PaymentID INT AUTO_INCREMENT PRIMARY KEY," +
                    "BookingID INT NOT NULL," +
                    "PaymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "Amount DECIMAL(10, 2) NOT NULL," +
                    "PaymentMethod VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID)" +
                    ");";
            stmt.execute(sqlPayments);

            // Create Users table
            String sqlUsers = "CREATE TABLE IF NOT EXISTS Users (" +
                    "UserID INT AUTO_INCREMENT PRIMARY KEY," +
                    "Username VARCHAR(100) UNIQUE NOT NULL," +
                    "Password VARCHAR(255) NOT NULL," +
                    "Role VARCHAR(50) NOT NULL" +
                    ");";
            stmt.execute(sqlUsers);

            System.out.println("MySQL database initialized or already exists.");

        } catch (SQLException e) {
            System.err.println("Error initializing MySQL database: " + e.getMessage());
        }
    }
}
