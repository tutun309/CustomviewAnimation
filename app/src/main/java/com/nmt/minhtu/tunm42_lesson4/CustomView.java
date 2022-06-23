package com.nmt.minhtu.tunm42_lesson4;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomView extends View {
    private static final float STROKE_WIDTH = 10f;
    private static final int ICON_COIN_NUMBER = 10;
    private static final int MAX_ALPHA = 255;
    private static final int BOUND_RANDOM = 400;
    private static final int DEFAULT_DELAY_VALUE = 1400;
    private static final int DEFAULT_DELAY_MOVE_DOWN = 500;
    private static final int FADE_OUT_DELAY = 2000;

    private static final int SHOW_COIN_DURATION = 1000;
    private static final int FADE_IN_DURATION = 1000;
    private static final int MOVE_AROUND_DURATION = 500;
    private static final int MOVE_UP_DURATION = 1000;
    private static final int FADE_OUT_DURATION = 600;


    private Paint mPaint;
    private List<Point> pointList = new ArrayList<>();
    private int currentAlpha;
    private int count = 0;
    private int delayValue;
    private int delayMoveDown;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 1; i <= ICON_COIN_NUMBER; i++) {
            Random random = new Random();
            pointList.add(new Point(random.nextInt(BOUND_RANDOM) + getWidth() / 2 - BOUND_RANDOM/2,
                    random.nextInt(BOUND_RANDOM) + getHeight() / 2 - BOUND_RANDOM/2));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_ggold);
        for (int i = 0; i < count; i++) {
            mPaint.setAlpha(currentAlpha);
            canvas.drawBitmap(bitmap, pointList.get(i).getX(), pointList.get(i).getY(), mPaint);
        }

    }

    public void setAnimation() {
        delayValue = DEFAULT_DELAY_VALUE;
        delayMoveDown = DEFAULT_DELAY_MOVE_DOWN;

        //------vẽ lượt đầu tiên-----
        ValueAnimator showCoin = ValueAnimator.ofInt(0, pointList.size() - 1);
        showCoin.setDuration(SHOW_COIN_DURATION);
        showCoin.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (count < pointList.size()) {
                    count = (int) showCoin.getAnimatedValue();
                    invalidate();
                }
            }

        });
        showCoin.start();

        for (int i = 0; i < pointList.size(); i++) {
            delayValue+=50;
            delayMoveDown+=50;
            Point point = pointList.get(i);
            if (point.getX() == 0 || point.getY() == 0) {
                Random random = new Random();
                point.setX(random.nextInt(BOUND_RANDOM) + getWidth() / 2 - BOUND_RANDOM/2);
                point.setY(random.nextInt(BOUND_RANDOM) + getHeight() / 2 - BOUND_RANDOM/2);
            }

            //   --------------hiện ra---------
            ValueAnimator alphaFadeIn = ValueAnimator.ofInt(0, MAX_ALPHA);
            alphaFadeIn.setDuration(FADE_IN_DURATION);
            alphaFadeIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentAlpha = (int) alphaFadeIn.getAnimatedValue();
                    invalidate();
                }

            });
            alphaFadeIn.start();

            //-------------nhún------------------
            ValueAnimator moveDownY = ValueAnimator.ofInt(
                    pointList.get(i).getY(),pointList.get(i).getY() + 30);
            moveDownY.setStartDelay(delayMoveDown);
            moveDownY.setDuration(MOVE_AROUND_DURATION);
            moveDownY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    point.setY((int) animation.getAnimatedValue());
                    invalidate();
                }

            });
            moveDownY.start();
            ValueAnimator moveUpY = ValueAnimator.ofInt(
                    pointList.get(i).getY(),pointList.get(i).getY() - 30);
            moveUpY.setStartDelay(delayMoveDown+100);
            moveUpY.setDuration(MOVE_AROUND_DURATION);
            moveUpY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    point.setY((int) animation.getAnimatedValue());
                    invalidate();
                }

            });
            moveUpY.start();

            //----------------bay lên---------------
            ValueAnimator animatorX = ValueAnimator.ofInt(pointList.get(i).getX(), 0);
            animatorX.setDuration(MOVE_UP_DURATION);
            animatorX.setStartDelay(delayValue);
            animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    point.setX((int) animation.getAnimatedValue());
                    invalidate();
                }

            });
            animatorX.start();

            ValueAnimator animatorY = ValueAnimator.ofInt(pointList.get(i).getY(), 0);
            animatorY.setDuration(MOVE_UP_DURATION);
            animatorY.setStartDelay(delayValue);
            animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    point.setY((int) animation.getAnimatedValue());
                    invalidate();
                }

            });
            animatorY.start();
        }

        // ------------------ẩn đi--------------
        ValueAnimator alpha = ValueAnimator.ofInt(MAX_ALPHA, 0);
        alpha.setDuration(FADE_OUT_DURATION);
        alpha.setStartDelay(FADE_OUT_DELAY);
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAlpha = (int) alpha.getAnimatedValue();
                invalidate();
            }

        });
        alpha.start();
    }
}
