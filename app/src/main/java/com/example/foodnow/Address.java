package com.example.foodnow;

public class Address {
    private String id;
    private String title;
    private String fullAddress;
    private String receiverName;
    private String receiverPhone;
    private boolean isDefault;

    public Address() {
    }

    public Address(String id, String title, String fullAddress, String receiverName, String receiverPhone, boolean isDefault) {
        this.id = id;
        this.title = title;
        this.fullAddress = fullAddress;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.isDefault = isDefault;
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
