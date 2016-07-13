package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Video extends AppCompatActivity {
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        
        Button settings = (Button) findViewById(R.id.settingsbutton2);
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onSettings(view);
            }
        });
        Button data = (Button) findViewById(R.id.data);
        data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onData(view);
            }
        });
        Button start_recording = (Button) findViewById(R.id.start_recording);
        start_recording.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onStartRecording(view);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    
    public void onSettings(View view){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finish();
    }

    public void onStartRecording(View view){

    }

    public void onData(View view){
        Intent intent = new Intent(this, Data.class);
        startActivity(intent);
        finish();
    }

}
