package de.hdmstuttgart.einkaufsliste.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int productID;

    public String name;
    private boolean isChecked;
    public int groceryListID;

    public Product(String name, boolean isChecked, int groceryListID) {

        this.name = name;
        this.isChecked = isChecked;
        this.groceryListID = groceryListID;

    }

    public boolean getIsChecked() {
        return isChecked;
    }
}
