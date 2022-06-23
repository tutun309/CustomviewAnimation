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
import androidx.core.content.ContextCompat;

public class CustomViewHeader extends View {
    //background
    private static final float STROKE_WIDTH = 10f;
    private static final int BACKGROUND_Y_VALUE_DEFAULT = -250;
    private static final int COIN_VALUE_DEFAULT = 10000;
    private static final int BACKGROUND_COLOR = R.color.my_color;

    //rectangle value
    private static final int LEFT_RECT_BACKGROUND = 0;
    private static final int RIGHT_RECT_BACKGROUND = 0;
    private static final int BOTTOM_RECT_BACKGROUND = 160;

    //text value
    private static final int TEXT_SIZE = 80;
    private static final float TEXT_X_AXIS_VALUE = 170;
    private static final float TEXT_Y_AXIS_VALUE = 100;

    //icon
    private static final float ICON_LEFT_VALUE = 30;
    private static final float ICON_TOP_VALUE = 30;

    private Paint paintBackground, paintText;
    private int  moveY = BACKGROUND_Y_VALUE_DEFAULT;
    private int coin = COIN_VALUE_DEFAULT;
    public CustomViewHeader(Context context) {
        super(context);
    }

    public CustomViewHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBackground.setColor(ContextCompat.getColor(getContext(),BACKGROUND_COLOR));
        paintBackground.setStrokeWidth(STROKE_WIDTH);
        paintBackground.setStyle(Paint.Style.FILL);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(TEXT_SIZE);
        paintText.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(0,moveY);
        canvas.drawRect(LEFT_RECT_BACKGROUND, RIGHT_RECT_BACKGROUND,
                getWidth(), BOTTOM_RECT_BACKGROUND, paintBackground);
        canvas.drawText(coin + "$", TEXT_X_AXIS_VALUE, TEXT_Y_AXIS_VALUE, paintText);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_ggold);
        canvas.drawBitmap(bitmap, ICON_LEFT_VALUE, ICON_TOP_VALUE, paintBackground);

    }

    public void setAnimation() {

        //--------------trượt header xuống-----------
        ValueAnimator moveHeaderDown = ValueAnimator.ofInt(BACKGROUND_Y_VALUE_DEFAULT, 0);
        moveHeaderDown.setDuration(500);
        moveHeaderDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveY = (int) moveHeaderDown.getAnimatedValue();
                invalidate();
            }
        });
        moveHeaderDown.start();

        //--------------cộng tiền-----------
        ValueAnimator coinPlus = ValueAnimator.ofInt(1, 120);
        coinPlus.setDuration(1000);
        coinPlus.setStartDelay(2000);
        coinPlus.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                coin += (int) coinPlus.getAnimatedValue();
                invalidate();
            }
        });
        coinPlus.start();

        //--------------đẩy header lên-----------
        ValueAnimator moveHeaderUp = ValueAnimator.ofInt(0, BACKGROUND_Y_VALUE_DEFAULT);
        moveHeaderUp.setDuration(800);
        moveHeaderUp.setStartDelay(3500);
        moveHeaderUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveY = (int) moveHeaderUp.getAnimatedValue();
                invalidate();
            }
        });
        moveHeaderUp.start();
    }
}
