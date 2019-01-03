package com.example.satyam.inventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Addquantity extends AppCompatActivity {
    Spinner spinner;
    ImageView image;
    ArrayList<String> array;
    String response,images75;
    TextView name, currentstock, error;
    EditText quantity;
    AppCompatImageButton button;
    Button additem;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Bundle bundle;
    View.OnClickListener additemlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            response = preferences.getString("response", null);

            try {
                String productsdone = preferences.getString("productsdone", "null");
                JSONObject object = new JSONObject();
                object.put("name", bundle.getString("name"));
                object.put("skucode", bundle.getString("skucode"));
                object.put("quantity", quantity.getText().toString() + spinner.getSelectedItem().toString());
                object.put("currentStock", bundle.getString("currentstock"));
                object.put("outletProduct", bundle.getString("_id"));
                object.put("url",bundle.getString("url"));

                String cart = preferences.getString("Cartitems", null);
                Log.d("checkingquantity", quantity.getText().toString());
                JSONArray array;
                if (quantity.getText().toString().matches("")) {
                    error.setVisibility(View.VISIBLE);
                } else {
                    if (response.toLowerCase().equals("closing")) {
                        object.put("stockAdjusted", Integer.toString(Integer.parseInt(quantity.getText().toString()) - Integer.parseInt(bundle.getString("currentstock"))));
                    } else {
                        object.put("stockAdjusted", quantity.getText().toString());
                    }
                    if (!(cart == null)) {
                        array = new JSONArray(cart);

                    } else {
                        Log.d("Nullcart", "yes");
                        array = new JSONArray();
                    }
                    array.put(object);
                    JSONObject object1;
                    if (productsdone.contains("{")) {
                        object1 = new JSONObject(productsdone);
                    } else {
                        object1 = new JSONObject();
                    }
                    object1.put(bundle.getString("name"), "yes");
                    editor.putString("productsdone", object1.toString());
                    Log.d("productedadded", preferences.getString("productsdone", null));
                    editor.putString("Cartitems", array.toString());
                    editor.apply();
                    Toast.makeText(Addquantity.this, "Item Added To the the cart", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Addquantity.this, Searchable.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
            }
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
        setContentView(R.layout.quantity_layout);
        images75=getString(R.string.images300);
        bundle = getIntent().getExtras();
        button = findViewById(R.id.back_buttonaddquantity);
        button.setOnClickListener(backlistener);
        try {
            JSONArray units = new JSONArray(bundle.getString("units"));
            array=new ArrayList<>();
            for (int i=0;i<units.length();i++){
                array.add(units.getJSONObject(i).getString("unit"));
            }
        }catch (JSONException e){}
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String prodname = bundle.getString("name");
        String prodstock = bundle.getString("currentstock") + " " + bundle.getString("baseunit");
        preferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        editor = preferences.edit();
        quantity = findViewById(R.id.quantityedit);
        error = findViewById(R.id.errormessage);
        error.setVisibility(View.INVISIBLE);
        name = findViewById(R.id.setproductname);
        image=findViewById(R.id.setproductimage);
        final String url=bundle.getString("url");
        Picasso.get()
                .load(images75+url)
                .resize(150, 150)
                .centerCrop().placeholder(R.drawable.noimage)
                .into(image);
        currentstock = findViewById(R.id.currentstock);
        additem = findViewById(R.id.additembutton);
        additem.setOnClickListener(additemlistener);
        name.setText(prodname);
        currentstock.setText(prodstock);
        Log.d("Checkextras", prodname);
//        TextView textView =findViewById(R.id);
//        textView.setText(prodname);

    }
}
