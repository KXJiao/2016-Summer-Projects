package cn.truthvision.stopsignproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Recording extends AppCompatActivity {

    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);



        Intent i = getIntent();

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        int MEDIA_TYPE_VIDEO = 2;

        File file=getOutputMediaFile(2);
        fileUri = Uri.fromFile(file);
        //fileUri = VideoManager.getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

    }

    private  File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "StopSignVidStore");

        /**Create the storage directory if it does not exist*/
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".png");//errors if deleted
        }
        else if (type == 2) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "VID_" + timeStamp + ".mp4");
        }
        else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //Intent i;
            switch (requestCode) {
                case CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE:

                    Uri uri= fileUri;

                    Toast.makeText(this, "Video saved to:\n" +
                            uri, Toast.LENGTH_LONG).show();
                    
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Video capture failed", Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(this, Video.class);
        startActivity(i);
    }
}
