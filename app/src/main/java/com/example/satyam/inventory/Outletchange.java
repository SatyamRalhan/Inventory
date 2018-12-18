package com.example.satyam.inventory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.satyam.inventory.misc.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Outletchange extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    SharedPreferences preferences;
    LinearLayoutCompat layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outletchange);
        preferences=getSharedPreferences(BuildConfig.APPLICATION_ID,this.MODE_PRIVATE);
        ArrayList<String> list=new ArrayList<>();
        try{
            Log.d("sharedprefcheck",preferences.getString("outlets",null));
            JSONArray jsonArray=new JSONArray(preferences.getString("outlets",null));
            for(int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getJSONObject(i).getString("name"));
            }
        }
        catch (JSONException e){}
        layout=findViewById(R.id.outlet_layout);
        recyclerView=new RecyclerView(Outletchange.this);
        manager=new LinearLayoutManager(Outletchange.this);
        adapter =new MyAdapter(list.toArray(new String[0]));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        layout.addView(recyclerView);

    }
}
