package com.yanzhikai.guiderview;

import android.app.Activity;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class YGuider {
    public static final String TAG = "guiderview";

    private Activity mActivity;
    private FrameLayout mDecorView;
    private MaskLayout mMask;
    private ArrayList<RectF> mScanRegions;

    public YGuider(Activity activity){
        mActivity = activity;
        init();
    }

    private void init(){
        mDecorView = (FrameLayout)mActivity.getWindow().getDecorView();
        mMask = new MaskLayout(mActivity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMask.setLayoutParams(layoutParams);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(720,1280);

        mScanRegions = new ArrayList<>();

        mMask.setScannerRegions(mScanRegions);

    }

    public void startGuide(){
        mDecorView.addView(mMask);
    }

    public void addNextHighlight(final View itemView){
        final ViewTreeObserver observer = itemView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int[] viewLocation = {0,0};
                itemView.getLocationOnScreen(viewLocation);
                Log.d(TAG, "addNextHighlight: viewLocation0:  " + viewLocation[0]);
                Log.d(TAG, "addNextHighlight: viewLocation1:  " + viewLocation[1]);
                RectF region = new RectF(
                        viewLocation[0],viewLocation[1]
                        ,viewLocation[0] + itemView.getWidth()
                        ,viewLocation[1] + itemView.getHeight());
                addNextHighlight(region);


            }
        });

    }

    public void addNextHighlight(RectF highlightRegion){
        mScanRegions.add(highlightRegion);
    }

    public void cancelGuide(){
        mDecorView.removeView(mMask);
    }

    public void setGuiderOnClickListener(GuiderOnClickListener guiderClickListener){
        mMask.setGuiderOnClickListener(guiderClickListener);
    }
}
