package com.yanzhikai.yguiderview;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yanzhikai.guiderview.GuiderOnClickListener;
import com.yanzhikai.guiderview.MaskLayout;
import com.yanzhikai.guiderview.YGuider;
import com.yanzhikai.typertextview.TyperTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GuiderOnClickListener{
    public static final String TAG = "yguiderview";
    TyperTextView tv;
    Button btn_show_all,btn_show;
    private YGuider mYGuider;
    private TestFragment mTestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TyperTextView) findViewById(R.id.tv);
//        tv.animateText("asdasdansct" + "\n" + "dafdasfasf \n dsadfasdf");
        btn_show_all = (Button) findViewById(R.id.btn_show_all);
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_show_all.setOnClickListener(this);
        btn_show.setOnClickListener(this);


        mYGuider = new YGuider(this);
        mYGuider.setGuiderOnClickListener(this);
        mYGuider.addNextHighlight(btn_show);
    }

    private void addFragment() {
        mTestFragment = new TestFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,mTestFragment);
        ft.commit();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show:
//                tv.animateText("asdasdansct" + "\n" + "dafdasfasf \n dsadfasdf");
                Log.d(TAG, "onClick: erssfa");
                mYGuider.startGuide();
                break;
            case R.id.btn_show_all:
//                tv.showAll();
//                addFragment();
                mYGuider.cancelGuide();
                break;
        }

    }

    @Override
    public void onMaskClick() {
//        mYGuider.cancelGuide();
    }
}
