package de.hdmstuttgart.einkaufsliste.UnitTests;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import de.hdmstuttgart.einkaufsliste.models.Product;
import de.hdmstuttgart.einkaufsliste.roomDB.GroceryListDAO;
import de.hdmstuttgart.einkaufsliste.roomDB.GroceryListDatabase;

@RunWith(AndroidJUnit4.class)
public class DbTest {

    private GroceryListDAO groceryListDAO;
    private GroceryListDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, GroceryListDatabase.class).build();
        groceryListDAO = db.groceryListDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void ProductTest() throws Exception {

        Product product = new Product("Tomato", false, 1);
        groceryListDAO.insertProduct("Tomato", false, 1);
        Assert.assertEquals(product.name, db.groceryListDAO().getProductName(1));
        Assert.assertEquals(product.getIsChecked(), db.groceryListDAO().getIsChecked("Tomato", 1));

    }
}

