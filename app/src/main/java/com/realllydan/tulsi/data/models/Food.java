package com.realllydan.tulsi.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

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

    protected Food(Parcel in) {
        name = in.readString();
        type = in.readString();
        quantity = in.readInt();
        price = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(quantity);
        dest.writeInt(price);
    }
}
