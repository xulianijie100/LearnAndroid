package com.hy.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hy.android.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class StateLoading extends View {

    /**
     * 加载状态
     */
    public final static int LoadingState = 0;
    /**
     * 加载失败状态
     */
    public final static int FaileState = 1;
    /**
     * 加载超时状态
     */
    public final static int SleepState = 2;
    /**
     * 初始设置循环一次是两秒
     */
    private final static long cycleTime = 4000L;

    /**
     * 加载状态图片
     */
    private Bitmap[] stateBitmap = new Bitmap[26];
    /**
     * 加载失败状态图片
     */
    private Bitmap failBitmap;
    /**
     * 加载超时状态
     */
    private Bitmap sleepBitmap;
    /**
     * 当前状态码
     */
    private int stateCode = LoadingState;
    /**
     * 当前动画下标
     */
    private int animationIndex = 0;
    /**
     *
     */
    private Paint paint = new Paint();

    /**
     * 当前画图线程状态
     */
    private AtomicBoolean mContinueBoolean = new AtomicBoolean(false);

    public StateLoading(Context context) {
        super(context);
        init(null);
    }

    public StateLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StateLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Log.d("loading", "加载。。。。。");
        loadBitmap();
    }

    /**
     * 加载图片
     */
    private void loadBitmap() {
        stateBitmap[0] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_01);
        stateBitmap[1] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_02);
        stateBitmap[2] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_03);
        stateBitmap[3] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_04);
        stateBitmap[4] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_05);
        stateBitmap[5] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_06);
        stateBitmap[6] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_07);
        stateBitmap[7] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_08);
        stateBitmap[8] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_09);
        stateBitmap[9] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_10);
        stateBitmap[10] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_11);
        stateBitmap[11] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_12);
        stateBitmap[12] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_13);
        stateBitmap[13] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_14);
        stateBitmap[14] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_15);
        stateBitmap[15] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_16);
        stateBitmap[16] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_17);
        stateBitmap[17] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_18);
        stateBitmap[18] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_19);
        stateBitmap[19] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_20);
        stateBitmap[20] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_21);
        stateBitmap[21] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_22);
        stateBitmap[22] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_23);
        stateBitmap[23] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_24);
        stateBitmap[24] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_25);
        stateBitmap[25] = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_26);
        failBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_fail);
        sleepBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.state_sequencen_sleep);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("loading", "loading..........");
        if (stateCode == LoadingState) {
            if (!mContinueBoolean.get()) {
                startAnimationThread();
            }
            canvas.drawBitmap(stateBitmap[animationIndex], 0, 0, paint);
        }
        if (stateCode == FaileState) {
            canvas.drawBitmap(failBitmap, 0, 0, paint);
        }
        if (stateCode == SleepState) {
            canvas.drawBitmap(sleepBitmap, 0, 0, paint);
        }
    }

    /**
     * 开启动画线程
     */
    private void startAnimationThread() {
        if (mContinueBoolean.get())
            return;
        mContinueBoolean.set(true);
        ThreadPool.initialize();
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (mContinueBoolean.get()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    animationIndex++;
                    if (animationIndex >= 26)
                        animationIndex = 0;
                    handler.sendEmptyMessage(0);            //刷新一下loading图片
                }
            }
        });
    }

    /**
     * 设置当前动画状态
     *
     * @param state
     */
    public void setStateCode(int state) {
        stateCode = state;
        if (mOnLoadingStateListener != null) {
            mOnLoadingStateListener.onLoadingState(state);
        }
        switch (stateCode) {
            case LoadingState:
                animationIndex = 0;
                startAnimationThread();
                break;
            case FaileState:
                handler.sendEmptyMessage(0);
                break;
            case SleepState:
                handler.sendEmptyMessage(0);
                break;
        }
    }

    private OnLoadingStateListener mOnLoadingStateListener;

    public interface OnLoadingStateListener {
        void onLoadingState(int state);
    }

    public void setOnLoadingStateListener(OnLoadingStateListener listener) {
        this.mOnLoadingStateListener = listener;
    }


    /**
     * 停止动画
     */
    public void stopStateDraw() {
        mContinueBoolean.set(false);
    }

    /**
     * 开始动画
     */
    public void startStateDraw() {
        stateCode = LoadingState;
        mContinueBoolean.set(true);
        startAnimationThread();
    }

    /**
     * 动画刷新Handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("loading", "重新计算大小" + "width:" + widthMeasureSpec + " height:" + heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        for (int i = 0; i < stateBitmap.length; i++) {
            stateBitmap[i] = Bitmap.createScaledBitmap(stateBitmap[i], width, height, false);
        }
        failBitmap = Bitmap.createScaledBitmap(failBitmap, width, height, false);
        sleepBitmap = Bitmap.createScaledBitmap(sleepBitmap, width, height, false);
    }
}
