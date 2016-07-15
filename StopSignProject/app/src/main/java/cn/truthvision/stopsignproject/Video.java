package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import cn.truthvision.stopsignlib.VideoManager;

public class Video extends AppCompatActivity {

    private int RecOptions;
    private int SaveOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent i = getIntent();

        RecOptions = i.getIntExtra("Record", 1);
        SaveOptions = i.getIntExtra("Save", 1);

        Button settings = (Button) findViewById(R.id.settingsbutton2);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSettings(view);
            }
        });
        Button data = (Button) findViewById(R.id.data);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onData(view);
            }
        });
        Button start_recording = (Button) findViewById(R.id.start_recording);
        start_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartRecording(view);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lol bruh wuddub muh bigguh", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void onSettings(View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finish();
    }

    public void onStartRecording(View view) {
        Intent i = new Intent(this, Recording.class);
        i.putExtra("Record", RecOptions);
        i.putExtra("Save", SaveOptions);
        startActivity(i);

    }


    public void onData(View view) {
        Intent intent = new Intent(this, Data.class);
        startActivity(intent);
        finish();
    }

}
