package com.example.satyam.inventory;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.satyam.inventory.misc.VolleyController;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Inventoryhome extends AppCompatActivity {
    Calendar c=Calendar.getInstance();
    Date date=c.getTime();
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    String final_date=dateFormat.format(date);
    SimpleDateFormat dateview=new SimpleDateFormat("dd-MM-yyyy");
    TextInputLayout textInputLayout;
    Button b1;
    EditText textInputEditText1,textInputEditText;
    String store;
    VolleyController volleyController;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
/////////////////   Next Activity button    ////////////////////////
    View.OnClickListener searchlistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Inventoryhome.this,Searchable.class);
            startActivity(intent);
        }
    };
//////////////////////   Date and Time Selection ///////////////////
    View.OnClickListener datelistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog=new DatePickerDialog(Inventoryhome.this,new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    Log.d("idcompaison",Integer.toString(R.id.te2));
                    Log.d("idcompaison",Integer.toString(view.getId()));
                    textInputEditText1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    };
    View.OnClickListener datetimelistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog=new TimePickerDialog(Inventoryhome.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    if (i==0){
                        i=12;
                        store=store+" "+i+":"+i1+"am" ;
                    }
                    else if (i<12){
                        store=store+" "+i+":"+i1+"am" ;
                    }
                    else if (i==12){
                        store=store+" "+i+":"+i1+"pm" ;
                    }
                    else{
                        i=i-12;
                        store=store+" "+i+":"+i1+"pm" ;
                    }
                    textInputEditText.setText(store);
                }
            },hour,minute,false);

            DatePickerDialog datePickerDialog=new DatePickerDialog(Inventoryhome.this,new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    store=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                }
            }, mYear, mMonth, mDay);

            timePickerDialog.show();
            datePickerDialog.show();

        }
    };
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
////////////////////////////////////////////////////////////////////
///////////////     Code For Nav Bar   /////////////////////////////
    View.OnClickListener navlistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawerLayout.openDrawer(navigationView);
        }
    };
////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventoryhome);
        preferences=this.getSharedPreferences(BuildConfig.APPLICATION_ID,this.MODE_PRIVATE);
        editor=preferences.edit();
        String outlets=preferences.getString("outlets",null);
/////////////////////   Side Navigation   //////////////////////////
        ImageButton imageButton=findViewById(R.id.ib1);
        drawerLayout=(DrawerLayout)findViewById(R.id.dwlay2);
        navigationView=(NavigationView)findViewById(R.id.navview2);
        imageButton.setOnClickListener(navlistener);
////////////////////////////////////////////////////////////////////
////////////////////   taking date and time   //////////////////////
        textInputLayout=findViewById(R.id.textInputLayout3);
        textInputEditText =findViewById(R.id.te1);
        textInputEditText.setText(final_date);
        textInputEditText1=findViewById(R.id.te2);
        textInputEditText1.setText(dateview.format(date));
        textInputEditText.setOnClickListener(datetimelistener);
        textInputEditText1.setOnClickListener(datelistener);
////////////////////////////////////////////////////////////////////
////////////////////    Next Activity    ///////////////////////////
        b1=findViewById(R.id.inventorybutton1);
        b1.setOnClickListener(searchlistener);
////////////////////////////////////////////////////////////////////
        JsonArrayRequest jsonObjRequest1 = new JsonArrayRequest
                (Request.Method.GET, getString(R.string.user_info),null ,new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray respons)
                    {
                        Log.d("secondresponsechecking","getting response");
                    }
                },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error1)
                {
                    Log.e("response error",error1.getMessage());
                }
        });
        volleyController=VolleyController.getInstance(Inventoryhome.this);
        volleyController.addToRequestQueue(jsonObjRequest1);
    }
////////////////////////////////////////   onCreate Ends/////////////////
    @Override
    public void onBackPressed() {
        Log.d("fornavbar","It is entering");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(navigationView);
            Log.d("fornavbar","the function is not doing the work");
        }
        else {
            Log.d("fornavbar","the if is not doing the work");
            super.onBackPressed();
        }
    }
////////////////////////////////////////////////////////////////////


}
