package com.yxhuang.scanview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * Author  :  yxhuang
 * Date :  2017/4/20
 * Email : yxhuang1012@gmail.com
 */

public class ScanView extends View {
    private static final String TAG = ScanView.class.getSimpleName();

    private int mTouchSlop;

    private int mLastX;
    private float mLastY;
    private int mActivePointerId;

    private float mWithFactor = 1.0f;

    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0.5f);

    public ScanView(Context context) {
        super(context);
        init(context);
    }

    public ScanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);



        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (Float) animation.getAnimatedValue();
                Log.i("scanView", "v " + v);
                ScanView.this.setScaleX(v);
                ScanView.this.setScaleY(v);
            }
        });
        valueAnimator.setDuration(500);// 设置动画持续时间
//        valueAnimator.setRepeatCount(1);// 设置重复次数
//        valueAnimator.setRepeatMode(valueAnimator.REVERSE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
//                int width = ScanView.this.getWidth();
//                int height = ScanView.this.getHeight();
//                ScanView.this.setX(event.getX() - (width / 2));//ball是图片所在的imageView控件
//                ScanView.this.setY(event.getY() - (height / 2));
//
//                ScanView.this.setPivotX(ScanView.this.getWidth() / 2f);
//                ScanView.this.setPivotY(ScanView.this.getHeight() / 2f);
//
//                valueAnimator.start();
                playAnimation();
                break;
        }
        return true;
    }

    private void playAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.1f, 1.0f, 0.f);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setDuration(1000);
        set.setFillAfter(true);
        this.startAnimation(set);
    }
}
