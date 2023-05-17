package de.hdmstuttgart.einkaufsliste.roomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;


import java.util.List;

import de.hdmstuttgart.einkaufsliste.models.GroceryList;
import de.hdmstuttgart.einkaufsliste.models.Product;


@Dao
public interface GroceryListDAO {

    @Insert
    void insertGroceryList(GroceryList groceryList);

    @Query("INSERT INTO Product (name, isChecked, groceryListID) VALUES (:name,:isChecked,:groceryListID)")
    void insertProduct(String name, boolean isChecked, int groceryListID);

    @Query("SELECT * FROM GroceryList")
    List<GroceryList> getAll();

    @Query("SELECT * FROM GroceryList WHERE groceryListID = :listID")
    GroceryList getList(int listID);

    @Query("SELECT groceryListID FROM GroceryList WHERE name = :listName")
    int getGroceryListID(String listName);

    @Query("UPDATE GroceryList SET name = :newName WHERE groceryListID = :listID")
    int updateGroceryList(int listID, String newName);

    @Query("SELECT productID FROM Product WHERE name = :productName AND groceryListID = :groceryListID")
    int getProductID(String productName, int groceryListID);

    @Query("SELECT isChecked FROM Product WHERE name = :productName AND groceryListID = :groceryListID")
    boolean getIsChecked(String productName, int groceryListID);

    //returns all instances of the data class that pairs the parent entity and the child entity.
    @Transaction
    @Query("SELECT * FROM GroceryList WHERE groceryListID = :groceryListID")
    ListWithProducts getListWithProducts(int groceryListID);

    @Query("SELECT name FROM Product WHERE productID = :productID")
    String getProductName(int productID);

    @Query("SELECT * FROM product WHERE groceryListID = :groceryListID")
    List<Product> getProducts(int groceryListID);

    @Query("UPDATE Product SET isChecked = :checkedBoolean WHERE productID = :productID")
    void updateProductIsChecked(boolean checkedBoolean, int productID);

    @Delete
    void deleteGroceryList(GroceryList groceryList);

    @Delete
    void deleteProducts(List<Product> product);

}
