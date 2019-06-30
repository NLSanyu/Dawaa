package com.example.android.dawaa;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Lydia on 04-Apr-18.
 */

public class PharmAdapter extends RecyclerView.Adapter<PharmAdapter.PharmViewHolder> {

    //private String[] mDataset;
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    //constructor for PharmAdapter class
    public PharmAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    //inner class (ViewHolder)
    public static class PharmViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_Pharm, tv_Empty;

        //constructor for ViewHolder class
        public PharmViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_Pharm = itemView.findViewById(R.id.tv_med_item);
            tv_Empty = itemView.findViewById(R.id.tv_med_freq_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }


    @Override
    public PharmAdapter.PharmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.med_element_for_rv, parent, false);
        return new PharmViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(PharmAdapter.PharmViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.PharmacistsList.COLUMN_PHARMACIST_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(MedicinesContract.PharmacistsList._ID)); //to get the id (primary key) of that row in the database

        holder.tv_Pharm.setText(name);
        holder.tv_Empty.setText(" ");
        holder.itemView.setTag(id); //for the RecyclerView to hold onto the id

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            // Force the RecyclerView to refresh
            notifyDataSetChanged();
        }
    }

}