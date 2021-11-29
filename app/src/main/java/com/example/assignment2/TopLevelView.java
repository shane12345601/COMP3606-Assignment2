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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class TopLevelView extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor c;
    private ProductAPI productAPI;

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

        Button btn = (Button) findViewById(R.id.syncBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPress(view);
            }
        });
    }

    public void onPress(View view){
        productAPI = new ProductAPI();
        int res;
        res = productAPI.onSync(view);
        if(res==200){
            Toast.makeText(this, "Successfully Synced", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Failed Sync", Toast.LENGTH_LONG).show();
        }
    }
}