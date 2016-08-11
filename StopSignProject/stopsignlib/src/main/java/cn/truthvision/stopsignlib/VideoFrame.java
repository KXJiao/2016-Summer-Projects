package cn.truthvision.stopsignlib;

import org.opencv.core.Mat;

import java.util.Date;

/**
 * Created by bball on 8/8/2016.
 */
public class VideoFrame {

    private Mat mat;
    private Date date;

    public VideoFrame(Mat m, Date d){
        mat = m;
        date = d;
    }

    public Mat retrieveMat(){
        return mat;
    }

    public Date getDate(){
        return date;
    }

}
