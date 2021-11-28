package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class TopLevelView extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toplevelview);
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.topViewOptions));
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
//                    Toast.makeText(getApplicationContext(), "First Item", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TopLevelView.this, ReceivingStocks.class);
                    startActivity(intent);
                }
                if (i == 1){
//                    Toast.makeText(getApplicationContext(), "Second Item", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TopLevelView.this, OrderingStocks.class);
                    startActivity(intent);
                }
            }
        });

//        SQLiteOpenHelper DBHelper = new ProductDatabaseHelper(this);
//        try {
//            db = DBHelper.getReadableDatabase();
//            c = db.rawQuery("SELECT * FROM PRODUCT", null);
//            ArrayList<String> prods = new ArrayList<>();
//            if(c.moveToFirst()){
//                do {
//                    prods.add(c.getString(1) + "|" + c.getString(2));
//                }while (c.moveToNext());
//            }
//            ArrayAdapter<String> temp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prods);
//            ListView listView1 = (ListView)findViewById(R.id.tempList);
//            listView1.setAdapter(temp);
//
//        }catch (SQLiteException e){
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }


}