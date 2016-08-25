package cn.truthvision.stopsignproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ImageView stopsign = (ImageView) findViewById(R.id.stopsignimage);
        Intent i = getIntent();

        Button s = (Button) findViewById(R.id.button2);
        s.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openSettings(v);
            }
        });

        Button p = (Button) findViewById(R.id.button);
        p.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openVideo(v);
            }
        });

        Button sv = (Button) findViewById(R.id.button3);
        sv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openSavedVideo(v);
            }
        });

        Button test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openTest(v);
            }
        });


    }

    private void openTest(View v) {
        Intent i = new Intent(this, Testing.class);
        startActivity(i);
    }


    private void openVideo(View v) {
        Intent intent = new Intent(this, AutoRecording.class);

        int RecOptions = 1;
        int SaveOptions = 1;
        int DBOptions = 1;

        intent.putExtra("Record", RecOptions);
        intent.putExtra("Save", SaveOptions);
        intent.putExtra("Database", DBOptions);

        startActivity(intent);
    }

    private void openSettings(View v) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    private void openSavedVideo(View v) {
        Intent intent = new Intent(this, SavedVideo.class);
        startActivity(intent);
    }
}
