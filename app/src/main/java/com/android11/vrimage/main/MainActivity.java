package com.android11.vrimage.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MenuItem;

import com.android11.vrimage.R;
import com.android11.vrimage.find.FindFragment;
import com.android11.vrimage.home.HomeFragment;
import com.android11.vrimage.login.LoginActivity;
import com.android11.vrimage.login.event.LoginEvent;
import com.android11.vrimage.my.MyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.navigation)
    BottomNavigationView navigation;

    private List<Fragment> list_fragment = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vp.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    vp.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    if (TextUtils.isEmpty(spu.getName())) {
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                    } else {
                        vp.setCurrentItem(2);
                    }


                    return true;
            }
            return false;
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        vp.setOffscreenPageLimit(3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        vp.setCurrentItem(2);
    }
    private void initView() {
        HomeFragment f1 = new HomeFragment();
        FindFragment f2 = new FindFragment();
        MyFragment f3 = new MyFragment();
        list_fragment.add(f1);
        list_fragment.add(f2);
        list_fragment.add(f3);

        vp.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), list_fragment));

        vp.setCurrentItem(1);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
