package cn.truthvision.stopsignproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;

import cn.truthvision.stopsignlib.DBHandlerVideo;
import cn.truthvision.stopsignlib.DBHandlerViolation;
import cn.truthvision.stopsignlib.VideoInfo;
import cn.truthvision.stopsignlib.Violation;

public class Testing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        Button b = (Button) findViewById(R.id.testb);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                test(v);
            }
        });


    }

    private void test(View v) {

        DBHandlerVideo dbh = new DBHandlerVideo(this,null,null,1);
        DBHandlerViolation dbv = new DBHandlerViolation(this,null,null,1);
        //SQLiteDatabase sql = dbh.getWritableDatabase();

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
        File[] temp = mediaStorageDir.listFiles();
        ArrayList<Violation> violations = new ArrayList<>();
        Violation test = new Violation(new Time(100), 1, 2, 3, 1);
        violations.add(test);
        VideoInfo vid = new VideoInfo("test", "uri", 0, 0, violations);
        dbh.addVideo(vid);
        dbv.addEntry(vid, test);
        System.out.println("added test entry");

    }
}
