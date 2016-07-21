package com.example.iosuser12.orbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;


/**
 * Created by iosuser12 on 7/19/16.
 */
public class MainActivity extends Activity{
    Button capture;
    Button startTracking;
    FrameLayout cameraView;
    TextView numOfMatches;
    TextView isSameObject;

    CameraPreview cameraPreview;

    byte[] currentPhoto;
    int width;
    int height;
    private int previewFormat;

    FeatureDetector detector;
    DescriptorExtractor descriptor;



    DescriptorMatcher matcher;
    Mat img1;
    Mat descriptors1;
    MatOfKeyPoint keypoints1;

    Mat img2 ;
    Mat descriptors2 ;
    MatOfKeyPoint keypoints2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        if(OpenCVLoader.initDebug())
            Log.d("","exav");
        capture = (Button)findViewById(R.id.captureFrame);
        startTracking  = (Button)findViewById(R.id.startTracking);
        cameraView = (FrameLayout)findViewById(R.id.cameraView);
        numOfMatches = (TextView)findViewById(R.id.numOfMatches);
        isSameObject = (TextView)findViewById(R.id.isSameObject);
        cameraPreview = new CameraPreview(getApplicationContext());
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture();
            }
        });
        startTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTracking();
            }
        });

        cameraView.addView(cameraPreview);
        }

    @Override
    protected void onStart() {
        super.onStart();
        previewFormat = cameraPreview.getPreviewFormat();

    }

    private void startTracking() {
        if(currentPhoto==null)
        {
            Toast.makeText(getApplicationContext(),"Please Capture First!",Toast.LENGTH_SHORT).show();
        }


        img2.put(0,0,cameraPreview.getCurrentFrame());
        detector.detect(img2, keypoints2);
        descriptor.compute(img2, keypoints2, descriptors2);

        //matcher should include 2 different image's descriptors
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1,descriptors2,matches);
        //feature and connection colors
        Scalar RED = new Scalar(255,0,0);
        Scalar GREEN = new Scalar(0,255,0);
        //output image
        Mat outputImg = new Mat();
        MatOfByte drawnMatches = new MatOfByte();
        //this will draw all matches, works fine
        Features2d.drawMatches(img1, keypoints1, img2, keypoints2, matches,
                outputImg, GREEN, RED,  drawnMatches, Features2d.NOT_DRAW_SINGLE_POINTS);

    }

    private void capture() {
        detector = FeatureDetector.create(FeatureDetector.ORB);
        descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);;
        matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        width = cameraPreview.getWidth();
        height = cameraPreview.getHeight();
        //first image
        currentPhoto = cameraPreview.getCurrentFrame();
        img1 = new Mat(width, height, CvType.CV_8UC3);
        img1.put(0,0,currentPhoto);
        descriptors1 = new Mat();

        img2 = new Mat(width, height, CvType.CV_8UC3);
        descriptors2 = new Mat();
        keypoints2 = new MatOfKeyPoint();


        keypoints1 = new MatOfKeyPoint();

        detector.detect(img1, keypoints1);
        descriptor.compute(img1, keypoints1, descriptors1);
    }
    //change

}
