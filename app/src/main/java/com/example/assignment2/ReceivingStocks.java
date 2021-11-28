package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ReceivingStocks extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_stocks);
        populateSpinner();
    }

    public void populateSpinner(){
        SQLiteOpenHelper DBHelper = new ProductDatabaseHelper(this);
        try {
            db = DBHelper.getReadableDatabase();
            c = db.rawQuery("SELECT * FROM PRODUCT", null);
            ArrayList<String> prods = new ArrayList<>();
            if(c.moveToFirst()){
                do {
                    prods.add(c.getString(1));
                }while (c.moveToNext());
            }
            ArrayAdapter<String> listOfItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prods);
            Spinner spinner = (Spinner) findViewById(R.id.R_items_spinner);
            spinner.setAdapter(listOfItems);

        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}