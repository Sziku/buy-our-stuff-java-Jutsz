package com.codecool.buyourstuff.model;


public class ShippingInfo {

    private String name;
    private String email;
    private String phoneNumber;
    private String billingAddress;
    private String shippingAddress;

    public ShippingInfo(String name, String email, String phoneNumber, String billingAddress, String shippingAddress) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public String toString() {
        return String.format("%1$s={" +
                        "name: %2$s, " +
                        "email: %3$s, " +
                        "phoneNumber: %4$s, " +
                        "billingAddress: %5$s, " +
                        "shippingAddress: %6$s}",
                getClass().getSimpleName(),
                name,
                email,
                phoneNumber,
                billingAddress,
                shippingAddress
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
