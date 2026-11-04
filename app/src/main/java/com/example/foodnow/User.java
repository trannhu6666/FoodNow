package com.example.foodnow;

public class User {
    private String id;
    private String name;
    private String phone;
    private int points;

    public User() {
    }

    public User(String id, String name, String phone, int points) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
