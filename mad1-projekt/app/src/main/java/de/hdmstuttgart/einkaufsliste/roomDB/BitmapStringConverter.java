package de.hdmstuttgart.einkaufsliste.roomDB;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class BitmapStringConverter {
    @TypeConverter
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        String temp = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return temp;
    }

    @TypeConverter
    public static Bitmap StringToBitmap(String string) {
        byte[] byteArrayDecoded = Base64.decode(string, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayDecoded, 0, byteArrayDecoded.length);
        return bitmap;
    }
}
