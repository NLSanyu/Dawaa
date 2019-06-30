package com.example.android.dawaa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class MedInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tv_Disease_Info;
    String[] diseases = {"Tuberculose", "Diabete", "Tension", "Asthme"};
    //define an array resource for this and @string resources

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner_med_info = (Spinner) findViewById(R.id.spinner_med_info);
        spinner_med_info.setOnItemSelectedListener(this);

        tv_Disease_Info = (TextView) findViewById(R.id.tv_disease_info);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, diseases);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_med_info.setAdapter(spinnerAdapter);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(), diseases[position], Toast.LENGTH_LONG).show();

        tv_Disease_Info.setText(R.string.info_text);

        switch(position){
            case 0:
                //tv_Disease_Info.setText(R.string.info_text_tuberculosis);
                tv_Disease_Info.setText(R.string.info_text);

                break;

            case 1:
                //tv_Disease_Info.setText(R.string.info_text_diabetes);
                tv_Disease_Info.setText(R.string.info_text);


                break;

            case 2:
                //tv_Disease_Info.setText(R.string.info_text_blood_pressure);
                tv_Disease_Info.setText(R.string.info_text);

                break;

            case 3:
                //tv_Disease_Info.setText(R.string.info_text_asthma);
                tv_Disease_Info.setText(R.string.info_text);


                break;

            default: break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        switch(itemSelected){
            case R.id.item_add:
                //Toast.makeText(MedicinesListActivity.this, "Ajouté", Toast.LENGTH_SHORT).show();
                Intent AddMedicineIntent = new Intent(MedInfoActivity.this, AddMedicineActivity.class);
                startActivity(AddMedicineIntent);
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

}
