package com.example.iosuser12.orbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        if(OpenCVLoader.initDebug())
            Log.d("","exavc");
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

    private void startTracking() {

    }

    private void capture() {

    }

}
