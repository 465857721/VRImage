package com.android11.vrimage.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android11.vrimage.R;
import com.android11.vrimage.login.event.LoginEvent;
import com.android11.vrimage.main.AboutActivity;
import com.android11.vrimage.main.BaseFragment;
import com.android11.vrimage.utils.Tools;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends BaseFragment {

    @BindView(R.id.ivhead)
    CircleImageView ivhead;
    @BindView(R.id.tv_name)
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        tvName.setText(spu.getName());
        Glide.with(this).load(spu.getHeadurl()).into(ivhead);
    }

    @OnClick({R.id.ll_comment, R.id.ll_send, R.id.ll_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_comment:
                Tools.goMarket(getActivity());
                break;
            case R.id.ll_send:
                joinQQGroup();
                break;
            case R.id.ll_about:
                Intent about = new Intent(getActivity(), AboutActivity.class);
                startActivity(about);
                break;
        }
    }
    public boolean joinQQGroup() {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "a7kpL7ND9wz0t8XiqUt7"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            Tools.toastInBottom(getActivity(), "未安装手Q或安装的版本不支持");
            return false;
        }
    }
}
