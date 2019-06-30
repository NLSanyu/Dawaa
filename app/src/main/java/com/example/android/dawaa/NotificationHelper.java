package com.example.android.dawaa;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Lydia on 19-Mar-18.
 */

public class NotificationHelper extends ContextWrapper {

    public static final String Channel1_ID = "Channel1_ID";
    public static final String Channel1_Name = "Channel1";
    public static final String Channel2_ID = "Channel2_ID";
    public static final String Channel2_Name = "Channel2";
    private static int REQUEST_CALL = 1;
    private static int REQUEST_CODE = 1;
    private static final String FILE_NAME = "ConfirmMedFile.txt";
    CountDownTimer countDownTimer;
    long timeLeftInMillis = 900000;
    boolean timerRunning;
    private static final String YES_ACTION = "com.example.lydia.myapplication2.YES_ACTION";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);

        REQUEST_CODE++;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel(){
        NotificationChannel channel1 = new NotificationChannel(Channel1_ID, Channel1_Name, NotificationManager.IMPORTANCE_HIGH);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        //channel1.setLightColor(R.color.colorPrimaryLight);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        //channel1.setSound();   //find out how to set sound

        NotificationChannel channel2 = new NotificationChannel(Channel2_ID, Channel2_Name, NotificationManager.IMPORTANCE_HIGH);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        //channel1.setLightColor(R.color.colorPrimaryLight);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);

    }

    public NotificationManager getManager(){
       if(mManager == null){
           mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       }

       return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String message){ //title will be med name and msg med details

        Intent confirmIntent = new Intent(this, Alarm2Activity.class); //can create an activity here that simply says "Take med"
        confirmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        confirmIntent.setAction(YES_ACTION);

        Intent openAppIntent = new Intent(this, MedicineActivity.class); //can create an activity here that simply says "Take med"
        openAppIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //processIntentAction(getIntent());  //where to put this???

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), Channel1_ID)
                .setContentTitle(getString(R.string.med_notif_title))
                .setContentText(message)
                .setContentIntent(PendingIntent.getActivity(this, REQUEST_CODE, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.drawable.ic_blur_circular_black_24dp) //setLargeIcon as a picture of a pill
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_blur_circular_black_24dp,
                        getString(R.string.confirm_med_taken),
                        PendingIntent.getActivity(this, REQUEST_CODE, confirmIntent, PendingIntent.FLAG_UPDATE_CURRENT)))
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true);
                //.setTimeOutAfter()
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(message)))
                //.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                //.setTimeOutAfter();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        return notificationBuilder;
    }

    public NotificationCompat.Builder getChannel2Notification(String message){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), Channel2_ID)
                .setContentTitle(getString(R.string.rdv_notif_title))
                .setContentText(message)
                //.setContentIntent(PendingIntent.getActivity(this, REQUEST_CODE, openCalendarIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.drawable.ic_blur_circular_black_24dp) //setLargeIcon as a picture of a pill
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        return notificationBuilder;
    }

    private void processIntentAction(Intent intent){
        if(intent.getAction() != null){
            if(intent.getAction() == YES_ACTION){
                Toast.makeText(this, "Notification action works", Toast.LENGTH_LONG).show();
                //stopTimer();
                //writeToFile();
            }
        }
    }


    private void startTimer(){
       countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
           @Override
           public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
           }

           @Override
           public void onFinish()
           {
                makePhoneCall();
           }
       }.start();

       timerRunning = true;

    }

    public void stopTimer(){
        countDownTimer.cancel();
        timerRunning = false;
    }

    // METHOD THAT DIALS THE NUMBER SAVED AS FAMILY MEMBER/FRIEND

    private void makePhoneCall(){
        if(ContextCompat.checkSelfPermission(NotificationHelper.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "Phone call permission not given", Toast.LENGTH_SHORT).show();
           // ActivityCompat.requestPermissions(Alarm2Activity.this, new String[] {android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            String number =   "+213658305662";
            String phoneNum = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(phoneNum)));
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

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
