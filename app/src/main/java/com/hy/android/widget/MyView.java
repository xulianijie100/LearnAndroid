package com.hy.android.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    private static final float RADIUS = 70f;
    private MyPoint myPoint;
    private Paint mPaint;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (myPoint == null) {
            myPoint = new MyPoint(RADIUS, RADIUS);
            canvas.drawCircle(myPoint.getX(), myPoint.getY(), RADIUS, mPaint);
            MyPoint startPoint = new MyPoint(RADIUS, RADIUS);
            MyPoint endPoint = new MyPoint(700, 1000);
            ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
            animator.setDuration(5000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    myPoint = (MyPoint) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        } else {
            canvas.drawCircle(myPoint.getX(), myPoint.getY(), RADIUS, mPaint);
        }
    }

    public class PointEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {

            MyPoint startPoint = (MyPoint) startValue;
            MyPoint endPoint = (MyPoint) endValue;

            float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
            float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
            return new MyPoint(x, y);
        }
    }

    public class MyPoint {
        private float x;
        private float y;

        public MyPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
