package com.realllydan.tulsi.data.models;

public class Food {

    public static final String TYPE_VEG = "Veg";
    public static final String TYPE_NON_VEG = "Non-Veg";

    private String name;
    private String type;
    private int quantity;
    private int price;

    public Food() {

    }

    public Food(String name, String type, int price) {
        this.name = name;
        this.type = type;
        this.quantity = 0;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
