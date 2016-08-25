package cn.truthvision.stopsignproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;

import cn.truthvision.stopsignlib.DBHandlerVideo;
import cn.truthvision.stopsignlib.DBHandlerViolation;
import cn.truthvision.stopsignlib.VideoInfo;
import cn.truthvision.stopsignlib.Violation;

public class ViolationsList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violations_list);
        Intent i = getIntent();
        String s = i.getStringExtra("val");
        displayViolations(s);

    }

    private void displayViolations(String val) {
        String query = "SELECT * FROM videos";
        DBHandlerVideo dbh = new DBHandlerVideo(this,null,null,1);
        ArrayList<VideoInfo> vidarr = new ArrayList<>();
        if(dbh.count()>0) {
            SQLiteDatabase sql = dbh.getWritableDatabase();
            Cursor cursor = sql.rawQuery(query, null);
            cursor.moveToFirst();
            try {
                while (cursor.moveToNext()) {
                    vidarr.add(new VideoInfo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4))));
                }
            } finally {
                cursor.close();
            }
        }

        String vquery = "SELECT * FROM violations";
        DBHandlerViolation dbv = new DBHandlerViolation(this,null,null,1);
        if(dbv.count()>0){
            SQLiteDatabase sql = dbv.getWritableDatabase();
            Cursor cursor = sql.rawQuery(vquery, null);
            cursor.moveToFirst();
            try {
                while(cursor.moveToNext()) {
                    //@TODO: traverse violation database and adds Violations to videos in the list
                    int found = -1;
                    ArrayList<Violation> temp = new ArrayList<>();
                    for(int x = 0; x<vidarr.size(); x++){
                        if(vidarr.get(x).getFileName().equals(cursor.getString(1))){
                            temp = vidarr.get(x).getViolations();
                            Time a = new Time(Long.parseLong(cursor.getString(3)));
                            int b = Integer.parseInt(cursor.getString(4));
                            int c = Integer.parseInt(cursor.getString(5));
                            int d = Integer.parseInt(cursor.getString(6));
                            System.out.println(cursor.getString(7));
                            int e = Integer.parseInt(cursor.getString(7));
                            Violation addedViolation = new Violation(a, b, c, d, e);
                            System.out.println(addedViolation.toString());
                            temp.add(addedViolation);
                            found = x;
                            break;
                        }
                    }
                    if(found>=0) {
                        VideoInfo set = vidarr.get(found);
                        set.setViolations(temp);
                        vidarr.set(found, set);
                    }

                }
            } finally {
                cursor.close();
            }
        }
        VideoInfo theOne = new VideoInfo();
        LinearLayout ll = (LinearLayout) findViewById(R.id.lazy);
        for(int x = 0; x < vidarr.size(); x++){
            if(vidarr.get(x).getFileName().equals(val)){
                theOne = vidarr.get(x);
                break;
            }
        }
        ArrayList<Violation> violationslist = theOne.getViolations();
        for(int x = 0; x< violationslist.size(); x++){
            TextView t = new TextView(this);
            System.out.println(violationslist.get(x).getDescval());
            t.setText(violationslist.get(x).toString());
            ll.addView(t);
        }
        System.out.println("lol");
    }
}
