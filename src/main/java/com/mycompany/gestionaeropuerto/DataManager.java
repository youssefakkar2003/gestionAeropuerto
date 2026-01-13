package com.mycompany.gestionaeropuerto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {
    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

    // User operations
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch users", e);
        }
        return users;
    }

    public static boolean addUser(String name, String email) {
        if (!isValidEmail(email)) return false;
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add user", e);
            return false;
        }
    }

    public static boolean updateUser(int id, String name, String email) {
        if (!isValidEmail(email)) return false;
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update user", e);
            return false;
        }
    }

    public static boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete user", e);
            return false;
        }
    }

    // Airport operations
    public static List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM airports")) {
            while (rs.next()) {
                airports.add(new Airport(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getString("city")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch airports", e);
        }
        return airports;
    }

    public static boolean addAirport(String code, String name, String city) {
        if (!isValidCode(code)) return false;
        String sql = "INSERT INTO airports (code, name, city) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.setString(2, name);
            pstmt.setString(3, city);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add airport", e);
            return false;
        }
    }

    public static boolean updateAirport(int id, String code, String name, String city) {
        if (!isValidCode(code)) return false;
        String sql = "UPDATE airports SET code = ?, name = ?, city = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.setString(2, name);
            pstmt.setString(3, city);
            pstmt.setInt(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update airport", e);
            return false;
        }
    }

    public static boolean deleteAirport(int id) {
        String sql = "DELETE FROM airports WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete airport", e);
            return false;
        }
    }

    // Flight operations
    public static List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM flights")) {
            while (rs.next()) {
                flights.add(new Flight(rs.getInt("id"), rs.getString("flight_number"), rs.getInt("origin_id"),
                        rs.getInt("destination_id"), rs.getString("departure_time")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch flights", e);
        }
        return flights;
    }

    public static boolean addFlight(String flightNumber, int originId, int destinationId, String departureTime) {
        if (!isValidFlightNumber(flightNumber) || !isValidDepartureTime(departureTime)) return false;
        String sql = "INSERT INTO flights (flight_number, origin_id, destination_id, departure_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            pstmt.setInt(2, originId);
            pstmt.setInt(3, destinationId);
            pstmt.setString(4, departureTime);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add flight", e);
            return false;
        }
    }

    public static boolean updateFlight(int id, String flightNumber, int originId, int destinationId, String departureTime) {
        if (!isValidFlightNumber(flightNumber) || !isValidDepartureTime(departureTime)) return false;
        String sql = "UPDATE flights SET flight_number = ?, origin_id = ?, destination_id = ?, departure_time = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            pstmt.setInt(2, originId);
            pstmt.setInt(3, destinationId);
            pstmt.setString(4, departureTime);
            pstmt.setInt(5, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update flight", e);
            return false;
        }
    }

    public static boolean deleteFlight(int id) {
        String sql = "DELETE FROM flights WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete flight", e);
            return false;
        }
    }

    // Validation methods
    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private static boolean isValidCode(String code) {
        return code != null && code.matches("^[A-Z]{3}$");
    }

    private static boolean isValidFlightNumber(String flightNumber) {
        return flightNumber != null && flightNumber.matches("^[A-Z0-9]{2,6}$");
    }

    private static boolean isValidDepartureTime(String departureTime) {
        return departureTime != null && departureTime.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$");
    }
}