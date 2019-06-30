package com.example.android.dawaa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.dawaa.MedicinesContract.*;

/**
 * Created by Lydia on 15-Mar-18.
 */

public class MedicinesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AllMedicines.db";
    public static final int DATABASE_VERSION = 1;

    MedicinesDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ALL_MEDICINES_TABLE = "CREATE TABLE " +
                MedicinesList.TABLE_NAME + " (" +
                MedicinesList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MedicinesList.COLUMN_MEDICINE_NAME + " TEXT NOT NULL, " +
                MedicinesList.COLUMN_MEDICINE_DOSAGE + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_DETAILS + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_START_DATE + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_FINISH_DATE + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_TIME1 + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_TIME2 + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_TIME3 + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_TIME4 + " TEXT, " +
                MedicinesList.COLUMN_MEDICINE_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";


        db.execSQL(SQL_CREATE_ALL_MEDICINES_TABLE);


        final String SQL_CREATE_ALL_DOCTORS_TABLE = "CREATE TABLE " +
                DoctorsList.TABLE_NAME + " (" +
                DoctorsList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DoctorsList.COLUMN_DOCTOR_NAME + " TEXT NOT NULL, " +
                DoctorsList.COLUMN_DOCTOR_SPEC + " TEXT, " +
                DoctorsList.COLUMN_DOCTOR_EMAIL + " TEXT, " +
                DoctorsList.COLUMN_DOCTOR_TEL_NO + " TEXT, " +
                DoctorsList.COLUMN_DOCTOR_ADDRESS + " TEXT" +
                ");";

        db.execSQL(SQL_CREATE_ALL_DOCTORS_TABLE);

        final String SQL_CREATE_ALL_PHARMACISTS_TABLE = "CREATE TABLE " +
                PharmacistsList.TABLE_NAME + " (" +
                PharmacistsList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PharmacistsList.COLUMN_PHARMACIST_NAME + " TEXT NOT NULL, " +
                PharmacistsList.COLUMN_PHARMACIST_EMAIL + " TEXT, " +
                PharmacistsList.COLUMN_PHARMACIST_TEL_NO + " TEXT, " +
                PharmacistsList.COLUMN_PHARMACIST_ADDRESS + " TEXT" +
                ");";

        db.execSQL(SQL_CREATE_ALL_PHARMACISTS_TABLE);

        final String SQL_CREATE_ALL_DATES_TABLE = "CREATE TABLE " +
                ImportantDatesList.TABLE_NAME + " (" +
                ImportantDatesList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ImportantDatesList.COLUMN_PERSON + " TEXT, " +
                ImportantDatesList.COLUMN_DESCRIPTION + " TEXT, " +
                ImportantDatesList.COLUMN_DAY_OF_WEEK + " TEXT NOT NULL, " +
                ImportantDatesList.COLUMN_DAY_OF_MONTH + " INTEGER NOT NULL, " +
                ImportantDatesList.COLUMN_MONTH + " INTEGER NOT NULL, " +
                ImportantDatesList.COLUMN_YEAR + " INTEGER NOT NULL, " +
                ImportantDatesList.COLUMN_HOUR + " INTEGER NOT NULL, " +
                ImportantDatesList.COLUMN_MINUTE + " INTEGER NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_ALL_DATES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //code which drops the db and recreates it
        db.execSQL("DROP TABLE IF EXISTS " + MedicinesList.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DoctorsList.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PharmacistsList.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImportantDatesList.TABLE_NAME);
        onCreate(db);

    }
}
