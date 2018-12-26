package com.example.satyam.inventory;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.example.satyam.inventory.misc.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Outletchange extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    DividerItemDecoration divider;
    SharedPreferences preferences;
    LinearLayoutCompat layout;
    TextView toolbartext;
    AppCompatImageButton button;
    JSONArray jsonArray;
    ArrayList<String> list;
    SearchView searchView;
    SearchView.OnQueryTextListener textlistener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            doMySearch(s);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            doMySearch(s);
            return false;
        }
    };
    View.OnClickListener backlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outletchange);
        Toolbar toolbar = findViewById(R.id.tb1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        button = toolbar.findViewById(R.id.back_button);
        button.setOnClickListener(backlistener);
        toolbartext = findViewById(R.id.searchbartext);
        toolbartext.setText("Select Outlet");
        preferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        list = new ArrayList<>();
        try{
            Log.d("sharedprefcheck",preferences.getString("outlets",null));
            jsonArray = new JSONArray(preferences.getString("outlets", null));
            for(int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getJSONObject(i).getString("name"));
            }
        }
        catch (JSONException e){}
        layout=findViewById(R.id.outlet_layout);
        recyclerView=new RecyclerView(Outletchange.this);
        manager=new LinearLayoutManager(Outletchange.this);
        divider=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        adapter =new MyAdapter(list.toArray(new String[0]));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);
        layout.addView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.searchitem).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(textlistener);
        return true;
    }

    private void doMySearch(String query) {
        list = new ArrayList<String>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("name").toLowerCase().contains(query.toLowerCase())) {
                    list.add(jsonArray.getJSONObject(i).getString("name"));
                }
            }
        } catch (JSONException e) {
        }
        adapter = new MyAdapter(list.toArray(new String[0]));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        if (searchView.isIconified()) {
            super.onBackPressed();
        } else {
            searchView.setIconified(true);
        }
    }
}
