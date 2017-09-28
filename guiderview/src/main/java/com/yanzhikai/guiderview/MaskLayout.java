package com.yanzhikai.guiderview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.yanzhikai.guiderview.ScannerView.EXPANDING;
import static com.yanzhikai.guiderview.ScannerView.MOVING;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class MaskLayout extends ViewGroup implements View.OnClickListener {
    public static final String TAG = "guiderview";
    private Context mContext;
    private Paint sPaint;
    private ArrayList<ScannerView> mScannerList;
    private ArrayList<RectF> mScanRegions;
    private boolean isMoving = false;
    private int scanIndex = 0;
    private OnGuiderClickListener mClickListener;
    private OnGuiderChangedListener mChangedListener;

    public MaskLayout(Context context) {
        super(context);
        init(context);
    }

    public MaskLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mContext = context;
        checkAPILevel();
//        setBackgroundResource(R.color.colorTransparency);
        setOnClickListener(this);
        setWillNotDraw(false);
        initPaint();
        initScanner();
    }

    private void checkAPILevel(){
        if (Build.VERSION.SDK_INT < 18){
            setLayerType(LAYER_TYPE_NONE,null);
        }
    }

    private void initPaint() {
        sPaint = new Paint();
        sPaint.setAlpha(60);
        sPaint.setStyle(Paint.Style.STROKE);

    }

    private void initScanner(){
        mScannerList = new ArrayList<>();
        mScanRegions = new ArrayList<>();

        ScannerView scannerView1 = new ScannerView(mContext,0,0,0,0);
//        LayoutParams layoutParams1 = new LayoutParams(50,50);
//        scannerView1.setLayoutParams(layoutParams1);
        addView(scannerView1);
        mScannerList.add(scannerView1);

//        ScannerView scannerView2 = new ScannerView(mContext);
////        LayoutParams layoutParams2 = new LayoutParams(50,50);
////        scannerView1.setLayoutParams(layoutParams2);
//        addView(scannerView2);
//        mScannerList.add(scannerView2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");

        int widthMode = MeasureSpec.AT_MOST;
        int heightMode = MeasureSpec.AT_MOST;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure: widthSize" + widthSize);
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
//        setMeasuredDimension(widthSize,heightSize);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
////                break;
//            case MotionEvent.ACTION_UP:
//                return true;
//            case MotionEvent.ACTION_DOWN:
//                return false;
//
//        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        if (!scanner.execute(canvas) && !scanner2.execute(canvas)) {
//            postInvalidateDelayed(10);
//            Log.d(TAG, "onDraw: ");
//        }



        Log.d(TAG, "onDraw: canvas()" + canvas.toString());
        for (int i = 0; i < getChildCount(); i++){
            ScannerView child = (ScannerView) getChildAt(i);

            if (!isMoving){
                clipHighlight(canvas,child.getsRegion());
            }else {
                canvas.drawColor(Color.argb(188,33,33,33));
            }

            drawScannerLine(canvas,child);
        }


        if (isMoving){
            postInvalidateDelayed(20);
        }

    }

    private void clipHighlight(Canvas canvas,RectF rectF){
        canvas.save();
        canvas.clipRect(rectF, Region.Op.DIFFERENCE);
        canvas.drawColor(Color.argb(188,33,33,33));
        canvas.restore();
    }

    private void drawScannerLine(Canvas canvas, ScannerView view){
        float y = view.getY() + view.getHeight()/2;
        float x = view.getX();
        switch (view.getState()) {
            case MOVING:
                Log.d(TAG, "drawScannerLine: MOVING");
                canvas.drawLine(0, y, canvas.getWidth(), y, view.getsPaint());
                canvas.drawLine(x, 0, x, canvas.getHeight(), view.getsPaint());
                break;
            case EXPANDING:
                Log.d(TAG, "drawScannerLine: EXPANDING" + view.getSLeft());
                canvas.drawRect(view.getSLeft(),view.getSTop(),view.getSRight(),view.getSBottom(),view.getsPaint());
                canvas.drawLine(0,y,view.getSLeft(),y,view.getsPaint());
                canvas.drawLine(view.getSRight(),y,canvas.getWidth(),y,view.getsPaint());
                canvas.drawLine(x,0,x,view.getSTop(),view.getsPaint());
                canvas.drawLine(x,view.getSBottom(),x,canvas.getHeight(),view.getsPaint());
                break;

        }

    }

    @Override
    public void onClick(View v) {
        Log.d("guiderview", "onClick: ");
//        scannerView.layout(300,300,350,350);
//        postInvalidate();
        onNext();

        if (mClickListener != null){
            mClickListener.onMaskClick();
        }
    }

    private void onNext(){
        if (scanIndex < mScanRegions.size()) {
            setTranslationAnimator(mScannerList.get(0), mScanRegions.get(scanIndex).centerX(), mScanRegions.get(scanIndex).centerY(), 555);
            scanIndex ++;
        }else {
            scanIndex = 0;
        }

    }

    private void setTranslationAnimator(final ScannerView scannerView, float toX, float toY, int duration){
        mScannerList.get(0).setScannerRegion(mScanRegions.get(scanIndex));
        float fromX = scannerView.getLastCenterX();
        float fromY = scannerView.getLastCenterY();
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(scannerView,"TranslationX",fromX,toX);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(scannerView,"TranslationY",fromY,toY);

        ObjectAnimator objectAnimatorTop = ObjectAnimator.ofFloat(scannerView,"sTop"
                ,scannerView.getSTop(),scannerView.getsRegion().top);
        ObjectAnimator objectAnimatorLeft = ObjectAnimator.ofFloat(scannerView,"sLeft"
                ,scannerView.getSLeft(),scannerView.getsRegion().left);
        Log.d(TAG, "setTranslationAnimator: left " + scannerView.getsRegion().left);
        ObjectAnimator objectAnimatorBottom = ObjectAnimator.ofFloat(scannerView,"sBottom"
                ,scannerView.getSBottom(),scannerView.getsRegion().bottom);
        ObjectAnimator objectAnimatorRight = ObjectAnimator.ofFloat(scannerView,"sRight"
                ,scannerView.getSRight(),scannerView.getsRegion().right);

        AnimatorSet moveAnimator = new AnimatorSet();
        moveAnimator.playTogether(objectAnimatorX,objectAnimatorY);

        AnimatorSet scaleAnimator = new AnimatorSet();
        scaleAnimator.playTogether(objectAnimatorTop,objectAnimatorLeft,objectAnimatorBottom,objectAnimatorRight);

        AnimatorSet doAnimator = new AnimatorSet();
        doAnimator.play(moveAnimator).before(scaleAnimator);

        moveAnimator.setDuration(duration);
        scaleAnimator.setDuration(duration);
        moveAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                scannerView.setState(MOVING);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                scannerView.setState(ScannerView.EXPANDING);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        doAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isMoving = true;
                setClickable(false);
                postInvalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isMoving = false;
//                scannerView.setState(ScannerView.STAY_EXPANDED);
                setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        doAnimator.start();
    }

    public void setScannerRegions(ArrayList<RectF> scanRegions){
        mScanRegions = scanRegions;
    }

    public void setGuiderOnClickListener(OnGuiderClickListener onGuiderClickListener) {
        this.mClickListener = onGuiderClickListener;
    }
}
