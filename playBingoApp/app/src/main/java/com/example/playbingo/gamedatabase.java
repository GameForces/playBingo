package com.example.playbingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class gamedatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "game";
    private static final String DATABASE_TABLE = "gamet";

    private static final String KEY_ID = "id";
    private static final String a11 = "a11";
    private static final String a12 = "a12";
    private static final String a13 = "a13";
    private static final String a14 = "a14";
    private static final String a15 = "a15";
    private static final String a21 = "a21";
    private static final String a22 = "a22";
    private static final String a23 = "a23";
    private static final String a24 = "a24";
    private static final String a25 = "a25";
    private static final String a31 = "a31";
    private static final String a32 = "a32";
    private static final String a33 = "a33";
    private static final String a34 = "a34";
    private static final String a35 = "a35";
    private static final String a41 = "a41";
    private static final String a42 = "a42";
    private static final String a43 = "a43";
    private static final String a44 = "a44";
    private static final String a45 = "a45";
    private static final String a51 = "a51";
    private static final String a52 = "a52";
    private static final String a53 = "a53";
    private static final String a54 = "a54";
    private static final String a55 = "a55";
    private static final String v11 = "v11";
    private static final String v12 = "v12";
    private static final String v13 = "v13";
    private static final String v14 = "v14";
    private static final String v15 = "v15";
    private static final String v21 = "v21";
    private static final String v22 = "v22";
    private static final String v23 = "v23";
    private static final String v24 = "v24";
    private static final String v25 = "v25";
    private static final String v31 = "v31";
    private static final String v32 = "v32";
    private static final String v33 = "v33";
    private static final String v34 = "v34";
    private static final String v35 = "v35";
    private static final String v41 = "v41";
    private static final String v42 = "v42";
    private static final String v43 = "v43";
    private static final String v44 = "v44";
    private static final String v45 = "v45";
    private static final String v51 = "v51";
    private static final String v52 = "v52";
    private static final String v53 = "v53";
    private static final String v54 = "v54";
    private static final String v55 = "v55";
    private static final String i = "i";
    private static final String s1 = "s1";
    private static final String s2 = "s2";





    gamedatabase(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table;
        String query = "CREATE TABLE "+DATABASE_TABLE+"("+KEY_ID+"INT PRIMARY KEY,"+
                i+" INT, "+
                a11+" TEXT, "+
                s1+" TEXT, "+
                s2+" TEXT, "+
                a12+" TEXT, "+
                a13+" TEXT, "+
                a14+" TEXT, "+
                a15+" TEXT, "+
                a21+" TEXT, "+
                a22+" TEXT, "+
                a23+" TEXT, "+
                a24+" TEXT, "+
                a25+" TEXT, "+
                a31+" TEXT, "+
                a32+" TEXT, "+
                a33+" TEXT, "+
                a34+" TEXT, "+
                a35+" TEXT, "+
                a41+" TEXT, "+
                a42+" TEXT, "+
                a43+" TEXT, "+
                a44+" TEXT, "+
                a45+" TEXT, "+
                a51+" TEXT, "+
                a52+" TEXT, "+
                a53+" TEXT, "+
                a54+" TEXT, "+
                a55+" TEXT, "+
                v11+" INT, "+
                v12+" INT, "+
                v13+" INT, "+
                v14+" INT, "+
                v15+" INT, "+
                v21+" INT, "+
                v22+" INT, "+
                v23+" INT, "+
                v24+" INT, "+
                v25+" INT, "+
                v31+" INT, "+
                v32+" INT, "+
                v33+" INT, "+
                v34+" INT, "+
                v35+" INT, "+
                v41+" INT, "+
                v42+" INT, "+
                v43+" INT, "+
                v44+" INT, "+
                v45+" INT, "+
                v51+" INT, "+
                v52+" INT, "+
                v53+" INT, "+
                v54+" INT, "+
                v55+" INT "+")";
        db.execSQL(query);


    }

    public void add()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues c = new ContentValues();
        c.put(i,1);
        c.put(a11,"0");
        c.put(a12,"0");
        c.put(a13,"0");
        c.put(a14,"0");
        c.put(a15,"0");
        c.put(a21,"0");
        c.put(a22,"0");
        c.put(a23,"0");
        c.put(a24,"0");
        c.put(a25,"0");
        c.put(a31,"0");
        c.put(a32,"0");
        c.put(a33,"0");
        c.put(a34,"0");
        c.put(a35,"0");
        c.put(a41,"0");
        c.put(a42,"0");
        c.put(a43,"0");
        c.put(a44,"0");
        c.put(a45,"0");
        c.put(a51,"0");
        c.put(a52,"0");
        c.put(a53,"0");
        c.put(a54,"0");
        c.put(a55,"0");
        c.put(v11,0);
        c.put(v12,0);
        c.put(v13,0);
        c.put(v14,0);
        c.put(v15,0);
        c.put(v21,0);
        c.put(v22,0);
        c.put(v23,0);
        c.put(v24,0);
        c.put(v25,0);
        c.put(v31,0);
        c.put(v32,0);
        c.put(v33,0);
        c.put(v34,0);
        c.put(v35,0);
        c.put(v41,0);
        c.put(v42,0);
        c.put(v43,0);
        c.put(v44,0);
        c.put(v45,0);
        c.put(v51,0);
        c.put(v52,0);
        c.put(v53,0);
        c.put(v54,0);
        c.put(v55,0);


        long ID = db.insert(DATABASE_TABLE,null,c);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);

    }


    public String[][] getA()
    {
        SQLiteDatabase db = this.getReadableDatabase();


        String [][] aa = new String[5][5];
        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            aa[0][0] = cursor.getString(cursor.getColumnIndex("a11"));
            aa[0][1] = cursor.getString(cursor.getColumnIndex("a12"));
            aa[0][2] = cursor.getString(cursor.getColumnIndex("a13"));
            aa[0][3] = cursor.getString(cursor.getColumnIndex("a14"));
            aa[0][4] = cursor.getString(cursor.getColumnIndex("a15"));
            aa[1][0] = cursor.getString(cursor.getColumnIndex("a21"));
            aa[1][1] = cursor.getString(cursor.getColumnIndex("a22"));
            aa[1][2] = cursor.getString(cursor.getColumnIndex("a23"));
            aa[1][3] = cursor.getString(cursor.getColumnIndex("a24"));
            aa[1][4] = cursor.getString(cursor.getColumnIndex("a25"));
            aa[2][0] = cursor.getString(cursor.getColumnIndex("a31"));
            aa[2][1] = cursor.getString(cursor.getColumnIndex("a32"));
            aa[2][2] = cursor.getString(cursor.getColumnIndex("a33"));
            aa[2][3] = cursor.getString(cursor.getColumnIndex("a34"));
            aa[2][4] = cursor.getString(cursor.getColumnIndex("a35"));
            aa[3][0] = cursor.getString(cursor.getColumnIndex("a41"));
            aa[3][1] = cursor.getString(cursor.getColumnIndex("a42"));
            aa[3][2] = cursor.getString(cursor.getColumnIndex("a43"));
            aa[3][3] = cursor.getString(cursor.getColumnIndex("a44"));
            aa[3][4] = cursor.getString(cursor.getColumnIndex("a45"));
            aa[4][0] = cursor.getString(cursor.getColumnIndex("a51"));
            aa[4][1] = cursor.getString(cursor.getColumnIndex("a52"));
            aa[4][2] = cursor.getString(cursor.getColumnIndex("a53"));
            aa[4][3] = cursor.getString(cursor.getColumnIndex("a54"));
            aa[4][4] = cursor.getString(cursor.getColumnIndex("a55"));

        }
        return aa;

    }
    public int[][] getV()
    {
        SQLiteDatabase db = this.getReadableDatabase();


        int [][] aa = new int[5][5];
        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            aa[0][0] = cursor.getInt(cursor.getColumnIndex("v11"));
            aa[0][1] = cursor.getInt(cursor.getColumnIndex("v12"));
            aa[0][2] = cursor.getInt(cursor.getColumnIndex("v13"));
            aa[0][3] = cursor.getInt(cursor.getColumnIndex("v14"));
            aa[0][4] = cursor.getInt(cursor.getColumnIndex("v15"));
            aa[1][0] = cursor.getInt(cursor.getColumnIndex("v21"));
            aa[1][1] = cursor.getInt(cursor.getColumnIndex("v22"));
            aa[1][2] = cursor.getInt(cursor.getColumnIndex("v23"));
            aa[1][3] = cursor.getInt(cursor.getColumnIndex("v24"));
            aa[1][4] = cursor.getInt(cursor.getColumnIndex("v25"));
            aa[2][0] = cursor.getInt(cursor.getColumnIndex("v31"));
            aa[2][1] = cursor.getInt(cursor.getColumnIndex("v32"));
            aa[2][2] = cursor.getInt(cursor.getColumnIndex("v33"));
            aa[2][3] = cursor.getInt(cursor.getColumnIndex("v34"));
            aa[2][4] = cursor.getInt(cursor.getColumnIndex("v35"));
            aa[3][0] = cursor.getInt(cursor.getColumnIndex("v41"));
            aa[3][1] = cursor.getInt(cursor.getColumnIndex("v42"));
            aa[3][2] = cursor.getInt(cursor.getColumnIndex("v43"));
            aa[3][3] = cursor.getInt(cursor.getColumnIndex("v44"));
            aa[3][4] = cursor.getInt(cursor.getColumnIndex("v45"));
            aa[4][0] = cursor.getInt(cursor.getColumnIndex("v51"));
            aa[4][1] = cursor.getInt(cursor.getColumnIndex("v52"));
            aa[4][2] = cursor.getInt(cursor.getColumnIndex("v53"));
            aa[4][3] = cursor.getInt(cursor.getColumnIndex("v54"));
            aa[4][4] = cursor.getInt(cursor.getColumnIndex("v55"));
        }
        return aa;
    }

    public String gets1()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        String aa="";
        if(cursor.moveToFirst())
        {
            aa = cursor.getString(cursor.getColumnIndex("s1"));
        }
        return aa;
    }
    public String gets2()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        String aa="";
        if(cursor.moveToFirst())
        {
            aa = cursor.getString(cursor.getColumnIndex("s2"));
        }
        return aa;
    }
    public void savegame(String A[][],int V[][],String s1,String s2)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("s1",s1);
            cv.put("s1",s2);
            cv.put("a11",A[0][0]); //These Fields should be your String values of actual column names
            cv.put("a12",A[0][1]);
            cv.put("a13",A[0][2]);
            cv.put("a14",A[0][3]);
            cv.put("a15",A[0][4]);
            cv.put("a21",A[1][0]); //These Fields should be your String values of actual column names
            cv.put("a22",A[1][1]);
            cv.put("a23",A[1][2]);
            cv.put("a24",A[1][3]);
            cv.put("a25",A[1][4]);
            cv.put("a31",A[2][0]); //These Fields should be your String values of actual column names
            cv.put("a32",A[2][1]);
            cv.put("a33",A[2][2]);
            cv.put("a34",A[2][3]);
            cv.put("a35",A[2][4]);
            cv.put("a41",A[3][0]); //These Fields should be your String values of actual column names
            cv.put("a42",A[3][1]);
            cv.put("a43",A[3][2]);
            cv.put("a44",A[3][3]);
            cv.put("a45",A[3][4]);
            cv.put("a51",A[4][0]); //These Fields should be your String values of actual column names
            cv.put("a52",A[4][1]);
            cv.put("a53",A[4][2]);
            cv.put("a54",A[4][3]);
            cv.put("a55",A[4][4]);

            cv.put("v11",V[0][0]); //These Fields should be your String values of actual column names
            cv.put("v12",V[0][1]);
            cv.put("v13",V[0][2]);
            cv.put("v14",V[0][3]);
            cv.put("v15",V[0][4]);
            cv.put("v21",V[1][0]); //These Fields should be your String values of actual column names
            cv.put("v22",V[1][1]);
            cv.put("v23",V[1][2]);
            cv.put("v24",V[1][3]);
            cv.put("v25",V[1][4]);
            cv.put("v31",V[2][0]); //These Fields should be your String values of actual column names
            cv.put("v32",V[2][1]);
            cv.put("v33",V[2][2]);
            cv.put("v34",V[2][3]);
            cv.put("v35",V[2][4]);
            cv.put("v41",V[3][0]); //These Fields should be your String values of actual column names
            cv.put("v42",V[3][1]);
            cv.put("v43",V[3][2]);
            cv.put("v44",V[3][3]);
            cv.put("v45",V[3][4]);
            cv.put("v51",V[4][0]); //These Fields should be your String values of actual column names
            cv.put("v52",V[4][1]);
            cv.put("v53",V[4][2]);
            cv.put("v54",V[4][3]);
            cv.put("v55",V[4][4]);


            db.update(DATABASE_TABLE, cv, i+ "="+1, null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
