package com.example.satyam.inventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Addquantity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quantity_layout);
        Bundle bundle=getIntent().getExtras();
        String prodname=bundle.getString("itemname");
//        TextView textView =findViewById(R.id);
//        textView.setText(prodname);

    }
}
