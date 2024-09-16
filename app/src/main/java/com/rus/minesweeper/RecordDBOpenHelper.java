package com.rus.minesweeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordDBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "time_records.db";
    private static final int DATABASE_VERSION = 1;

    public RecordDBOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE \"Time_records\" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "game_time TEXT, " +
                "time TEXT, " +
                "date TEXT)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String DROP_TABLE = "DROP TABLE IF EXISTS \"Time_records\"";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    void addGameResult(String gameTime)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = timeFormat.format(new Date());

        ContentValues values = new ContentValues();
        values.put("game_time", gameTime);
        values.put("time", currentTime);
        values.put("date", currentDate);

        db.insert("\"Time_records\"", null, values);

        db.close();
    }

    ArrayList<String> getTop10Results()
    {
        ArrayList<String> top10Results = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM \"Time_records\" ORDER BY game_time LIMIT 10",null);

        int i = 0;
        if (cursor.moveToFirst())
        {
            do {
                i++;

                String time = cursor.getString(Math.max(cursor.getColumnIndex("time"), 0));
                String date = cursor.getString(Math.max(cursor.getColumnIndex("date"),0));
                String gameTime = cursor.getString(Math.max(cursor.getColumnIndex("game_time"),0));
                top10Results.add("#" + i + "\t" + gameTime + "\t" + date + "\t" + time);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return top10Results;
    }



}