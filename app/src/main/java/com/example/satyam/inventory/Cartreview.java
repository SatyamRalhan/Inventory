package com.example.satyam.inventory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.satyam.inventory.misc.Reviewcartadapter;

import org.json.JSONArray;
import org.json.JSONException;

public class Cartreview extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutCompat layout;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    SharedPreferences preferences;
    JSONArray array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartreview);
        layout = findViewById(R.id.cartlayout);
        preferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        try {
            array = new JSONArray(preferences.getString("Cartitems", null));
        } catch (JSONException e) {
        }
        recyclerView = new RecyclerView(this);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new Reviewcartadapter(array);
        recyclerView.setAdapter(adapter);
        layout.addView(recyclerView);
    }
}
