package com.example.sofra.data.local.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {

    @PrimaryKey(autoGenerate = true)
    int id;
    private int itemId;
    private String restaurantId;
    private String itemName;
    private String quantity;
    private String image;
    private String note;



    public Item(int itemId, String restaurantId, String itemName, String quantity, String image, String note) {
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.image = image;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
