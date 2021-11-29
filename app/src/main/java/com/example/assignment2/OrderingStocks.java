package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderingStocks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SQLiteDatabase db;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_stocks);
        populateSpinner();

        Button btn = (Button) findViewById(R.id.updateButton_ordering);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPress(view);
            }
        });
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
            Spinner spinner = (Spinner) findViewById(R.id.O_items_spinner);
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(listOfItems);

        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        SQLiteOpenHelper DBHelper = new ProductDatabaseHelper(this);
        try {
            db = DBHelper.getReadableDatabase();
            c = db.rawQuery("SELECT * FROM PRODUCT WHERE _id = ?", new String[] { Integer.toString(i+1) });
            c.moveToFirst();
            TextView textView;
            textView = (TextView) findViewById(R.id.stockOnHand_ordering);
            textView.setText("Stock On Hand: "+c.getString(2));
            textView = (TextView) findViewById(R.id.stockInTransit_ordering);
            textView.setText("Stock In Transit: "+c.getString(3));
            textView = (TextView) findViewById(R.id.reorderQuantity_ordering);
            textView.setText("Reorder Quantity: "+c.getString(4));
            textView = (TextView) findViewById(R.id.reorderAmount_ordering);
            textView.setText("Reorder Amount: "+c.getString(5));

        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onPress(View view){
        Spinner spinner = (Spinner) findViewById(R.id.O_items_spinner);
        EditText editText = (EditText) findViewById(R.id.inputNumber_ordering);
        int spinnerPos = spinner.getSelectedItemPosition();
        if(editText.getText().toString().trim().length() > 0){

            SQLiteOpenHelper DBHelper = new ProductDatabaseHelper(this);
            try {
                db = DBHelper.getReadableDatabase();

                int numberEntered = Integer.parseInt(editText.getText().toString());
                int itemSelected = spinner.getSelectedItemPosition();
                ContentValues cv = new ContentValues();

                c = db.rawQuery("SELECT STOCK_IN_TRANSIT FROM PRODUCT WHERE _id = ?", new String[] { Integer.toString(spinnerPos+1) });
                c.moveToFirst();
                int newValue2 = c.getInt(0) + numberEntered;
                cv.put("STOCK_IN_TRANSIT", newValue2);

                db.update("PRODUCT", cv, "_id = ?", new String[]{Integer.toString(itemSelected+1)});
                this.recreate();
            }catch (SQLiteException e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}