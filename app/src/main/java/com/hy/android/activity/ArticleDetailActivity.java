package com.hy.android.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hy.android.R;
import com.hy.android.base.BaseActivity;
import com.hy.android.utils.CommonUtils;
import com.hy.android.utils.Constants;
import com.hy.android.utils.SpUtil;
import com.hy.android.utils.StatusBarUtil;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;

public class ArticleDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webContent)
    FrameLayout mWebContent;

    private Bundle bundle;
    private AgentWeb mAgentWeb;
    private String articleLink;
    private String title;

    @Override
    public int getContentLayout() {
        return R.layout.activity_detail_webview;
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        initToolBar();

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(articleLink);

        WebView mWebView = mAgentWeb.getWebCreator().getWebView();
        WebSettings mSettings = mWebView.getSettings();

        //设置无图模式
        SpUtil.writeBoolean(this, Constants.STATE_NO_IMAGE, false);
        //设备自动缓存
        SpUtil.writeBoolean(this, Constants.STATE_AUTO_CACHE, true);

        boolean image_state = SpUtil.readBoolean(this, Constants.STATE_NO_IMAGE);
        if (image_state) {
            mSettings.setBlockNetworkImage(true);
        } else {
            mSettings.setBlockNetworkImage(false);
        }

        boolean cache_state = SpUtil.readBoolean(this, Constants.STATE_AUTO_CACHE);

        if (cache_state) {
            mSettings.setAppCacheEnabled(true);
            mSettings.setDomStorageEnabled(true);
            mSettings.setDatabaseEnabled(true);
            if (CommonUtils.isNetworkConnected()) {
                mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        } else {
            mSettings.setAppCacheEnabled(false);
            mSettings.setDomStorageEnabled(false);
            mSettings.setDatabaseEnabled(false);
            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onRetry() {
    }

    public static void startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_TITLE, title);
        intent.putExtra(Constants.ARTICLE_URL, url);
        context.startActivity(intent);
    }

    private void initToolBar() {
        bundle = getIntent().getExtras();
        assert bundle != null;
        articleLink = (String) bundle.get(Constants.ARTICLE_URL);
        title = (String) bundle.get(Constants.ARTICLE_TITLE);
        mToolbar.setTitle(Html.fromHtml(title));
        setSupportActionBar(mToolbar);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_common, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_type_url, getString(R.string.app_name), title, articleLink));
//                intent.setType("text/plain");
//                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.item_system_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink)));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
