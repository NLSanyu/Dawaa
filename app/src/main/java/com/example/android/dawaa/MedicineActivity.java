package com.example.android.dawaa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MedicineActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private MedAdapter mAdapter;
    public long med_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

       //show the details of the chosen medicine

        Intent intent = getIntent();
        if(intent.hasExtra("db_id")) {
            med_id = intent.getIntExtra("db_id", 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        switch(itemSelected){
            case R.id.item_add:
                Toast.makeText(MedicineActivity.this, "Ajouté", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_language:
                //change the language
                break;

            case R.id.item_log_out:
                //log out
                break;

            default: break;
        }
        return super.onOptionsItemSelected(item);
    }


    private Cursor getMedDetails() {

        //String whereClause = MedicinesContract.MedicinesList._ID + "=?";
        //String[] selectionArgs = {String.valueOf(med_id)};
        String[] selectionArgs = {MedicinesContract.MedicinesList._ID, String.valueOf(med_id)};


       return mDatabase.rawQuery("SELECT * FROM " +  MedicinesContract.MedicinesList.TABLE_NAME, selectionArgs);


      /*return mDatabase.query(
                    MedicinesContract.MedicinesList.TABLE_NAME,
                    null,
                     whereClause,
                     selectionArgs,
                    null,
                    null,
                    null
            );
      */

    }


}



