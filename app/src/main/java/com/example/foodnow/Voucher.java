package com.example.foodnow;

public class Voucher {
    private String id;
    private String title;
    private String description;
    private String expiryDate;
    private String code;
    private String type;
    private double minSpend;
    private double discountValue;

    public Voucher() {
    }

    public Voucher(String id, String title, String description, String expiryDate, String code, String type, double minSpend, double discountValue) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.expiryDate = expiryDate;
        this.code = code;
        this.type = type;
        this.minSpend = minSpend;
        this.discountValue = discountValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMinSpend() {
        return minSpend;
    }

    public void setMinSpend(double minSpend) {
        this.minSpend = minSpend;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }
}
