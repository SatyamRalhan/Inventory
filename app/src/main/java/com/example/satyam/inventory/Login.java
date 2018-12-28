package com.example.satyam.inventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.satyam.inventory.misc.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    TextInputEditText user,pass;
    Button login;
    VolleyController volleyController;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences=getSharedPreferences(BuildConfig.APPLICATION_ID,this.MODE_PRIVATE);
        editor=preferences.edit();
        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(user.length()==0||pass.length()==0) {
                        Toast.makeText(Login.this, "The required fields are empty", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        JSONObject object = new JSONObject();
                        object.put("username", user.getText().toString());
                        object.put("password", pass.getText().toString());
                        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                                (Request.Method.POST, getString(R.string.sign_in), object, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("responsecheck", "getting response");
                                        try {
                                            editor.putString("b_id", response.getString("_id"));
                                            editor.apply();
                                        } catch (JSONException e) {
                                        }
                                        intent=new Intent(Login.this,Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override

                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(Login.this, "Given credentials do not match", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                        volleyController = VolleyController.getInstance(Login.this);
                        volleyController.addToRequestQueue(jsonObjRequest);

                    }
                }
                catch (JSONException e){}
            }
        });
    }
}
