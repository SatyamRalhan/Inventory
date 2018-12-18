package com.example.satyam.inventory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.satyam.inventory.misc.MyAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Searchable extends AppCompatActivity {
    RecyclerView listView;
    LinearLayoutCompat linearLayoutCompat;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] strings;
    ArrayList<String> result;
    AppCompatImageButton back;
    SearchView searchView;
    RelativeLayout relativeLayout;
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
                onBackPressed();
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
        /////////////// Search Bar
        Toolbar toolbar=findViewById(R.id.tb1);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        //////////////////
        relativeLayout=findViewById(R.id.addquantityscreen);
        relativeLayout.setVisibility(View.GONE);
        ///////fuctionality to back button
        back=findViewById(R.id.back_button);
        back.setOnClickListener(backlistener);
        //////////////////
        strings=new String[]{"search","copy","searching","copying"};
        result=new ArrayList<String>(Arrays.asList(strings));
        /////////////////
        listView=new RecyclerView(this);
        listView.setId(R.id.recyclerviewid);
        linearLayoutCompat=findViewById(R.id.linearlayout);
        linearLayoutCompat.addView(listView);
        layoutManager=new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
        adapter= new MyAdapter(result.toArray(new String[0]));
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        searchView=(SearchView) menu.findItem(R.id.searchitem).getActionView();
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(textlistener);
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

        result.clear();
        int i=strings.length;
        for(int j=0;j<i;j++){
            if(strings[j].contains(query)){
                result.add(strings[j]);
            }
        }
        Log.d("resultchecking",result.toString());
        adapter= new MyAdapter(result.toArray(new String[0]));
        listView.setAdapter(adapter);
        // adapter.notifyDataSetChanged();

    }

}
