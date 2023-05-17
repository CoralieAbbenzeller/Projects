package de.hdmstuttgart.einkaufsliste.models;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class GroceryList {

    public String name;

    @PrimaryKey(autoGenerate = true)
    public int groceryListID; //starts at 1

    public Bitmap groceryListPicture;

    public GroceryList(String name, Bitmap groceryListPicture) {
        this.name = name;
        this.groceryListPicture = groceryListPicture;
    }

}
