package com.example.android.dawaa;

import android.provider.BaseColumns;

/**
 * Created by Lydia on 15-Mar-18.
 */

public class MedicinesContract {

    private MedicinesContract(){}

    public static final class MedicinesList implements BaseColumns{

        public static final String TABLE_NAME = "Medicines";
        public static final String COLUMN_MEDICINE_NAME = "Medicine_Name";
        public static final String COLUMN_MEDICINE_DOSAGE = "Medicine_Dosage";
        public static final String COLUMN_MEDICINE_DETAILS = "Medicine_Details";
        public static final String COLUMN_MEDICINE_START_DATE = "Medicine_Start_Date";
        public static final String COLUMN_MEDICINE_FINISH_DATE = "Medicine_Finish_Date";
        public static final String COLUMN_MEDICINE_TIME1 = "Medicine_Time1";
        public static final String COLUMN_MEDICINE_TIME2 = "Medicine_Time2";
        public static final String COLUMN_MEDICINE_TIME3 = "Medicine_Time3";
        public static final String COLUMN_MEDICINE_TIME4 = "Medicine_Time4";
        public static final String COLUMN_MEDICINE_TIMESTAMP = "Medicine_Timestamp";



    }

    public static final class DoctorsList implements BaseColumns{

        public static final String TABLE_NAME = "Doctors";
        public static final String COLUMN_DOCTOR_NAME = "Doctor_Name";
        public static final String COLUMN_DOCTOR_SPEC = "Doctor_Speciality";
        public static final String COLUMN_DOCTOR_EMAIL = "Doctor_Email";
        public static final String COLUMN_DOCTOR_TEL_NO = "Doctor_Tel_No";
        public static final String COLUMN_DOCTOR_ADDRESS = "Doctor_Address";

    }

    public static final class PharmacistsList implements BaseColumns{

        public static final String TABLE_NAME = "Pharmacists";
        public static final String COLUMN_PHARMACIST_NAME = "Pharmacist_Name";
        public static final String COLUMN_PHARMACIST_EMAIL = "Pharmacist_Email";
        public static final String COLUMN_PHARMACIST_TEL_NO = "Pharmacist_Tel_No";
        public static final String COLUMN_PHARMACIST_ADDRESS = "Pharmacist_Address";

    }

    public static final class ImportantDatesList implements BaseColumns {

        public static final String TABLE_NAME = "Important_Dates";
        public static final String COLUMN_PERSON = "Person";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_DAY_OF_WEEK= "Day_Of_Week";
        public static final String COLUMN_DAY_OF_MONTH = "Day_Of_Month";
        public static final String COLUMN_MONTH = "Month";
        public static final String COLUMN_YEAR = "Year";
        public static final String COLUMN_HOUR = "Hour";
        public static final String COLUMN_MINUTE = "Minute";

    }


    /*
    public static final class Measurements implements BaseColumns{

        public static final String TABLE_NAME = "Measurements";
        public static final String COLUMN_MEASUREMENT_NAME = "MeasurementName";
        public static final String COLUMN_MEASUREMENT_VALUE1 = "MeasurementValue1";
        public static final String COLUMN_MEASUREMENT_VALUE2 = "MeasurementValue2";
        public static final String EFFECTS = "Effects";

    }*/
}
