package com.example.iosuser12.orbtest;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

    int matchnumber;


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
        matchnumber = 0;
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
        setPreviewFormat();
        cameraView.addView(cameraPreview);
        cameraPreview.startPreview();
        }

    @Override
    protected void onStart() {
        super.onStart();

    }
    private void setPreviewFormat(){
        List<Integer> format = cameraPreview.getSupportedPreiewFormats();
        for (int i = 0; i < format.size(); i++){
            if(format.get(i)== ImageFormat.NV21) {
                previewFormat = ImageFormat.NV21;
            }
        }
        if(previewFormat != ImageFormat.NV21)
            Toast.makeText(getApplicationContext(),"Your phone not supported yet", Toast.LENGTH_LONG).show();
        cameraPreview.setPreviewFormat(previewFormat);

    }
    private void startTracking() {
        if(currentPhoto==null)
        {
            Toast.makeText(getApplicationContext(),"Please Capture First!",Toast.LENGTH_SHORT).show();
            return;
        }
        MatchImages matchImages = new MatchImages();

           matchImages.execute();
            /*img2.put(0, 0, cameraPreview.getCurrentFrame());
            detector.detect(img2, keypoints2);
            descriptor.compute(img2, keypoints2, descriptors2);

            //matcher should include 2 different image's descriptors
            MatOfDMatch matches = new MatOfDMatch();
            matcher.match(descriptors1, descriptors2, matches);
            List<DMatch> match = matches.toList();
            numOfMatches.setText("Number of Matches: " + match.size());
            //feature and connection colors*/
    }

    private void capture() {
        detector = FeatureDetector.create(FeatureDetector.ORB);
        descriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);;
        matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);

        width = cameraPreview.getPreviewWidth();
        height = cameraPreview.getPreviewHeight();
        //first image
        currentPhoto = cameraPreview.getCurrentFrame();
        Log.d("Photo: ", ""+currentPhoto[564]);

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
    private class MatchImages extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {

            matchImages();
            return  null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            numOfMatches.setText("NumOfMatches: " + matchnumber);
        }
    }
    public void matchImages(){
        while(true) {
            byte[] data = cameraPreview.getCurrentFrame();
            Log.d("Photo: " , "" + data[564]);
            img2.put(0, 0, data);

            Log.d("mmages: ", "got current frame");
            detector.detect(img2, keypoints2);
            Log.d("mmages: ", "detected keypoints");
            descriptor.compute(img2, keypoints2, descriptors2);
            Log.d("mmages: ", "got descriptors");
    //matcher should include 2 different image's descriptors
            MatOfDMatch matches = new MatOfDMatch();
            matcher.match(descriptors1, descriptors2, matches);


            int DIST_LIMIT = 60;
            List<DMatch> matchesList = matches.toList();
            List<DMatch> matches_final= new ArrayList<DMatch>();
            for(int i=0; i<matchesList.size(); i++) {
                if (matchesList.get(i).distance <= DIST_LIMIT) {
                    matches_final.add(matches.toList().get(i));
                }
            }

            matchnumber = matches_final.size();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    numOfMatches.setText("Number of Matches: "+matchnumber);

                }
            });


            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //return matches.toList().size();

    }
}
