package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.truthvision.stopsignlib.VideoManager;

public class Recording extends AppCompatActivity {

    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        Intent i = getIntent();

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        int MEDIA_TYPE_VIDEO = 2;

        fileUri = VideoManager.getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

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
                Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Video capture failed", Toast.LENGTH_LONG).show();
            }
        }

        Intent i = new Intent(this, Video.class);
        startActivity(i);

    }
}
