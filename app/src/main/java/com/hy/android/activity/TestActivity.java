package com.hy.android.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;

import com.hy.android.Base.BaseActivity;
import com.hy.android.R;
import com.hy.android.bean.User;
import com.hy.android.utils.PlayerJNI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;

public class TestActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_rx;
    }

    @Override
    public void initView() {

        String str= PlayerJNI.stringFromJNI();
        Log.e("----",str);

    }

    @Override
    protected void initData() {
        writeObj();
        readObj();
        testHandler();
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
            Log.e("---", bean.toString());
            //打印值为  E/---: User{name='jack', age=20, gender='man'}

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    String text = (String) msg.obj;
                    Log.e("---", text);
                    //打印结果：E/---: hello
                    break;
                default:
                    break;
            }
        }
    };

    private void testHandler() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = mHandler.obtainMessage();
                message.what = 100;
                message.obj = "hello";
                mHandler.sendMessage(message);
            }
        }.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                @SuppressLint("HandlerLeak")
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                Looper.loop();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
            }
        }).start();

    }

    public Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.handler=new ActivityHandler(this);
    }

    private static class ActivityHandler extends Handler {
        private final WeakReference<TestActivity> mActivity;

        public ActivityHandler(TestActivity activity) {
            this.mActivity = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (this.mActivity != null) {
                TestActivity activity = this.mActivity.get();
                if (activity != null && !activity.isFinishing()) {
                    activity.handleMessage(msg);
                }
            }
        }
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 101:
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
