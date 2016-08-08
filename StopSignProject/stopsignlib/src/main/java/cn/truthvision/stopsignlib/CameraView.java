package cn.truthvision.stopsignproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cn.truthvision.stopsignlib.VideoFrame;

/**
 * Created by bball on 7/29/2016.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private VideoCapture mCamera;
    private Mat oldest, newest;
    private Date date;
    private Queue<Mat> FrameBuffer = new LinkedList<Mat>();
    public CameraView(Context context){
        super(context);
        getHolder().addCallback(this);
    }

    public void run(){
        while (true) {
            Bitmap bmp = null;
            Mat rgba = new Mat();
            synchronized (this) {
                if (mCamera == null)
                    break;
                if (!mCamera.grab())
                    break;


                //1. retrieve mat using videocapture
                //mCamera.retrieve(newmat);
                //2. get the current timestamp
                //maybe access system time

                mCamera.retrieve(rgba, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);
                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);
                int minutes = c.get(Calendar.MINUTE);
                int hours = c.get(Calendar.HOUR);
                String time = hours+":"+minutes+":"+seconds;
                date = c.getTime();

                Mat newFrame = updateBuffer(rgba, date);

                //internally, maybe generate an event randomly
                //bool detected = DetectSomething(newMat, time);

                //if (detected) {
                //1. Go to my buffer to save multiple mats/frames to a file
                //SaveEvent();

                //}

                boolean detected = detectFeature(newFrame, date);

                if(detected){
                    saveFrame(newFrame);
                }



                bmp = processFrame(mCamera);
            }
            if (bmp != null) {
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    canvas.drawBitmap(bmp, (canvas.getWidth()  - bmp.getWidth())  / 2,
                            (canvas.getHeight() - bmp.getHeight()) / 2, null);
                    getHolder().unlockCanvasAndPost(canvas);

                }
                bmp.recycle();
            }
        }
    }

    public Mat updateBuffer(Mat mat, Date date){
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
    }

    private boolean detectFeature(Mat mat, Date date){

        if(DateFormat.format("yyyy_MM_dd-HH:mm:ss", date) == "2016_08_08-16:32:00"){
            return true;
        }
        return false;
    }

    private void saveFrame(Mat frame){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
        String filepath = mediaStorageDir.getPath();
        CharSequence timestamp = DateFormat.format("yyyy_MM_dd-HH:mm:ss", date);

        Bitmap bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(frame, bm);
        String fname = "Frame-" + timestamp;

        File file = new File(mediaStorageDir, fname);
        if(file.exists())
            file.delete();
        try{
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    protected Bitmap processFrame(VideoCapture capture) {
        Mat mRgba = new Mat();
        capture.retrieve(mRgba, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);
        //process mRgba
        Bitmap bmp = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888);
        try {
            Utils.matToBitmap(mRgba, bmp);
        } catch(Exception e) {
            Log.e("processFrame", "Utils.matToBitmap() throws an exception: " + e.getMessage());
            bmp.recycle();
            bmp = null;
        }
        return bmp;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        (new Thread(this)).start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraRelease();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        cameraSetup(width, height);
    }

    public boolean cameraOpen() {
        synchronized (this) {
            cameraRelease();
            mCamera = new VideoCapture(Highgui.CV_CAP_ANDROID);
            if (!mCamera.isOpened()) {
                mCamera.release();
                mCamera = null;
                Log.e("HelloOpenCVView", "Failed to open native camera");
                return false;
            }
        }
        return true;
    }

    private void cameraRelease() {
        synchronized(this) {
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    private void cameraSetup(int width, int height) {
        synchronized (this) {
            if (mCamera != null && mCamera.isOpened()) {
                List<Size> sizes = mCamera.getSupportedPreviewSizes();
                int mFrameWidth = width;
                int mFrameHeight = height;
                { // selecting optimal camera preview size
                    double minDiff = Double.MAX_VALUE;
                    for (Size size : sizes) {
                        if (Math.abs(size.height - height) < minDiff) {
                            mFrameWidth = (int) size.width;
                            mFrameHeight = (int) size.height;
                            minDiff = Math.abs(size.height - height);
                        }
                    }
                }
                mCamera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, mFrameWidth);
                mCamera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, mFrameHeight);
            }
        }
    }

    public Mat getCurrentFrame(VideoCapture vc){
        Mat currentFrame = new Mat();
        if(vc.isOpened()){
            vc.read(currentFrame);
        }

        return currentFrame;
    }

}
