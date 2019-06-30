package com.example.android.dawaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //View header = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.profile);
        }


    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.contacts:
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new ContactsFragment()).commit();
                break;

            case R.id.medicine:
                Intent MedListIntent = new Intent(MainActivity.this, MedicinesListActivity.class);
                startActivity(MedListIntent);
                break;

            case R.id.doctors:
                Intent DocIntent = new Intent(MainActivity.this, DocActivity.class);
                startActivity(DocIntent);
                break;

            case R.id.pharmacists:
                Intent PharmIntent = new Intent(MainActivity.this, PharmActivity.class);
                startActivity(PharmIntent);
                break;

            case R.id.measurements:
                Intent MeasurementsIntent = new Intent(MainActivity.this, MeasurementsActivity.class);
                startActivity(MeasurementsIntent);
                break;

            case R.id.calendar:
                Intent CalendarIntent = new Intent(MainActivity.this, Calendar2Activity.class);
                startActivity(CalendarIntent);
                break;

            case R.id.medical_info:
                Intent MedInfoIntent = new Intent(MainActivity.this, MedInfoActivity.class);
                startActivity(MedInfoIntent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;

    }
}
