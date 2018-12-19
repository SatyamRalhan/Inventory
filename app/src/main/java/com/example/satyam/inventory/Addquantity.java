package com.example.satyam.inventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Addquantity extends AppCompatActivity {
    MaterialCardView cardView;
    TextView name, currentstock, unit;
    EditText quantity;
    Button additem;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Bundle bundle;
    View.OnClickListener additemlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                JSONObject object = new JSONObject();
                object.put("name", bundle.getString("name"));
                object.put("skucode", bundle.getString("skucode"));
                object.put("quantity", quantity.getText().toString() + unit.getText().toString());
                String cart = preferences.getString("Cartitems", null);
                JSONArray array;
                if (!(cart == null)) {
                    array = new JSONArray(cart);

                } else {
                    Log.d("Nullcart", "yes");
                    array = new JSONArray();
                }
                array.put(object);
                editor.putString("Cartitems", array.toString());
                editor.apply();
            } catch (JSONException e) {
            }
            Toast.makeText(Addquantity.this, "Item Added To the the cart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Addquantity.this, Searchable.class);
            startActivity(intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quantity_layout);
        bundle = getIntent().getExtras();
        String prodname = bundle.getString("name");
        String prodstock = bundle.getString("currentstock") + " " + bundle.getString("baseunit");
        preferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        editor = preferences.edit();
        quantity = findViewById(R.id.quantityedit);
        unit = findViewById(R.id.unit);
        cardView = findViewById(R.id.itemcard);
        name = cardView.findViewById(R.id.setproductname);
        currentstock = cardView.findViewById(R.id.currentstock);
        additem = findViewById(R.id.additembutton);
        additem.setOnClickListener(additemlistener);
        name.setText(prodname);
        currentstock.setText(prodstock);
        Log.d("Checkextras", prodname);
//        TextView textView =findViewById(R.id);
//        textView.setText(prodname);

    }
}
