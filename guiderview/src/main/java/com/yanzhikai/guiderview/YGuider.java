package com.yanzhikai.guiderview;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class YGuider {
    public static final String TAG = "guiderview";

    private Activity mActivity;
    private FrameLayout mDecorView;
    private MaskLayout mMask;

    public YGuider(Activity activity){
        mActivity = activity;
        init();
    }

    public void init(){
        mDecorView = (FrameLayout)mActivity.getWindow().getDecorView();
        mMask = new MaskLayout(mActivity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(720,1280);
        mDecorView.addView(mMask,layoutParams);
    }
}
