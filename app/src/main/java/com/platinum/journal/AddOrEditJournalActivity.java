package com.platinum.journal;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddOrEditJournalActivity extends AppCompatActivity {

    JournalsDataBase journalsDataBase = new JournalsDataBase(this);
    EditText Title_EditText , Note_EditText ;
    TextView Date_View ;

    FloatingActionButton delete_fab;

    String idToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_journal);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Date_View = (TextView)findViewById(R.id.Date_View);
        Title_EditText = (EditText)findViewById(R.id.Title_EditView);
        Note_EditText = (EditText)findViewById(R.id.Body_EditView);
        delete_fab = (FloatingActionButton)findViewById(R.id.delete_fab);

        Date_View.setText(DateFormat.getDateTimeInstance().format(new Date()));
        idToUpdate = getIntent().getExtras().getString("Extra");

        if (!(idToUpdate.matches("New")))
        {
            edit(idToUpdate);
        }
        else{
            delete_fab.setVisibility(View.GONE);
        }
    }

    private void edit(String identifier){
        Cursor data = journalsDataBase.getData(identifier);
        data.moveToFirst();
        String EditTitle = data.getString(data.getColumnIndex("Title"));
        String Note = data.getString(data.getColumnIndex("Note"));

        Title_EditText.setText(EditTitle);
        Note_EditText.setText(Note);

        data.close();
    }

    public void saveButtonPressed(View view) {
        String text_title = Title_EditText.getText().toString();
        String text_note =  Note_EditText.getText().toString();
        if(text_title.equals("") || text_note.equals("")){
            Toast.makeText(getApplicationContext(),"Fill empty fields",Toast.LENGTH_LONG).show();
        }
        else {
            String current_date = get_Date();
            String current_time = get_Time();
            String identifier = current_date.replaceAll(",","").replaceAll("\\s+","") + text_title;
            if (!(idToUpdate.equals("New")))
            {
                journalsDataBase.update(identifier,current_date,current_time,text_title,text_note);
            }
            else {
                journalsDataBase.addJournal(identifier,current_date,current_time,text_title,text_note);
            }
            setResult(RESULT_OK);
            finish();
        }
    }

    void close(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Close")
                .setMessage(" Sure to close?")
                .setCancelable(true)
                .setPositiveButton("Yes" , new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog , int id){
                        //
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                })
                .setNegativeButton("No" , new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog , int id){
                        //
                    }
                });
        builder.show();
    }

    public void closeButtonPressed(View view) {
        close();
    }

    private String get_Date(){
        return DateFormat.getDateInstance().format(new Date());
    }

    private String get_Time(){
        DateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public void delete_btn_pressed(View view) {
        journalsDataBase.delete(idToUpdate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        close();
    }
}
