package cn.truthvision.stopsignproject;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

        Button c = (Button) findViewById(R.id.dropb);
        c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                test2(v);
            }
        });

        Button d = (Button) findViewById(R.id.viddrop);
        d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                test3(v);
            }
        });

        Button e = (Button) findViewById(R.id.openthat);
        e.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openit(v);
            }
        });


    }

    private void openit(View v) {
        Intent i = new Intent(this, DataBrowser.class);
        startActivity(i);
    }

    private void test3(View v) {

        DBHandlerVideo dbv = new DBHandlerVideo(this,null,null,1);
        SQLiteDatabase s = dbv.getWritableDatabase();
        s.execSQL("DROP TABLE IF EXISTS videos");
        dbv.onCreate(s);
    }

    private void test2(View v) {
        DBHandlerViolation dbv = new DBHandlerViolation(this,null,null,1);
        SQLiteDatabase s = dbv.getWritableDatabase();
        s.execSQL("DROP TABLE IF EXISTS violations");
        dbv.onCreate(s);

    }

    private void test(View v) {
        //shitty code incoming

        DBHandlerVideo dbh = new DBHandlerVideo(this,null,null,1);
        DBHandlerViolation dbv = new DBHandlerViolation(this,null,null,1);
        //SQLiteDatabase sql = dbh.getWritableDatabase();

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
        File[] temp = mediaStorageDir.listFiles();
        ArrayList<Violation> violations = new ArrayList<>();
        Random r = new Random();
        Random r2 = new Random();

        long random = 1000 + r.nextInt(86400000 - 1000 + 1);
        Violation test = new Violation(new Time(random), 1, 20, 33, 1);
        System.out.println(test);
        violations.add(test);

        long random2 = 1000 + r2.nextInt(86400000 - 1000 + 1);
        Violation test2 = new Violation(new Time(random2), 11, 11, 11, 3);
        System.out.println(test2.getDescval());
        violations.add(test2);

        SecureRandom randname = new SecureRandom();
        String randomnamestring = new BigInteger(130, randname).toString(32);

        SecureRandom randuri = new SecureRandom();
        String randomuristring = new BigInteger(130, randuri).toString(32);

        VideoInfo vid = new VideoInfo(randomnamestring, randomuristring, 42, 12, violations);
        dbh.addVideo(vid);
        dbv.addEntry(vid, test);
        dbv.addEntry(vid, test2);
        System.out.println("added test entry");

    }
}
