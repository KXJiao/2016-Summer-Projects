package cn.truthvision.stopsignproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import java.util.List;

/**
 * Created by bball on 7/29/2016.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private VideoCapture mCamera;
    public CameraView(Context context){
        super(context);
        getHolder().addCallback(this);
    }

    public void run(){
        while (true) {
            Bitmap bmp = null;
            synchronized (this) {
                if (mCamera == null)
                    break;
                if (!mCamera.grab())
                    break;

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
}
