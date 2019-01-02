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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.satyam.inventory.misc.CardViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Searchable extends AppCompatActivity {
    TextView toolbartext;
    RecyclerView listView;
    LinearLayoutCompat linearLayoutCompat;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    AppCompatImageButton back;
    SearchView searchView;
    String productsdone;
    RelativeLayout relativeLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONArray outletproduct, inforforadapter;
    Button reviewcart;
    SearchView.OnQueryTextListener textlistener=new SearchView.OnQueryTextListener() {
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
    View.OnClickListener backlistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(searchView.isIconified()){
                Intent intent = new Intent(Searchable.this, Inventoryhome.class);
                startActivity(intent);
            }
            else{
                searchView.setIconified(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        toolbartext = findViewById(R.id.searchbartext);
        toolbartext.setText("Select Item");
        //////////////////////////////
        reviewcart = findViewById(R.id.reviewcartbutton);
        reviewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Searchable.this, Cartreview.class);
                startActivity(intent);
            }
        });
        //////////////////////////////
        preferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        /////////////// Search Bar
        Toolbar toolbar = findViewById(R.id.tb1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        //////////////////
        relativeLayout = findViewById(R.id.addquantityscreen);
        relativeLayout.setVisibility(View.GONE);
        ///////fuctionality to back button
        back = findViewById(R.id.back_button);
        back.setOnClickListener(backlistener);
        productsdone = preferences.getString("productsdone", "null");
        Log.d("productsdonesearch",productsdone);
        //////////////////
        /////////////////
        try {

            outletproduct = new JSONArray(preferences.getString("outletproducts", null));
            inforforadapter = new JSONArray();
            if(productsdone.equals("null")) {
                for (int i = 0; i < outletproduct.length(); i++) {
                    inforforadapter.put(putit(i));
                }
            }
            else{
                JSONObject object=new JSONObject(productsdone);
                for (int i = 0; i < outletproduct.length(); i++) {
                    String name=outletproduct.getJSONObject(i).getJSONObject("product").getString("productTitle");
                    try {
                        Log.d("yesorno",object.getString(name));
                    }
                    catch (JSONException e){
                        inforforadapter.put(putit(i));
                    }
                }
            }
            Log.d("IFGETTININFO", inforforadapter.toString());
        } catch (JSONException e) {
        }
        listView = new RecyclerView(this);
        listView.setId(R.id.recyclerviewid);
        linearLayoutCompat = findViewById(R.id.linearlayout);
        linearLayoutCompat.addView(listView);
        layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider=new DividerItemDecoration(Searchable.this,DividerItemDecoration.VERTICAL);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(divider);
        adapter = new CardViewAdapter(inforforadapter);
        listView.setAdapter(adapter);
        if(preferences.getString("searchopened","no").equals("yes")){
            searchView.setIconified(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        searchView=(SearchView) menu.findItem(R.id.searchitem).getActionView();
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(textlistener);
        searchView.setQueryHint("Search Products");
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query){
//        if(preferences.getString("searchopened","yes").equals("no")){
//            editor.putString("searchopened","yes");
//            editor.apply();
//        }
        inforforadapter = new JSONArray();
        try {
            if(productsdone.equals("null")) {
                for (int i = 0; i < outletproduct.length(); i++) {
                    if (outletproduct.getJSONObject(i).getJSONObject("product").getString("productTitle").toLowerCase().contains(query.toLowerCase())) {
                        inforforadapter.put(putit(i));
                    }
                }
            }
            else{
                JSONObject object=new JSONObject(productsdone);
                for (int i = 0; i < outletproduct.length(); i++) {
                    String name=outletproduct.getJSONObject(i).getJSONObject("product").getString("productTitle");
                    try {
                        Log.d("yesorno",object.getString(name));
                    }
                    catch (JSONException e){
                        if (outletproduct.getJSONObject(i).getJSONObject("product").getString("productTitle").toLowerCase().contains(query.toLowerCase())) {
                            inforforadapter.put(putit(i));
                        }
                    }
                }
            }

        } catch (JSONException e) {
        }
        Log.d("resultchecking", inforforadapter.toString());
        adapter = new CardViewAdapter(inforforadapter);
        listView.setAdapter(adapter);
        // adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Searchable.this, Inventoryhome.class);
        startActivity(intent);
    }

    private JSONObject putit(int j) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", outletproduct.getJSONObject(j).getJSONObject("product").getString("productTitle"));
            object.put("baseunit", outletproduct.getJSONObject(j).getJSONObject("product").getString("baseUnit"));
            object.put("skucode", outletproduct.getJSONObject(j).getJSONObject("product").getString("skuProductCode"));
            object.put("currentstock", Integer.toString(outletproduct.getJSONObject(j).getInt("currentStock")));
            object.put("_id", outletproduct.getJSONObject(j).getString("_id"));
            object.put("url",outletproduct.getJSONObject(j).getJSONObject("product").getJSONArray("imagesURLs").optString(0,"noimage"));
//            Log.d("url",outletproduct.getJSONObject(j).getJSONObject("product").getJSONArray("imagesURLs").getString(0));
            object.put("units",outletproduct.getJSONObject(j).getJSONObject("product").getJSONArray("stockUnits").toString());
        } catch (JSONException e) {
        }
        return object;
    }
}
