package com.yanzhikai.guiderview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class MaskLayout extends ViewGroup implements View.OnClickListener {
    public static final String TAG = "guiderview";
    private Context mContext;
    private Paint sPaint;
    private Bitmap transparencyBitmap;
    private ArrayList<ScannerView> mScannerList;
    private boolean isMoving = false;

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
//        setBackgroundResource(R.color.colorTransparency);
        setOnClickListener(this);
        setWillNotDraw(false);
        initPaint();
        initScanner();
//        scanner = new Scanner(400,400);
//        scanner2 = new Scanner(600,600,200,200);
//        scanner.prepare();
//        scanner2.prepare();
    }

    private void initPaint() {
        sPaint = new Paint();
        sPaint.setAlpha(10);

        transparencyBitmap = Bitmap.createBitmap(22,22, Bitmap.Config.ARGB_8888);
        transparencyBitmap.setHasAlpha(true);
    }

    private void initScanner(){
        mScannerList = new ArrayList<>();

        ScannerView scannerView1 = new ScannerView(mContext);
        LayoutParams layoutParams1 = new LayoutParams(50,50);
        scannerView1.setLayoutParams(layoutParams1);
        addView(scannerView1);
        mScannerList.add(scannerView1);

        ScannerView scannerView2 = new ScannerView(mContext);
        LayoutParams layoutParams2 = new LayoutParams(50,50);
        scannerView1.setLayoutParams(layoutParams2);
        addView(scannerView2);
        mScannerList.add(scannerView2);
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
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:

//                break;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_DOWN:
                return false;

        }
        return false;
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
        canvas.drawColor(Color.argb(188,33,33,33));
        Log.d(TAG, "onDraw: canvas()" + canvas.toString());
        for (int i = 0; i < getChildCount(); i++){
            drawScannerLine(canvas, (ScannerView) getChildAt(i),false);
        }
        canvas.save();
        canvas.clipRect(220,1055,500,1200);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        
        canvas.drawBitmap(transparencyBitmap,new Rect(0,0,22,22),new Rect(0,0,280,145),sPaint);
        canvas.restore();

        if (isMoving){
            postInvalidateDelayed(20);
        }

    }

    private void drawScannerLine(Canvas canvas, ScannerView view, boolean isExpand){
        if (!isExpand){
            Log.d(TAG, "drawScannerLine: ");
            float y = view.getY() + view.getHeight()/2;
            float x = view.getX() + view.getWidth()/2;
            canvas.drawLine(0,y,canvas.getWidth(),y,view.getsPaint());
            canvas.drawLine(x,0,x,canvas.getHeight(), view.getsPaint());

        }
    }

    @Override
    public void onClick(View v) {
        Log.d("guiderview", "onClick: ");
//        scannerView.layout(300,300,350,350);
//        postInvalidate();
        setAnimator(mScannerList.get(0),0,0,400,400,555);
        setAnimator(mScannerList.get(1),200,222,600,999,777);

    }

    private void setAnimator(ScannerView scannerView, int fromX, int fromY, int toX, int toY,int duration){
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(scannerView,"TranslationX",fromX,toX);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(scannerView,"TranslationY",fromY,toY);
        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(objectAnimatorX,objectAnimatorY);
        animator.setDuration(duration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isMoving = true;
                postInvalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isMoving = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

}
