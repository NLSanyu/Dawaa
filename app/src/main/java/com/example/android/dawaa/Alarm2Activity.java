package com.example.android.dawaa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;

public class Alarm2Activity extends AppCompatActivity {

    private static final String FILE_NAME = "ConfirmMedFile.txt";
    private TextView tv_Show_Alarm;
    static int REQUEST_CODE = 1;
    //private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alarm2);

        REQUEST_CODE++;

        //Intent intent = getIntent();
        //intent.getStringExtra(Med_Name);

        //mNotificationHelper = new NotificationHelper(this);

        /*
        tv_Show_Alarm = (TextView) findViewById(R.id.tv_show_alarm_time);
        Button btn_Set_Alarm = (Button) findViewById(R.id.btn_set_alarm);

        btn_Set_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
                //sendOnChannel1 or channel2

            }
        });

        Button btn_Cancel_Alarm = (Button) findViewById(R.id.btn_cancel_alarm);

        btn_Cancel_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cancelAlarm(REQUEST_CODE);

            }
        });
        */

    }

    /*@Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c); //to set the TextView
        startAlarm(c, REQUEST_CODE);

       }*/

    /*
    public void updateTimeText(Calendar c){
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        tv_Show_Alarm.setText(timeText);
    }
    */

    public void startAlarm(Calendar c, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startAlarmIntent = new Intent(this, AlertReceiver.class);

        PendingIntent startAlarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, startAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), startAlarmPendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, startAlarmPendingIntent);
    }

    private void cancelAlarm(int requestCode){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent startAlarmIntent = new Intent(this, AlertReceiver.class);
        PendingIntent startAlarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, startAlarmIntent, 0);

        alarmManager.cancel(startAlarmPendingIntent);
        tv_Show_Alarm.setText(getString(R.string.Alarm_cancelled));
    }




    /*
    //notifications
    public void sendOnChannel1(String title, String message){
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title, message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    //notifications
    public void sendOnChannel2(String title, String message){
        NotificationCompat.Builder nb = mNotificationHelper.getChannel2Notification(title, message);
        mNotificationHelper.getManager().notify(2, nb.build());
    }*/





    // ACTION FOR MED CONFIRMED : WRITE INTO TEXT FILE. PLUS THE METHOD THAT READS THE FILE

    private void writeToFile(){
        Calendar c = Calendar.getInstance();
        String dateMedTaken = DateFormat.getDateInstance(DateFormat.FULL).format(c);
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(dateMedTaken.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void ReadFile(){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            //set text to a TextView for example

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}