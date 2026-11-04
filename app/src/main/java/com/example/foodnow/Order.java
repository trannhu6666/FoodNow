package com.example.foodnow;

public class Order {
    private String orderId;
    private String restaurantName;
    private String itemsSummary;
    private String status;
    private double totalAmount;
    private String date;

    public Order() {
    }

    public Order(String orderId, String restaurantName, String itemsSummary, String status, double totalAmount, String date) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.itemsSummary = itemsSummary;
        this.status = status;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getItemsSummary() {
        return itemsSummary;
    }

    public void setItemsSummary(String itemsSummary) {
        this.itemsSummary = itemsSummary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
