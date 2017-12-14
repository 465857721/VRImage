package com.android11.vrimage.guide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android11.vrimage.R;
import com.android11.vrimage.guide.fragment.F1;
import com.android11.vrimage.guide.fragment.F2;
import com.android11.vrimage.guide.fragment.F3;
import com.android11.vrimage.main.BaseActivity;
import com.android11.vrimage.main.HomeFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GuideActivity extends BaseActivity {
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.tv_index1)
    TextView tvIndex1;
    @BindView(R.id.tv_index2)
    TextView tvIndex2;
    @BindView(R.id.tv_index3)
    TextView tvIndex3;
    private List<Fragment> list_fragment = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gulid);
        ButterKnife.bind(this);
        initViw();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initViw() {
        F1 f1 = new F1();
        F2 f2 = new F2();
        F3 f3 = new F3();
        list_fragment.add(f1);
        list_fragment.add(f2);
        list_fragment.add(f3);

        vp.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), list_fragment));

        vp.setCurrentItem(0);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tvIndex1.setBackgroundResource(R.drawable.bg_index_select);
                        tvIndex2.setBackgroundResource(R.drawable.bg_index_unselect);
                        tvIndex3.setBackgroundResource(R.drawable.bg_index_unselect);
                        break;
                    case 1:
                        tvIndex1.setBackgroundResource(R.drawable.bg_index_unselect);
                        tvIndex2.setBackgroundResource(R.drawable.bg_index_select);
                        tvIndex3.setBackgroundResource(R.drawable.bg_index_unselect);
                        break;
                    case 2:
                        tvIndex1.setBackgroundResource(R.drawable.bg_index_unselect);
                        tvIndex2.setBackgroundResource(R.drawable.bg_index_unselect);
                        tvIndex3.setBackgroundResource(R.drawable.bg_index_select);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
