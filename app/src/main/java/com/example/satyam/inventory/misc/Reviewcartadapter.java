package com.example.satyam.inventory.misc;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.satyam.inventory.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Reviewcartadapter extends RecyclerView.Adapter<Reviewcartadapter.MyViewHolder> {
    private JSONArray mDataset;

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
            JSONObject jsonObject = mDataset.getJSONObject(position);
            holder.name.setText(jsonObject.getString("name"));
            holder.quantity.setText(jsonObject.getString("quantity"));
            holder.skucode.setText(jsonObject.getString("skucode"));
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
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("ButtonDetected", "Yes");
                }
            });
        }


    }
}