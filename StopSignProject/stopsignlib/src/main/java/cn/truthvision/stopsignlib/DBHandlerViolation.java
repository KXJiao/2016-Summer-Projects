package cn.truthvision.stopsignlib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;

/**
 * Created by TV_Laptop_01 on 8/9/2016.
 */
public class DBHandlerViolation extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ViolationDB.db";
    private static final String TABLE_VIOLATIONS = "violations";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FILENAME = "filename";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_HR = "hour";
    public static final String COLUMN_MIN = "minute";
    public static final String COLUMN_SEC = "second";
    public static final String COLUMN_DESC = "description";



    private SQLiteDatabase mDatabase;

    public DBHandlerViolation(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIOLATIONS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_FILENAME + " TEXT," + COLUMN_URI + " TEXT" + COLUMN_TIME + " REAL," + COLUMN_HR + " INTEGER," + COLUMN_MIN + " INTEGER," + COLUMN_SEC + " INTEGER," + COLUMN_DESC + " INTEGER" +  ")";
        db.execSQL(CREATE_VIDEOS_TABLE);
        mDatabase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIOLATIONS);
        onCreate(db);
    }

    //Methods
    public boolean addVideo(VideoInfo video, Violation v) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_FILENAME, video.getFileName());
        values.put(COLUMN_URI, video.getURI());
        values.put(COLUMN_TIME, v.getTime().getTime());
        long time = Time.valueOf(v.getVidTime()).getTime();
        int hr = ((int) time)/3600;
        int min = (((int) time)%3600)/60;
        int sec = ((int) time)%60;



        values.put(COLUMN_HR, hr);
        values.put(COLUMN_MIN, min);
        values.put(COLUMN_SEC, sec);
        values.put(COLUMN_DESC, v.getDesc());

        //values.put(COLUMN_LAT, video.getLat());
        //values.put(COLUMN_LNG, video.getLng());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_VIOLATIONS, null, values);
        db.close();
        return true;
    }

    public VideoInfo findVideo(String filename) {
        String query = "Select * FROM " + TABLE_VIOLATIONS + " WHERE " + COLUMN_FILENAME + " =  \"" + filename + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        VideoInfo vid = new VideoInfo();
        Violation v = new Violation();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            vid.setID(Integer.parseInt(cursor.getString(0)));
            vid.setFileName(cursor.getString(1));
            vid.setURI(cursor.getString(2));
            v.setTime(new Time(Long.parseLong(cursor.getString(3))));
            v.setVidTime(Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6)));
            v.setDesc(Integer.parseInt(cursor.getString(7)));
            cursor.close();
        } else {
            vid = null;
        }
        db.close();
        return vid;
    }

    public boolean deleteVideo(String filename) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_VIOLATIONS + " WHERE " + COLUMN_FILENAME + " =  \"" + filename + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        VideoInfo vid = new VideoInfo();

        if (cursor.moveToFirst()) {
            vid.setID(Integer.parseInt(cursor.getString(0)));
            //vid.setFileName(cursor.getString(0));
            db.delete(TABLE_VIOLATIONS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(vid.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public VideoInfo findID(int id) {
        String query = "Select * FROM " + TABLE_VIOLATIONS + " WHERE " + COLUMN_ID+ " =  \"" + id + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        VideoInfo vid = new VideoInfo();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            vid.setID(Integer.parseInt(cursor.getString(0)));
            vid.setFileName(cursor.getString(1));
            vid.setURI(cursor.getString(2));
            vid.setLatLng(Double.parseDouble(cursor.getString(3)),Double.parseDouble(cursor.getString(4)));
            cursor.close();
        } else {
            vid = null;
        }
        db.close();
        return vid;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_VIOLATIONS;

        SQLiteDatabase db = this.getWritableDatabase();

        return DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM videos", null);
    }

}