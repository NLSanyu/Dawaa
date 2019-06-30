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

public class DocListActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private DocAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btn_add_doc = (Button) findViewById(R.id.btn_open_add_doc_activity);
        btn_add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddDocIntent = new Intent(DocListActivity.this, AddDocActivity.class);
                startActivity(AddDocIntent);
            }
        });


        //find a solution for going backwards to the MedicinesListActivity without losing info


        MedicinesDBHelper dbHelper = new MedicinesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        RecyclerView mRecyclerView = findViewById(R.id.rv_all_docs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DocAdapter(this, getAllDoctors());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mAdapter.setOnItemClickListener(new DocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(DocListActivity.this, "Click from DocListActivity", Toast.LENGTH_LONG).show();
                //Intent DocIntent = new Intent(DocListActivity.this, DocActivity.class);
                //startActivity(DocIntent);

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

    private void removeItemId(long id){
        mDatabase.delete(MedicinesContract.DoctorsList.TABLE_NAME,
                MedicinesContract.DoctorsList._ID + "=" + id, null);
        mAdapter.swapCursor(getAllDoctors());
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
                Intent AddDocIntent = new Intent(DocListActivity.this, AddDocActivity.class);
                startActivity(AddDocIntent);
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


    private Cursor getAllDoctors() {
        return mDatabase.query(
                MedicinesContract.DoctorsList.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MedicinesContract.DoctorsList.COLUMN_DOCTOR_NAME
        );

    }







}
