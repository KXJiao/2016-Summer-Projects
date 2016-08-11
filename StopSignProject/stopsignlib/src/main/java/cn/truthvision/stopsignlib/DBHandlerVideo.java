package cn.truthvision.stopsignlib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by TV_Laptop_01 on 7/25/2016.
 */
public class DBHandlerVideo extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VideoDB.db";
    private static final String TABLE_VIDEOS = "videos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FILENAME = "filename";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    //public static final String COLUMN_VIOLATION = "violation";

    private SQLiteDatabase mDatabase;

    public DBHandlerVideo(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIDEOS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_FILENAME + " TEXT," + COLUMN_URI + " TEXT," + COLUMN_LAT + " REAL," +  COLUMN_LNG + " REAL" + ")";
        db.execSQL(CREATE_VIDEOS_TABLE);
        mDatabase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);
        onCreate(db);
    }

    //Methods
    public boolean addVideo(VideoInfo video) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_FILENAME, video.getFileName());
        values.put(COLUMN_URI, video.getURI());
        values.put(COLUMN_LAT, video.getLat());
        values.put(COLUMN_LNG, video.getLng());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_VIDEOS, null, values);
        db.close();
        return true;
    }

    public VideoInfo findVideo(String filename) {
        String query = "Select * FROM " + TABLE_VIDEOS + " WHERE " + COLUMN_FILENAME + " =  \"" + filename + "\"";

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

    public boolean deleteVideo(String filename) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_VIDEOS + " WHERE " + COLUMN_FILENAME + " =  \"" + filename + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        VideoInfo vid = new VideoInfo();

        if (cursor.moveToFirst()) {
            vid.setID(Integer.parseInt(cursor.getString(0)));
            //vid.setFileName(cursor.getString(0));
            db.delete(TABLE_VIDEOS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(vid.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public VideoInfo findID(int id) {
        String query = "Select * FROM " + TABLE_VIDEOS + " WHERE " + COLUMN_ID+ " =  \"" + id + "\"";

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
        String sql = "SELECT COUNT(*) FROM " + TABLE_VIDEOS;

        SQLiteDatabase db = this.getWritableDatabase();
        long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM videos", null);

        return numRows;
    }

}