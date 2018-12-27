package com.example.satyam.inventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.satyam.inventory.misc.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    Button textView;
    boolean inventorytaken;
    ImageView right;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    BottomNavigationView.OnNavigationItemSelectedListener bottomnavlistener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case(R.id.inventory):
                    intent=new Intent(Home.this,Inventoryhome.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    VolleyController volleyController;
    JSONArray jsonArray;
    /////////////////////////////////
    View.OnClickListener changeoutlet=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent outlet_change=new Intent(Home.this,Outletchange.class);
            startActivity(outlet_change);
        }
    };
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
///////////////Code For Nav Bar
    View.OnClickListener navlistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawerLayout.openDrawer(navigationView);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageButton imageButton=findViewById(R.id.ib1);
        findViewById(R.id.bottom1).findViewById(R.id.inventory).setSelected(false);
        findViewById(R.id.bottom1).findViewById(R.id.home).setSelected(true);
        findViewById(R.id.bottom1).findViewById(R.id.stocktransfer).setSelected(false);
        preferences = this.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        editor=preferences.edit();
        editor.remove("Cartitems");
        editor.apply();
        drawerLayout = findViewById(R.id.dwlay1);
        navigationView = findViewById(R.id.navview1);
        imageButton.setOnClickListener(navlistener);
        inventorytaken=false;
        IfInventoryTaken();
        bottomNavigationView=findViewById(R.id.bottom1);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomnavlistener);
        textView=findViewById(R.id.outlet_name);
        ///////////////////////////////  SETTING UP FOR LOGIN///////////////
        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username","+91-9999777777");
            jsonObject.put("password","applemango");
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, getString(R.string.sign_in), jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("responsecheck", "getting response");
                        try {
                            editor.putString("b_id", response.getString("_id"));
                            editor.apply();
                        } catch (JSONException e) {
                        }
                        JsonArrayRequest jsonObjRequest1 = new JsonArrayRequest
                                (Request.Method.GET, getString(R.string.outlets), null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray respons) {
                                        try {
                                            if (preferences.getBoolean("firstrun", true)) {
                                                editor.putString("Currentoutlet", respons.getJSONObject(0).getString("name"));
                                            }
                                            editor.putBoolean("firstrun", false);
                                        } catch (JSONException e) {
                                        }
                                        editor.putString("outlets", respons.toString());
                                        editor.apply();
                                        Log.d("responsetostring", respons.toString());
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error1) {
                                                Log.e("response error", error1.getMessage());
                                            }
                                        });
                        volleyController.addToRequestQueue(jsonObjRequest1);
                    }
                },
                        new Response.ErrorListener() {
                            @Override

                            public void onErrorResponse(VolleyError error) {
                            }
                        });
        volleyController = VolleyController.getInstance(Home.this);
        volleyController.addToRequestQueue(jsonObjRequest);

        textView.setOnClickListener(changeoutlet);
        textView.setText(preferences.getString("Currentoutlet", null));
    }

/////////////////////////    onCreate ends /////////////////////
    @Override
    public void onBackPressed() {
        if (navigationView.hasFocus()) {
            drawerLayout.closeDrawer(navigationView);
        }
        else {
            super.onBackPressed();
        }
    }

//////////////////////////////////////
    public void IfInventoryTaken(){
        right=findViewById(R.id.done1);

        if(inventorytaken){
            right.setBackgroundColor(Color.parseColor("#0aa60a"));
            right.setImageResource(R.drawable.done_white);
        }
        else{
            right.setBackgroundColor(Color.parseColor("#ce0a0b"));
            right.setImageResource(R.drawable.close_white);
        }
    }


}
