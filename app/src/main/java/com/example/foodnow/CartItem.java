package com.example.foodnow;

import java.util.List;

public class CartItem {
    private String foodId;
    private String foodName;
    private int quantity;
    private List<String> selectedToppings;
    private double totalPrice;

    public CartItem() {
    }

    public CartItem(String foodId, String foodName, int quantity, List<String> selectedToppings, double totalPrice) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.selectedToppings = selectedToppings;
        this.totalPrice = totalPrice;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getSelectedToppings() {
        return selectedToppings;
    }

    public void setSelectedToppings(List<String> selectedToppings) {
        this.selectedToppings = selectedToppings;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
