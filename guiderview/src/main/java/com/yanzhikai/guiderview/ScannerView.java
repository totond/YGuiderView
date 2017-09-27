package com.yanzhikai.guiderview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class ScannerView extends View {
    public static final int GONE = 0, MOVING = 1, EXPANDING = 2, STAY_EXPANDED = 3;
    @IntDef({GONE, MOVING, EXPANDING, STAY_EXPANDED})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface ScannerState {}
    private @Scanner.ScannerState
    int mState = GONE;

    private Paint sPaint;
    private RectF sRegion = new RectF();
    private float offsetX = 0, offsetY = 0;
    private float sLeft = 0, sTop = 0, sRight = 0, sBottom = 0;
    private int scanIndex = 0;

    public ScannerView(Context context, float sLeft, float sTop, float sBottom, float sRight) {
        super(context);
        sRegion.left = sLeft;
        sRegion.top = sTop;
        sRegion.bottom = sBottom;
        sRegion.right = sRight;
        init();
    }

    public ScannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        initPaint();
    }



    private void initPaint() {
        sPaint = new Paint();
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setColor(Color.RED);
        sPaint.setStrokeWidth(3);
    }

    public void setsPaint(Paint sPaint) {
        this.sPaint = sPaint;
    }

    public void setScannerRegion(float left, float top, float bottom, float right){
        sRegion.left = left;
        sRegion.top = top;
        sRegion.bottom = bottom;
        sRegion.right = right;
        //框区域
        this.sTop = sRegion.centerY();
        this.sBottom = sRegion.centerY();
        this.sLeft = sRegion.centerX();
        this.sRight = sRegion.centerX();
    }

    public void setScannerRegion(RectF region){
        setScannerRegion(region.left,region.top,region.bottom,region.right);
    }

    public float getOffsetX() {
        return offsetX;
    }

    public Paint getsPaint() {
        return sPaint;
    }

    public void setSLeft(float sLeft) {
        this.sLeft = sLeft;
    }

    public void setSBottom(float sBottom) {
        this.sBottom = sBottom;
    }

    public void setSRight(float sRight) {
        this.sRight = sRight;
    }

    public void setSTop(float sTop) {
        this.sTop = sTop;
    }

    public float getSTop() {
        return sTop;
    }

    public float getSLeft() {
        return sLeft;
    }

    public float getSBottom() {
        return sBottom;
    }

    public float getSRight() {
        return sRight;
    }

    public void setScanIndex(int scanIndex) {
        this.scanIndex = scanIndex;
    }

    public int getScanIndex() {
        return scanIndex;
    }

    public void setState(@ScannerState int mState) {
        this.mState = mState;
    }

    public int getState() {
        return mState;
    }

    public RectF getsRegion() {
        return sRegion;
    }
}
