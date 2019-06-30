package com.example.android.dawaa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddMedicineActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private SQLiteDatabase mDatabase;
    private static final String FILE_NAME = "ConfirmMedFile.txt";
    static int REQUEST_CODE = 1;
    String med_name;
    EditText et_New_Med, et_New_Med_Freq, et_New_Med_Dosage, et_New_Med_Details, et_New_Med_Start_Date, et_New_Med_Finish_Date,
    et_New_Med_Time1, et_New_Med_Time2, et_New_Med_Time3, et_New_Med_Time4;
    Button btn_Add_New_Med, btn_Set_Alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        REQUEST_CODE++;

        /*
        RecyclerView mRecyclerView = findViewById(R.id.rv_all_meds2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MedAdapter(this, getAllMedicines());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        */

        assignVarsAndListeners();

    }

    public void assignVarsAndListeners(){

        et_New_Med = (EditText) findViewById(R.id.et_New_Med);
        et_New_Med_Dosage = (EditText) findViewById(R.id.et_New_Med_Dosage);
        et_New_Med_Start_Date = (EditText) findViewById(R.id.et_New_Med_Start_Date);
        et_New_Med_Finish_Date = (EditText) findViewById(R.id.et_New_Med_Finish_Date);
        et_New_Med_Time1 = (EditText) findViewById(R.id.et_New_Med_Time1);
        et_New_Med_Time2 = (EditText) findViewById(R.id.et_New_Med_Time2);
        et_New_Med_Time3 = (EditText) findViewById(R.id.et_New_Med_Time3);
        et_New_Med_Time4 = (EditText) findViewById(R.id.et_New_Med_Time4);

        med_name = et_New_Med.getText().toString();

        btn_Add_New_Med = (Button) findViewById(R.id.btn_Add_New_Med);
        btn_Set_Alarm = (Button) findViewById(R.id.btn_set_alarm2);


        btn_Add_New_Med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToMedicinesTable();

                Toast.makeText(AddMedicineActivity.this, R.string.med_added, Toast.LENGTH_LONG).show();

                Intent MedListIntent = new Intent(AddMedicineActivity.this, MedicinesListActivity.class);
                startActivity(MedListIntent);
            }
        });

        btn_Set_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");

            }
        });

    }

    //method that adds a row into the database////////////////////////////////////////////////
    private void addToMedicinesTable() {
        /*
        if(et_New_Med.getText().toString().trim().length() == 0 ||
                et_New_Med_Freq.getText().toString().trim().length() == 0){
            return;
        }
        */


        String text;
        ContentValues cv = new ContentValues();

        text = et_New_Med.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(AddMedicineActivity.this, R.string.enter_a_name, Toast.LENGTH_LONG).show();
            return;
        }
        else {
            cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_NAME, text);

            text = et_New_Med_Dosage.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_DOSAGE, text);
            }

            text = et_New_Med_Details.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_DETAILS, text);
            }

            text = et_New_Med_Start_Date.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_START_DATE, text);
            }

            text = et_New_Med_Finish_Date.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_FINISH_DATE, text);
            }

            text = et_New_Med_Time1.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_TIME1, text);
            }

            text = et_New_Med_Time2.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_TIME2, text);
            }

            text = et_New_Med_Time3.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_TIME3, text);
            }

            text = et_New_Med_Time4.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                cv.put(MedicinesContract.MedicinesList.COLUMN_MEDICINE_TIME4, text);
            }

            mDatabase.insert(MedicinesContract.MedicinesList.TABLE_NAME, null, cv);

        }

        clearEditTexts();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        startAlarm(c, REQUEST_CODE);

        //Intent Alarm2Intent = new Intent(AddMedicineActivity.this, Alarm2Activity.class);
        //startActivity(Alarm2Intent);

    }


    public void clearEditTexts(){

        //et_New_Med_Freq.clearFocus();
        et_New_Med.getText().clear();
        et_New_Med_Freq.getText().clear();
        et_New_Med_Dosage.getText().clear();
        et_New_Med_Details.getText().clear();
        et_New_Med_Start_Date.getText().clear();
        et_New_Med_Finish_Date.getText().clear();
        et_New_Med_Time1.getText().clear();
        et_New_Med_Time2.getText().clear();
        et_New_Med_Time3.getText().clear();
        et_New_Med_Time4.getText().clear();
    }


    public void startAlarm(Calendar c, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startAlarmIntent = new Intent(this, AlertReceiver.class);
        //startAlarmIntent.putExtra("req_code", requestCode); //will see what to use this for
        startAlarmIntent.putExtra("med_name", med_name);

        PendingIntent startAlarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, startAlarmIntent, 0);

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), startAlarmPendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, startAlarmPendingIntent);
    }

    private void cancelAlarm(int requestCode){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startAlarmIntent = new Intent(this, AlertReceiver.class);
        PendingIntent startAlarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, startAlarmIntent, 0);

        alarmManager.cancel(startAlarmPendingIntent);

    }
}