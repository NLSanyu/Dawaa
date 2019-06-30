package com.example.android.dawaa;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lydia on 16-Mar-18.
 */

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.MedViewHolder> {

    private static Context mContext;
    private Cursor mCursor;
    public static int db_id;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    //constructor for MedAdapter class
    public MedAdapter(Context context, Cursor cursor){
        this.mContext = context;
        this.mCursor = cursor;
    }

    //inner class (ViewHolder)
    public static class MedViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_Med;
        public TextView tv_Freq;

        //constructor for ViewHolder class
        public MedViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            tv_Med = itemView.findViewById(R.id.tv_med_item);
            tv_Freq = itemView.findViewById(R.id.tv_med_freq_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);

                            db_id = (int) v.getTag();
                            Toast.makeText(mContext, "Med clicked from constructor", Toast.LENGTH_LONG).show();

                            Intent MedicineIntent = new Intent(mContext, MedicineActivity.class);
                            MedicineIntent.putExtra("db_id", db_id);
                            mContext.startActivity(MedicineIntent);

                        }
                    }
                }
            });
        }
    }



    @Override
    public MedAdapter.MedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.med_element_for_rv, parent, false);
        return new MedViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(MedAdapter.MedViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){ return; }
        String name = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.MedicinesList.COLUMN_MEDICINE_NAME));
        String freq = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.MedicinesList.COLUMN_MEDICINE_DOSAGE));
        //String freq2 = freq + " " + R.string.times_per_day;
        int id = mCursor.getInt(mCursor.getColumnIndex(MedicinesContract.MedicinesList._ID)); //to get the id (primary key) of that row in the database

        holder.tv_Med.setText(name);
        holder.tv_Freq.setText(freq);
        holder.itemView.setTag(id); //for the RecyclerView to hold onto the id


    }

    @Override
    public int getItemCount()
    {
        return mCursor.getCount();
    }


    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
        // Force the RecyclerView to refresh
         notifyDataSetChanged();
        }
    }
}
