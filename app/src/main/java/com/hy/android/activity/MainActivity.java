package com.hy.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hy.android.R;
import com.hy.android.base.BaseActivity;
import com.hy.android.fragment.HomeFragment;
import com.hy.android.fragment.TypeFragment;
import com.hy.android.knowledge.MyActivity;
import com.hy.android.ui.NewsActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigationView)
    NavigationView navigationView;

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;

    private HomeFragment homeFragment;
    private TypeFragment typeFragment;
    private Fragment[] fragments;
    private int lastShowFragment = 0;

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        initToolbar();
        initDrawerLayout();
        initBottomNavigation();
        initFragments();
    }

    private void initFragments() {

        homeFragment = new HomeFragment();
        typeFragment = new TypeFragment();
        fragments = new Fragment[]{homeFragment, typeFragment};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, homeFragment)
                .show(homeFragment)
                .commit();
    }

    private void initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if (lastShowFragment != 0) {
                            switchFragment(lastShowFragment, 0);
                            lastShowFragment = 0;
                            toolbar.setTitle(R.string.title_home);
                        }
                        return true;
                    case R.id.navigation_type:
                        if (lastShowFragment != 1) {
                            switchFragment(lastShowFragment, 1);
                            lastShowFragment = 1;
                            toolbar.setTitle(R.string.title_dashboard);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_like:
                        Log.e(TAG, "onNavigationItemSelected: 1111");
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(MainActivity.this, MyActivity.class));
                        break;
                    case R.id.nav_news:
                        startActivity(new Intent(MainActivity.this, NewsActivity.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_home);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void initData() {

    }

    public void switchFragment(int lastIndex, int index) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(fragments[lastIndex]);

        if (!fragments[index].isAdded()) {

            transaction.add(R.id.content, fragments[index]);
        }

        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    @Override
    public void onRetry() {

    }
}
