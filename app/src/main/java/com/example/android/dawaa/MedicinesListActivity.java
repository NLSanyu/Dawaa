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
import android.widget.Toast;

public class MedicinesListActivity extends AppCompatActivity implements MedAdapter.OnItemClickListener{

    private SQLiteDatabase mDatabase;
    private MedAdapter mAdapter;
    public static final String DB_ID = "db_id";
    //public long db_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btn_add_med = (Button) findViewById(R.id.btn_open_add_med_activity);
        btn_add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddMedicineIntent = new Intent(MedicinesListActivity.this, AddMedicineActivity.class);
                startActivity(AddMedicineIntent);
            }
        });


        //find a solution for going backwards to the MedicinesListActivity without losing info


        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        RecyclerView mRecyclerView = findViewById(R.id.rv_all_meds);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MedAdapter(this, getAllMedicines());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mAdapter.setOnItemClickListener(MedicinesListActivity.this);

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

    private void removeItemId(long id){
        mDatabase.delete(MedicinesContract.MedicinesList.TABLE_NAME,
                MedicinesContract.MedicinesList._ID + "=" + id, null);
        mAdapter.swapCursor(getAllMedicines());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        switch(itemSelected){
            case R.id.item_add:
            //Toast.makeText(MedicinesListActivity.this, "Ajouté", Toast.LENGTH_SHORT).show();
                Intent AddMedicineIntent = new Intent(MedicinesListActivity.this, AddMedicineActivity.class);
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

    @Override
    public void onItemClick(int position) {
        //Intent MedicineIntent = new Intent(MedicinesListActivity.this, MedicineActivity.class);
        //MedicineIntent.putExtra(DB_ID, );
        //startActivity(MedicineIntent);
        Toast.makeText(MedicinesListActivity.this, "Click from MedicinesListActivity", Toast.LENGTH_LONG).show();

    }

    private Cursor getAllMedicines() {
        return mDatabase.query(
                MedicinesContract.MedicinesList.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MedicinesContract.MedicinesList.COLUMN_MEDICINE_TIMESTAMP + " DESC"
        );

    }


}



