package com.hy.android.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hy.android.Base.BaseActivity;
import com.hy.android.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJavaActivity extends BaseActivity {

    private static final String TAG = "RxJavaActivity";

    @Override
    public int bindLayout() {
        return R.layout.activity_rx;
    }

    @Override
    public void initView() {
        initToolbar();
    }

    @Override
    protected void initData() {
        test1();
        test2();
    }

    private void test1() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e(TAG, "observable  ======== 000000000");
                e.onNext("00000000000");
                Log.e(TAG, "observable  ======== 1111111111");
                e.onNext("111111111111");
                e.onComplete();
                Log.e(TAG, "observable  ======== 2222222222");
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
