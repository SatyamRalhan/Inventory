package com.example.satyam.inventory.misc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.satyam.inventory.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {
    private JSONArray mDataset;

    public CardViewAdapter(JSONArray myValues) {
        mDataset = myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewgroup, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject jsonObject = mDataset.getJSONObject(position);
            holder.name.setText(jsonObject.getString("name"));
            holder.base.setText(jsonObject.getString("baseunit"));
            holder.current.setText(jsonObject.getString("currentstock"));
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
        private TextView name;
        private ImageView imageView;
        private TextView skucode;
        private TextView current;
        private TextView base;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_cardview);
            imageView = itemView.findViewById(R.id.productimage);
            skucode = itemView.findViewById(R.id.skuproductcode);
            current = itemView.findViewById(R.id.product_currentstock);
            base = itemView.findViewById(R.id.product_baseunit);
        }
    }
}