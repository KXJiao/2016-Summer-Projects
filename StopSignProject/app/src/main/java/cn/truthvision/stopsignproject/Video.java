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
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent i = getIntent();




        /*Note: I will be editing the video portion so please do not touch the code between these comments
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
         */




        //create new Intent
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        int MEDIA_TYPE_VIDEO = 2;

        fileUri = VideoManager.getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

        // start the Video Capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);



        /*
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
        **************************************************************************************************
         */



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

    }



    public void onData(View view) {
        Intent intent = new Intent(this, Data.class);
        startActivity(intent);
        finish();
    }


    //Intents for Video
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Video saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        }
    }
}
