package cn.truthvision.stopsignlib;

import android.provider.BaseColumns;

/**
 * Created by TV_Laptop_01 on 7/25/2016.
 */
public final class SQLDatabase {
    public SQLDatabase(){}
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }



}
