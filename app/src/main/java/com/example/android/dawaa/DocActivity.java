package com.example.android.dawaa;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DocActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    Calendar c;
    String person, desc;
    public String name, spec, email, telno, address;
    static int REQUEST_CODE = 100;
    private SQLiteDatabase mDatabase;
    TextView tv_doc_name, tv_doc_spec, tv_doc_email, tv_doc_telno, tv_doc_address;
    //private Cursor mCursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        REQUEST_CODE++;

        Intent intent = getIntent();
        if(intent.hasExtra("name_doc")) {
            name = intent.getStringExtra("name_doc");
        }

        if(intent.hasExtra("spec_doc")) {
            spec = intent.getStringExtra("spec_doc");
        }

        if(intent.hasExtra("email_doc")) {
            email = intent.getStringExtra("email_doc");
        }

        if(intent.hasExtra("telno_doc")) {
            telno = intent.getStringExtra("telno_doc");
        }

        if(intent.hasExtra("address_doc")) {
            address = intent.getStringExtra("address_doc");
        }

        tv_doc_name = findViewById(R.id.tv_doc_name);
        tv_doc_spec = findViewById(R.id.tv_doc_spec);
        tv_doc_email = findViewById(R.id.tv_doc_email);
        tv_doc_telno = findViewById(R.id.tv_doc_telno);
        tv_doc_address = findViewById(R.id.tv_doc_address);
        EditText et_desc = findViewById(R.id.et_desc);
        Button btn_add_rdv = findViewById(R.id.btn_doc_add_rdv);
        ImageView icon_date = findViewById(R.id.icon_doc_rdv_date);
        ImageView icon_time = findViewById(R.id.icon_doc_rdv_time);

        tv_doc_name.setText(name);
        tv_doc_spec.setText(spec);
        tv_doc_email.setText(email);
        tv_doc_telno.setText(telno);
        tv_doc_address.setText(address);

        person = tv_doc_name.getText().toString();
        desc = et_desc.getText().toString();

        c = Calendar.getInstance();

        btn_add_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insertDatesToDatabase(person, desc, c.DAY_OF_WEEK, c.DAY_OF_MONTH, c.MONTH, c.YEAR, c.HOUR, c.MINUTE); //add name & desc here
                startAlarm(c, REQUEST_CODE);
            }
        });

        icon_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        icon_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    public void insertDatesToDatabase(String person, String desc, String day_of_week, int day_of_month, int month, int year, int hour, int minute){
        ContentValues cv = new ContentValues();
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_PERSON, person);
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_DESCRIPTION, desc );
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_DAY_OF_WEEK, day_of_week);
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_DAY_OF_MONTH, day_of_month);
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_MONTH, month);
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_YEAR, year);
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_HOUR, hour);
        cv.put(MedicinesContract.ImportantDatesList.COLUMN_MINUTE, minute);


        mDatabase.insert(MedicinesContract.ImportantDatesList.TABLE_NAME, null, cv);
    }

    public void startAlarm(Calendar c, int requestCode) {

        c.add(Calendar.DAY_OF_MONTH, -5);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startAlarmIntent = new Intent(this, AlertReceiver.class);
        startAlarmIntent.putExtra("name", name);

        PendingIntent startAlarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, startAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), startAlarmPendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, startAlarmPendingIntent);
    }

    private void cancelAlarm(int requestCode){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startAlarmIntent = new Intent(this, AlertReceiver.class);
        PendingIntent startAlarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, startAlarmIntent, 0);

        alarmManager.cancel(startAlarmPendingIntent);
    }

    /*
    private Cursor getDocDetails() {

        //String whereClause = MedicinesContract.MedicinesList._ID + "=?";
        //String[] selectionArgs = {String.valueOf(med_id)};
        String[] selectionArgs = {MedicinesContract.MedicinesList._ID, String.valueOf(doc_id)};

        return mDatabase.rawQuery("SELECT * FROM " +  MedicinesContract.DoctorsList.TABLE_NAME, selectionArgs);

    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        switch(itemSelected){
            case R.id.item_add:
                //Intent AddDocIntent = new Intent(DocActivity.this, AddDocActivity.class);
                //startActivity(AddDocIntent);
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