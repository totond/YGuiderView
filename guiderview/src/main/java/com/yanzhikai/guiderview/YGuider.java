package com.yanzhikai.guiderview;

import android.app.Activity;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yanzhikai.guiderview.Views.MaskLayout;
import com.yanzhikai.guiderview.beans.ScanTarget;
import com.yanzhikai.guiderview.interfaces.OnGuiderClickListener;

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
    private boolean mIsGuiding = false;

    public YGuider(Activity activity){
        mActivity = activity;
        init();
    }

    private void init(){
        FrameLayout decorView = (FrameLayout)mActivity.getWindow().getDecorView();
        LinearLayout linearLayout = (LinearLayout) decorView.getChildAt(0);
        mContentView = (FrameLayout) linearLayout.getChildAt(1);

        mScanRegions = new ArrayList<>();
        mScanTargets = new ArrayList<>();

        buildMask();

    }

    private void buildMask(){
        mMask = new MaskLayout(mActivity,this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMask.setLayoutParams(layoutParams);
        mMask.setScanTargets(mScanTargets);
    }

    public void startGuide(){
        if (!mIsGuiding) {
            if (mMask != null) {
                buildMask();
            }
            mIsGuiding = true;
            mContentView.addView(mMask);
        }

    }

    public void addNextHighlight(View itemView, String text){
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
        ScanTarget scanTarget = new ScanTarget(itemView,text);
        mScanTargets.add(scanTarget);

    }

    public void addNextHighlight(RectF highlightRegion, String text){
        ScanTarget scanTarget = new ScanTarget(highlightRegion,text);
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
                    mIsGuiding = false;
                }
            }
        });

    }

    public void cancelGuide(){
        mMask.exit();
    }

    public void setIsGuiding(boolean isGuiding) {
        mIsGuiding = isGuiding;
    }

    protected boolean getIsGuiding(){
        return mIsGuiding;
    }

    public void setGuiderOnClickListener(OnGuiderClickListener guiderClickListener){
        mMask.setGuiderOnClickListener(guiderClickListener);
    }
}
