package com.example.satyam.inventory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.satyam.inventory.misc.Reviewcartadapter;
import com.example.satyam.inventory.misc.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cartreview extends AppCompatActivity {
    VolleyController volleyController;
    TextView titletext;
    RecyclerView recyclerView;
    LinearLayoutCompat layout;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    SharedPreferences preferences;
    JSONArray array;
    AppCompatImageButton button;
    SearchView searchView;
    Button button1;
    View.OnClickListener backlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };
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

    View.OnClickListener finishlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            JSONObject inventorylog = new JSONObject();
            try {
                inventorylog.put("referenceNo", preferences.getString("refno", null));
                inventorylog.put("outlet", preferences.getString("outlet_id", null));
                inventorylog.put("business", preferences.getString("b_id", null));
                inventorylog.put("reason", preferences.getString("response", null));
                inventorylog.put("lineItems", new JSONArray(preferences.getString("Cartitems", "null")));
                inventorylog.put("dateOfLog", preferences.getString("dateoflog", null));
                inventorylog.put("dayOfLog", preferences.getString("dayoflog", null));
            } catch (JSONException e) {
            }
            Log.d("postobjectcheck", inventorylog.toString());
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.POST, getString(R.string.inventorypost), inventorylog, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Inventorycheck", "Inventory log checked");
                            Intent intent = new Intent(Cartreview.this, Home.class);
                            startActivity(intent);
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }
                    );
            volleyController.addToRequestQueue(request);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartreview);
        Toolbar toolbar = findViewById(R.id.tb1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        volleyController = VolleyController.getInstance(this);
        titletext = findViewById(R.id.searchbartext);
        titletext.setText("Cart Items");
        layout = findViewById(R.id.cartlayout);
        preferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        button1 = findViewById(R.id.finishinventory);
        button1.setOnClickListener(finishlistener);
        button = findViewById(R.id.back_button);
        button.setOnClickListener(backlistener);
        recyclerView = new RecyclerView(this);
        manager = new LinearLayoutManager(this);
        DividerItemDecoration divider=new DividerItemDecoration(Cartreview.this,DividerItemDecoration.VERTICAL);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(divider);
        recyclerView.setId(R.id.recyclerviewid);
        try {
            if (!(preferences.getString("Cartitems", null) == null)) {
                array = new JSONArray(preferences.getString("Cartitems", null));
                adapter = new Reviewcartadapter(array);
                recyclerView.setAdapter(adapter);
                layout.addView(recyclerView);
            } else {
                Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        try {
            if (!(preferences.getString("Cartitems", null) == null)) {
                array = new JSONArray(preferences.getString("Cartitems", null));
                adapter = new Reviewcartadapter(array);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isIconified()) {
            Intent intent = new Intent(Cartreview.this, Searchable.class);
            startActivity(intent);
        } else {
            searchView.setIconified(true);
        }

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
        try {
            if (!(preferences.getString("Cartitems", null) == null)) {
                JSONArray parent = new JSONArray(preferences.getString("Cartitems", null));
                array = new JSONArray();
                for (int i = 0; i < parent.length(); i++) {
                    if (parent.getJSONObject(i).getString("name").toLowerCase().contains(query.toLowerCase())) {
                        array.put(parent.getJSONObject(i));
                    }
                }
                resetRecycler(array);

            } else {
                Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
        }
    }

    private void resetRecycler(JSONArray array) {
        adapter = new Reviewcartadapter(array);
        recyclerView.setAdapter(adapter);
    }

    public void init() {
        finish();
        startActivity(getIntent());
    }
}
