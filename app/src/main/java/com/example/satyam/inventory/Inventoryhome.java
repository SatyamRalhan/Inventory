package com.example.satyam.inventory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.satyam.inventory.misc.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Inventoryhome extends AppCompatActivity {
    AppBarLayout appbar;
    Calendar c=Calendar.getInstance();
    Date date=c.getTime();
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    SimpleDateFormat dateoflogformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    String final_date=dateFormat.format(date);
    SimpleDateFormat dateview=new SimpleDateFormat("dd-MM-yyyy");
    TextInputLayout textInputLayout;
    Boolean backpressed;
    Button b1;
    Button b,b2;
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case(R.id.button2):
                    b.setSelected(true);
                    b.setTextColor(getColor(R.color.white));
                    b2.setSelected(false);
                    b2.setTextColor(getColor(R.color.primary_text));
                    break;
                case(R.id.button3):
                    b2.setSelected(true);
                    b2.setTextColor(getColor(R.color.white));
                    b.setSelected(false);
                    b.setTextColor(getColor(R.color.primary_text));
                    break;
            }


        }
    };
    TextInputEditText refno;
    EditText textInputEditText1, textInputEditText;
    String store;
    VolleyController volleyController;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String currentoutlet;
    String currentoutletid;
    TextView outlet_name;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    String strorereverse;
    BottomNavigationView.OnNavigationItemSelectedListener bottomnavlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case (R.id.home):
                    final Dialog dialog=new Dialog(Inventoryhome.this);
                    dialog.setContentView(R.layout.alert_layout);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            intent = new Intent(Inventoryhome.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    break;
            }
            return false;
        }
    };
/////////////////   Next Activity button    ////////////////////////
    View.OnClickListener searchlistener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

//            radioButton = findViewById(radiogroup.getCheckedRadioButtonId());
//            editor.putString("response", radioButton.getText().toString());
            if(b.isSelected()){
                editor.putString("response","New Order");
            }
            else{
                editor.putString("response","Closing");
            }
            if (refno.getText().toString().matches("")) {
                editor.putString("refno", refno.getText().toString());
            }
            editor.apply();
            Intent intent=new Intent(Inventoryhome.this,Searchable.class);
            startActivity(intent);
            finish();
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
                    editor.putString("dayoflog", year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "T00:00:00Z");
                    editor.apply();

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

            final DatePickerDialog datePickerDialog = new DatePickerDialog(Inventoryhome.this,new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    store=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    strorereverse = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                }
            }, mYear, mMonth, mDay);


            TimePickerDialog timePickerDialog = new TimePickerDialog(Inventoryhome.this,
                    new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    String min, hr, dateoflog, a;
                    if (i1 < 10) {
                        min = "0" + i1;
                    } else {
                        min = Integer.toString(i1);
                    }
                    if (i < 10) {
                        hr = "0" + i;
                    } else {
                        hr = Integer.toString(i);
                    }
                    dateoflog = strorereverse + "T" + hr + ":" + min + ":00Z";
                    editor.putString("dateoflog", dateoflog);
                    editor.apply();
                    if (i == 0) {
                        i = 12;
                        a = "am";
                    } else if (i < 12) {
                        a = "am";
                    } else if (i == 12) {
                        a = "pm";
                    } else {
                        i = i - 12;
                        a = "pm";
                    }
                    if (i < 10) {
                        store = store + " 0" + i + ":" + min + a;
                    } else {
                        store = store + " " + i + ":" + min + a;
                    }
                    textInputEditText.setText(store);
                    datePickerDialog.show();

                }
            },hour,minute,false);

            timePickerDialog.show();

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
        backpressed=false;
        findViewById(R.id.bottom2).findViewById(R.id.inventory).setSelected(true);
        findViewById(R.id.bottom2).findViewById(R.id.home).setSelected(false);
        findViewById(R.id.bottom2).findViewById(R.id.stocktransfer).setSelected(false);
        b=findViewById(R.id.button2);
        b.setSelected(false);
        b.setBackgroundResource(R.drawable.button_left);
        b2=findViewById(R.id.button3);
        b2.setBackgroundResource(R.drawable.button_right);
        b2.setSelected(true);
        b2.setTextColor(getColor(R.color.white));
        b.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        refno = findViewById(R.id.inventoryedit3);
        preferences = this.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        editor=preferences.edit();
        bottomNavigationView = findViewById(R.id.bottom2);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomnavlistener);
        String outlets=preferences.getString("outlets",null);
        String currentoutlet = preferences.getString("Currentoutlet", null);
        outlet_name = findViewById(R.id.outlet_name);
        outlet_name.setText(currentoutlet);
        try {
            JSONArray jsonArray = new JSONArray(outlets);
            for (int i = 0; i < jsonArray.length(); i++) {
                if (currentoutlet.equals(jsonArray.getJSONObject(i).getString("name"))) {
                    currentoutletid = jsonArray.getJSONObject(i).getString("_id");
                    editor.putString("outlet_id", currentoutletid);
                    editor.apply();
                    Log.d("IDCHECKING", currentoutletid);
                }
            }

        } catch (JSONException e) {
            Log.d("exceptionError", e.getMessage());
        }
/////////////////////   Side Navigation   //////////////////////////
        ImageButton imageButton=findViewById(R.id.ib1);
        drawerLayout = findViewById(R.id.dwlay2);
        navigationView = findViewById(R.id.navview2);
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
        editor.putString("dateoflog", dateoflogformat.format(date));
        editor.putString("dayoflog", dateoflogformat.format(date));
        editor.apply();
////////////////////////////////////////////////////////////////////
////////////////////    Next Activity    ///////////////////////////
        b1=findViewById(R.id.inventorybutton1);
        b1.setOnClickListener(searchlistener);
////////////////////////////////////////////////////////////////////
        String url = getString(R.string.outletproducts) + currentoutletid;
        Log.d("checkingurl", url);
        JsonArrayRequest jsonObjRequest1 = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray outletproducts)
                    {
                        editor.putString("outletproducts", outletproducts.toString());
                        editor.apply();
                        Log.d("secondresponsechecking","getting response");
                    }
                },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error1)
                {
//                    Log.e("response error",error1.getMessage());
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
            if(backpressed){
                finish();
            }
            else{
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                backpressed=true;
            }
            Log.d("fornavbar","the if is not doing the work");
        }
    }
////////////////////////////////////////////////////////////////////


}
