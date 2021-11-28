package com.example.assignment2;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutputView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutputView extends Fragment {
    private SQLiteDatabase db;
    private Cursor c;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OutputView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OutputView.
     */
    // TODO: Rename and change types and number of parameters
    public static OutputView newInstance(String param1, String param2) {
        OutputView fragment = new OutputView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View RootView = inflater.inflate(R.layout.fragment_output_view, container, false);

        SQLiteOpenHelper DBHelper = new ProductDatabaseHelper(getContext());
        try {
            db = DBHelper.getReadableDatabase();
            c = db.rawQuery("SELECT * FROM PRODUCT", null);
            ArrayList<String> prods = new ArrayList<>();
            if(c.moveToFirst()){
                do {
                    //prods.add(c.getString(1));
                    String valu = String.valueOf(c.getDouble(6)*c.getInt(2));
                    String inTransValu = String.valueOf(c.getDouble(6)*c.getInt(3));
                    prods.add("Name: "+c.getString(1) + "\nStock On Hand: " + c.getString(2) + "\nStock In Transit: " + c.getString(3) + "\nReorder Amount: " + c.getString(5) + "\nReorder Quantity: " + c.getString(4) + "\nValuation: $" + valu + "\nIn-Transit Valuation: $" + inTransValu);
                }while (c.moveToNext());
            }

            ArrayAdapter<String> temp = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, prods);
            ListView listView1 = (ListView) RootView.findViewById(R.id.frag_listOfItems);
            listView1.setAdapter(temp);

        }catch (SQLiteException e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return RootView;
    }

}