package com.example.satyam.inventory.misc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satyam.inventory.Addquantity;
import com.example.satyam.inventory.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {
    private JSONArray mDataset;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public CardViewAdapter(JSONArray myValues) {
        mDataset = myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchviewgroup, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {

            String images75=holder.cardView.getContext().getString(R.string.images75);
            final JSONObject jsonObject = mDataset.getJSONObject(position);
            holder.name.setText(jsonObject.getString("name"));
            holder.base.setText(jsonObject.getString("baseunit"));
            holder.current.setText(jsonObject.getString("currentstock"));
            holder.skucode.setText(jsonObject.getString("skucode"));
            final String url=jsonObject.getString("url");
            Picasso.get()
                    .load(images75 + url)
                    .resize(40, 40)
                    .centerCrop().placeholder(R.drawable.delete_black)
                    .into(holder.imageView);

            holder.cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    TextView name = view.findViewById(R.id.text_cardview);
                    TextView sku = view.findViewById(R.id.skuproductcode);
                    TextView currentstock = view.findViewById(R.id.product_currentstock);
                    TextView baseunit = view.findViewById(R.id.product_baseunit);
                    Intent intent = new Intent(context, Addquantity.class);
                    intent.putExtra("name", name.getText());
                    intent.putExtra("skucode", sku.getText());
                    intent.putExtra("currentstock", currentstock.getText());
                    intent.putExtra("baseunit", baseunit.getText());
                    intent.putExtra("url",url);
                    try {
                        intent.putExtra("units",jsonObject.getString("units"));
                        intent.putExtra("_id", jsonObject.getString("_id"));
                    } catch (JSONException e) {
                    }
                    context.startActivity(intent);

                }
            });
        } catch (Exception e) {
            Log.e("Card View Error", e.getMessage());
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
        private RelativeLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_cardview);
            imageView = itemView.findViewById(R.id.productimage);
            skucode = itemView.findViewById(R.id.skuproductcode);
            current = itemView.findViewById(R.id.product_currentstock);
            base = itemView.findViewById(R.id.product_baseunit);
            cardView = itemView.findViewById(R.id.cardview_search);
//            itemView.setOnClickListener();
        }


    }
}