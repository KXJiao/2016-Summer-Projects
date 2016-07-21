package cn.truthvision.stopsignlib;

import android.graphics.Point;
import android.media.ExifInterface;
import android.view.Display;
import android.view.WindowManager;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import android.app.Activity;

/**
 * Created by bball on 7/12/2016.
 */
public class ImageProc {

    public static double calculateSubSampleSize(Mat srcImage, int reqWidth, int reqHeight){
        final int height = srcImage.height();
        final int width = srcImage.width();
        double inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final double heightRatio = (double) reqHeight / (double) height;
            final double widthRatio = (double) reqWidth / (double) width;

            inSampleSize = heightRatio<widthRatio ? heightRatio :widthRatio;
        }
        return inSampleSize;
    }

}
