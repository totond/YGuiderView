package com.yanzhikai.guiderview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class ScannerView extends View {
    private Paint sPaint;

    public ScannerView(Context context) {
        super(context);
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
        sPaint.setStyle(Paint.Style.FILL);
        sPaint.setColor(Color.RED);
        sPaint.setStrokeWidth(5);
    }

    public void setsPaint(Paint sPaint) {
        this.sPaint = sPaint;
    }

    public Paint getsPaint() {
        return sPaint;
    }
}
