package com.example.playbingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class settingdatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "setting";
    private static final String DATABASE_TABLE = "settingt";

    private static final String KEY_ID = "id";
    private static final String vibrate = "vibrate";
    private static final String sound = "sound";
    private static final String i = "i";





    settingdatabase(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table;
        String query = "CREATE TABLE "+DATABASE_TABLE+"("+KEY_ID+"INT PRIMARY KEY,"+
                i+" INT, "+
                sound+" TEXT, "+
                vibrate+" TEXT "+")";
        db.execSQL(query);


    }

    public void add()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues c = new ContentValues();
        c.put(i,1);
        c.put(sound,"on");
        c.put(vibrate,"on");
        long ID = db.insert(DATABASE_TABLE,null,c);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);

    }


    public String getsound()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String s ="off";
        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            s = cursor.getString(cursor.getColumnIndex("sound"));

        }
        return s;

    }

    public String getVibrate()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String s="off";
        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            s = cursor.getString(cursor.getColumnIndex("vibrate"));

        }
        return s;

    }
    public void savesetting(String s1,String s2)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("sound",s1);
            cv.put("vibrate",s2);
            db.update(DATABASE_TABLE, cv, i+ "="+1, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
