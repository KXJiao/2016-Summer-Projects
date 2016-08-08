package cn.truthvision.stopsignproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.*;
import org.opencv.highgui.VideoCapture;
import cn.truthvision.stopsignlib.ImageProc;
import java.io.File;
import java.io.IOException;
import org.opencv.android.Utils;


/**
 * Created by bball on 7/21/2016.
 */
//Although we'll be processing videos, I plan to add this page for the purposes of processing images as a way to familiarize ourselves with th
//different algorithms on opencv.
public class Processing extends AppCompatActivity {
    private String selectedImagePath;
    private String selectedVideoPath;
    private Mat originalImage;
    private Mat sampledImage;
    private Mat videoFrame;
    protected Boolean Using = false;
    private static int RESULT_LOAD_IMAGE = 1;
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    Bitmap[] frames = new Bitmap[31];



    private Video sampledVideo;
    private static int RESULT_LOAD_VIDEO = 2;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this){
        @Override
        public void onManagerConnected(int status){
            switch(status){
                case LoaderCallbackInterface.SUCCESS:{
                    Log.i("StopSignProject", "OpenCV Loaded Successfully");
                    System.loadLibrary("StopSignDetection");
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };
    @Override
    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        Button openGallery = (Button) findViewById(R.id.openGallery);
        openGallery.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               i.setType("*/*");
               //startActivityForResult(i, RESULT_LOAD_IMAGE);
               startActivityForResult(i, RESULT_LOAD_VIDEO);
           }
        });

        /*Button fast = (Button) findViewById(R.id.fastcorner);
        fast.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(sampledImage == null){
                    Context context = getApplicationContext();
                    CharSequence text = "You need to load an image first!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                Mat greyImage = new Mat();
                Imgproc.cvtColor(sampledImage, greyImage, Imgproc.COLOR_RGB2GRAY);
                FindFastFeatures(greyImage.getNativeObjAddr(), sampledImage.getNativeObjAddr());
                displayImage(sampledImage);
            }
        });*/

        /*Button canny = (Button) findViewById(R.id.canny);
        canny.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(sampledImage == null){
                    Context context = getApplicationContext();
                    CharSequence text = "You need to load an image first!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                Mat greyImage = new Mat();
                Imgproc.cvtColor(sampledImage, greyImage, Imgproc.COLOR_RGB2GRAY);
                CannyEdgeDetection(greyImage.getNativeObjAddr(), sampledImage.getNativeObjAddr());
                displayImage(sampledImage);
            }
        });*/

        Button savedVids = (Button) findViewById(R.id.savedvideos);
        savedVids.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(), SavedVideo.class);
                startActivity(i);
            }
        });


    }

    //public native void FindFastFeatures(long matAddrGr, long matAddrRgba);
    //public native void CannyEdgeDetection(long matAddrGr, long matAddrRgba);
    //public native void KalmanFilter_0();
    //public native void estimateRigidTransform_0(long src_nativeObj, long dst_nativeObj, boolean fullAffine);
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == RESULT_LOAD_IMAGE){
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Log.i("StopSignDetection", "selectedImagePath: " + selectedImagePath);
                loadImage(selectedImagePath);
                displayImage(sampledImage);
            }
            else if(requestCode == RESULT_LOAD_VIDEO){
                Bitmap frame = null;
                //int FRAME_BYTES = 350;
                Uri selectedVideoUri = data.getData();
                selectedVideoPath = getVideoPath(selectedVideoUri);
                Uri uri2 = Uri.parse(selectedVideoPath);
                retriever.setDataSource(selectedVideoPath);
                final VideoView videoView = (VideoView) findViewById(R.id.video);
                videoView.setVideoURI(uri2);
                MediaController controller = new MediaController(this);
                controller.setAnchorView(videoView);
                videoView.setMediaController(controller);
                videoView.requestFocus();
                videoView.start();


                //may be used later

                ///displayImage(videoFrame);

                //while(videoView.isPlaying()) {
                /*Log.i("StopSignDetection", "selectedVideoPath: " + selectedVideoPath);
                loadVideo(selectedVideoPath);
                displayVideo(sampledVideo);*/
                ///File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
            }
        }
    }
    private String getPath(Uri uri){
        if(uri == null){
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    private String getVideoPath(Uri uri){
        if(uri == null){
            return null;
        }
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
    private void loadImage(String path){
        originalImage = Highgui.imread(path);
        Mat rgbImage = new Mat();

        Imgproc.cvtColor(originalImage, rgbImage, Imgproc.COLOR_BGR2RGB);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        sampledImage = new Mat();

        double downSampleRatio = ImageProc.calculateSubSampleSize(rgbImage, width, height);
        Imgproc.resize(rgbImage, sampledImage, new Size(), downSampleRatio, downSampleRatio, Imgproc.INTER_AREA);

        try{
            ExifInterface exif = new ExifInterface(selectedImagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            switch(orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    sampledImage = sampledImage.t();
                    Core.flip(sampledImage, sampledImage, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    sampledImage = sampledImage.t();
                    Core.flip(sampledImage, sampledImage, 0);
                    break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /*private static double calculateSubSampleSize(Mat srcImage, int reqWidth, int reqHeight){
        final int height = srcImage.height();
        final int width = srcImage.width();
        double inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final double heightRatio = (double) reqHeight / (double) height;
            final double widthRatio = (double) reqWidth / (double) width;

            inSampleSize = heightRatio<widthRatio ? heightRatio :widthRatio;
        }
        return inSampleSize;
    }*/
    private void displayImage(Mat image){
        Bitmap bitmap = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(image, bitmap);
        ImageView iv = (ImageView) findViewById(R.id.image);
        iv.setImageBitmap(bitmap);
    }

/*


    protected boolean isUsing() {
        synchronized (Using) {
            return Using;
        }
    }

    private final class VideoBitmapLoadTask extends BitmapLoadTask{
        public VideoBitmapLoadTask(ImageView v) {
            super(v);
        }
        @Override
        protected Bitmap doInBackground(Void...v){
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            Bitmap bitmap = null;
            try{
                retriever.setDataSource(selectedVideoPath);
                byte[] data = retriever.getEmbeddedPicture();
                if (*//*!isCancelled() &&*//* isUsing()) {
                    if (data != null) {
                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    }
                    if (bitmap == null) {
                        bitmap = retriever.getFrameAtTime();
                    }
                }
            }
            catch(IllegalArgumentException e){
                Log.e("StopSignDetection", "MediaMetadataRetriever.setDataSource() fail:"
                        + e.getMessage());
            }
            retriever.release();
            return bitmap;
        }

    }
    protected abstract class BitmapLoadTask extends AsyncTask<Void, Void, Bitmap>{
        protected ImageView imageView;

        protected BitmapLoadTask(ImageView v){
            imageView = v;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            if(!isUsing()){
                return;
            }

            if(bitmap == null){
                Log.e("StopSignDetection", "Failed decoding bitmap for file:" + selectedVideoPath);
                return;
            }
            imageView.setImageBitmap(bitmap);

        }
    }*/


}
