package com.mycompany.gestionaeropuerto;

public class Flight {
    private int id;
    private String flightNumber;
    private int originId;
    private int destinationId;
    private String departureTime;

    public Flight(int id, String flightNumber, int originId, int destinationId, String departureTime) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.originId = originId;
        this.destinationId = destinationId;
        this.departureTime = departureTime;
    }

    public int getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public int getOriginId() { return originId; }
    public int getDestinationId() { return destinationId; }
    public String getDepartureTime() { return departureTime; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public void setOriginId(int originId) { this.originId = originId; }
    public void setDestinationId(int destinationId) { this.destinationId = destinationId; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    @Override
    public String toString() {
        return flightNumber + " (Origin ID: " + originId + ", Destination ID: " + destinationId + ", Time: " + departureTime + ")";
    }
}