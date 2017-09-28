package com.yanzhikai.guiderview;

import android.graphics.RectF;
import android.view.View;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class ScanTarget {
    private View mTargetView;
    private RectF mRegion;
    private boolean mIsRegion = false;

    public ScanTarget(View targetView){
        mTargetView = targetView;
        init();
    }

    public ScanTarget(RectF region){
        mRegion = region;
        init();
    }

    private void init(){
        if (mRegion != null){
            mIsRegion = true;
        }
    }

    public void setRegion(RectF mRegion) {
        this.mRegion = mRegion;
        init();
    }

    public void setTargetView(View mTargetView) {
        this.mTargetView = mTargetView;
        init();
    }

    public RectF getRegion() {
        return mRegion;
    }

    public View getTargetView() {
        return mTargetView;
    }

    public boolean getIsRegion(){
        return mIsRegion;
    }
}
