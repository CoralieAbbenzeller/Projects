package de.hdmstuttgart.einkaufsliste.roomDB;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import de.hdmstuttgart.einkaufsliste.models.GroceryList;
import de.hdmstuttgart.einkaufsliste.models.Product;

public class ListWithProducts {
    @Embedded
    public GroceryList groceryList;
    @Relation(
            parentColumn = "groceryListID", //primary key in list table
            entityColumn = "groceryListID" //foreign key in product table
    )
    public List<Product> products;
}
