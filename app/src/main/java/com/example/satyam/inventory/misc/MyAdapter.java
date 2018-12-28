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
import com.example.satyam.inventory.R;
import com.example.satyam.inventory.Searchable;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private String[] mDataset;


    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View listview=LayoutInflater.from(parent.getContext())  .inflate(R.layout.outlet_show,parent,false);
        return (new MyViewHolder(listview));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        private TextView mTextView;
        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.new_text);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            preferences = view.getContext().getSharedPreferences(BuildConfig.APPLICATION_ID, view.getContext().MODE_PRIVATE);
            editor = preferences.edit();
            if(view.getContext() instanceof Searchable){
            Intent intent=new Intent(view.getContext(),Addquantity.class);
            intent.putExtra("itemname",((TextView) view.findViewById(R.id.new_text)).getText());
            view.getContext().startActivity(intent);
            ((Searchable) view.getContext()).finish();}
            else{
                Intent intent=new Intent(view.getContext(),Home.class);
                editor.putString("Currentoutlet", ((TextView) view.findViewById(R.id.new_text)).getText().toString());
                editor.apply();
                view.getContext().startActivity(intent);
            }
//                LayoutInflater mInflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                LinearLayoutCompat linearLayoutCompat=(LinearLayoutCompat) View.inflate(view.getContext(),R.layout.activity_searchable,null);
//                RelativeLayout relativeLayout=linearLayoutCompat.findViewById(R.id.addquantityscreen);
//                relativeLayout.setVisibility(View.VISIBLE);
//                TextView textView=linearLayoutCompat.findViewById(R.id.setproductname);
//                textView.setText("Yo");
            Log.d("onclickchecking",((TextView) view.findViewById(R.id.new_text)).getText().toString());

        }
    }


}