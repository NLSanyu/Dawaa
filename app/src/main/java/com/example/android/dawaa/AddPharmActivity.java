package com.example.android.dawaa;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPharmActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    //private MedAdapter mAdapter;

    private EditText et_New_Pharm, et_New_Pharm_Email, et_New_Pharm_Tel, et_new_Pharm_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pharm);

        Button btn_Add_Pharm;

        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        /*
        RecyclerView mRecyclerView = findViewById(R.id.rv_all_meds2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MedAdapter(this, getAllDoctors());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        */

        et_New_Pharm = (EditText) findViewById(R.id.et_New_Pharm);
        et_new_Pharm_Address = (EditText) findViewById(R.id.et_New_Pharm_Address);
        et_New_Pharm_Email = (EditText) findViewById(R.id.et_New_Pharm_Email);
        et_New_Pharm_Tel = (EditText) findViewById(R.id.et_New_Pharm_Tel);

        btn_Add_Pharm = (Button) findViewById(R.id.btn_add_pharm);

        btn_Add_Pharm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToPharmacistsTable();

                Toast.makeText(AddPharmActivity.this, R.string.pharm_added, Toast.LENGTH_LONG).show();

                //Intent PharmListIntent = new Intent(AddPharmActivity.this, PharmListActivity.class);
                //startActivity(PharmListIntent);
            }
        });


    }

    //method that adds a row into the database////////////////////////////////////////////////
    private void addToPharmacistsTable(){

        if(et_New_Pharm.getText().toString().trim().length() == 0){
            Toast.makeText(AddPharmActivity.this, R.string.enter_a_name, Toast.LENGTH_LONG).show();
            return;
        }

        String pharm_name = et_New_Pharm.getText().toString();
        String pharm_email = et_New_Pharm.getText().toString();
        String pharm_tel = et_New_Pharm.getText().toString();
        String pharm_address = et_new_Pharm_Address.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(MedicinesContract.PharmacistsList.COLUMN_PHARMACIST_NAME, pharm_name);
        cv.put(MedicinesContract.PharmacistsList.COLUMN_PHARMACIST_EMAIL, pharm_email);
        cv.put(MedicinesContract.PharmacistsList.COLUMN_PHARMACIST_TEL_NO, pharm_tel);
        cv.put(MedicinesContract.PharmacistsList.COLUMN_PHARMACIST_ADDRESS, pharm_address);

        mDatabase.insert(MedicinesContract.PharmacistsList.TABLE_NAME, null, cv);
        //mAdapter.swapCursor(getAllPharmacists());

        et_New_Pharm.getText().clear();
        et_New_Pharm_Email.getText().clear();
        et_New_Pharm_Tel.getText().clear();
        et_new_Pharm_Address.getText().clear();


    }


}