package cn.truthvision.stopsignproject;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.media.MediaExtractor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import cn.truthvision.stopsignlib.DBHandlerVideo;
import cn.truthvision.stopsignlib.DBHandlerViolation;
import cn.truthvision.stopsignlib.VideoInfo;
import cn.truthvision.stopsignlib.Violation;


public class AutoRecording extends Activity implements SurfaceHolder.Callback, CameraBridgeViewBase.CvCameraViewListener2 {


    private Uri fileUri;
    private int RecOptions = 1;
    private int SaveOptions = 1;
    private int DBOptions = 1;

    private Mat mRgba, mGray;
    private VideoCapture Camera;
    String uri;
    String filename;

    MediaExtractor extractor;
    Queue<Mat> frames;
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;
    Button stopper;

    private VideoCapture mCamera;
    private Date date;
    private ArrayList<VideoInfo> snips;

    CameraBridgeViewBase cameraView;
    //private Queue<Mat> FrameBuffer = new LinkedList<>();
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(AutoRecording.this){
        @Override
        public void onManagerConnected(int status){
            switch(status){
                case LoaderCallbackInterface.SUCCESS:{
                    Log.i("StopSignProject", "OpenCV Loaded Successfully");
                    //setContentView(R.layout.activity_recording2);

                    cameraView.enableView();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording2);



        Intent i = getIntent();

        RecOptions = i.getIntExtra("Record", 1);
        SaveOptions = i.getIntExtra("Save", 1);
        DBOptions = i.getIntExtra("Database", 1);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));


        recorder = new MediaRecorder();
        initRecorder();

        cameraView = (CameraBridgeViewBase) findViewById(R.id.CameraView);
        cameraView.setCvCameraViewListener(this);
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
    @Override
    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    }
    public void run() throws IOException{
        while (true) {
            //Bitmap bmp = null;
            //Mat rgba = new Mat();


            synchronized (this) {
                if (mCamera == null)
                    break;
                if (!mCamera.grab())
                    break;


                //1. retrieve mat using videocapture
                //mCamera.retrieve(newmat);
                //2. get the current timestamp
                //maybe access system time

                //mCamera.retrieve(rgba, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);
                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);
                int minutes = c.get(Calendar.MINUTE);
                int hours = c.get(Calendar.HOUR);
                String time = hours+":"+minutes+":"+seconds;
                date = c.getTime();

                //Mat newFrame = updateBuffer(rgba, date);

                //internally, maybe generate an event randomly
                //bool detected = DetectSomething(newMat, time);

                //if (detected) {
                //1. Go to my buffer to save multiple mats/frames to a file
                //SaveEvent();

                //}

                //boolean detected = detectFeature(newFrame, date);

                //if(detected)
                //saveFrame(newFrame);
                //}



                //bmp = processFrame(mCamera);
            }
            /*if (bmp != null) {
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    canvas.drawBitmap(bmp, (canvas.getWidth()  - bmp.getWidth())  / 2,
                            (canvas.getHeight() - bmp.getHeight()) / 2, null);
                    getHolder().unlockCanvasAndPost(canvas);
                }
                bmp.recycle();
            }*/
        }
    }

    /*public Mat updateBuffer(Mat mat, Date date){
        final int maxQueueSize = 50;
        oldest = new Mat();
        newest = new Mat();
        Iterator<Mat> it = FrameBuffer.iterator();
        if(FrameBuffer.size() > maxQueueSize){
            oldest = FrameBuffer.poll();
            FrameBuffer.add(mat);
            return oldest;
        }
        else{
            FrameBuffer.add(mat);
        }
        return FrameBuffer.peek();
    }*/

    private void saveFrame(Mat frame){

        /*File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
        String filepath = mediaStorageDir.getPath();
        CharSequence timestamp = DateFormat.format("yyyy_MM_dd-HH:mm:ss", date);
        Bitmap bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(frame, bm);
        String fname = "Frame-" + timestamp;
        File file = new File(mediaStorageDir, fname);
        try{
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }*/

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.e("StopSignDetection", "Failed to create directory");
            }
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath()+File.separator+"StopSignFrame_"+timestamp+".png");
        Log.w("StopSignDetection", "save image to" + mediaFile.toString());
        Highgui.imwrite(mediaFile.toString(), frame);

    }



    private void initRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss").format(new Date());
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return;
            }
        }
        String filepath = mediaStorageDir.getPath();


        filename = timeStamp;
        uri = filepath + "/"  + timeStamp+ ".mp4";
        recorder.setOutputFile(filepath + "/" + timeStamp+".mp4");
        recorder.setMaxDuration(500000000); // 500000 seconds
        recorder.setMaxFileSize(2000000000); // Approximately 2000 megabytes
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
            //();
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
            //recorder.stop();
            recording = false;



            Intent i = new Intent(AutoRecording.this,Video.class);
            Toast.makeText(this, "Video saved to:\n" + uri, Toast.LENGTH_LONG).show();


            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                DBHandlerVideo dbh = new DBHandlerVideo(this,null,null,1);
                ArrayList<Violation> temp = getViolations();
                if(temp != null) {
                    VideoInfo vid = new VideoInfo(filename, uri, location.getLatitude(), location.getLongitude(), temp);
                    System.out.println(vid);
                    System.out.println(dbh.addVideo(vid));
                }

                //code for the database
                /*DBHandlerVideo dbh = new DBHandlerVideo(this,null,null,1);
                VideoInfo vid = new VideoInfo(filename,uri,location.getLatitude(),location.getLongitude());*//*
                System.out.println(vid);
                System.out.println(dbh.addVideo(vid));*/
                //System.out.println(FrameBuffer.size()+" Frames");
                //Toast.makeText(this, vid.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "This is: " + latlng.toString(), Toast.LENGTH_LONG).show();


            }





            startActivity(i);

            // Let's initRecorder so we can record again
            //initRecorder();
            //prepareRecorder();

        } else {
            recording = true;
            //recorder.start();
        }
    }
    private ArrayList<Violation> getViolations() {
        //@TODO: this method will return an arraylist of Violations
        ArrayList<Violation> violations = new ArrayList<>();
        DBHandlerViolation dbv = new DBHandlerViolation(this,null,null,1);
        SQLiteDatabase sql = dbv.getWritableDatabase();
        String query = "SELECT * FROM violations";

        for(int x = 0; x < snips.size(); x++){ // snips is list of Video snippets
            VideoInfo tempvid = snips.get(x);
            Cursor cursor = sql.rawQuery(query, null);
            /*ArrayList<Violation> temp = tempvid.getViolations();
            for(int y = 0; y < tempvid.getViolations().size(); y++){// traverses the list of violations in each snippet
                violations.add(temp.get(y));
            }*/
            cursor.close();
        }
        return violations;
    }
    public void surfaceCreated(SurfaceHolder holder) {
        //prepareRecorder();
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

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        Mat rgba = inputFrame.rgba();
        saveFrame(rgba);
        return rgba;
    }
    public void onCameraViewStarted(int width, int height){
        prepareRecorder();
        mRgba = new Mat();
        mGray = new Mat();
    }
    public void onCameraViewStopped(){
        mRgba.release();
        mGray.release();
        if (recording) {
            //recorder.stop();
            recording = false;
        }
        recorder.release();
        finish();
    }



}
