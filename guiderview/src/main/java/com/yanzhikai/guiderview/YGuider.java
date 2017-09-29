package com.yanzhikai.guiderview;

import android.app.Activity;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class YGuider {
    public static final String TAG = "guiderview";

    private Activity mActivity;
    private FrameLayout mContentView;
    private MaskLayout mMask;
    private ArrayList<RectF> mScanRegions;
    private ArrayList<ScanTarget> mScanTargets;
    private int mContentLocationX = 0, mContentLocationY = 0;

    public YGuider(Activity activity){
        mActivity = activity;
        init();
    }

    private void init(){
        FrameLayout decorView = (FrameLayout)mActivity.getWindow().getDecorView();
        LinearLayout linearLayout = (LinearLayout) decorView.getChildAt(0);
        mContentView = (FrameLayout) linearLayout.getChildAt(1);


        mMask = new MaskLayout(mActivity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMask.setLayoutParams(layoutParams);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(720,1280);

        mScanRegions = new ArrayList<>();
        mScanTargets = new ArrayList<>();

        mMask.setScannerRegions(mScanRegions);

    }

    public void startGuide(){
        mContentView.addView(mMask);
    }

    public void addNextHighlight(View itemView){
//        final ViewTreeObserver observer = itemView.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                int[] viewLocation = {0,0};
//                itemView.getLocationOnScreen(viewLocation);
//                Log.d(TAG, "addNextHighlight: viewLocation0:  " + viewLocation[0]);
//                Log.d(TAG, "addNextHighlight: viewLocation1:  " + viewLocation[1]);
//                RectF region = new RectF(
//                        viewLocation[0],viewLocation[1]
//                        ,viewLocation[0] + itemView.getWidth()
//                        ,viewLocation[1] + itemView.getHeight());
//                addNextHighlight(region);
//
//
//            }
//        });
        ScanTarget scanTarget = new ScanTarget(itemView);
        mScanTargets.add(scanTarget);

    }

    public void addNextHighlight(RectF highlightRegion){
        ScanTarget scanTarget = new ScanTarget(highlightRegion);
        mScanTargets.add(scanTarget);
    }

    private void getContentLocation(){
        int[] contentLocation = {0,0};
        mContentView.getLocationOnScreen(contentLocation);
        mContentLocationX = contentLocation[0];
        mContentLocationY = contentLocation[1];

    }

    public void prepareTarget(){
        final ViewTreeObserver observerD = mContentView.getViewTreeObserver();
        observerD.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                getContentLocation();
                for (ScanTarget scanTarget : mScanTargets){
                    if (!scanTarget.getIsRegion()){
                        int[] location = {0,0};
                        View view = scanTarget.getTargetView();
                        view.getLocationInWindow(location);
                        scanTarget.setRegion(new RectF(
                                location[0]
                                ,location[1]
                                ,location[0] + view.getWidth()
                                ,location[1] + view.getHeight())
                        );
                    }
                    scanTarget.getRegion().offset(-mContentLocationX,-mContentLocationY);
                    mScanRegions.add(scanTarget.getRegion());
                }
            }
        });

    }

    public void cancelGuide(){
        mContentView.removeView(mMask);
    }

    public void setGuiderOnClickListener(OnGuiderClickListener guiderClickListener){
        mMask.setGuiderOnClickListener(guiderClickListener);
    }
}
