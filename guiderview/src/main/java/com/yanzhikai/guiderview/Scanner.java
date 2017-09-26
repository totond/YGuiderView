package com.yanzhikai.guiderview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class Scanner {
    private static final int GONE = 0, MOVING = 1, EXPANDING = 2, STAY_EXPANDED = 3;
    @IntDef({GONE, MOVING, EXPANDING, STAY_EXPANDED})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface ScannerState {}
    private @ScannerState int mState = GONE;

    private float mFromX = 0, mFromY = 0;
    private float mToX = 0, mToY = 0;
    private float mMoveDeltaX = 0, mMoveDeltaY = 0;
    private float mExpandDeltaX = 0, mExpandDeltaY = 0;
    private boolean isExpanded = false;
    private Paint sPaint;
    private int moveTimes = 80,expandDuration = 150;
    private int mCount = 0;

    public Scanner(int toX, int toY){
        mToX = toX;
        mToY = toY;
        init();
    }

    public Scanner(int fromX, int fromY,int toX, int toY){
        mFromX = fromX;
        mFromY = fromY;
        mToX = toX;
        mToY = toY;
        init();
    }

    private void init() {
        sPaint = new Paint();
        sPaint.setStyle(Paint.Style.FILL);
        sPaint.setColor(Color.RED);
        sPaint.setStrokeWidth(5);
    }

    public void prepare(){
        mMoveDeltaX = (mToX - mFromX) / moveTimes;
        mMoveDeltaY = (mToY - mFromY) / moveTimes;
    }

    public boolean execute(Canvas canvas){
        switch (mState){
            case GONE:
                mState = MOVING;
                mCount = 0;
                break;
            case MOVING:
                if (doMove(canvas)){
//                    mState = EXPANDING;
                    mCount = 0;
                    return true;
                }
                break;
            case EXPANDING:
                if (doExpand(canvas)){
                    mState = STAY_EXPANDED;
                    return true;
                }
                break;
            case STAY_EXPANDED:
                mState = MOVING;
                mCount = 0;
                break;
        }
        return false;
    }

    private boolean doMove(Canvas canvas){
        canvas.drawLine(0,mFromY,canvas.getWidth(),mFromY,sPaint);
        canvas.drawLine(mFromX,0,mFromX,canvas.getHeight(),sPaint);
        mFromX += mMoveDeltaX;
        mFromY += mMoveDeltaY;
        mCount ++;
        return (mCount > moveTimes);
    }

    private boolean doExpand(Canvas canvas){
        return true;
    }

    public void setPaint(Paint sPaint) {
        this.sPaint = sPaint;
    }

    public void setFromXY(int fromX, int fromY){
        mFromX = fromX;
        mFromY = fromY;
    }

    public Paint getPaint() {
        return sPaint;
    }

    public float getFromX() {
        return mFromX;
    }

    public float getFromY() {
        return mFromY;
    }

    public float getToX() {
        return mToX;
    }

    public float getToY() {
        return mToY;
    }
}
