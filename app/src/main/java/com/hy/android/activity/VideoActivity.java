package com.hy.android.activity;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hy.android.Base.BaseActivity;
import com.hy.android.R;
import com.hy.android.bean.User;
import com.hy.android.filter.YuvFilter;
import com.hy.android.utils.PlayerJNI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoActivity extends BaseActivity {

    private TextView tv_decoder;
    private GLSurfaceView mGLView;
    private YuvFilter mFilter;
    private byte[] data;
    private boolean isCodecStarted=true;

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

        int res= PlayerJNI.start();
        if (res==0) {
            initGLView();
        }

        tv_decoder=findViewById(R.id.tv_decoder);
        tv_decoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGLView!=null){
                    mGLView.requestRender();
                }
            }
        });

    }

    private void initGLView() {

        mGLView=findViewById(R.id.mGLView);
        mFilter=new YuvFilter(getResources());
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
                if(isCodecStarted){
                    if(data==null||data.length!=PlayerJNI.get(PlayerJNI.KEY_WIDTH)*PlayerJNI.get(PlayerJNI.KEY_HEIGHT)){
                        data=new byte[PlayerJNI.get(PlayerJNI.KEY_WIDTH)*PlayerJNI.get(PlayerJNI.KEY_HEIGHT)*3/2];
                    }
                    if(PlayerJNI.output(data)==0){
                        mFilter.updateFrame(PlayerJNI.get(PlayerJNI.KEY_WIDTH),PlayerJNI.get(PlayerJNI.KEY_HEIGHT),data);
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
        writeObj();
        readObj();
    }

    //序列化
    private void writeObj() {
        User user = new User("jack", 20, "man");
        try {
            String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(sdCardDir + "/user.text");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //反序列化
    private void readObj() {
        try {
            String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            FileInputStream fis = new FileInputStream(sdCardDir + "/user.text");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User bean = (User) ois.readObject();
            // Log.e("---", bean.toString());
            //打印值为  E/---: User{name='jack', age=20, gender='man'}

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mGLView!=null){
            mGLView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mGLView!=null){
            mGLView.onPause();
        }
    }

}
