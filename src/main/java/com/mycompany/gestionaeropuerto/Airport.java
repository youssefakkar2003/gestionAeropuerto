package com.mycompany.gestionaeropuerto;

public class Airport {
    private int id;
    private String code;
    private String name;
    private String city;

    public Airport(int id, String code, String name, String city) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.city = city;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return code + " - " + name + ", " + city;
    }
}