package com.mycompany.gestionaeropuerto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gestionAeropuerto extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(gestionAeropuerto.class.getName());

    // UI Components
    private JTabbedPane tabbedPane;
    private JTable userTable, airportTable, flightTable;
    private JTextField userNameField, userEmailField;
    private JTextField airportCodeField, airportNameField, airportCityField;
    private JTextField flightNumberField, flightDepartureField;
    private JComboBox<Airport> flightOriginCombo, flightDestinationCombo;

    public gestionAeropuerto() {
        DatabaseConnection.initializeDatabase();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Airport Management System");
        setPreferredSize(new Dimension(800, 600));

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // User Panel
        JPanel userPanel = new JPanel(new BorderLayout(10, 10));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userTable = new JTable();
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userNameField = new JTextField(20);
        userEmailField = new JTextField(20);
        JButton userShowButton = new JButton("Show Users");
        JButton userAddButton = new JButton("Add User");
        JButton userEditButton = new JButton("Edit User");
        JButton userDeleteButton = new JButton("Delete User");

        JPanel userInputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        userInputPanel.add(new JLabel("Name:"));
        userInputPanel.add(userNameField);
        userInputPanel.add(new JLabel("Email:"));
        userInputPanel.add(userEmailField);

        JPanel userButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        userButtonPanel.add(userShowButton);
        userButtonPanel.add(userAddButton);
        userButtonPanel.add(userEditButton);
        userButtonPanel.add(userDeleteButton);

        userPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        userPanel.add(userInputPanel, BorderLayout.NORTH);
        userPanel.add(userButtonPanel, BorderLayout.SOUTH);
        JPanel inicio = new JPanel();
        // Airport Panel
        JPanel airportPanel = new JPanel(new BorderLayout(10, 10));
        airportPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        airportTable = new JTable();
        airportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        airportCodeField = new JTextField(10);
        airportNameField = new JTextField(20);
        airportCityField = new JTextField(20);
        JButton airportShowButton = new JButton("Show Airports");
        JButton airportAddButton = new JButton("Add Airport");
        JButton airportEditButton = new JButton("Edit Airport");
        JButton airportDeleteButton = new JButton("Delete Airport");

        JPanel airportInputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        airportInputPanel.add(new JLabel("Code:"));
        airportInputPanel.add(airportCodeField);
        airportInputPanel.add(new JLabel("Name:"));
        airportInputPanel.add(airportNameField);
        airportInputPanel.add(new JLabel("City:"));
        airportInputPanel.add(airportCityField);

        JPanel airportButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        airportButtonPanel.add(airportShowButton);
        airportButtonPanel.add(airportAddButton);
        airportButtonPanel.add(airportEditButton);
        airportButtonPanel.add(airportDeleteButton);

        airportPanel.add(new JScrollPane(airportTable), BorderLayout.CENTER);
        airportPanel.add(airportInputPanel, BorderLayout.NORTH);
        airportPanel.add(airportButtonPanel, BorderLayout.SOUTH);

        // Flight Panel
        JPanel flightPanel = new JPanel(new BorderLayout(10, 10));
        flightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        flightTable = new JTable();
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightNumberField = new JTextField(10);
        flightOriginCombo = new JComboBox<>();
        flightDestinationCombo = new JComboBox<>();
        flightDepartureField = new JTextField(20);
        JButton flightShowButton = new JButton("Show Flights");
        JButton flightAddButton = new JButton("Add Flight");
        JButton flightEditButton = new JButton("Edit Flight");
        JButton flightDeleteButton = new JButton("Delete Flight");

        JPanel flightInputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        flightInputPanel.add(new JLabel("Flight Number:"));
        flightInputPanel.add(flightNumberField);
        flightInputPanel.add(new JLabel("Origin Airport:"));
        flightInputPanel.add(flightOriginCombo);
        flightInputPanel.add(new JLabel("Destination Airport:"));
        flightInputPanel.add(flightDestinationCombo);
        flightInputPanel.add(new JLabel("Departure Time (YYYY-MM-DD HH:MM):"));
        flightInputPanel.add(flightDepartureField);

        JPanel flightButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        flightButtonPanel.add(flightShowButton);
        flightButtonPanel.add(flightAddButton);
        flightButtonPanel.add(flightEditButton);
        flightButtonPanel.add(flightDeleteButton);

        flightPanel.add(new JScrollPane(flightTable), BorderLayout.CENTER);
        flightPanel.add(flightInputPanel, BorderLayout.NORTH);
        flightPanel.add(flightButtonPanel, BorderLayout.SOUTH);

        // Add tabs
        tabbedPane.addTab("Manage Users", userPanel);
        tabbedPane.addTab("Manage Airports", airportPanel);
        tabbedPane.addTab("Manage Flights", flightPanel);

        // Layout
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        // Event Handlers
        userShowButton.addActionListener(e -> showUsers());
        userAddButton.addActionListener(e -> addUser());
        userEditButton.addActionListener(e -> editUser());
        userDeleteButton.addActionListener(e -> deleteUser());

        airportShowButton.addActionListener(e -> showAirports());
        airportAddButton.addActionListener(e -> addAirport());
        airportEditButton.addActionListener(e -> editAirport());
        airportDeleteButton.addActionListener(e -> deleteAirport());

        flightShowButton.addActionListener(e -> showFlights());
        flightAddButton.addActionListener(e -> addFlight());
        flightEditButton.addActionListener(e -> editFlight());
        flightDeleteButton.addActionListener(e -> deleteFlight());

        // Populate airport combos
        updateAirportCombos();
    }

    private void showUsers() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Email","Flights","Total"}, 0);
        for (User user : DataManager.getAllUsers()) {
            model.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(),0,0});
        }
        userTable.setModel(model);
    }

    private void addUser() {
        String name = userNameField.getText().trim();
        String email = userEmailField.getText().trim();
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataManager.addUser(name, email)) {
            showUsers();
            userNameField.setText("");
            userEmailField.setText("");
            JOptionPane.showMessageDialog(this, "User added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email format or database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) userTable.getValueAt(selectedRow, 0);
        String name = userNameField.getText().trim();
        String email = userEmailField.getText().trim();
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataManager.updateUser(id, name, email)) {
            showUsers();
            JOptionPane.showMessageDialog(this, "User updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email format or database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
// Eliminar Usuarios 
    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) userTable.getValueAt(selectedRow, 0);
        if (DataManager.deleteUser(id)) {
            showUsers();
            JOptionPane.showMessageDialog(this, "User deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
// Mostrar aeropuertos 
    private void showAirports() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Code", "Name", "City"}, 0);
        for (Airport airport : DataManager.getAllAirports()) {
            model.addRow(new Object[]{airport.getId(), airport.getCode(), airport.getName(), airport.getCity()});
        }
        airportTable.setModel(model);
        updateAirportCombos();
    }

    private void addAirport() {
        String code = airportCodeField.getText().trim().toUpperCase();
        String name = airportNameField.getText().trim();
        String city = airportCityField.getText().trim();
        if (code.isEmpty() || name.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataManager.addAirport(code, name, city)) {
            showAirports();
            airportCodeField.setText("");
            airportNameField.setText("");
            airportCityField.setText("");
            JOptionPane.showMessageDialog(this, "Airport added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid airport code (must be 3 letters) or database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editAirport() {
        int selectedRow = airportTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an airport", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) airportTable.getValueAt(selectedRow, 0);
        String code = airportCodeField.getText().trim().toUpperCase();
        String name = airportNameField.getText().trim();
        String city = airportCityField.getText().trim();
        if (code.isEmpty() || name.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataManager.updateAirport(id, code, name, city)) {
            showAirports();
            JOptionPane.showMessageDialog(this, "Airport updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid airport code (must be 3 letters) or database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAirport() {
        int selectedRow = airportTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an airport", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) airportTable.getValueAt(selectedRow, 0);
        if (DataManager.deleteAirport(id)) {
            showAirports();
            JOptionPane.showMessageDialog(this, "Airport deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete airport", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showFlights() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Flight Number", "Origin ID", "Destination ID", "Departure Time"}, 0);
        for (Flight flight : DataManager.getAllFlights()) {
            model.addRow(new Object[]{flight.getId(), flight.getFlightNumber(), flight.getOriginId(), flight.getDestinationId(), flight.getDepartureTime()});
        }
        flightTable.setModel(model);
    }

    private void addFlight() {
        String flightNumber = flightNumberField.getText().trim().toUpperCase();
        Airport origin = (Airport) flightOriginCombo.getSelectedItem();
        Airport destination = (Airport) flightDestinationCombo.getSelectedItem();
        String departureTime = flightDepartureField.getText().trim();
        if (flightNumber.isEmpty() || origin == null || destination == null || departureTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (origin.getId() == destination.getId()) {
            JOptionPane.showMessageDialog(this, "Origin and destination cannot be the same", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataManager.addFlight(flightNumber, origin.getId(), destination.getId(), departureTime)) {
            showFlights();
            flightNumberField.setText("");
            flightDepartureField.setText("");
            JOptionPane.showMessageDialog(this, "Flight added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid flight number or departure time ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editFlight() {
        int selectedRow = flightTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a flight", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) flightTable.getValueAt(selectedRow, 0);
        String flightNumber = flightNumberField.getText().trim().toUpperCase();
        Airport origin = (Airport) flightOriginCombo.getSelectedItem();
        Airport destination = (Airport) flightDestinationCombo.getSelectedItem();
        String departureTime = flightDepartureField.getText().trim();
        if (flightNumber.isEmpty() || origin == null || destination == null || departureTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (origin.getId() == destination.getId()) {
            JOptionPane.showMessageDialog(this, "Origin and destination cannot be the same", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataManager.updateFlight(id, flightNumber, origin.getId(), destination.getId(), departureTime)) {
            showFlights();
            JOptionPane.showMessageDialog(this, "Flight updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid flight number (2-6 alphanumeric) or departure time (YYYY-MM-DD HH:MM)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFlight() {
        int selectedRow = flightTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a flight", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) flightTable.getValueAt(selectedRow, 0);
        if (DataManager.deleteFlight(id)) {
            showFlights();
            JOptionPane.showMessageDialog(this, "Flight deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete flight", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAirportCombos() {
        flightOriginCombo.removeAllItems();
        flightDestinationCombo.removeAllItems();
        for (Airport airport : DataManager.getAllAirports()) {
            flightOriginCombo.addItem(airport);
            flightDestinationCombo.addItem(airport);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.SEVERE, "Failed to set look and feel", e);
        }
        EventQueue.invokeLater(() -> new gestionAeropuerto().setVisible(true));
    }
}