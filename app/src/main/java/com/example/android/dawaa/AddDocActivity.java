package com.example.android.dawaa;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDocActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    //private MedAdapter mAdapter;

    private EditText et_New_Doc, et_New_Doc_Spec, et_New_Doc_Email, et_New_Doc_Tel, et_New_Doc_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doc);

        Button btn_Add_Doc;

        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        /*
        RecyclerView mRecyclerView = findViewById(R.id.rv_all_meds2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MedAdapter(this, getAllDoctors());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        */

        et_New_Doc = (EditText) findViewById(R.id.et_New_Doc);
        et_New_Doc_Spec = (EditText) findViewById(R.id.et_New_Doc_Spec);
        et_New_Doc_Email = (EditText) findViewById(R.id.et_New_Doc_Email);
        et_New_Doc_Tel = (EditText) findViewById(R.id.et_New_Doc_Tel);
        et_New_Doc_Address = (EditText) findViewById(R.id.et_New_Doc_Address);

        btn_Add_Doc = (Button) findViewById(R.id.btn_add_doc);

        btn_Add_Doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDoctorsTable();

                Toast.makeText(AddDocActivity.this, R.string.doc_added, Toast.LENGTH_LONG).show();

                //Intent DocListIntent = new Intent(AddDocActivity.this, DocListActivity.class);
                //startActivity(DocListIntent);
            }
        });


    }

    //method that adds a row into the database////////////////////////////////////////////////
    private void addToDoctorsTable(){

        if(et_New_Doc.getText().toString().trim().length() == 0){
            Toast.makeText(AddDocActivity.this, R.string.enter_a_name, Toast.LENGTH_LONG).show();
            return;
        }

        String doc_name = et_New_Doc.getText().toString();
        String doc_spec = et_New_Doc_Spec.getText().toString();
        String doc_email = et_New_Doc_Email.getText().toString();
        String doc_tel = et_New_Doc_Tel.getText().toString();
        String doc_address = et_New_Doc_Address.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(MedicinesContract.DoctorsList.COLUMN_DOCTOR_NAME, doc_name);
        cv.put(MedicinesContract.DoctorsList.COLUMN_DOCTOR_SPEC, doc_spec);
        cv.put(MedicinesContract.DoctorsList.COLUMN_DOCTOR_EMAIL, doc_email);
        cv.put(MedicinesContract.DoctorsList.COLUMN_DOCTOR_TEL_NO, doc_tel);
        cv.put(MedicinesContract.DoctorsList.COLUMN_DOCTOR_ADDRESS, doc_address);

        mDatabase.insert(MedicinesContract.DoctorsList.TABLE_NAME, null, cv);
        //mAdapter.swapCursor(getAllDoctors());

        et_New_Doc.getText().clear();
        et_New_Doc_Spec.getText().clear();
        et_New_Doc_Email.getText().clear();
        et_New_Doc_Tel.getText().clear();
        et_New_Doc_Address.getText().clear();


    }



}