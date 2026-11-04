package com.example.foodnow;

public class Food {
    private String id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private double rating;
    private String categoryId;
    private String restaurantName;
    private String distance;
    private String deliveryTime;

    public Food() {
    }

    public Food(String id, String name, String description, double price, String imageUrl, double rating, String categoryId) {
        this(id, name, description, price, imageUrl, rating, categoryId, "Cửa hàng FoodNow", "1.2 km", "20 phút");
    }

    public Food(String id, String name, String description, double price, String imageUrl, double rating, String categoryId, String restaurantName, String distance, String deliveryTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.categoryId = categoryId;
        this.restaurantName = restaurantName;
        this.distance = distance;
        this.deliveryTime = deliveryTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getRestaurantName() {
        return restaurantName != null && !restaurantName.isEmpty() ? restaurantName : "Cửa hàng FoodNow";
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDistance() {
        return distance != null && !distance.isEmpty() ? distance : "1.2 km";
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDeliveryTime() {
        return deliveryTime != null && !deliveryTime.isEmpty() ? deliveryTime : "20 phút";
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
