package com.yanzhikai.guiderview;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public interface OnGuiderChangedListener {
    void onGuiderStart();
    void onGuiderNext();
    void onGuiderFinished();
    void onGuiderJump();
    void onGuiderInterrupted();
}
