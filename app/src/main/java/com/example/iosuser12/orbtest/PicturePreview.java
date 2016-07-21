package com.example.iosuser12.orbtest;

import android.app.ActionBar;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.ViewGroup;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by iosuser11 on 7/21/16.
 */
public class PicturePreview extends GLSurfaceView implements GLSurfaceView.Renderer {
    public PicturePreview(Context context) {
        super(context);
        //this.setLayoutParams());
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
