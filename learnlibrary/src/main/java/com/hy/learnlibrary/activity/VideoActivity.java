package com.hy.learnlibrary.activity;

import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.hy.learnlibrary.R;
import com.hy.learnlibrary.base.BaseActivity;
import com.hy.learnlibrary.filter.YuvFilter;
import com.hy.learnlibrary.utils.PlayerJNI;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoActivity extends BaseActivity {

    private TextView tv_decoder;
    private GLSurfaceView mGLView;
    private YuvFilter mFilter;
    private byte[] data;
    private boolean isCodecStarted = true;

    @Override
    public int bindLayout() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {
        initToolbar();

        String str = PlayerJNI.stringFromJNI();
        Log.e("----", str);
        //打印值为 Hello from C++

        // PlayerJNI player=new PlayerJNI();
        // PlayerJNI.funFromJava(player,"1000");

        int res = PlayerJNI.start();
        if (res == 0) {
            initGLView();
        }

        tv_decoder = findViewById(R.id.tv_decoder);
        tv_decoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGLView != null) {
                    mGLView.requestRender();
                }
            }
        });

    }

    private void initGLView() {

        mGLView = findViewById(R.id.mGLView);
        mFilter = new YuvFilter(getResources());
        mGLView.setEGLContextClientVersion(2);
        mGLView.setPreserveEGLContextOnPause(true);
        mGLView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                mFilter.create();
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                mFilter.setSize(width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                if (isCodecStarted) {
                    if (data == null || data.length != PlayerJNI.get(PlayerJNI.KEY_WIDTH) * PlayerJNI.get(PlayerJNI.KEY_HEIGHT)) {
                        data = new byte[PlayerJNI.get(PlayerJNI.KEY_WIDTH) * PlayerJNI.get(PlayerJNI.KEY_HEIGHT) * 3 / 2];
                    }
                    if (PlayerJNI.output(data) == 0) {
                        mFilter.updateFrame(PlayerJNI.get(PlayerJNI.KEY_WIDTH), PlayerJNI.get(PlayerJNI.KEY_HEIGHT), data);
                        mFilter.draw();
                    }
                }
            }
        });

        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        //在创建曲面时或在调用requestRender时才渲染渲染器
        //mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Video");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mGLView != null) {
            mGLView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGLView != null) {
            mGLView.onPause();
        }
    }

}
