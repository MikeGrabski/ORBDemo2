package com.example.iosuser12.orbtest;

/**
 * Created by iosuser12 on 7/21/16.
 */import android.app.ActionBar;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private byte[] data;
    private Camera.PreviewCallback previewCallback;
    private boolean frameread;

    public CameraPreview(Context context) {
        super(context);
        this.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);

        mHolder = getHolder();
        mHolder.addCallback(this);
        previewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] bytes, Camera camera) {
                if(frameread == true) {
                    frameread = false;
                    data = bytes;
                    frameread = true;
                }
            }
        };
        mCamera.setPreviewCallback(previewCallback);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("CameraPreview", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        mCamera.stopPreview();

        // make any resize, rotate or reformatting changes here


        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d("CameraPreview", "Error starting camera preview: " + e.getMessage());
        }
    }

    byte[] getCurrentFrame()
    {
        return data;
    }

    int getPreviewFormat() {
        return mCamera.getParameters().getPreviewFormat();
    }

    int getPreviewHeight() {
        return mCamera.getParameters().getPreviewSize().height;
    }

    int getPreviewWidth() {
        return mCamera.getParameters().getPreviewSize().width;
    }


}