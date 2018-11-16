package com.hy.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {
    private int mWidth,mHeight;
    private Paint mPaint = new Paint();

    public CanvasView(Context context) {
        super(context);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth=w;
        this.mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth/2,mHeight/2);
        RectF rectF=new RectF(-300,-300,300,300);
        for(int i=0;i<20;i++){
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rectF,mPaint);
        }

//        canvas.drawCircle(0,0,300,mPaint);
//        canvas.drawCircle(0,0,280,mPaint);
//        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
//            canvas.drawLine(0,280,0,300,mPaint);
//            canvas.rotate(10);
//        }
    }
}
