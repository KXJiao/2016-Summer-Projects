//
// Created by bball on 7/21/2016.
//

#include<jni.h>
#include<opencv2/core/core.hpp>
#include<opencv2/imgproc/imgproc.hpp>
#include<opencv2/features2d/features2d.hpp>
#include<vector>

extern"C"{
    JNIEXPORT void JNICALL
    Java_cn_truthvision_stopsignproject_Processing_FindFastFeatures(JNIEnv*, jobject, jlong addrGray, jlong addrRgba){

        cv::Mat& mGr = *(cv::Mat*)addrGray;
        cv::Mat& mRgb = *(cv::Mat*)addrRgba;
        std::vector<cv::KeyPoint> v;

        cv::FastFeatureDetector detector(50);
        detector.detect(mGr, v);
        for(unsigned int i=0; i < v.size(); i++){
            const cv::KeyPoint& kp=v[i];
            cv::circle(mRgb, cv::Point(kp.pt.x, kp.pt.y), 10, cv::Scalar(255,0,0,255));
        }

    }
}
