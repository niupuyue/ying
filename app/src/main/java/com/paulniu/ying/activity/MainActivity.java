package com.paulniu.ying.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.paulniu.ying.R;
import com.paulniu.ying.activity.fragment.MainHomeFragment;
import com.paulniu.ying.activity.fragment.MainMovieFragment;
import com.paulniu.ying.activity.fragment.MainTallyFragment;
import com.paulniu.ying.base.BaseActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int FRAGMENT_HOME = 0x01;
    private static final int FRAGMENT_MOVIE = 0x02;
    private static final int FRAGMENT_TALLY = 0x03;

    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "select_item";

    private Toolbar toolbar;
    private BottomNavigationView bottom_navigation;
    private NavigationView nav_view;
    private DrawerLayout drawer_layout;

    private MainHomeFragment mHomeFragmet;
    private MainMovieFragment mMovieFragment;
    private MainTallyFragment mTallyFragment;

    private int position = -1;
    private long exitTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        try {
            if (savedInstanceState != null) {
                mHomeFragmet = (MainHomeFragment) getSupportFragmentManager().findFragmentByTag(MainHomeFragment.class.getName());
                mMovieFragment = (MainMovieFragment) getSupportFragmentManager().findFragmentByTag(MainMovieFragment.class.getName());
                mTallyFragment = (MainTallyFragment) getSupportFragmentManager().findFragmentByTag(MainTallyFragment.class.getName());
                // 回复之前的fragment
                showFragment(savedInstanceState.getInt(POSITION));
                bottom_navigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
            } else {
                showFragment(FRAGMENT_HOME);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initView() {
        try {
            toolbar = findViewById(R.id.toolbar);
            toolbar.inflateMenu(R.menu.menu_main_activity);
            bottom_navigation = findViewById(R.id.bottom_navigation);
//        BottomNavigationViewHelper.disableShiftMode(bottom_navigation);
            setSupportActionBar(toolbar);
            bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_news:
                            showFragment(FRAGMENT_HOME);
                            doubleClick(FRAGMENT_HOME);
                            break;
                        case R.id.action_photo:
                            showFragment(FRAGMENT_MOVIE);
                            doubleClick(FRAGMENT_MOVIE);
                            break;
                        case R.id.action_video:
                            showFragment(FRAGMENT_TALLY);
                            doubleClick(FRAGMENT_TALLY);
                            break;
                    }
                    return true;
                }
            });
            drawer_layout = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer_layout.addDrawerListener(toggle);
            toggle.syncState();

            nav_view = findViewById(R.id.nav_view);
            nav_view.setNavigationItemSelectedListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, position);
        outState.putInt(SELECT_ITEM, bottom_navigation.getSelectedItemId());
    }

    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_HOME:
                toolbar.setTitle(getString(R.string.main_activity_home));
                if (null == mHomeFragmet) {
                    mHomeFragmet = MainHomeFragment.getInstance();
                    ft.add(R.id.container, mHomeFragmet, MainHomeFragment.class.getName());
                } else {
                    ft.show(mHomeFragmet);
                }
                break;
            case FRAGMENT_MOVIE:
                toolbar.setTitle(getString(R.string.main_activity_movie));
                if (null == mMovieFragment) {
                    mMovieFragment = MainMovieFragment.getInstance();
                    ft.add(R.id.container, mMovieFragment, MainMovieFragment.class.getName());
                } else {
                    ft.show(mMovieFragment);
                }
                break;
            case FRAGMENT_TALLY:
                toolbar.setTitle(getString(R.string.main_activity_tally));
                if (null == mTallyFragment) {
                    mTallyFragment = MainTallyFragment.getInstance();
                    ft.add(R.id.container, mTallyFragment, MainTallyFragment.class.getName());
                } else {
                    ft.show(mTallyFragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    private void doubleClick(int index) {

    }

    private void hideFragment(FragmentTransaction ft) {
        if (null == ft) {
            return;
        }
        if (mHomeFragmet != null) {
            ft.hide(mHomeFragmet);
        }
        if (null != mMovieFragment) {
            ft.hide(mMovieFragment);
        }
        if (null != mTallyFragment) {
            ft.hide(mTallyFragment);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - exitTime < 2000) {
                super.onBackPressed();
            } else {
                CustomToastUtility.makeTextWarn(getString(R.string.app_exit));
                exitTime = currentTime;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            // 添加记账或者记录todo

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.main_navigation_menu_switch:
                startActivity(SwitchThemeActivity.getIntent(MainActivity.this));
                return false;
            case R.id.main_navigation_menu_setting:
                startActivity(SettingActivity.getIntent(MainActivity.this));
                return false;
            case R.id.main_navigation_menu_share:
                startActivity(ShareActivity.getIntent(MainActivity.this));
                return false;
        }
        return false;
    }
}
