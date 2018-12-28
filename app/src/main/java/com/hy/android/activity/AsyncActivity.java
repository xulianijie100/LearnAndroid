package com.hy.android.activity;

import android.annotation.SuppressLint;
import android.os.*;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import com.hy.android.Component.ApplicationComponent;
import com.hy.android.base.BaseActivity;
import com.hy.android.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.lang.ref.WeakReference;

public class AsyncActivity extends BaseActivity {

    private static final String TAG = "AsyncActivity";

    public Handler myHandler;

    @Override
    public int getContentLayout() {
        return R.layout.activity_rx;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        this.myHandler = new ActivityHandler(this);
        initToolbar();
    }

    @Override
    public void initData() {
        test1();
        test2();
        testHandler();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * RxJava
     */
    private void test1() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("00000000000");
                e.onNext("111111111111");
                e.onComplete();
                e.onNext("222222222222");
            }
        });


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Observer---------: onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "observer onNext: ========= " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "observer onComplete: --------");
            }
        };

        observable.subscribe(observer);
    }

    private void test2() {
        Flowable.range(0, 10)
                .subscribe(new Subscriber<Integer>() {
                    Subscription sub;
                    //当订阅后，会首先调用这个方法，其实就相当于onStart()，
                    //传入的Subscription s参数可以用于请求数据或者取消订阅
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.w("TAG", "onsubscribe start");
                        sub = s;
                        sub.request(2);
                        Log.w("TAG", "onsubscribe end");
                    }

                    @Override
                    public void onNext(Integer o) {
                        Log.w("TAG", "onNext--->" + o);
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * handler
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    String text = (String) msg.obj;
                    Log.e(TAG, "---"+text);
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


        //子线程创建handler,需要创建looper
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

        //将主线程的looper传进来
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


        new MyAsyncTask().execute();

    }

    @Override
    public void onRetry() {

    }

    private static class ActivityHandler extends Handler {
        private final WeakReference<AsyncActivity> mActivity;

        public ActivityHandler(AsyncActivity activity) {
            this.mActivity = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (this.mActivity != null) {
                AsyncActivity activity = this.mActivity.get();
                if (activity != null && !activity.isFinishing()) {
                    activity.handleMessage(msg);
                }
            }
        }
    }

    public void handleMessage(Message msg) {
    }


    /**
     * Params:启动任务时输入的参数类型.
       Progress:后台任务执行中返回进度值的类型.
       Result:后台任务执行完成后返回结果的类型.
     */

    private class MyAsyncTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG,"-----> onPreExecute");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e(TAG,"-----> doInBackground");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "AsyncTask";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e(TAG,"-----> onProgressUpdate"+values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG,"-----> onPostExecute  "+s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("test");
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

}
