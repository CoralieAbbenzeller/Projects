package de.hdmstuttgart.einkaufsliste.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import de.hdmstuttgart.einkaufsliste.models.GroceryList;
import de.hdmstuttgart.einkaufsliste.models.Product;


@Database(entities = {GroceryList.class, Product.class}, version = 1)
@TypeConverters({BitmapStringConverter.class})
public abstract class GroceryListDatabase extends RoomDatabase {

    public abstract GroceryListDAO groceryListDAO();

    private static volatile GroceryListDatabase instance;

    public GroceryListDatabase() {
    }

    public static synchronized GroceryListDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context,
                            GroceryListDatabase.class,
                            "groceryListDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


}
