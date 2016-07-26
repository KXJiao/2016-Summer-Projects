package cn.truthvision.stopsignproject;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoRecording extends AppCompatActivity implements  SurfaceHolder.Callback{


    private Uri fileUri;
    private int RecOptions = 1;
    private int SaveOptions = 1;
    private int DBOptions = 1;
    String uri;

    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;
    Button stopper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recording2);


        Intent i = getIntent();

        RecOptions = i.getIntExtra("Record", 1);
        SaveOptions = i.getIntExtra("Save", 1);
        DBOptions = i.getIntExtra("Database", 1);


        recorder = new MediaRecorder();
        initRecorder();
        setContentView(R.layout.activity_recording2);

        SurfaceView cameraView = (SurfaceView) findViewById(R.id.CameraView);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        stopper = (Button) findViewById(R.id.start);
        stopper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                onClicker(v);
            }
        });
        //stopper.callOnClick();
    }


    private void initRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
        String filepath = mediaStorageDir.getPath();



        uri = filepath + "/" + timeStamp + ".mp4";
        recorder.setOutputFile(filepath + "/" + timeStamp + ".mp4");
        recorder.setMaxDuration(500000000); // 500000 seconds
        recorder.setMaxFileSize(2000000000); // Approximately 2000 megabytes
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
            stopper.callOnClick();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    public void onClicker(View v) {
        if (recording) {
            recorder.stop();
            recording = false;

            Intent i = new Intent(AutoRecording.this,Video.class);
            Toast.makeText(this, "Video saved to:\n" + uri, Toast.LENGTH_LONG).show();


            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                Toast.makeText(this, "This is: " + latlng.toString(), Toast.LENGTH_LONG).show();
            }
            



            startActivity(i);

            // Let's initRecorder so we can record again
            //initRecorder();
            //prepareRecorder();

        } else {
            recording = true;
            recorder.start();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            recorder.stop();
            recording = false;
        }
        recorder.release();
        finish();
    }
}
