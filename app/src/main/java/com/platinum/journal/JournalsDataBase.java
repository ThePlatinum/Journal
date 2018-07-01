package com.platinum.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PLATINUM
 * Date 6/29/2018
 * Time 10:47 AM
 * Package com.platinum.journal
 * Project Journal
 */

class JournalsDataBase extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "JournalsDatabase.db";
    private static  String TABLE_NAME = "JournalsTable";

    JournalsDataBase(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( Id Integer primary key , Identifier text , Date text , Time text , Title text , Note text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop if exits JournalsDatabase");
        onCreate(db);
    }

    Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME + " where Identifier = " + id + "", null );
        res.moveToFirst();
        return res;
    }

    long addJournal(String Identifier , String Date , String Time , String Title , String Note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Identifier",Identifier);
        cv.put("Date",Date);
        cv.put("Time",Time);
        cv.put("Title",Title);
        cv.put("Note",Note);

        return db.insert(TABLE_NAME,null,cv);
    }

    long update(String Identifier , String Date , String Time , String Title , String Note )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Identifier",Identifier);
        cv.put("Date",Date);
        cv.put("Time",Time);
        cv.put("Title",Title);
        cv.put("Note",Note);

        return db.update(TABLE_NAME,cv,"Identifier = ?",new String[]{Identifier});
    }

    Integer delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "Identifier = ?",new String[]{id});
    }

    Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from JournalsTable", null );
        res.moveToFirst();
        return res;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
