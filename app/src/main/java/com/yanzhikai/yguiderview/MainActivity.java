package com.yanzhikai.yguiderview;

import android.graphics.RectF;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yanzhikai.guiderview.OnGuiderClickListener;
import com.yanzhikai.guiderview.YGuider;
import com.yanzhikai.typertextview.TyperTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnGuiderClickListener {
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
        mYGuider.addNextHighlight(new RectF(444,777,567,922));
        mYGuider.addNextHighlight(btn_show_all);
        mYGuider.addNextHighlight(new RectF(300,300,500,600));
        mYGuider.addNextHighlight(btn_show);
        mYGuider.prepareTarget();
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

    @Override
    public void onNextClick() {

    }

    @Override
    public void onJumpClick() {

    }
}
