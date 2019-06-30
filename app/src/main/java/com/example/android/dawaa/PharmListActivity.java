package com.example.android.dawaa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Lydia on 04-Apr-18.
 */

public class PharmListActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private PharmAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharm_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btn_add_pharm = (Button) findViewById(R.id.btn_open_add_pharm_activity);
        btn_add_pharm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddPharmIntent = new Intent(PharmListActivity.this, AddPharmActivity.class);
                startActivity(AddPharmIntent);
            }
        });


        //find a solution for going backwards to the MedicinesListActivity without losing info


        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        RecyclerView mRecyclerView = findViewById(R.id.rv_all_pharms);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PharmAdapter(this, getAllPharmacists());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mAdapter.setOnItemClickListener(new PharmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(PharmListActivity.this, "Testing the click", Toast.LENGTH_SHORT).show();
                Intent PharmIntent = new Intent(PharmListActivity.this, PharmActivity.class);
                startActivity(PharmIntent);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItemId((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(mRecyclerView);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    private void removeItemId(long id){
        mDatabase.delete(MedicinesContract.PharmacistsList.TABLE_NAME,
                MedicinesContract.PharmacistsList._ID + "=" + id, null);
        mAdapter.swapCursor(getAllPharmacists());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        switch(itemSelected){
            case R.id.item_add:
                //Toast.makeText(MedicinesListActivity.this, "Ajouté", Toast.LENGTH_SHORT).show();
                Intent AddPharmIntent = new Intent(PharmListActivity.this, AddPharmActivity.class);
                startActivity(AddPharmIntent);
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


    private Cursor getAllPharmacists() {
        return mDatabase.query(
                MedicinesContract.PharmacistsList.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MedicinesContract.PharmacistsList.COLUMN_PHARMACIST_NAME
        );

    }







}
