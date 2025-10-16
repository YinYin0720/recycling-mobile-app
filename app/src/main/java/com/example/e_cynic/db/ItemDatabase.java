package com.example.e_cynic.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.e_cynic.db.mapper.ItemMapper;
import com.example.e_cynic.entity.Item;
import com.example.e_cynic.utils.ImageUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ItemDatabase
{
    public static final String itemsTable = "items";
    public static final String itemId = "itemId";
    public static final String orderId = "orderId";
    public static final String itemName = "itemName";
    public static final String numberOfItems = "numberOfItems";
    public static final String image = "image";
    public static final String point = "point";

    private static SQLiteDatabase db = DatabaseConnectionProvider.getDatabase(null);

    public static boolean insertItem(Item item) throws IllegalAccessException {
        ContentValues cv = ItemMapper.mapItemToContentValues(item);
        long result = db.insert(itemsTable, null, cv);
        return result > 0;
    }

    public static List<Item> getItemsByOrderId(Integer orderId) throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Cursor c = db.rawQuery("select * from items where orderId=?", new String[]{String.valueOf(orderId)});
        return (c.moveToNext()) ? ItemMapper.mapCursorToItems(c) : null;
    }

    public static Item getItemByItemId(Integer itemId) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Cursor c = db.rawQuery("select * from items where itemId=?", new String[]{String.valueOf(itemId)});
        return (c.moveToNext()) ? ItemMapper.mapCursorToOneItem(c) : null;
    }

    public static Bitmap getFirstItemImageByOrderId(Integer orderId) throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Item item = getFirstItemByOrderId(orderId);

        if (item != null)
        {
            Bitmap bitmap = ImageUtil.byteArrayToBitmap(item.image);
            return bitmap;
        }
        return null;
    }

    public static Item getFirstItemByOrderId(Integer orderId) throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Cursor c = db.rawQuery("select * from items where orderId=? limit 1", new String[]{String.valueOf(orderId)});
        return (c.moveToNext()) ? ItemMapper.mapCursorToOneItem(c) : null;
    }

    public static Integer getTotalPointByOrderId(Integer orderId) {
        Cursor c = db.rawQuery("select SUM(point) from items where orderId=? group by orderId", null);
        return c.moveToNext() ? c.getInt(0) : -1;
    }

    public static boolean editItemPointsByItemId(Integer itemId,Integer itemPoints) {
        ContentValues cv = new ContentValues();
        cv.put(point,itemPoints);
        long result = db.update(itemsTable, cv, "itemId=?", new String[]{String.valueOf(itemId)})  ;
        return result > 0;
    }
}
