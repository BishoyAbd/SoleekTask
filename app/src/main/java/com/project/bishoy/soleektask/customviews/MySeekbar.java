package com.project.bishoy.soleektask.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.project.bishoy.soleektask.R;

/**
 * Created by bisho on 1/25/2018.
 */public class MySeekbar extends CrystalSeekbar {

    public MySeekbar(Context context) {
        super(context);
    }

    public MySeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }





    @Override
    protected int getLeftThumbColor(TypedArray typedArray) {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    protected int getLeftThumbColorPressed(TypedArray typedArray) {
        return Color.parseColor("#FFFFFF");
    }


//
//    @Override
//    protected Drawable getLeftDrawablePressed(TypedArray typedArray) {
//        return ContextCompat.getDrawable(getContext(), R.drawable.thumb_pressed);
//    }
}