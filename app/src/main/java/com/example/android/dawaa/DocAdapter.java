package com.example.android.dawaa;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Lydia on 04-Apr-18.
 */

public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocViewHolder> {

    private static Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;
    public static String name, spec, email, telno, address;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    //constructor for DocAdapter class
    public DocAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    //inner class (ViewHolder)
    public static class DocViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_Doc;
        public TextView tv_Doc_Spec;

        //constructor for ViewHolder class
        public DocViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_Doc = itemView.findViewById(R.id.tv_med_item);
            tv_Doc_Spec = itemView.findViewById(R.id.tv_med_freq_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(listener != null){
                       int position = getAdapterPosition();
                       if(position != RecyclerView.NO_POSITION){
                           listener.onItemClick(position);
                           //db_id_doc = (int) v.getTag();
                           //Toast.makeText(mContext, "Doc clicked from constructor", Toast.LENGTH_LONG).show();

                           Intent DocIntent = new Intent(mContext, DocActivity.class);
                           DocIntent.putExtra("name_doc", name);
                           DocIntent.putExtra("spec_doc", spec);
                           DocIntent.putExtra("email_doc", email);
                           DocIntent.putExtra("telno_doc", telno);
                           DocIntent.putExtra("address_doc", address);
                           mContext.startActivity(DocIntent);
                       }
                   }
                }
            });
        }
    }


    @Override
    public DocAdapter.DocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.med_element_for_rv, parent, false);
        return new DocViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(DocAdapter.DocViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
         name = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.DoctorsList.COLUMN_DOCTOR_NAME));
         spec = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.DoctorsList.COLUMN_DOCTOR_SPEC));
         email = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.DoctorsList.COLUMN_DOCTOR_EMAIL));
         telno = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.DoctorsList.COLUMN_DOCTOR_TEL_NO));
         address = mCursor.getString(mCursor.getColumnIndex(MedicinesContract.DoctorsList.COLUMN_DOCTOR_ADDRESS));

        int id = mCursor.getInt(mCursor.getColumnIndex(MedicinesContract.DoctorsList._ID)); //to get the id (primary key) of that row in the database

        holder.tv_Doc.setText(name);
        holder.tv_Doc_Spec.setText(spec);
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