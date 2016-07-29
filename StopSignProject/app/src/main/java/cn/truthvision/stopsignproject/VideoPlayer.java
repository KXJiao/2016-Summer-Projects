package cn.truthvision.stopsignproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
//import android.widget.RelativeLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by bball on 7/20/2016.
 */
public class VideoPlayer extends Activity {

    VideoView videoView;
    MediaMetadataRetriever dataRetriever;
    Bitmap bmFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Intent i = getIntent();
        String s = i.getStringExtra("URI");



        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");

        File[] temp = mediaStorageDir.listFiles();
//        for(int x = 0; x < mediaStorageDir.listFiles().length; x++){
//            Toast.makeText(this, temp[x].toURI() + "", Toast.LENGTH_LONG).show();
//        }

        videoView = (VideoView)findViewById(R.id.videoView);
        String uriPath2 = s;
        dataRetriever = new MediaMetadataRetriever();
        dataRetriever.setDataSource(s);
        Uri uri2 = Uri.parse(uriPath2);
        videoView.setVideoURI(uri2);
        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnCompletionListener(myVideoViewCompletionListener);
        videoView.setOnPreparedListener(MyVideoViewPreparedListener);
        videoView.setOnErrorListener(myVideoViewErrorListener);

        videoView.requestFocus();
        videoView.start();

        Button captureFrame = (Button) findViewById(R.id.processvid);
        captureFrame.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               int currentFrame = videoView.getCurrentPosition();//milliseconds
               Toast.makeText(getApplicationContext(), "Current Position: " + currentFrame + "(ms)", Toast.LENGTH_LONG).show();
               bmFrame = dataRetriever.getFrameAtTime(currentFrame*1000); //microseconds

               if(bmFrame==null){
                   Toast.makeText(getApplicationContext(),
                           "bmFrame == null!",
                           Toast.LENGTH_LONG).show();
               }
               else{
                   AlertDialog.Builder myCaptureDialog =
                           new AlertDialog.Builder(VideoPlayer.this);

                   RelativeLayout frameLayout = new RelativeLayout(VideoPlayer.this);
                   ImageView frame = new ImageView(VideoPlayer.this);
                   Button save = new Button(VideoPlayer.this);
                   save.setText("Save");
                   frame.setImageBitmap(bmFrame);
                   frame.setMaxWidth(LayoutParams.MATCH_PARENT);
                   frame.setMaxHeight(LayoutParams.MATCH_PARENT);
                   frameLayout.addView(frame);
                   frameLayout.addView(save);


                   LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                   //frame.setLayoutParams(params);
                   //save.setLayoutParams(params);
                   frameLayout.setLayoutParams(params);
                   myCaptureDialog.setView(frameLayout);
                   myCaptureDialog.show();


                   save.setOnClickListener(new View.OnClickListener(){
                       @Override
                       public void onClick(View view){
                            saveImage(bmFrame);
                       }
                   });
               }
           }
        });
    }

    private File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "StopSignVidFrames");

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".png");//errors if deleted
        } else if (type == 2) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void saveImage(Bitmap bitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File myDir = new File(root + "/Pictures/StopSignVidFrames");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+n+"TimeStamp- "+timeStamp+".jpg";
        File file = new File(myDir, fname);
        if(file.exists())
            file.delete();
        try{
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    MediaPlayer.OnCompletionListener myVideoViewCompletionListener =
            new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer arg0) {
                    Toast.makeText(VideoPlayer.this, "End of Video",
                            Toast.LENGTH_LONG).show();
                }
            };

    MediaPlayer.OnPreparedListener MyVideoViewPreparedListener =
            new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {

                    long duration = videoView.getDuration(); //in millisecond
                    Toast.makeText(VideoPlayer.this,
                            "Duration: " + duration + " (ms)",
                            Toast.LENGTH_LONG).show();

                }
            };

    MediaPlayer.OnErrorListener myVideoViewErrorListener =
            new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {

                    Toast.makeText(VideoPlayer.this,
                            "Error!!!",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
            };

}
