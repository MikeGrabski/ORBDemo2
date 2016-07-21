package com.example.iosuser12.orbtest;

import android.app.ActionBar;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.ViewGroup;

/**
 * Created by iosuser11 on 7/21/16.
 */
public class PicturePreview extends GLSurfaceView {
    public PicturePreview(Context context) {
        super(context);
        this.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


}
