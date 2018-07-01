package com.platinum.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListOfNotesActivity extends AppCompatActivity {

    List<Journals> JournalsList = new ArrayList<>();
    JournalsDataBase journalsDataBase = new JournalsDataBase(this);
    RecyclerView NotesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        Toolbar toolbar = (Toolbar)findViewById(R.id.list_toolbar);
        setSupportActionBar(toolbar);

        loadLists();
    }

    private void loadLists(){
        Cursor cursor = journalsDataBase.getAll();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String Date = cursor.getString(cursor.getColumnIndex("Date"));
                String Time = cursor.getString(cursor.getColumnIndex("Time"));
                String Title = cursor.getString(cursor.getColumnIndex("Title"));
                String Note = cursor.getString(cursor.getColumnIndex("Note"));

                Journals journals = new Journals(Date,Time,Title,Note);
                JournalsList.add(journals);

                cursor.moveToNext();
            }
            cursor.close();
        }
        journalsDataBase.close();

        NotesList = (RecyclerView) findViewById(R.id.list_of_notes);
        NotesList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        NotesList.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new NotesAdapter(JournalsList);
        mAdapter.notifyDataSetChanged();
        NotesList.setAdapter(mAdapter);
    }


    public void AddNoteFAB_Click(View view) {
        Intent i = new Intent(getApplication().getApplicationContext(),AddOrEditJournalActivity.class);
        i.putExtra("Extra","New");
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadLists();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
