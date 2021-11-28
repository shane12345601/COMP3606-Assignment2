package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    private static final String name = "Product";
    private static final int DB_version= 1;
    public ProductDatabaseHelper(@Nullable Context context) {
        super(context, name, null, DB_version);
    }


    @Override
    public void onCreate(SQLiteDatabase dp) {
        updateMyDatabase(dp,0,DB_version);
    }
    public static void insertProduct(SQLiteDatabase db,String name,int stockOnHand,int stockInTransit,Double price,int reorderQuantity, int reorderAmount){
        ContentValues productValues = new ContentValues();
        productValues.put("NAME",name);
        productValues.put("STOCK_ON_HAND",stockOnHand);
        productValues.put("STOCK_IN_TRANSIT",stockInTransit);
        productValues.put("PRICE",price);
        productValues.put("REORDER_QUANTITY",reorderQuantity);
        productValues.put("REORDER_AMOUNT",reorderAmount);
        db.insert("PRODUCT",null,productValues);
    }
    @Override
    public void onUpgrade(SQLiteDatabase dp, int i, int i1) {
        updateMyDatabase(dp,i,i1);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion,int newVersion){
        if(oldVersion < 1) {
            db.execSQL("CREATE TABLE PRODUCT ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT, "
                    + "STOCK_ON_HAND INTEGER,"
                    + "STOCK_IN_TRANSIT INTEGER,"
                    + "REORDER_QUANTITY INTEGER,"
                    + "REORDER_AMOUNT INTERGER,"
                    + "PRICE DOUBLE);");
//                insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
//                insertDrink(db, "Cappucino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino);
//                insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if(oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVOURITE NUMERIC;");
        }
    }
}
