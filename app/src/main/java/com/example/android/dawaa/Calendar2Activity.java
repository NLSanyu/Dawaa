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
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Calendar2Activity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private SQLiteDatabase mDatabase;
    private static int REQUEST_CODE = 100;

    String person, desc, day_of_week;
    int day_of_month, month, year, hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar2);

        EditText et_cal_rdv_person, et_cal_rdv_desc;

        et_cal_rdv_person = findViewById(R.id.et_cal_rdv_person);
        et_cal_rdv_desc = findViewById(R.id.et_cal_rdv_desc);
        person = et_cal_rdv_person.getText().toString();
        desc = et_cal_rdv_desc.getText().toString();

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final CalendarPickerView calendarPicker = findViewById(R.id.calendar2);
        calendarPicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(today);

        final List<Date> dates = new ArrayList<>();

        // to get the selected date directly : calendarPicker.getSelectedDate(); or getSelectedDates()

        calendarPicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
               String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
               Calendar calSelected = Calendar.getInstance();
               calSelected.setTime(date);
               dates.add(date);
               calendarPicker.highlightDates(dates);

                //getting the selected date as seperate strings for day, month and year
               String selectedDate2 = "" + calSelected.get(Calendar.DAY_OF_WEEK)
                       + " " + (calSelected.get(Calendar.DAY_OF_MONTH))
                        + " " + (calSelected.get(Calendar.MONTH))
                        + " " + calSelected.get(Calendar.YEAR);

               String splitDate[] = selectedDate.split(",");
               day_of_week = splitDate[0];
               day_of_month = calSelected.get(Calendar.DAY_OF_MONTH);
               month = calSelected.get(Calendar.MONTH);
               year = calSelected.get(Calendar.YEAR);

               Toast.makeText(Calendar2Activity.this, selectedDate, Toast.LENGTH_SHORT).show();

               android.support.v4.app.DialogFragment timePicker = new TimePickerFragment();
               timePicker.show(getSupportFragmentManager(), "Time Picker");


            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        //c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day_of_month);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        c.add(Calendar.DAY_OF_MONTH, -5); //trying to set it 5 days before

        this.hour = hourOfDay;
        this.min = minute;

        //store date in db
        insertDatesToDatabase(person, desc, day_of_week, day_of_month, month, year, hour, min);

        //set alarm for this rdv
        startAlarm(c, REQUEST_CODE);
    }

    private void startAlarm(Calendar c, int requestCode) {
        REQUEST_CODE++;

        /////////////////////////////////////////////////////////////////////////
        //set alarm for particular day //day should be 5 days before actual day
        /////////////////////////////////////////////////////////////////////////

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startAlarmIntent = new Intent(this, AlertReceiver.class);

        PendingIntent startAlarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, startAlarmIntent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), startAlarmPendingIntent);
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
}
