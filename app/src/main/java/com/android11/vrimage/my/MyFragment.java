package com.android11.vrimage.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android11.vrimage.R;
import com.android11.vrimage.login.event.LoginEvent;
import com.android11.vrimage.main.BaseFragment;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends BaseFragment {

    @Bind(R.id.ivhead)
    CircleImageView ivhead;
    @Bind(R.id.tv_name)
    TextView tvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_my, null);
        ButterKnife.bind(this, v);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        initView();
        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        tvName.setText(spu.getName());
        Glide.with(this).load(spu.getHeadurl()).dontAnimate().into(ivhead);
    }
}
