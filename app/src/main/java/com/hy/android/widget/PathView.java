package com.hy.android.widget;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathView extends View {

    private static final float C = 0.551915024494f;
    private Paint mPaint =new Paint();
    private int mCenterX, mCenterY;

    private PointF mCenter = new PointF(0,0);
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = mCircleRadius*C;        // 圆形的控制点与数据点的差值

    private float[] mData = new float[8];               // 顺时针记录绘制圆形的四个数据点
    private float[] mCtrl = new float[16];              // 顺时针记录绘制圆形的八个控制点

    private float[] mData1 = new float[8];
    private float[] mCtrl1 = new float[16];
    private float mOffset = 300;


    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration/mCount;            // 每一份的时长


    private float mCurrent1 = 0;                         // 当前已进行时长
    private float mPiece1 = mDuration/mCount;

    public PathView(Context context) {
       this(context,null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        // 初始化数据点
        mData[0] = 0;
        mData[1] = mCircleRadius;

        mData[2] = mCircleRadius;
        mData[3] = 0;

        mData[4] = 0;
        mData[5] = -mCircleRadius;

        mData[6] = -mCircleRadius;
        mData[7] = 0;

        // 初始化控制点
        mCtrl[0]  = mData[0]+mDifference;
        mCtrl[1]  = mData[1];

        mCtrl[2]  = mData[2];
        mCtrl[3]  = mData[3]+mDifference;

        mCtrl[4]  = mData[2];
        mCtrl[5]  = mData[3]-mDifference;

        mCtrl[6]  = mData[4]+mDifference;
        mCtrl[7]  = mData[5];

        mCtrl[8]  = mData[4]-mDifference;
        mCtrl[9]  = mData[5];

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7]-mDifference;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7]+mDifference;

        mCtrl[14] = mData[0]-mDifference;
        mCtrl[15] = mData[1];


        //-------------------------------
        mData1[0] = mOffset;
        mData1[1] = mCircleRadius;

        mData1[2] = mCircleRadius+mOffset;
        mData1[3] = 0;

        mData1[4] = mOffset;
        mData1[5] = -mCircleRadius;

        mData1[6] = -mCircleRadius+mOffset;
        mData1[7] = 0;

        mCtrl1[0]  = mData1[0]+mDifference;
        mCtrl1[1]  = mData1[1];

        mCtrl1[2]  = mData1[2];
        mCtrl1[3]  = mData1[3]+mDifference;

        mCtrl1[4]  = mData1[2];
        mCtrl1[5]  = mData1[3]-mDifference;

        mCtrl1[6]  = mData1[4]+mDifference;
        mCtrl1[7]  = mData1[5];

        mCtrl1[8]  = mData1[4]-mDifference;
        mCtrl1[9]  = mData1[5];

        mCtrl1[10] = mData1[6];
        mCtrl1[11] = mData1[7]-mDifference;

        mCtrl1[12] = mData1[6];
        mCtrl1[13] = mData1[7]+mDifference;

        mCtrl1[14] = mData1[0]-mDifference;
        mCtrl1[15] = mData1[1];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1,-1);                 // 翻转Y轴

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        drawCircleView1(canvas);
        drawCircleView2(canvas);
    }

    private void drawCircleView1(Canvas canvas){

        Path path = new Path();
        path.moveTo(mData[0],mData[1]);

        path.cubicTo(mCtrl[0],  mCtrl[1],  mCtrl[2],  mCtrl[3],     mData[2], mData[3]);
        path.cubicTo(mCtrl[4],  mCtrl[5],  mCtrl[6],  mCtrl[7],     mData[4], mData[5]);
        path.cubicTo(mCtrl[8],  mCtrl[9],  mCtrl[10], mCtrl[11],    mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15],    mData[0], mData[1]);

        canvas.drawPath(path, mPaint);
        mCurrent += mPiece;
        if (mCurrent1 < mDuration){

            mData[1] -= 120/mCount;
            mCtrl[7] += 80/mCount;
            mCtrl[9] += 80/mCount;

            mCtrl[4] -= 20/mCount;
            mCtrl[10] += 20/mCount;

            postInvalidateDelayed((long) mPiece1);
        }
    }

    private void drawCircleView2(Canvas canvas){
        Path path = new Path();
        path.moveTo(mData1[0],mData1[1]);

        path.cubicTo(mCtrl1[0],  mCtrl1[1],  mCtrl1[2],  mCtrl1[3],     mData1[2], mData1[3]);
        path.cubicTo(mCtrl1[4],  mCtrl1[5],  mCtrl1[6],  mCtrl1[7],     mData1[4], mData1[5]);
        path.cubicTo(mCtrl1[8],  mCtrl1[9],  mCtrl1[10], mCtrl1[11],    mData1[6], mData1[7]);
        path.cubicTo(mCtrl1[12], mCtrl1[13], mCtrl1[14], mCtrl1[15],    mData1[0], mData1[1]);

        canvas.drawPath(path, mPaint);
        mCurrent1 += mPiece1;
        if (mCurrent1 < mDuration){

            mData1[1] -= 120/mCount;
            mCtrl1[7] += 80/mCount;
            mCtrl1[9] += 80/mCount;

            mCtrl1[4] -= 20/mCount;
            mCtrl1[10] += 20/mCount;

            postInvalidateDelayed((long) mPiece1);
        }
    }

}
