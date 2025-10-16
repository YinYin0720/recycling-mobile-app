package com.example.e_cynic.dbTests;

import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.ItemDatabase;
import com.example.e_cynic.entity.Item;
import com.example.e_cynic.utils.DatabaseUtil;
import com.example.e_cynic.utils.ImageUtil;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ItemDatabaseTest {
    private static SQLiteDatabase database = DatabaseUtil.getTestDatabase();

    private Integer itemId = 1;
    private Integer orderId = 1;
    private String itemName = "smartphone";
    private String imgPath = "/storage/emulated/0/DCIM/Camera/IMG_20211127_072317.jpg";
    private Integer point = 300;

    @Test
    public void insertItem() throws IllegalAccessException {
        byte[] imgBytes = ImageUtil.imagePathToByteArray(imgPath);
        Item item = new Item(null, orderId, itemName, imgBytes, null);
        boolean result = ItemDatabase.insertItem(item);
        LoggingUtil.printMessage("insert item", (result == true) ? "true" : "false");
    }

    @Test
    public void getItemsByOrderId() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<Item> itemList = ItemDatabase.getItemsByOrderId(orderId);
        if(itemList == null) {
            LoggingUtil.printMessage("get items by orderid", "no items found");
            return;
        }
        for (Item item :
                itemList) {
            LoggingUtil.printMessage("get items by orderid", item.toString());
        }
    }

    @Test
    public void getItemByItemId() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Item item = ItemDatabase.getItemByItemId(itemId);
        LoggingUtil.printMessage("get item by itemid", (item != null) ? item.toString() : "null");
    }

}