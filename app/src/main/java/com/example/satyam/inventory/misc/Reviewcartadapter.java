package com.example.satyam.inventory.misc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satyam.inventory.BuildConfig;
import com.example.satyam.inventory.Cartreview;
import com.example.satyam.inventory.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Reviewcartadapter extends RecyclerView.Adapter<Reviewcartadapter.MyViewHolder> {
    private JSONArray mDataset;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Reviewcartadapter(JSONArray myValues) {
        mDataset = myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcartviewgroup, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            final Context context = holder.name.getContext();
            preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            editor = preferences.edit();
            String images75=holder.name.getContext().getString(R.string.images75);
            final Cartreview cartreview = new Cartreview();
            final JSONArray cart = new JSONArray(preferences.getString("Cartitems", null));
            final JSONObject jsonObject = mDataset.getJSONObject(position);
            holder.name.setText(jsonObject.getString("name"));
            holder.quantity.setText(jsonObject.getString("quantity"));
            holder.skucode.setText(jsonObject.getString("skucode"));
            final String url=jsonObject.getString("url");
            Picasso.get()
                    .load(images75+url)
                    .resize(40, 40)
                    .centerCrop().placeholder(R.drawable.noimage)
                    .into(holder.imageView);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Log.d("ButtonDetected", "Yes");
                        for (int i = 0; i < cart.length(); i++) {
                            if (jsonObject.getString("name").equals(cart.getJSONObject(i).getString("name"))) {
                                cart.remove(i);
                            }
                        }
                        Log.d("JsonObject",jsonObject.getString("name"));
                        JSONObject object=new JSONObject(preferences.getString("productsdone","null"));
                        object.remove(jsonObject.getString("name"));
                        editor.putString("productsdone",object.toString());
                        editor.putString("Cartitems", cart.toString());
                        editor.apply();
                        Log.d("cartvalue", cart.toString());
                        Intent intent = new Intent(context, Cartreview.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                        cartreview.finish();

                        Toast.makeText(view.getContext(),"Item "+jsonObject.getString("name")+"is deleted",Toast.LENGTH_SHORT).show();
                    } catch (JSONException er) {
                    }
                }
            });
        } catch (JSONException e) {
        }
//        holder.myTextView.setText(mDataset[position]);
    }


    @Override
    public int getItemCount() {
        return mDataset.length();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        private TextView name;
        private ImageView imageView;
        private TextView skucode;
        private TextView quantity;
        private AppCompatImageButton button;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_cardview);
            imageView = itemView.findViewById(R.id.productimage);
            skucode = itemView.findViewById(R.id.skuproductcode);
            quantity = itemView.findViewById(R.id.quantityinput);
            button = itemView.findViewById(R.id.deletebutton);

        }


    }
}