package com.hy.android.activity;

import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import com.hy.android.base.BaseActivity;
import com.hy.android.R;

public class AnimationActivity extends BaseActivity {

    private ImageView img_01;
    private Button btn_01, btn_02, btn_03, btn_04, btn_05,
            btn_06, btn_07, btn_08, btn_09, btn_10,btn_11;

    @Override
    public int getContentLayout() {
        return R.layout.activity_animation;
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        img_01 = findViewById(R.id.img_01);
        img_01.setImageResource(R.drawable.anim1);
        AnimationDrawable anim = (AnimationDrawable) img_01.getDrawable();  //逐帧动画
        anim.start();

        testViewAnimation();
        testPropertyAnimation();
    }

    //补间动画
    private void testViewAnimation() {

        //---------------------------------- 1、平移动画 --------------------------------------------------------
        btn_01 = findViewById(R.id.btn_01);
        final Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_animation);

        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_01.startAnimation(translateAnimation);
            }
        });

        btn_02 = findViewById(R.id.btn_02);
        final Animation translateAnim = new TranslateAnimation(0, 300, 0, 300);
        translateAnim.setDuration(3000);

        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_02.startAnimation(translateAnim);
            }
        });

        //----------------------------------------- 2、缩放动画 ------------------------------------------------------
        btn_03 = findViewById(R.id.btn_03);
        final Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        btn_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_03.startAnimation(scaleAnimation);
            }
        });

        btn_04 = findViewById(R.id.btn_04);
        final Animation scale = new ScaleAnimation(0, 2, 0, 2, 0.5f, 0.5f);
        scale.setDuration(3000);
        btn_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_04.startAnimation(scale);
            }
        });

        //----------------------------------------- 3、旋转动画 ------------------------------------------------------
        btn_05 = findViewById(R.id.btn_05);
        final Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        btn_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_05.startAnimation(rotateAnimation);
            }
        });

        btn_06 = findViewById(R.id.btn_06);
        final Animation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        btn_06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_06.startAnimation(rotate);
            }
        });

        //----------------------------------------- 4、透明度动画 ------------------------------------------------------
        btn_07 = findViewById(R.id.btn_07);
        final Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        btn_07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_07.startAnimation(alphaAnimation);
            }
        });

        btn_08 = findViewById(R.id.btn_08);
        final Animation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(3000);
        btn_08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_08.startAnimation(alpha);
            }
        });

        //----------------------------------------- 5、组合动画 ------------------------------------------

        btn_09 = findViewById(R.id.btn_09);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_animation);
        btn_09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_09.startAnimation(animation);
            }
        });


        btn_10 = findViewById(R.id.btn_10);
        // 创建组合动画对象(设置为true)
        final AnimationSet setAnimation = new AnimationSet(true);


        setAnimation.setRepeatMode(Animation.RESTART);
        setAnimation.setRepeatCount(1);// 设置了循环一次,但无效


        // 子动画1:旋转动画
        Animation rotate1 = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate1.setDuration(1000);
        rotate1.setRepeatMode(Animation.RESTART);
        rotate1.setRepeatCount(Animation.INFINITE);

        // 子动画2:平移动画
        Animation translate1 = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT,-0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT,0.5f,
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        translate1.setDuration(10000);

        // 子动画3:透明度动画
        Animation alpha1 = new AlphaAnimation(1,0);
        alpha1.setDuration(3000);
        alpha1.setStartOffset(7000);

        // 子动画4:缩放动画
        Animation scale1 = new ScaleAnimation(1,0.5f,1,0.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale1.setDuration(1000);
        scale1.setStartOffset(4000);

        // 步骤4:将创建的子动画添加到组合动画里
        setAnimation.addAnimation(alpha1);
        setAnimation.addAnimation(rotate1);
        setAnimation.addAnimation(translate1);
        setAnimation.addAnimation(scale1);


        btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_10.startAnimation(setAnimation);
            }
        });


    }


    //属性动画
    private void testPropertyAnimation(){
        btn_11 = findViewById(R.id.btn_11);
        final ValueAnimator animator=ValueAnimator.ofInt(btn_11.getLayoutParams().width,500);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue= (int) animation.getAnimatedValue();
                btn_11.getLayoutParams().width=currentValue;
                btn_11.requestLayout();
            }
        });

        btn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
            }
        });

    }

    @Override
    public void initData() {
        initToolbar();
    }

    @Override
    public void onRetry() { }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Animation");
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
