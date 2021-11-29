package com.example.assignment2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class ProductAPI {


    public  String SQLiteToJson(Context context){
        ArrayList<HashMap<String,String>> dirtyProductList;
        dirtyProductList = new ArrayList<HashMap<String,String>>();
        String query = "SELECT * FROM PRODUCT where DIRTY = 1";
        SQLiteOpenHelper productHelper = new ProductDatabaseHelper(context);
        SQLiteDatabase database = productHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("_id",cursor.getString(0));
                map.put("NAME",cursor.getString(1));
                map.put("STOCK_ON_HAND",cursor.getString(2));
                map.put("STOCK_IN_TRANSIT",cursor.getString(3));
                map.put("PRICE",cursor.getString(4));
                map.put("RECORDER_QUANTITY",cursor.getString(5));
                map.put("RECORDER_AMOUNT",cursor.getString(6));
                dirtyProductList.add(map);
            }while(cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(dirtyProductList);
    }

    public void onSync(View view){
       // Toast.makeText(TopLevelActivity.this, "Attempting to sync...", Toast.LENGTH_LONG).show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("drinksJSON", SQLiteToJson(view.getContext()));
        client.post("https://producttest.free.beeceptor.com", params,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String byteToString = null;
                try {
                    byteToString = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
               // Toast.makeText(TopLevelActivity.this, "Successful response: " + byteToString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if(i == 404){
                    //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }else if(i == 500){
                    //Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }else{
                    //Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
