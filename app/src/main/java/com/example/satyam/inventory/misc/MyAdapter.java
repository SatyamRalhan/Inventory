package com.example.satyam.inventory.misc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.satyam.inventory.Addquantity;
import com.example.satyam.inventory.BuildConfig;
import com.example.satyam.inventory.Home;
import com.example.satyam.inventory.Searchable;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private String[] mDataset;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view

        MyViewHolder vh;

            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1 , parent, false);
            vh = new MyViewHolder(v);



        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void onClick(View view){}

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view instanceof TextView){
                preferences = view.getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, view.getContext().MODE_PRIVATE);
                editor = preferences.edit();
                if(view.getContext() instanceof Searchable){
                Intent intent=new Intent(view.getContext(),Addquantity.class);
                intent.putExtra("itemname",((TextView) view).getText());
                view.getContext().startActivity(intent);}
                else{
                    Intent intent=new Intent(view.getContext(),Home.class);
                    editor.putString("Currentoutlet", ((TextView) view).getText().toString());
                    editor.apply();
                    view.getContext().startActivity(intent);
                }
//                LayoutInflater mInflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                LinearLayoutCompat linearLayoutCompat=(LinearLayoutCompat) View.inflate(view.getContext(),R.layout.activity_searchable,null);
//                RelativeLayout relativeLayout=linearLayoutCompat.findViewById(R.id.addquantityscreen);
//                relativeLayout.setVisibility(View.VISIBLE);
//                TextView textView=linearLayoutCompat.findViewById(R.id.setproductname);
//                textView.setText("Yo");
                Log.d("onclickchecking",((TextView) view).getText().toString());
            }
        }
    }


}